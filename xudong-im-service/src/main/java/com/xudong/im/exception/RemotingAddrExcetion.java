package com.xudong.im.exception;


import com.xudong.im.constant.CommonOperateResult;
import org.evanframework.exception.ServiceException;

/**
 * Created on 2017/9/5.
 *
 * @author evan.shen
 */
public class RemotingAddrExcetion extends ServiceException {
    private static final long serialVersionUID = -951777198106425369L;

    public RemotingAddrExcetion() {
        super(CommonOperateResult.REMOTING_ADDR_WRONG);
    }

    public RemotingAddrExcetion(String message) {
        super(CommonOperateResult.REMOTING_ADDR_WRONG.getCode(), message);
    }
}
