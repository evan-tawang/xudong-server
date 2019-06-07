package com.xudong.im.domain.help;


import org.evanframework.query.AbstractQueryParam;
import org.evanframework.query.QueryParam;

import java.io.Serializable;
import java.util.Date;

/**
 * 常用话术查询对象
 */
public class TalkSkillQuery extends AbstractQueryParam implements QueryParam, Serializable {
    private static final long serialVersionUID = 15593867104741L;

    private Integer[] idArray;//
    private Date gmtCreateFrom;//
    private Date gmtCreateTo;//
    private Date gmtModifyFrom;//
    private Date gmtModifyTo;//
    private Integer isDeleted;//是否删除(0 未删除 1 删除)
    private Integer status;//状态(1 正常 2 停用)
    private Integer[] statusArray;//状态(1 正常 2 停用)
    private String content;//内容

    /****/
    public Integer[] getIdArray() {
        return idArray;
    }

    /****/
    public void setIdArray(Integer... idArray) {
        this.idArray = idArray;
    }

    /****/
    public Date getGmtCreateFrom() {
        return gmtCreateFrom;
    }

    /****/
    public void setGmtCreateFrom(Date gmtCreateFrom) {
        this.gmtCreateFrom = gmtCreateFrom;
    }

    /****/
    public Date getGmtCreateTo() {
        return gmtCreateTo;
    }

    /****/
    public void setGmtCreateTo(Date gmtCreateTo) {
        this.gmtCreateTo = gmtCreateTo;
    }

    /****/
    public Date getGmtModifyFrom() {
        return gmtModifyFrom;
    }

    /****/
    public void setGmtModifyFrom(Date gmtModifyFrom) {
        this.gmtModifyFrom = gmtModifyFrom;
    }

    /****/
    public Date getGmtModifyTo() {
        return gmtModifyTo;
    }

    /****/
    public void setGmtModifyTo(Date gmtModifyTo) {
        this.gmtModifyTo = gmtModifyTo;
    }

    /***是否删除(0 未删除 1 删除)*/
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /***是否删除(0 未删除 1 删除)*/
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /***状态(1 正常 2 停用)*/
    public Integer getStatus() {
        return status;
    }

    /***状态(1 正常 2 停用)*/
    public void setStatus(Integer status) {
        this.status = status;
    }

    /***状态(1 正常 2 停用)*/
    public Integer[] getStatusArray() {
        return statusArray;
    }

    /***状态(1 正常 2 停用)*/
    public void setStatusArray(Integer... statusArray) {
        this.statusArray = statusArray;
    }

    /***内容*/
    public String getContent() {
        return content;
    }

    /***内容*/
    public void setContent(String content) {
        this.content = content;
    }
}
