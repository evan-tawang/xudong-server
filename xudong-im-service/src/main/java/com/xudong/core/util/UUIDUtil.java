package com.xudong.im.util;

import java.util.UUID;

public class UUIDUtil {

    public static String nameUUIDFromBytes(String key) {
        return UUID.nameUUIDFromBytes(key.getBytes()).toString();
    }
}
