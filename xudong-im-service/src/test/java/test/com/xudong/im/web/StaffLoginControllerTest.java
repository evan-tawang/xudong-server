package test.com.xudong.im.web;

import com.xudong.core.util.RandomUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.evanframework.dto.ApiResponse;
import org.junit.Test;
import test.com.xudong.im.support.WebTestCaseSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Evan.Shen
 * @since 2019/6/9
 */
public class StaffLoginControllerTest extends WebTestCaseSupport {

    @Test
    public void testLogin() {
        String url = getFullApiUri("/staff/login?account={account}&pwd={pwd}");

        Map<String, Object> parames = new HashMap<String, Object>();
        //parames.put("account", RandomUtil.randomName("sysadmin"));
        parames.put("account", "sysadmin");
        parames.put("pwd", DigestUtils.md5Hex("111111"));
        //parames.put("random", RandomUtil.randomName("random"));

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, parames);

        LOGGER.info("====>> testLogin : " + response);
    }

    @Test
    public void testLogout() {
        String url = getFullApiUri("/staff/logout");

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class);

        LOGGER.info("====>> testLogout : " + response);
    }

    @Test
    public void testUpdateOnlineStatus() {
        String url = getFullApiUri("/staff/updateOnlineStatus?newStatus={0}");

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, 3);
        LOGGER.info("====>> testLogout : " + response);
    }

}
