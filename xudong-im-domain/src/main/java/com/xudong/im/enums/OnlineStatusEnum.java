package com.xudong.im.enums;

/**
 * @author Evan.Shen
 * @since 2019-06-19
 */
public enum OnlineStatusEnum {
    ONLINE(1),
    OFFLINE(2),
    BUSY(3)
    ;

    private Integer value;

    OnlineStatusEnum(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
