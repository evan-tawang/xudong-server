package test.com.xudong.im.manage;

import com.xudong.im.manage.SensitiveWordManage;
import com.xudong.core.util.RandomUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.ServiceTestCaseSupport;

/**
 * @author Evan.Shen
 * @since 2019/6/7
 */
public class SensitiveWordManageTest extends ServiceTestCaseSupport {
    @Autowired
    private SensitiveWordManage sensitiveWordMange;

    @Test
    public void test() {
        String[] words = sensitiveWordMange.get();

        LOGGER.info("====>> before update: " );
        printInfo(words);

        sensitiveWordMange.save(RandomUtil.randomString(5) + "," + RandomUtil.randomString(5));

        words = sensitiveWordMange.get();

        LOGGER.info("====>> after update: " );
        printInfo(words);
    }


}
