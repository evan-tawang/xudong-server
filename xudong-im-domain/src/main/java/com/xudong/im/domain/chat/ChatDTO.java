package com.xudong.im.domain.chat;

import java.io.Serializable;

public class ChatDTO  implements Serializable {

    private String sessionId;//聊天会话id
    private String receiveId;// 接收消息id
    private String content;//
    private Integer contentType;// 聊天类容类型

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }
}