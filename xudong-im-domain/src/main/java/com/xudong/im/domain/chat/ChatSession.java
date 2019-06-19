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
