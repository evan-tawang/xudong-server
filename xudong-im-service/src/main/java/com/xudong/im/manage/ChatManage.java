package com.xudong.im.manage;

import com.xudong.core.util.RandomUtil;
import com.xudong.core.util.UUIDUtil;
import com.xudong.im.data.mongo.ChatRecordRepository;
import com.xudong.im.data.mongo.ChatSessionRepository;
import com.xudong.im.domain.chat.*;
import com.xudong.core.websocket.WebSocketToClientUtil;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.UserTypeEnum;
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

    public void sendMsg(ChatDTO chatDTO, StaffAgent agent){
        Assert.notNull(chatDTO.getContent(),"聊天内容不能为空");

        ChatSession chatSession = null;
        if(StringUtils.isEmpty(chatDTO.getSessionId())){
            Assert.notNull(chatDTO.getReceiveId(),"接收者不能为空");

            String serviceId = null, visitorId = null;

            if(UserTypeEnum.SERVICE.getValue().equals(agent.getUserType())){
                serviceId = String.valueOf(agent.getId());
                visitorId = chatDTO.getReceiveId();
            } else {
                visitorId = String.valueOf(agent.getId());
                serviceId = chatDTO.getReceiveId();
            }
            chatSession = createSession(serviceId,visitorId);
        } else {
            chatSession = chatSessionRepository.load(chatDTO.getSessionId());
            Assert.notNull(chatSession, "sessionId不正确");
        }

        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setSendUserType(agent.getUserType());
        chatRecord.setContent(chatDTO.getContent());
        chatRecord.setSessionId(chatDTO.getSessionId());
        chatRecord.setServiceId(chatSession.getServiceId());
        chatRecord.setVisitorId(chatSession.getVisitorId());

        chatRecordRepository.insert(chatRecord);

        String receiveId = UserTypeEnum.SERVICE.getValue().equals(agent.getUserType()) ? chatSession.getVisitorId() : chatSession.getServiceId();
        webSocketToClientUtil.sendMsg(receiveId, chatDTO.getContent());
    }

    public ChatSession createSession(StaffAgent agent,String remoteAddr) {
        String serviceId = distributionService();

        if (StringUtils.isEmpty(serviceId)) {
            return null;
        }

        String visitorId = agent == null ? UUIDUtil.nameUUIDFromBytes(remoteAddr) : agent.getId() + "";
        ChatSession session = createSession(serviceId, visitorId);
        return new ChatSession(session.getId(), serviceId, visitorId);
    }

    private ChatSession createSession(String serviceId, String visitorId){
        return createSession(serviceId, visitorId, null);
    }

    private ChatSession createSession(String serviceId, String visitorId, String visitorIp){

        ChatSessionQuery chatSessionQuery = new ChatSessionQuery();
//        chatSessionQuery.setServiceId(serviceId);
        chatSessionQuery.setVisitorId(visitorId);
        List<ChatSession> chatSessions = chatSessionRepository.queryList(chatSessionQuery);

        if(!CollectionUtils.isEmpty(chatSessions)){
            return chatSessions.get(0);
        }

        ChatSession chatSession = new ChatSession();
        chatSession.setServiceId(serviceId);
        chatSession.setVisitorId(visitorId);
        chatSession.setVisitorIp(visitorIp);

        chatSessionRepository.insert(chatSession);

        return chatSession;
    }

    /**
     * 分配客服
     * @return
     */
    private String distributionService(){
        return RandomUtil.randomId() + "";
    }

    public PageResult<ChatRecord> queryPage(ChatRecordQuery chatRecordQuery) {
        return chatRecordRepository.queryPage(chatRecordQuery);
    }
}
