package com.xudong.im.manage;

import com.xudong.core.util.RandomUtil;
import com.xudong.core.util.UUIDUtil;
import com.xudong.im.cache.ChatSessionCache;
import com.xudong.im.cache.ChatWaitConnectQueueCache;
import com.xudong.im.constant.CommonConstant;
import com.xudong.im.data.mongo.ChatRecordRepository;
import com.xudong.im.data.mongo.ChatSessionRepository;
import com.xudong.im.domain.chat.*;
import com.xudong.core.websocket.WebSocketToClientUtil;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.enums.ChatContentTypeEnum;
import com.xudong.im.enums.UserTypeEnum;
import com.xudong.im.service.SensitiveWordService;
import com.xudong.im.session.UserAgentSession;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 聊天
 */
@Service
public class ChatManage {

    private static final int MAX_CHAT_SIZE = 20;

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
    private UserAgentSession userAgentSession;

    public ChatRecord sendMsg(ChatDTO chatDTO, UserAgent agent){
        Assert.notNull(chatDTO.getContent(),"聊天内容不能为空");

        ChatSession chatSession = null;
        if(StringUtils.isEmpty(chatDTO.getSessionId())){
            Assert.notNull(chatDTO.getReceiveId(),"接收者不能为空");

            String staffId = null, visitorId = null;

            if(UserTypeEnum.STAFF.getValue().equals(agent.getUserType())){
                staffId = String.valueOf(agent.getId());
                visitorId = chatDTO.getReceiveId();
            } else {
                visitorId = String.valueOf(agent.getId());
                staffId = chatDTO.getReceiveId();
            }
            chatSession = createSession(staffId,visitorId);
        } else {
            chatSession = chatSessionRepository.load(chatDTO.getSessionId());
            Assert.notNull(chatSession, "sessionId不正确");
        }

        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setSendUserType(agent != null ? agent.getUserType() : UserTypeEnum.VISITOR.getValue());
        chatRecord.setContent(chatDTO.getContent());
        chatRecord.setSessionId(chatDTO.getSessionId());
        chatRecord.setStaffId(chatSession.getStaffId());
        chatRecord.setVisitorId(chatSession.getVisitorId());
        chatRecord.setContentType(chatDTO.getContentType() != null ? chatDTO.getContentType() : ChatContentTypeEnum.TEXT.getValue());

        if(agent != null && UserTypeEnum.STAFF.getValue().equals(agent.getUserType())){
            chatRecord.setRead(true);
        }

        chatRecordRepository.insert(chatRecord);
        if(ChatContentTypeEnum.TEXT.getValue().equals(chatRecord.getContentType())){
            String filteredContent = sensitiveWordService.filter(chatDTO.getContent());
            chatRecord.setContent(filteredContent);
        }

        webSocketToClientUtil.sendMsg(chatRecord);
        return chatRecord;
    }

    public ChatSession createSession(UserAgent agent, String remoteAddr) {
        String staffId = allocateStaff();

        if (StringUtils.isEmpty(staffId)) {
            return null;
        }

        String visitorId = agent == null ? UUIDUtil.nameUUIDFromBytes(remoteAddr) : agent.getId() + "";


        ChatSession session = createSession(staffId, visitorId);

        if(session == null){
            chatWaitConnectQueueCache.leftPush(visitorId);
            return null;
        } else {

            // 缓存会话
            if(chatSessionCache.put(staffId, session.getId())){
                webSocketToClientUtil.allocate(staffId, visitorId, session.getId());
            }

            return new ChatSession(session.getId(), staffId, visitorId);
        }
    }

    public void disconnect(String sessionId) {
        Assert.notNull(sessionId, "会话id不能为空");

        ChatSession chatSession = chatSessionRepository.load(sessionId);

        Assert.notNull(chatSession, "会话不存在");

        webSocketToClientUtil.disconnect(chatSession.getStaffId(), chatSession.getVisitorId(), chatSession.getId());

        // 缓存会话
        chatSessionCache.remove(chatSession.getStaffId(), chatSession.getId());
    }

    public List<ChatSession> connected(UserAgent agent) {
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
        return chatSessions;
    }

    private ChatSession createSession(String staffId, String visitorId){
        return createSession(staffId, visitorId, null);
    }

    private ChatSession createSession(String staffId, String visitorId, String visitorIp){

        ChatSessionQuery chatSessionQuery = new ChatSessionQuery();
//        chatSessionQuery.setServiceId(serviceId);
        chatSessionQuery.setVisitorId(visitorId);
        List<ChatSession> chatSessions = chatSessionRepository.queryList(chatSessionQuery);

        if(!CollectionUtils.isEmpty(chatSessions)){
            return chatSessions.get(0);
        }

        ChatSession chatSession = new ChatSession();
        chatSession.setStaffId(staffId);
        chatSession.setVisitorId(visitorId);
        chatSession.setVisitorIp(visitorIp);

        chatSessionRepository.insert(chatSession);

        return chatSession;
    }

    public void read(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return;
        }
        chatRecordRepository.updateIsRead(sessionId);
    }

    public PageResult<ChatRecord> queryPage(ChatRecordQuery chatRecordQuery) {
        return chatRecordRepository.queryPage(chatRecordQuery);
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

    public static void main(String[] args) {
        Map<String, List<String>> sessionMap = new HashMap<>();

        ArrayList<String> list = new ArrayList<>();
        list.add("1111");
//        list.add("22121");
//        list.add("11112");
//        list.add("1111241");

        sessionMap.put("1",list);

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("1111");
        list1.add("22121");
        list1.add("11112");

        sessionMap.put("2",list1);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("1111");
        list2.add("22121");

        sessionMap.put("3",list2);

//        System.out.println(getMinChatStaff(sessionMap));
    }
}
