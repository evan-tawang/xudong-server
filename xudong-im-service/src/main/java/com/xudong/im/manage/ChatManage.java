package com.xudong.im.manage;

import com.xudong.core.util.RandomUtil;
import com.xudong.core.websocket.WebSocketToClientUtil;
import com.xudong.im.cache.ChatSessionCache;
import com.xudong.im.cache.ChatWaitConnectQueueCache;
import com.xudong.im.cache.ChatWaitVisitorCache;
import com.xudong.im.data.mongo.ChatRecordRepository;
import com.xudong.im.data.mongo.ChatSessionRepository;
import com.xudong.im.domain.chat.*;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.enums.ChatContentTypeEnum;
import com.xudong.im.enums.UserTypeEnum;
import com.xudong.im.service.BlacklistService;
import com.xudong.im.service.SensitiveWordService;
import com.xudong.im.session.UserAgentSession;
import org.evanframework.dto.OperateResult;
import org.evanframework.dto.PageResult;
import org.evanframework.exception.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 聊天
 */
@Service
public class ChatManage {
    private static final int DEFAULT_HIS_COUNT = 30;

    @Autowired
    private WebSocketToClientUtil webSocketToClientUtil;
    @Autowired
    private ChatSessionRepository chatSessionRepository;
    @Autowired
    private ChatRecordRepository chatRecordRepository;
    @Autowired
    private SensitiveWordService sensitiveWordService;
    @Autowired
    private ChatSessionCache chatSessionCache;
    @Autowired
    private ChatWaitConnectQueueCache chatWaitConnectQueueCache;
    @Autowired
    private ChatWaitVisitorCache chatWaitVisitorCache;
    @Autowired
    private UserAgentSession userAgentSession;
    @Autowired
    private BlackListManage blackListManage;
    @Autowired
    private BlacklistService blacklistService;


    public ChatRecord sendMsg(ChatRecordDTO dto, UserAgent agent){
        Assert.notNull(dto.getContent(), "聊天内容不能为空");
        Assert.notNull(dto.getSessionId(), "sessionId不能为空");

        ChatSession chatSession = chatSessionRepository.load(dto.getSessionId());

        //访客
//        if(agent == null){
//
//            ChatCreateSessionBO bo = new ChatCreateSessionBO();
//            bo.setVisitorId(dto.getReceiveId());
//            bo.setVisitorAccount(dto.getReceiveAccount());
//            bo.setVisitorName(dto.getReceiveName());
//
//            chatSession = saveOrUpdate(null, bo);
//        }

        Assert.notNull(chatSession, "sessionId不正确");

        updateTime(chatSession.getId(), new Date(), null);

        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setSendUserType(agent != null ? agent.getUserType() : UserTypeEnum.VISITOR.getValue());
        chatRecord.setContent(dto.getContent());
        chatRecord.setSessionId(dto.getSessionId());
        chatRecord.setStaffId(chatSession.getStaffId());
        chatRecord.setVisitorId(chatSession.getVisitorId());
        chatRecord.setContentType(dto.getContentType() != null ? dto.getContentType() : ChatContentTypeEnum.TEXT.getValue());

        if(agent != null && UserTypeEnum.STAFF.getValue().equals(agent.getUserType())){
            chatRecord.setRead(true);
        }

        if(ChatContentTypeEnum.TEXT.getValue().equals(chatRecord.getContentType())){
            String filteredContent = sensitiveWordService.filter(dto.getContent());
            chatRecord.setContent(filteredContent);
        }

        chatRecordRepository.insert(chatRecord);

        chatRecord.setVisitorName(chatSession.getVisitorName());

        webSocketToClientUtil.sendMsg(chatRecord);
        return chatRecord;
    }


    public OperateResult createSession(ChatCreateSessionDTO dto) {
        String staffId = allocateStaff();

        if (StringUtils.isEmpty(staffId)) {
            return null;
        }

        if(blacklistService.isBlock(dto.getConnectAccount())
                || blacklistService.isBlock(dto.getConnectId())
                || blacklistService.isBlock(dto.getConnectIp())
                ){
            throw new ServiceException("BLACK_ERROR","您已被拉入黑名单！");
        }

        String visitorId = dto.getConnectId();

        ChatSession session = createSession(staffId, dto);

        if(session == null){
            chatWaitConnectQueueCache.add(visitorId);
            chatWaitVisitorCache.put(visitorId, dto);
            return OperateResult.create("STAFF_BUSY", "客服繁忙中，请稍后", visitorId);
        } else {

            // 缓存会话
            if(chatSessionCache.put(staffId, session.getId())){
                webSocketToClientUtil.allocate(staffId, visitorId, dto.getConnectName(), session.getId());
            }

            ChatSessionVO chatSessionVO = new ChatSessionVO(session.getId(), staffId, visitorId);
            chatSessionVO.setOtherSideName(getUserName(staffId, UserTypeEnum.STAFF.getValue()));

            return OperateResult.create(chatSessionVO);
        }
    }

