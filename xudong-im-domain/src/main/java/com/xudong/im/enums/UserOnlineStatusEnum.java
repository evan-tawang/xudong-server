package com.xudong.im.enums;

/**
 * 用户状态
 */
public enum UserOnlineStatusEnum {

    ON_LINE(1),
    OFF_LINE(2),
    BE_BUSY(3)
    ;

    private Integer value;

    UserOnlineStatusEnum(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

