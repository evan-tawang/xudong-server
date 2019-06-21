package com.xudong.im.domain.chat;

import java.io.Serializable;

public class SessionBO implements Serializable {

    public SessionBO() {
    }

    public SessionBO(String staffId, String sessionId) {
        this.staffId = staffId;
        this.sessionId = sessionId;
    }

    private String staffId;
    private String sessionId;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
