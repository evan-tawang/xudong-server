package com.xudong.im.session.reader;


import com.xudong.im.domain.user.support.UserAgent;

/**
 * 获取登录用户
 */
public class UserAgentContext {
    private static ThreadLocal<UserAgent> threadLocal = new ThreadLocal<UserAgent>();

    public static UserAgent get() {
        return threadLocal.get();
    }

    public static void put(UserAgent userAgent) {
        threadLocal.set(userAgent);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
