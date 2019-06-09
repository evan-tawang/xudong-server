package com.xudong.im.domain.user;


import org.evanframework.dto.OperatorAgent;

import java.io.Serializable;
import java.util.Date;

/**
 * 客服会话信息
 * <p>
 *
 * @author Evan.Shen
 */
public class StaffAgent implements OperatorAgent, Serializable {
    private static final long serialVersionUID = -1830859706881331312L;

    private Long id;
    private String account;
    private String mobile;
    private String userName;
    private String remoteAddr;
    private Integer status;

    private String token;
    private String tokenSecret;
    private String userAgentHeader;//user-agent
    private Date lastLoginTime;


    /** */
    public Integer getStatus() {
        return status;
    }

    /***/
    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 账号
     */
    @Override
    public String getAccount() {
        return account;
    }

    /**
     * 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String getType() {
        return null;
    }


    /**
     * 登录时的ip
     */
    @Override
    public String getIp() {
        return this.remoteAddr;
    }

    /**
     * 真实姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 真实姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    /**
     * 令牌，登录成功后写入
     */
    public String getToken() {
        return token;
    }

    /**
     * 令牌，登录成功后写入
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 秘钥，登录成功后写入
     */
    public String getTokenSecret() {
        return tokenSecret;
    }

    /**
     * 秘钥，登录成功后写入
     */
    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }


    /**
     * 远程地址
     */
    public String getRemoteAddr() {
        return remoteAddr;
    }

    /**
     * 远程地址
     */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }


    /**
     * http请求的user-agent
     */
    public String getUserAgentHeader() {
        return userAgentHeader;
    }

    /**
     * http请求的user-agent
     */
    public void setUserAgentHeader(String userAgentHeader) {
        this.userAgentHeader = userAgentHeader;
    }


    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    /**
     * 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userName='" + userName + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", status=" + status +
                ", token='" + token + '\'' +
                ", tokenSecret='" + tokenSecret + '\'' +
                ", userAgentHeader='" + userAgentHeader + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
