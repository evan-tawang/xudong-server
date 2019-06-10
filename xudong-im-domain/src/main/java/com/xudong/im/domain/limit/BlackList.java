package com.xudong.im.domain.limit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 访客黑名单实体类
 */
@ApiModel("黑名单")
public class BlackList implements Serializable {
    private static final long serialVersionUID = 15585788068752L;


    private Integer id;//
    private Date gmtCreate;//
    private Date gmtModify;//
    private Integer isDeleted;//是否删除(0 未删除 1 删除)

    @ApiModelProperty(value = "状态(1 正常 2 停用)", allowableValues = "1,2")
    private Integer status;//状态(1 正常 2 停用)

    @ApiModelProperty(value = "屏蔽内容(ip,账号)")
    private String content;

    public BlackList() {
    }

    /**
     * @param id --
     */
    public BlackList(Integer id) {
        this.id = id;
    }

    /** */
    public Integer getId() {
        return id;
    }

    /** */
    public void setId(Integer id) {
        this.id = id;
    }

    /***/
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /***/
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /***/
    public Date getGmtModify() {
        return gmtModify;
    }

    /***/
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * 是否删除(0 未删除 1 删除)
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除(0 未删除 1 删除)
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 状态(1 正常 2 停用)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态(1 正常 2 停用)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 屏蔽内容(ip,账号)
     */
    public String getContent() {
        return content;
    }

    /**
     * 屏蔽内容(ip,账号)
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BlackList [ id=" + id + ", gmtCreate=" + gmtCreate + ", gmtModify=" + gmtModify + ", isDeleted=" + isDeleted + ", status=" + status + ", content=" + content + "]";
    }
}
