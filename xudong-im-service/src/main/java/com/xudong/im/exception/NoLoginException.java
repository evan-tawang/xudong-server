package com.xudong.im.exception;

import org.evanframework.exception.ServiceException;

/**
 * @author Evan.Shen
 */
public class NoLoginException extends ServiceException {
    private static final long serialVersionUID = 3583566093089790852L;
    private static final String CODE = "NO_LOGIN";

    public NoLoginException(String msg) {
        super("NO_LOGIN", msg);
    }

    public NoLoginException() {
        super("NO_LOGIN", "没有登录或登录超时");
    }
}
