package com.xudong.im.domain.user;

import java.io.Serializable;

/**
 * 登录传输对象
 * <p>
 * <p>
 * create at 2016年4月2日 下午4:39:20
 *
 * @author evan.shen
 * @see
 * @since %I%, %G%
 */
//@ApiModel(description = "客服登录对象")
public class StaffLoginDTO implements Serializable {
    private static final long serialVersionUID = 5776429104149356562L;

    //@ApiModelProperty(value = "账号", required = true, dataType = "String")
    private String account;

    private String pwd;

    //@ApiModelProperty(value = "随机数", required = true, dataType = "String")
    private String random;

    //@ApiModelProperty(value = "签名，格式：sha256(account + random + sha256(pwdlaintext))", required = true, dataType = "String")
    private String sign;

    //@ApiModelProperty(value = "生成图片验证码的key，和客户端约定好，验证的时候客户端传过来", required = true, dataType = "String")
    private String key;

    // @ApiModelProperty(value = "图片验证码", required = true, dataType = "String")
    private String validateCode;

    private Integer retryCount;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 重试次数，超过 x 次要求输入验证码
     */
    public Integer getRetryCount() {
        return retryCount;
    }

    /**
     * 重试次数，超过 x 次要求输入验证码
     */
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * 密码 md5
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * 密码 md5
     * */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "account='" + account + '\'' +
                ", random='" + random + '\'' +
                ", sign='" + sign + '\'' +
                ", key='" + key + '\'' +
                ", validateCode='" + validateCode + '\'' +
                ", retryCount=" + retryCount +
                '}';
    }
}
