package test.com.xudong.im.service;

import com.xudong.im.manage.SensitiveWordManage;
import com.xudong.im.service.SensitiveWordService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.ServiceTestCaseSupport;

/**
 * SensitiveWordService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>六月 13, 2019</pre>
 */
public class SensitiveWordServiceTest extends ServiceTestCaseSupport {

    @Autowired
    private SensitiveWordService sensitiveWordService;

    @Autowired
    private SensitiveWordManage sensitiveWordManage;


    /**
     * Method: filter(String content)
     */
    @Test
    public void testFilter() {
        String result = sensitiveWordService.filter("傻逼吃饭了阿妈a,大傻逼");
        LOGGER.info("====>>testFilter result1:" + result);

        String content = "我的插插插";

        result = sensitiveWordService.filter(content);
        LOGGER.info("====>>testFilter result2:" + result);

        String old = sensitiveWordManage.get();
        sensitiveWordManage.save(old + "," + content);

        result = sensitiveWordService.filter(content);
        LOGGER.info("====>>testFilter result3:" + result);
    }

} 
