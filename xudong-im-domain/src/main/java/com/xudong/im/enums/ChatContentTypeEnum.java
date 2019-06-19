package com.xudong.im.enums;

public enum ChatContentTypeEnum {

    TEXT(1),
    FILE(2);

    private Integer value;

    ChatContentTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
