package com.xudong.im.domain.chat;

import org.springframework.data.annotation.Id;

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
    private String visitorIp;//访客ip
    private String visitorId;//访客id
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
