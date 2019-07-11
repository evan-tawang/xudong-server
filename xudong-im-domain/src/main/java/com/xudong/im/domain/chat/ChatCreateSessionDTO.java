package com.xudong.im.domain.chat;

public class ChatCreateSessionDTO {

    public ChatCreateSessionDTO() {
    }

    public ChatCreateSessionDTO(String connectId) {
        this.connectId = connectId;
    }

    private String connectId;
    private String connectIp;
    private String connectName;
    private String connectAccount;

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
}
