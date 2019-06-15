package test.com.xudong.im.service;

import com.xudong.im.service.BlacklistService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.ServiceTestCaseSupport;

/**
 * @author Evan.Shen
 * @since 2019-06-12
 */

public class BlacklistServiceTest extends ServiceTestCaseSupport {
    @Autowired
    private BlacklistService blacklistService;

    /**
     * Method: login(StaffLoginDTO loginDto)
     */
    @Test
    public void testIsBlock() {
        Boolean result = blacklistService.isBlock("183.12.21.1");
        LOGGER.info("====>>testIsBlock 183.12.21.1 :" + result);

        result = blacklistService.isBlock("189.54.2.1");
        LOGGER.info("====>>testIsBlock 189.54.2.1 :" + result);

        result = blacklistService.isBlock("11.22.33.11");
        LOGGER.info("====>>testIsBlock 11.22.33.11 :" + result);

        result = blacklistService.isBlock("129.22.33.9");
        LOGGER.info("====>>testIsBlock 129.22.33.9 :" + result);

        result = blacklistService.isBlock("189.54.23.44");
        LOGGER.info("====>>testIsBlock 189.54.23.44:" + result);

        result = blacklistService.isBlock("13565202553");
        LOGGER.info("====>>testIsBlock 13565202553:" + result);
    }
}
