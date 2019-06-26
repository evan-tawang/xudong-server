package com.xudong.core.websocket;

import com.xudong.im.domain.chat.ChatRecord;
import com.xudong.im.enums.UserTypeEnum;
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
     * @param chatRecord
     */
    public void sendMsg( ChatRecord chatRecord) {
        if (chatRecord == null) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> WebSocket to client, chatRecord:{}",  chatRecord);
        }
//        Integer receiveUserType = UserTypeEnum.VISITOR.getValue().equals(chatRecord.getSendUserType()) ? UserTypeEnum.STAFF.getValue() : UserTypeEnum.VISITOR.getValue();

        simpMessageSendingOperations.convertAndSendToUser(chatRecord.getSessionId() + "-" + chatRecord.getSendUserType(), "/receiveMsg", chatRecord);
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
    public void allocate(String staffId, String visitorId,String otherSideName, String sessionId) {
        if (StringUtils.isEmpty(visitorId) || StringUtils.isEmpty(staffId)) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> WebSocket to client, visitorId:{}", visitorId);
        }

        Map<String,String> params = new HashMap<>();
        params.put("id", sessionId);
        params.put("visitorId", visitorId);
        params.put("otherSideName", otherSideName);

        simpMessageSendingOperations.convertAndSendToUser(staffId, "/allocate", params);
    }

    /**
     * 访客断开连接
     * @param visitorId
     * @param staffId
     * @param sessionId
     */
    public void disconnect(String staffId, String visitorId, String sessionId) {
        if (StringUtils.isEmpty(visitorId) || StringUtils.isEmpty(staffId)) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> WebSocket to client, visitorId:{}", visitorId);
        }

        Map<String,String> params = new HashMap<>();
        params.put("id", sessionId);
        params.put("visitorId", visitorId);
        params.put("staffId", staffId);

        simpMessageSendingOperations.convertAndSendToUser(sessionId, "/disconnect", params);
    }
}
