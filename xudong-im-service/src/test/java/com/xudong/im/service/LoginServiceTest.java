package com.xudong.im.service;

import com.xudong.core.util.RandomUtil;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.StaffLoginDTO;
import com.xudong.im.support.ServiceTestCaseSupport;
import org.evanframework.dto.OperateResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>六月 9, 2019</pre>
 */
public class LoginServiceTest extends ServiceTestCaseSupport {

    @Autowired
    private LoginService loginService;

    /**
     * Method: login(StaffLoginDTO loginDto)
     */
    @Test
    public void testLogin() {
        StaffLoginDTO dto = new StaffLoginDTO();

        dto.setAccount(RandomUtil.randomString(9));

        OperateResult<StaffAgent> operateResult = loginService.login(dto);

        LOGGER.info("====>testLogin:" + operateResult);
    }
} 
