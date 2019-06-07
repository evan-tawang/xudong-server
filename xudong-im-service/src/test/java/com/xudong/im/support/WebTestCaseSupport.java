package com.xudong.im.support;


import com.xudong.im.config.WebApiAutoConfiguraion;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by evan.shen on 2017/3/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        WebApiAutoConfiguraion.class,
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebTestCaseSupport {



}
