package com.xudong.im.support;


import com.xudong.im.config.MySQLAutoConfiguration;
import com.xudong.im.config.MongoDBAutoConfiguration;
import com.xudong.im.config.ServiceAutoConfiguration;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by evan.shen on 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        MySQLAutoConfiguration.class,
        MongoDBAutoConfiguration.class,
        ServiceAutoConfiguration.class
}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ServiceTestCaseSupport {
    @Before
    public void init() {
    }
}
