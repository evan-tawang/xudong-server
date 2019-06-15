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
        Boolean result = blacklistService.isBlock("129.22.33.11");
        LOGGER.info("====>>testIsBlock:" + result);

        result = blacklistService.isBlock("129.22.33.11");

        LOGGER.info("====>>testIsBlock:" + result);

        result = blacklistService.isBlock("129.22.33.11");

        LOGGER.info("====>>testIsBlock:" + result);

        result = blacklistService.isBlock("129.22.33.11");

        LOGGER.info("====>>testIsBlock:" + result);
    }
}
