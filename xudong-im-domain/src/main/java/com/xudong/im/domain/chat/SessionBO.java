package com.xudong.im.domain.chat;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionBO sessionBO = (SessionBO) o;
        return Objects.equals(staffId, sessionBO.staffId) &&
                Objects.equals(sessionId, sessionBO.sessionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(staffId, sessionId);
    }
}
