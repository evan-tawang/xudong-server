package test.com.xudong.im.support;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created on 2017/7/16.
 *
 * @author evan.shen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MySQLTestBeansConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)

public class MySQLTestCaseSupport {

}