    public void disconnect(String sessionId) {
        Assert.notNull(sessionId, "会话id不能为空");

        ChatSession chatSession = chatSessionRepository.load(sessionId);

        Assert.notNull(chatSession, "会话不存在");

        webSocketToClientUtil.disconnect(chatSession.getStaffId(), chatSession.getVisitorId(), chatSession.getId());

        // 缓存会话
        chatSessionCache.remove(chatSession.getStaffId(), chatSession.getId());

        updateTime(chatSession.getId(), null, new Date());

        String visitorId = chatWaitConnectQueueCache.pop();
        if(StringUtils.isEmpty(visitorId)){
            return;
        }

        ChatCreateSessionDTO dto = chatWaitVisitorCache.get(visitorId);

        if(dto == null){
            dto = new ChatCreateSessionDTO(visitorId);
        }

        OperateResult<ChatSessionVO> session = createSession(dto);
        webSocketToClientUtil.startSession(session.getData());
    }

    public List<ChatSessionVO> connected(UserAgent agent) {
        if(agent == null){
            return new ArrayList<>();
        }

        Set<String> sessionIds = chatSessionCache.get(agent.getId());
        if(CollectionUtils.isEmpty(sessionIds)){
            return new ArrayList<>();
        }

        ChatSessionQuery chatSessionQuery = new ChatSessionQuery();
        chatSessionQuery.setIdArray(sessionIds);
        List<ChatSession> chatSessions = chatSessionRepository.queryList(chatSessionQuery);

        List<ChatSessionVO> voList = new ArrayList<>();

        for (ChatSession chatSession: chatSessions) {
            ChatSessionVO chatSessionVO = new ChatSessionVO();
            BeanUtils.copyProperties(chatSession, chatSessionVO);

            chatSessionVO.setOtherSideName(chatSession.getVisitorName());

            voList.add(chatSessionVO);
        }

        return voList;
    }

    public void logout(String userId,Integer userType) {
        if(StringUtils.isEmpty(userId)){
            return;
        }

        if(UserTypeEnum.STAFF.getValue().equals(userType)){
            chatSessionCache.remove(userId);
        } else {
            ChatSessionQuery chatSessionQuery = new ChatSessionQuery();
            chatSessionQuery.setVisitorId(userId);
            List<ChatSession> chatSessionList = chatSessionRepository.queryList(chatSessionQuery);
            if (CollectionUtils.isEmpty(chatSessionList)) {
                return;
            }
            chatSessionCache.remove(chatSessionList.get(0).getStaffId(), userId);
        }
    }

    private ChatSession createSession(String staffId, ChatCreateSessionDTO dto) {

        ChatCreateSessionBO bo = new ChatCreateSessionBO();
        bo.setSessionId(dto.getSessionId());
        bo.setVisitorIdRandom(dto.getVisitorIdRandom());

        bo.setVisitorId(dto.getConnectId());
        bo.setVisitorIp(dto.getConnectIp());
        bo.setVisitorName(dto.getConnectName());
        bo.setVisitorAccount(dto.getConnectAccount());

        return saveOrUpdate(staffId, bo);
    }

    private ChatSession saveOrUpdate(String staffId, ChatCreateSessionBO bo) {

        ChatSession chatSession = null;

        //通过sessionId 查找
        if(!StringUtils.isEmpty(bo.getSessionId())){
            chatSession = chatSessionRepository.load(bo.getSessionId());
        }

        // 通过访问者id查找
        if(chatSession == null){
            ChatSessionQuery chatSessionQuery = new ChatSessionQuery();
            chatSessionQuery.setVisitorId(bo.getVisitorId());
            List<ChatSession> chatSessions = chatSessionRepository.queryList(chatSessionQuery);

            if (!CollectionUtils.isEmpty(chatSessions)) {
                chatSession = chatSessions.get(0);
            }
        }

        String sessionId = null, visitorId = bo.getVisitorId(), visitorIp = bo.getVisitorIp(), visitorName = bo.getVisitorName(), visitorAccount = bo.getVisitorAccount();
        Boolean isVisitorIdRandom = null;
        if (chatSession != null) {
            sessionId = chatSession.getId();

            visitorIp = compared(chatSession.getVisitorIp(), bo.getVisitorIp());
            visitorName = compared(chatSession.getVisitorName(), bo.getVisitorName());
            visitorAccount = compared(chatSession.getVisitorAccount(), bo.getVisitorAccount());

            visitorId = compared(chatSession.getVisitorId(), bo.getVisitorId());

            if(!StringUtils.isEmpty(visitorId)){
                if(chatSession.isVisitorIdRandom()){  // 随机id被更新为有效id
                    isVisitorIdRandom = false;
                } else {
                    sessionId = null;  // visitorId 有变化且有sessionId 需重新创建session 非更新
                }
            }
        } else {
            isVisitorIdRandom = bo.getVisitorIdRandom();
        }

        ChatSession session = new ChatSession(sessionId);
        session.setStaffId(staffId);
        session.setVisitorId(visitorId);
        session.setVisitorIp(visitorIp);
        session.setVisitorName(visitorName);
        session.setVisitorAccount(visitorAccount);
        session.setVisitorIdRandom(isVisitorIdRandom);

        if (StringUtils.isEmpty(sessionId)) {
            session.setConnectStartTime(new Date());
        }

        chatSessionRepository.save(session);

        return session;
    }

