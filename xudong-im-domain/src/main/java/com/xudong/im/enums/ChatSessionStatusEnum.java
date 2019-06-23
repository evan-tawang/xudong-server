package com.xudong.im.enums;

/**
 *
 */
public enum ChatSessionStatusEnum {

    IN_CONNECT(1),
    DISCONNECTED(2),

    ;

    private Integer value;

    ChatSessionStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
