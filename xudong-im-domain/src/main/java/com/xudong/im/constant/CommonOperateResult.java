package com.xudong.im.constant;

import org.evanframework.dto.OperateCommonResultType;
import org.evanframework.dto.OperateResultType;

/**
 * 公共的操作结果
 * Created by evan.shen on 2017/4/24.
 */
public interface CommonOperateResult extends OperateCommonResultType {

    OperateResultType USER_OVERDUE = new OperateResultType("NO_LOGIN", "用戶信息已过期");

    OperateResultType REMOTING_ADDR_WRONG = new OperateResultType("REMOTING_ADDR_WRONG", "客户端地址不正确，请重新登录");

    OperateResultType SIGN_WRONG = new OperateResultType("SIGN_WRONG", "接口签名不正确");

    OperateResultType VALIDATE_CODE_WRONG = new OperateResultType("VALIDATE_CODE_WRONG", "验证码不正确");

    OperateResultType DATA_REPEATED = new OperateResultType("DATA_REPEATED", "已经存在");

    OperateResultType DATA_INVALID = new OperateResultType("DATA_INVALID", "数据不正确");

    OperateResultType UPLOAD_ERROR = new OperateResultType("UPLOAD_ERROR", "文件上传出错");

    OperateResultType PROFILE_ERROR = new OperateResultType("PROFILE_ERROR", "环境不正确");


    OperateResultType NO_AUTH = new OperateResultType("NO_AUTH", "无权限");

    OperateResultType REPEAT_SUBMIT = new OperateResultType("REPEAT_SUBMIT", "重复提交");

    OperateResultType BUSY_OPERATE = new OperateResultType("BUSY_OPERATE", "您当前的操作过于频繁,请稍后再试");

    OperateResultType USER_NOT_EXIST_OR_PASSWORD_WRONG = new OperateResultType("USER_NOT_EXIST_OR_PASSWORD_WRONG", "账号或密码错误");

}
