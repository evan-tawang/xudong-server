package com.xudong.core.websocket;

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
     * @param message
     */
    public void newMsg(String receiveId, String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> websocket to client, receiveId:{},message:{}", receiveId, message);
        }

        simpMessageSendingOperations.convertAndSendToUser(receiveId, "/sendMsg", message);
    }

    /**
     * 用户排队排到后，通知用户
     * @param visitorId
     * @param serviceId
     * @param sessionId
     */
    public void newSession(String visitorId,String serviceId,String sessionId) {
        if (StringUtils.isEmpty(visitorId) || StringUtils.isEmpty(serviceId)) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> websocket to client, visitorId:{}", visitorId);
        }

        Map<String,String> params = new HashMap<>();
        params.put("sessionId",sessionId);
        params.put("serviceId",serviceId);

        simpMessageSendingOperations.convertAndSendToUser(visitorId, "/newSession", params);
    }
}