    public void read(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return;
        }
        chatRecordRepository.updateIsRead(sessionId);
    }

    public Long queryNonReadCount(ChatRecordQuery chatRecordQuery, UserAgent userAgent) {
        if (userAgent == null || !UserTypeEnum.STAFF.getValue().equals(userAgent.getUserType())) {
            return 0L;
        }
        chatRecordQuery.setRead(false);
        return chatRecordRepository.queryCount(chatRecordQuery);
    }

    public void pullBlack(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return;
        }

        ChatSession chatSession = chatSessionRepository.load(sessionId);

        if(chatSession == null){
            return;
        }

        blackListManage.setBlock(chatSession.getVisitorId());
        blackListManage.setBlock(chatSession.getVisitorIp());
    }

    public PageResult<ChatRecord> queryPage(ChatRecordQuery chatRecordQuery) {
        Assert.notNull(chatRecordQuery.getSessionId(), "会话id不能为空");

        PageResult<ChatRecord> result = chatRecordRepository.queryPage(chatRecordQuery);
        for (ChatRecord record : result.getData()){
            if(ChatContentTypeEnum.TEXT.getValue().equals(record.getContentType())){
                record.setContent(sensitiveWordService.filter(record.getContent()));
            }
        }
        return result;
    }

    public List<ChatRecord> history(String sessionId, Integer currentCount) {
        Assert.notNull(sessionId, "会话id不能为空");
        List<ChatRecord> list = chatRecordRepository.history(sessionId, currentCount);

//        for (ChatRecord record : list) {
//            if (ChatContentTypeEnum.TEXT.getValue().equals(record.getContentType())) {
//                record.setContent(sensitiveWordService.filter(record.getContent()));
//            }
//        }

        Collections.reverse(list);
        return list;
    }

    /**
     * 分配客服
     * @return
     */
    private String allocateStaff(){
        List<StaffAgent> staffs = userAgentSession.getOnlineStaffs();

        if (CollectionUtils.isEmpty(staffs)) {
            return null;
        }

        Map<String, List<String>> sessionMap = chatSessionCache.getAll();

        // 第一种 随机选
        if(CollectionUtils.isEmpty(sessionMap)){
            return staffs.get(RandomUtil.randomInt(staffs.size())).getId();
        }

        // 第二种 获取最少的
        List<String> minChatStaffs = getMinChatStaff( staffs,sessionMap);
        return minChatStaffs.get(RandomUtil.randomInt(minChatStaffs.size()));
    }

    private void updateTime(String id,Date connectStartTime,Date connectEndTime){

        if(StringUtils.isEmpty(id)){
            return;
        }

        if(connectStartTime == null && connectEndTime == null){
            return;
        }

        ChatSession chatSession = new ChatSession(id);
        chatSession.setConnectStartTime(connectStartTime);
        chatSession.setConnectEndTime(connectEndTime);
        chatSessionRepository.update(chatSession);
    }

    private static List<String> getMinChatStaff(List<StaffAgent> staffs,Map<String, List<String>> sessionMap) {
        if(CollectionUtils.isEmpty(sessionMap)){
            return new ArrayList<>(0);
        }

        List<String> staffIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(staffs)){
            staffIds = staffs.stream().map(StaffAgent::getId).collect(Collectors.toList());
        }

        Integer min = null;
        Map<Integer, List<String>> map = new HashMap<>();

        for (String key : sessionMap.keySet()) {
            if(staffIds.contains(key)){
                staffIds.remove(key);
            }
            int size = sessionMap.get(key).size();

            if (min == null) {
                min = size;
            } else if(size > min){
                continue;
            } else if (size < min) {
                map.remove(min);
                min = size;
            }

            List<String> values = map.get(min);
            if (values == null) {
                values = new ArrayList<>();
            }
            values.add(key);
            map.put(min, values);
        }

        if(CollectionUtils.isEmpty(staffIds)){
            return map.get(min);
        }
        List<String> values = map.get(0);
        if(values == null){
            return staffIds;
        } else {
            values.addAll(staffIds);
        }
        return values;
    }

    private String getUserName(String userId, Integer userType) {
        UserAgent userAgent = userAgentSession.get(userId, userType);
        return userAgent != null ? userAgent.getUserName() : "";
    }

    private static String compared(String oldStr, String newStr) {
        if (StringUtils.isEmpty(oldStr)) {
            return newStr;
        }
        if (StringUtils.isEmpty(newStr)) {
            return null;
        }
        return oldStr.equals(newStr) ? null : newStr;
    }
}
