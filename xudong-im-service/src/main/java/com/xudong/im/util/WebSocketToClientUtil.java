package com.xudong.im.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class WebSocketToClientUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketToClientUtil.class);

    @Autowired(required = false)
    private SimpMessageSendingOperations simpMessageSendingOperations;

    public void newMsg(String receiveId, String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> websocket to client, receiveId:{},message:{}", receiveId, message);
        }

        simpMessageSendingOperations.convertAndSendToUser(receiveId + "", "/newMsg", message);
    }
}
