package test.com.xudong.im.support;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created on 2017/9/20.
 *
 * @author evan.shen
 * @since
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MongoDBTestBeansConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MongoDBTestCaseSupport {

}
