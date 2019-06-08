package com.xudong.im.domain.chat;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 聊天会话
 */
public class ChatSession {

    @Id
    private String id;
    private String visitorIp;//访客ip
    private String visitorId;//访客id
    private String serviceId;//客服id
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
