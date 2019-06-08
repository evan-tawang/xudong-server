package com.xudong.im.domain.chat;

import org.evanframework.query.AbstractQueryParam;
import org.evanframework.query.QueryParam;

import java.io.Serializable;

public class ChatRecordQuery extends AbstractQueryParam implements QueryParam, Serializable {

    private String sessionId; //会话id
    private String content;//聊天内容

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
