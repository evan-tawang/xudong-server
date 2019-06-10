package test.com.xudong.im.support;


import com.xudong.core.restful.TokenClientHttpRequestInterceptor;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.enums.UserTypeEnum;
import com.xudong.im.session.UserAgentContext;
import com.xudong.im.session.UserAgentSession;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by evan.shen on 2017/3/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestBeansConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebTestCaseSupport implements WebMvcConfigurer {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected RestTemplate restTemplate;

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private UserAgentSession userAgentSession;

    @Autowired
    private TokenClientHttpRequestInterceptor tokenClientHttpRequestInterceptor;

    @Before
    public void init() {

        UserAgent userAgent = new StaffAgent();
        userAgent.setId("161");
        userAgent.setRemoteAddr("192.168.18.53");
        //userAgent.setRemoteAddr("127.2.3.1");
        userAgent.setUserName("测试");
        userAgent.setAccount("acc7055");

        userAgentSession.save(userAgent);
        UserAgentContext.put(userAgent);


        restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(tokenClientHttpRequestInterceptor);
        restTemplate.setInterceptors(interceptors);
    }

    protected String getApiUri() {
        return "http://127.0.0.1:" + port;
    }

    protected String getFullApiUri(String subUrl) {
        return "http://127.0.0.1:" + port + subUrl;
    }

}
