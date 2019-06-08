package com.xudong.im.domain.chat;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 聊天记录
 */
public class ChatRecord {

    @Id
    private String id;
    private String sessionId; //会话id
    private String content;//聊天内容
    private Date gmtCreate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "ChatRecord{" +
                "id='" + id + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", content='" + content + '\'' +
                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
