package com.xudong.im.service;

import com.xudong.core.util.RandomUtil;
import com.xudong.im.constant.CommonConstant;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.StaffLoginDTO;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.enums.OnlineStatusEnum;
import org.evanframework.dto.OperateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 登录
 *
 * @author 沈炜
 * @since 2017/9/4
 */
@Service
public class LoginService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private static final String OBJECT_TYPE_KEY = "ValidateCode";

    private static final Integer MAX_ERROR_LOGIN_COUNT = 3;


    /**
     * 登录验证
     *
     * @return
     */
    public OperateResult<StaffAgent> login(StaffLoginDTO loginDto) {
        OperateResult<StaffAgent> result = OperateResult.create();

        if (result.isSuccess()) {
            handlerLoginSuccess(result, loginDto);
            UserAgent agent = result.getData();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> login success, usertype [{}], account [{}], id [{}]", agent.getUserType(), agent.getAccount(), agent.getId());
            }
        } else {
            LOGGER.warn(">>>> login fail, account [{}]", loginDto.getAccount());

            //handlerLoginFail(dto, result, user);
        }

        //writeLoginLog(result, dto);

        return result;
    }

    /**
     * 处理用户登录成功
     */
    private void handlerLoginSuccess(OperateResult result, StaffLoginDTO loginDto) {
        StaffAgent userAgent = new StaffAgent();

        userAgent.setAccount(loginDto.getAccount());
        userAgent.setId(RandomUtil.randomLong(1000) + "");
        userAgent.setOnlineStatus(OnlineStatusEnum.ONLINE.getValue());

        result.setData(userAgent);
    }
}