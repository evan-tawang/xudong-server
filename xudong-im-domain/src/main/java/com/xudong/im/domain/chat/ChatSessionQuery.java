package com.xudong.im.domain.chat;

import org.evanframework.query.AbstractQueryParam;
import org.evanframework.query.QueryParam;

import java.io.Serializable;
import java.util.Collection;

public class ChatSessionQuery extends AbstractQueryParam implements QueryParam, Serializable {

    private Collection<String> idArray;//访客ip
    private String visitorIp;//访客ip
    private String visitorId;//访客id
    private String staffId; // 员工

    public Collection<String> getIdArray() {
        return idArray;
    }

    public void setIdArray(Collection<String> idArray) {
        this.idArray = idArray;
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
}
