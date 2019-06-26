package com.xudong.im.domain.chat;

import java.io.Serializable;

public class ChatSessionVO extends ChatSession implements Serializable {

    public ChatSessionVO() {
    }

    public ChatSessionVO(String id, String visitorId, String staffId) {
        super(id, visitorId, staffId);
    }

    public ChatSessionVO(String id, String visitorId, String staffId, String otherSideName) {
        super(id, visitorId, staffId);
        this.otherSideName = otherSideName;
    }

    private String otherSideName;

    public String getOtherSideName() {
        return otherSideName;
    }

    public void setOtherSideName(String otherSideName) {
        this.otherSideName = otherSideName;
    }
}
