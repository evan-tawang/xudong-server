package com.xudong.im.domain.user;

import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.enums.UserTypeEnum;

import java.io.Serializable;

/**
 * @author Evan.Shen
 * @since 2019/6/10
 */
public class VisitorAgent extends UserAgent implements Serializable {
    private static final long serialVersionUID = -1830859701881331312L;

    private String tel;

    /**
     * 用户类型 1 访客 2 客服
     */
    public Integer getUserType() {
        return UserTypeEnum.VISITOR.getValue();
    }

    /**
     *
     */
    public String getTel() {
        return tel;
    }

    /***/
    public void setTel(String tel) {
        this.tel = tel;
    }
}
