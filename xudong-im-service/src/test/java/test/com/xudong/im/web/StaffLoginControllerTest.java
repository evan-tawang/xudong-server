package test.com.xudong.im.web;

import com.xudong.core.util.RandomUtil;
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
        String url = getFullApiUri("/login?account={account}&random={random}");

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("account", RandomUtil.randomName("acc"));
        parames.put("random", RandomUtil.randomName("random"));

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, parames);

        LOGGER.info("====>> testLogin : " + response);
    }
}
