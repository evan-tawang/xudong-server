package com.xudong.im.enums;

/**
 * @author Evan.Shen
 * @since 2019-06-13
 */
public enum BlacklistStatusEnum {
    NORMAL(1),
    STOP(2);

    private Integer value;

    BlacklistStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
