package com.xudong.im.manage;

import com.xudong.core.util.RandomUtil;
import com.xudong.core.util.UUIDUtil;
import com.xudong.im.constant.CommonConstant;
import com.xudong.im.data.mongo.ChatRecordRepository;
import com.xudong.im.data.mongo.ChatSessionRepository;
import com.xudong.im.domain.chat.*;
import com.xudong.core.websocket.WebSocketToClientUtil;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.enums.UserTypeEnum;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 聊天
 */
@Service
public class ChatManage {

    @Autowired
    private WebSocketToClientUtil webSocketToClientUtil;
    @Autowired
    private ChatSessionRepository chatSessionRepository;
    @Autowired
    private ChatRecordRepository chatRecordRepository;

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

        chatRecordRepository.insert(chatRecord);

        String receiveId = agent == null || UserTypeEnum.STAFF.getValue().equals(agent.getUserType())
                ? chatSession.getVisitorId() : chatSession.getStaffId();
        webSocketToClientUtil.sendMsg(receiveId, chatRecord);
        return chatRecord;
    }

    public ChatSession createSession(UserAgent agent, String remoteAddr) {
        String staffId = allocateStaff();

        if (StringUtils.isEmpty(staffId)) {
            return null;
        }

        String visitorId = agent == null ? UUIDUtil.nameUUIDFromBytes(remoteAddr) : agent.getId() + "";
        ChatSession session = createSession(staffId, visitorId);

        webSocketToClientUtil.allocate(staffId, visitorId, session.getId());

        return new ChatSession(session.getId(), staffId, visitorId);
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

    /**
     * 分配客服
     * @return
     */
    //TODO: default user
    private String allocateStaff(){
        return CommonConstant.DEFAULT_STAFF_ID;
    }

    public PageResult<ChatRecord> queryPage(ChatRecordQuery chatRecordQuery) {
        return chatRecordRepository.queryPage(chatRecordQuery);
    }
}
