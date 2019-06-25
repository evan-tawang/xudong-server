package test.com.xudong.im.support;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author Evan.Shen
 * @since 2019-06-25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestBeansConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OpenApiTestCaseSupport {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected RestTemplate restTemplate;

    @Value("${local.server.port}")
    private int port;

    @Before
    public void init() {
        restTemplate = new RestTemplate();
    }

    protected String getApiUri() {
        return "http://127.0.0.1:" + port;
    }

    protected String getFullApiUri(String subUrl) {
        return "http://127.0.0.1:" + port + subUrl;
    }
}
