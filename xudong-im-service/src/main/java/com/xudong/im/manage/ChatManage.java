package com.xudong.im.manage;

import com.xudong.im.data.mongo.ChatRecordRepository;
import com.xudong.im.data.mongo.ChatSessionRepository;
import com.xudong.im.domain.chat.*;
import com.xudong.im.util.WebSocketToClientUtil;
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

    public void add(ChatDTO chatDTO){
        Assert.notNull(chatDTO.getContent(),"聊天内容不能为空");

        if(StringUtils.isEmpty(chatDTO.getSessionId())){
            Assert.notNull(chatDTO.getServiceId(),"客服id不能为空");
            Assert.notNull(chatDTO.getVisitorId(),"访客id不能为空");

            ChatSessionQuery chatSessionQuery = new ChatSessionQuery();
            chatSessionQuery.setServiceId(chatDTO.getServiceId());
            chatSessionQuery.setVisitorId(chatDTO.getVisitorId());
            List<ChatSession> chatSessions = chatSessionRepository.queryList(chatSessionQuery);
            if(CollectionUtils.isEmpty(chatSessions)){
                chatDTO.setSessionId(createSession(chatDTO.getServiceId(),chatDTO.getVisitorId()));;
            } else {
                chatDTO.setSessionId(chatSessions.get(0).getId());;
            }
        } else {
            ChatSession chatSession = chatSessionRepository.load(chatDTO.getSessionId());
            Assert.notNull(chatSession, "sessionId不正确");

            chatDTO.setServiceId(chatDTO.getServiceId());
            chatDTO.setVisitorId(chatSession.getVisitorId());
        }

        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setContent(chatDTO.getContent());
        chatRecord.setSessionId(chatDTO.getSessionId());

        chatRecordRepository.insert(chatRecord);

        String receiveId = ChatRecordFromEnum.SERVICE.getValue().equals(chatDTO.getForm()) ? chatDTO.getServiceId() : chatDTO.getVisitorId();
        webSocketToClientUtil.newMsg(receiveId, chatDTO.getContent());
    }

    public String createSession(String serviceId, String visitorId){
        return createSession(serviceId,visitorId);
    }

    public String createSession(String serviceId, String visitorId, String visitorIp){

        ChatSession chatSession = new ChatSession();
        chatSession.setServiceId(serviceId);
        chatSession.setVisitorId(visitorId);
        chatSession.setVisitorIp(visitorIp);

        chatSessionRepository.insert(chatSession);

        return chatSession.getId();
    }

    public PageResult<ChatRecord> queryPage(ChatRecordQuery chatRecordQuery) {
        return chatRecordRepository.queryPage(chatRecordQuery);
    }
}
