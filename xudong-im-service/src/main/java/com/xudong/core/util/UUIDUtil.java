package com.xudong.core.util;

import java.util.UUID;

public class UUIDUtil {

    public static String nameUUIDFromBytes(String key) {
        return UUID.nameUUIDFromBytes(key.getBytes()).toString();
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }
}
