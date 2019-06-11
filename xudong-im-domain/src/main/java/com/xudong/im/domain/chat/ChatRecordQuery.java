package com.xudong.im.domain.chat;

import org.evanframework.query.AbstractQueryParam;
import org.evanframework.query.QueryParam;

import java.io.Serializable;

public class ChatRecordQuery extends AbstractQueryParam implements QueryParam, Serializable {

    private String sessionId; //会话id
    private String connectorId;//连接人id
    private String visitorId; //访客id
    private String staffId; // 员工
    private String content;//聊天内容

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
