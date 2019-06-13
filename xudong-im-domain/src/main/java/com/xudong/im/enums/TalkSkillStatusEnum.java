package com.xudong.im.enums;

/**
 * @author Evan.Shen
 * @since 2019-06-13
 */
public enum TalkSkillStatusEnum {
    NORMAL(1),
    STOP(2);

    private Integer value;

    TalkSkillStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
