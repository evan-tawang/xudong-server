package test.com.xudong.im.manage;

import com.xudong.core.util.RandomUtil;
import com.xudong.im.manage.SensitiveWordManage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.ServiceTestCaseSupport;

/**
 * @author Evan.Shen
 * @since 2019/6/7
 */
public class SensitiveWordManageTest extends ServiceTestCaseSupport {
    @Autowired
    private SensitiveWordManage sensitiveWordManage;

    @Test
    public void testGet() {
        String words = sensitiveWordManage.get();

        LOGGER.info("====>> before update: ");
        printInfo(words);

        sensitiveWordManage.save(RandomUtil.randomString(5) + "," + RandomUtil.randomString(5) + ',' + words);

        words = sensitiveWordManage.get();

        LOGGER.info("====>> after update: ");
        printInfo(words);
    }

    @Test
    public void test2() {
        String words = sensitiveWordManage.get();
        long length = words.length();

        StringBuffer sb = new StringBuffer(words.length());

        for (int i = 0; i < length; i++) {
            char c = words.charAt(i);

            if (c == '\n') {
                sb.append(',');
            }else{
                sb.append(c);
            }
        }

        LOGGER.info("====>> words to update: " + sb);

        sensitiveWordManage.save(sb.toString());
    }
}
