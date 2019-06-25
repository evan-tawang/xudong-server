package test.com.xudong.im.web;

import com.xudong.core.util.RandomUtil;
import org.evanframework.dto.ApiResponse;
import org.junit.Test;
import test.com.xudong.im.support.OpenApiTestCaseSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Evan.Shen
 * @since 2019-06-25
 */
public class VisitorLoginOpenApiTest extends OpenApiTestCaseSupport {

    @Test
    public void testLogin() {
        String url = getFullApiUri("/visitor/login?customerId={customerId}&customerName={customerName}&customerTel={customerTel}");

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("customerId", RandomUtil.randomInt(1000));
        parames.put("customerName", "张三");
        parames.put("customerTel", "13" + RandomUtil.randomLong(999999999));

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, parames);

        LOGGER.info("====>> testLogin : " + response);
    }
}
