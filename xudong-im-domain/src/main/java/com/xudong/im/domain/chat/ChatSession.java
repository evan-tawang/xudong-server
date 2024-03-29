package com.xudong.im.domain.chat;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天会话
 */
public class ChatSession  implements Serializable {

    public ChatSession() {
    }

    public ChatSession(String id) {
        this.id = id;
    }

    public ChatSession(String id, String visitorId, String staffId) {
        this.id = id;
        this.visitorId = visitorId;
        this.staffId = staffId;
    }

    @Id
    private String id;

    private Boolean visitorIdRandom;//访客id 是否随机
    private String visitorIp;//访客ip
    private String visitorId;//访客id
    private String visitorName;//访客名称
    private String visitorAccount;//访客账户

    private String staffId; // 员工

    private Integer status; // 连接中
    private Date connectStartTime; // 连接开始时间
    private Date connectEndTime; // 连接结束时间
    private Date gmtCreate;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Transient
    public boolean isVisitorIdRandom() {
        return visitorIdRandom != null && visitorIdRandom;
    }

    public Boolean getVisitorIdRandom() {
        return visitorIdRandom;
    }

    public void setVisitorIdRandom(Boolean visitorIdRandom) {
        this.visitorIdRandom = visitorIdRandom;
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

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getConnectStartTime() {
        return connectStartTime;
    }

    public void setConnectStartTime(Date connectStartTime) {
        this.connectStartTime = connectStartTime;
    }

    public Date getConnectEndTime() {
        return connectEndTime;
    }

    public void setConnectEndTime(Date connectEndTime) {
        this.connectEndTime = connectEndTime;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "ChatSession{" +
                "id='" + id + '\'' +
                ", visitorIp='" + visitorIp + '\'' +
                ", visitorId='" + visitorId + '\'' +
                ", staffId='" + staffId + '\'' +
                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
