package com.xudong.im.domain.user;

/**
 * 用户类型
 */
public enum UserTypeEnum {

    SERVICE(1),
    VISITOR(2)
    ;

    private Integer value;

    UserTypeEnum(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
