package com.xudong.core.websocket;

import com.xudong.im.domain.chat.ChatRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketToClientUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketToClientUtil.class);

    @Autowired(required = false)
    private SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 新消息
     * @param receiveId
     * @param chatRecord
     */
    public void sendMsg(String receiveId, ChatRecord chatRecord) {
        if (chatRecord == null) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> WebSocket to client, receiveId:{},chatRecord:{}", receiveId, chatRecord);
        }

        simpMessageSendingOperations.convertAndSendToUser(receiveId, "/receiveMsg", chatRecord);
    }

    /**
     * 访客排队排到后，通知访客
     * @param visitorId
     * @param staffId
     * @param sessionId
     */
    public void startSession(String visitorId, String staffId, String sessionId) {
        if (StringUtils.isEmpty(visitorId) || StringUtils.isEmpty(staffId)) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> WebSocket to client, visitorId:{}", visitorId);
        }

        Map<String,String> params = new HashMap<>();
        params.put("sessionId",sessionId);
        params.put("staffId",staffId);

        simpMessageSendingOperations.convertAndSendToUser(visitorId, "/startSession", params);
    }

    /**
     * 给客服分配访客
     * @param visitorId
     * @param staffId
     * @param sessionId
     */
    public void allocate(String staffId, String visitorId, String sessionId) {
        if (StringUtils.isEmpty(visitorId) || StringUtils.isEmpty(staffId)) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> WebSocket to client, visitorId:{}", visitorId);
        }

        Map<String,String> params = new HashMap<>();
        params.put("sessionId", sessionId);
        params.put("visitorId", visitorId);

        simpMessageSendingOperations.convertAndSendToUser(staffId, "/allocate", params);
    }
}
