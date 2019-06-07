/**
 * Copyright (c) 2005-2012 springside.org.cn
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.xudong.im.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 随机测试数据生成工具类.
 */
public class RandomUtil {

    private static Random random = new Random();

    /**
     * 返回随机ID.
     */
    public static long randomId() {
        return random.nextLong();
    }

    public static double randomDouble() {
        return random.nextDouble();
    }

    public static int randomInt(int max) {
        return random.nextInt(max);
    }

    public static long randomLong(int max) {
        return random.nextInt(max);
    }

    public static String randomString(int length) {
        int max = (int) Math.pow(10, length);
        int int1 = random.nextInt(max - 1);
        return StringUtils.leftPad(int1 + "", length, "0");
    }

    /**
     * 返回随机名称, prefix字符串+5位随机数字.
     */
    public static String randomName(String prefix) {
        return prefix + random.nextInt(10000);
    }

    /**
     * 从输入list中随机返回一个对象.
     */
    public static <T> T randomOne(List<T> list) {
        Collections.shuffle(list);
        return list.get(0);
    }

    /**
     * 从输入list中随机返回n个对象.
     */
    public static <T> List<T> randomSome(List<T> list, int n) {
        Collections.shuffle(list);
        return list.subList(0, n);
    }

    /**
     * 从输入list中随机返回随机个对象.
     */
    public static <T> List<T> randomSome(List<T> list) {
        int size = random.nextInt(list.size());
        if (size == 0) {
            size = 1;
        }
        return randomSome(list, size);
    }

}
