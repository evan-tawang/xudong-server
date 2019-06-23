package com.xudong.im.domain.user.support;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Evan.Shen
 * @since 2019/6/10
 */
public abstract class UserAgent implements Serializable {
    private static final long serialVersionUID = -1830859706881331312L;

    private String id;
    private String account;
    private String mobile;
    private String userName;
    private String remoteAddr;
    private Integer status;

    private String token;
    private String tokenSecret;
    private String userAgentHeader;//user-agent
    private Date lastLoginTime;
    private Integer onlineStatus;


    /**
     * 用户类型 1 访客 2 客服
     */
    @JsonIgnore
    public abstract Integer getUserType();

    /**
     * 定义一个空的setter json反序列化的时候需要用到
     *
     * @param userType
     */
    public void setUserType(Integer userType) {

    }

    /**
     *
     */
    public Integer getStatus() {
        return status;
    }

    /***/
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

//    public String getType() {
//        return null;
//    }

    /**
     * 登录时的ip
     */
    @JsonIgnore
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


    /**
     * @see com.xudong.im.enums.OnlineStatusEnum
     */
    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    /**
     * @see com.xudong.im.enums.OnlineStatusEnum
     */
    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }



    @Override
    public String toString() {
        return "UserAgent{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userName='" + userName + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", status=" + status +
                ", token='" + token + '\'' +
                ", tokenSecret='" + tokenSecret + '\'' +
                ", userAgentHeader='" + userAgentHeader + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", onlineStatus=" + onlineStatus +
                '}';
    }
}
