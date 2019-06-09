package test.com.xudong.im.support;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by evan.shen on 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        ServiceTestBeansConfig.class
}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ServiceTestCaseSupport {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceTestCaseSupport.class);

    @Before
    public void init() {
    }
}
