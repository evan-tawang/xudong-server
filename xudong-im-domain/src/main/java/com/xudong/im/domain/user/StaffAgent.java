package com.xudong.im.domain.user;


import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.enums.UserTypeEnum;

import java.io.Serializable;

/**
 * 会话信息
 * <p>
 *
 * @author Evan.Shen
 */
public class StaffAgent extends UserAgent implements Serializable {
    private static final long serialVersionUID = -1830859706881331312L;

    /**
     * 用户类型 1 访客 2 客服
     */
    public Integer getUserType() {
        return UserTypeEnum.STAFF.getValue();
    }
}
