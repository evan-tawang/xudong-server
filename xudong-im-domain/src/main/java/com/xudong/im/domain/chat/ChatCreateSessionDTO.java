package com.xudong.im.domain.chat;

public class ChatCreateSessionDTO {

    public ChatCreateSessionDTO() {
    }

    public ChatCreateSessionDTO(String connectId) {
        this.connectId = connectId;
    }

    private String sessionId;
    private String connectId;
    private String connectIp;
    private String connectName;
    private String connectAccount;
    private Boolean visitorIdRandom;//访客id 是否随机

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getConnectId() {
        return connectId;
    }

    public void setConnectId(String connectId) {
        this.connectId = connectId;
    }

    public String getConnectIp() {
        return connectIp;
    }

    public void setConnectIp(String connectIp) {
        this.connectIp = connectIp;
    }

    public String getConnectName() {
        return connectName;
    }

    public void setConnectName(String connectName) {
        this.connectName = connectName;
    }

    public String getConnectAccount() {
        return connectAccount;
    }

    public void setConnectAccount(String connectAccount) {
        this.connectAccount = connectAccount;
    }

    public Boolean getVisitorIdRandom() {
        return visitorIdRandom;
    }

    public void setVisitorIdRandom(Boolean visitorIdRandom) {
        this.visitorIdRandom = visitorIdRandom;
    }
}
