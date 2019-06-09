package com.xudong.im.domain.chat;

public enum  ChatRecordFromEnum {

    SERVICE(1),
    VISITOR(2)
;

    private Integer value;

    ChatRecordFromEnum(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
