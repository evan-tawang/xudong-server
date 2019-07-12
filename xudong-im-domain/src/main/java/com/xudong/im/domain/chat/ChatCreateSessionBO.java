package com.xudong.im.domain.chat;

public class ChatCreateSessionBO {

    private String sessionId;
    private String visitorIp;//访客ip
    private String visitorId;//访客id
    private String visitorName;//访客名称
    private String visitorAccount;//访客账户
    private Boolean visitorIdRandom;//访客id 是否随机

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getVisitorIp() {
        return visitorIp;
    }

    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorAccount() {
        return visitorAccount;
    }

    public void setVisitorAccount(String visitorAccount) {
        this.visitorAccount = visitorAccount;
    }

    public Boolean getVisitorIdRandom() {
        return visitorIdRandom;
    }

    public void setVisitorIdRandom(Boolean visitorIdRandom) {
        this.visitorIdRandom = visitorIdRandom;
    }
}
