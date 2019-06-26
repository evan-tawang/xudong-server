package com.xudong.im.web.controller;

import com.xudong.core.util.IpUtil;
import com.xudong.im.domain.user.VisitorAgent;
import com.xudong.im.domain.user.VisitorLoginDTO;
import com.xudong.im.enums.OnlineStatusEnum;
import com.xudong.im.session.UserAgentSession;
import org.evanframework.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Evan.Shen
 * @since 2019-06-25
 */
@RestController
@RequestMapping("visitor")
public class VisitorLoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorLoginController.class);

    @Autowired
    private UserAgentSession userAgentSession;

    @PostMapping(value = "login")
    public ApiResponse login(VisitorLoginDTO loginDto, HttpServletRequest request) {
        ApiResponse apiResponse = ApiResponse.create();

        VisitorAgent userAgent = new VisitorAgent();
        userAgent.setAccount(loginDto.getCustomerName());
        userAgent.setId(loginDto.getCustomerId());
        userAgent.setOnlineStatus(OnlineStatusEnum.ONLINE.getValue());
        userAgent.setLastLoginTime(new Date());
        userAgent.setRemoteAddr(IpUtil.getRemoteIp(request));
        userAgent.setTel(loginDto.getCustomerTel());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>>> Receive Visitor login data, loginDto [{}]", loginDto);
        }

        userAgentSession.save(userAgent);

        apiResponse.setData(userAgent);

        return apiResponse;
    }

//    @PostMapping("logout")
//    public ApiResponse logout(HttpServletRequest request) {
//        UserAgent agent = userAgentSession.get(request);
//        if (null != agent) {
//            userAgentSession.remove(request);
//        }
//        return ApiResponse.create();
//    }
}
