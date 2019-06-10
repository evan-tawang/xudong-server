package com.xudong.im.enums;

/**
 * 用户类型
 */
public enum UserTypeEnum {

    STAFF(1),
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
