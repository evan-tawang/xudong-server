package com.xudong.im.web.controller;

import com.xudong.core.util.IpUtil;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.StaffLoginDTO;
import com.xudong.im.service.LoginService;
import com.xudong.im.session.StaffAgentSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.OperateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台用户登录
 *
 * @author create at 2016/6/20 10:15
 */
@RestController
public class LoginController {
    public static final String SMS_VALIDATE_CODE = "";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private final static int TIME_OUT = 14400;
    private static final String OBJECT_KEY = LoginController.class.getName();
    private final static String COOKIE_KEY = OBJECT_KEY + "ancun_bps_admin_token";
    private static final Long EMAIL_DATE_TIMEOUT = 86400L;//24小时
    private static final String LOGIN_ERROR_COUNT_VALIDATE = "loginByPWDOrSMS.errorcount.validate";
    private static final String LOGIN_ERROR_COUNT_SMS_VALIDATE = "loginByPWDOrSMS.errorcount.sms_validate";
    private static final String OBJECT_TYPE_KEY = LoginController.class.getCanonicalName();
    private static final String EMAIL_SET_URL = "email.set.url";

    @Autowired
    private StaffAgentSession staffAgentSession;

    @Autowired
    private LoginService loginService;

//    @Autowired
//    private SysConfig sysConfig;

    @PostMapping(value = "login")
    //@CsrfValidate
    public ApiResponse login(StaffLoginDTO loginDto, HttpServletRequest request) {
        OperateResult<StaffAgent> operateResult = loginService.login(loginDto);

        StaffAgent agent = operateResult.getData();

        agent.setRemoteAddr(IpUtil.getRemoteIp(request));

        staffAgentSession.save(agent);

        return ApiResponse.create(operateResult);
    }

    @PostMapping("logout")
    public ApiResponse logout(HttpServletRequest request) {
        StaffAgent agent = staffAgentSession.get(request);
        if (null != agent) {
            staffAgentSession.remove(request);
        }
        return ApiResponse.create();
    }

    @PostMapping(value = "login/debug")
    //@CsrfValidate
    public ApiResponse loginDebug(@RequestParam String account, HttpServletRequest request) {
        ApiResponse<StaffAgent> apiResponse = ApiResponse.create();

//        if (SysConfig.Profile.PRODUCTION.equals(sysConfig.getProfile())) {
//            apiResponse.setCodeAndMsg(CommonOperateResult.PROFILE_ERROR.getCode(), "该接口只有开发环境可用");
//            return apiResponse;
//        }

        return apiResponse;
    }

    private void setLoginDto(StaffLoginDTO loginDto) {
        String random = System.currentTimeMillis() + "";
        String pwdSha256 = null;
//        try {
//            pwdSha256 = AESUtil.decrypt(user.getPwd(), user.getId() + "");
//        } catch (AESException ex) {
//            throw new ServiceException("DECRYPT_USER_PASSWORD_ERROR", "校验密码时解密错误");
//        }
        String sign = DigestUtils.sha256Hex(loginDto.getAccount() + random + pwdSha256);
        loginDto.setRandom(random);
        loginDto.setSign(sign);
    }
}