package com.xudong.im.domain.chat;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 留言
 */
public class GuestBook  implements Serializable {
    @Id
    private String id;
    private String content; // 留言内容
    private String visitorId; //访客id
    private String visitorIp; //访客ip
        private String visitorName; //访客名称
    private String visitorPhone; //访客电话
    private Date gmtCreate; // 留言时间

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

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorPhone() {
        return visitorPhone;
    }

    public void setVisitorPhone(String visitorPhone) {
        this.visitorPhone = visitorPhone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
