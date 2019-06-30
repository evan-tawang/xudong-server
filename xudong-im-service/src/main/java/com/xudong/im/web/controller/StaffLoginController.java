package com.xudong.im.web.controller;

import com.xudong.core.mes.MesApiClient;
import com.xudong.core.mes.MesApiResult;
import com.xudong.core.util.IpUtil;
import com.xudong.core.util.RandomUtil;
import com.xudong.im.constant.CommonOperateResult;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.StaffLoginDTO;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.enums.OnlineStatusEnum;
import com.xudong.im.service.StaffLoginService;
import com.xudong.im.session.UserAgentSession;
import org.evanframework.dto.ApiResponse;
import org.evanframework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 后台用户登录
 *
 * @author create at 2016/6/20 10:15
 */
@RestController
@RequestMapping("staff")
public class StaffLoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaffLoginController.class);

    @Autowired
    private UserAgentSession userAgentSession;

    @Autowired
    private StaffLoginService loginService;


    @Autowired
    private MesApiClient mesApiClient;

//    @Autowired
//    private SysConfig sysConfig;

    @PostMapping(value = "login")
    //@CsrfValidate
    public ApiResponse login(StaffLoginDTO loginDto, HttpServletRequest request) {
        //OperateResult<StaffAgent> operateResult = loginService.login(loginDto);

        String account = loginDto.getAccount();

        MesApiResult mesApiResult = mesApiClient.staffLogin(loginDto.getAccount(), loginDto.getPwd());

        ApiResponse apiResponse = ApiResponse.create();

        if (mesApiResult.isSuccess()) {
            StaffAgent userAgent = new StaffAgent();

            userAgent.setAccount(account);
            userAgent.setId(mesApiResult.getUserId());
            userAgent.setOnlineStatus(OnlineStatusEnum.ONLINE.getValue());
            userAgent.setLastLoginTime(new Date());
            userAgent.setRemoteAddr(IpUtil.getRemoteIp(request));
            userAgent.setUserName(mesApiResult.get("UserCnName"));

            userAgentSession.save(userAgent);

            apiResponse.setData(userAgent);
        } else {
            apiResponse.setCode(CommonOperateResult.USER_NOT_EXIST_OR_PASSWORD_WRONG);
            apiResponse.setMsg(mesApiResult.getMsg());
        }

        return apiResponse;
    }

    @PostMapping(value = "virtual-login")
    //@CsrfValidate
    public ApiResponse loginDebug(@RequestParam String account, HttpServletRequest request) {
        ApiResponse<StaffAgent> apiResponse = ApiResponse.create();

        StaffAgent userAgent = new StaffAgent();

        userAgent.setAccount(account);
        userAgent.setId(RandomUtil.randomInt(1000) + "");
        userAgent.setOnlineStatus(OnlineStatusEnum.ONLINE.getValue());
        userAgent.setLastLoginTime(new Date());
        userAgent.setRemoteAddr(IpUtil.getRemoteIp(request));

        userAgentSession.save(userAgent);

        apiResponse.setData(userAgent);

        return apiResponse;
    }

    @PostMapping("logout")
    public ApiResponse logout(HttpServletRequest request) {
        UserAgent agent = userAgentSession.get(request);
        if (null != agent) {
            userAgentSession.remove(request);
        }
        return ApiResponse.create();
    }

    @PostMapping(value = "updateOnlineStatus")
    public ApiResponse updateOnlineStatus(@RequestParam Integer newStatus, HttpServletRequest request) {
        userAgentSession.updateOnlineStatus(newStatus, request);
        return ApiResponse.create();
    }


    @PostMapping(value = "isLogin")
    public ApiResponse isLogin(HttpServletRequest request) {
        UserAgent userAgent = userAgentSession.get(request);

        if(userAgent != null){
            return ApiResponse.create();
        }

        throw new ServiceException(CommonOperateResult.USER_OVERDUE);
    }
}