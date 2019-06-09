package com.xudong.im.session;


import com.xudong.im.domain.user.StaffAgent;

/**
 * 获取登录用户
 */
public class StaffAgentContext {
    private static ThreadLocal<StaffAgent> threadLocal = new ThreadLocal<StaffAgent>();

    public static StaffAgent get() {
        return threadLocal.get();
    }

    public static void put(StaffAgent userAgent) {
        threadLocal.set(userAgent);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
