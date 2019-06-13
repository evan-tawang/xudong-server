package test.com.xudong.im.manage;

import com.xudong.core.util.RandomUtil;
import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.domain.limit.BlackListQuery;
import com.xudong.im.manage.BlackListManage;
import org.evanframework.dto.PageResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.ServiceTestCaseSupport;
import test.com.xudong.im.testdata.BlackListTestData;

/**
 * BlackListService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>六月 7, 2019</pre>
 */
public class BlackListManageTest extends ServiceTestCaseSupport {

    @Autowired
    private BlackListManage blackListService;

    /**
     * Method: add(BlackList o)
     */
    @Test
    public void testAdd() {
        BlackList o = BlackListTestData.random();
        int id = blackListService.add(o);
        LOGGER.info("====>>testAdd:" + o);
    }

    /**
     * Method: update(BlackList o)
     */
    @Test
    public void testUpdate() {
//        BlackList o = BlackListTestData.random();
//        o.setId(RandomUtil.randomInt(10));
//        blackListService.update(o);

        for (int i = 8; i <= 19; i++) {
            BlackList o = new BlackList(i);

            if (i % 3 == 0) {
                o.setContent("1" + RandomUtil.randomString(10));
            } else {
                o.setContent(RandomUtil.randomInt(254) + "."
                        + RandomUtil.randomInt(254) + "."
                        + RandomUtil.randomInt(254) + "."
                        + RandomUtil.randomInt(254));
            }

            blackListService.update(o);
        }
    }

    /**
     * Method: getForList(BlackListQuery blackListQuery)
     */
    @Test
    public void testGetForList() {
        BlackListQuery query = new BlackListQuery();
        PageResult<BlackList> result = blackListService.getForList(query);
        LOGGER.info("====>>testGetForList:" + result);
    }

    /**
     * Method: getOne(int id)
     */
    @Test
    public void testGetOne() {
        BlackList result = blackListService.getOne(RandomUtil.randomInt(10));
        LOGGER.info("====>>testGetOne:" + result);

    }

    /**
     * Method: updateStatus(int id, String newStatus)
     */
    @Test
    public void testUpdateStatus() {
        blackListService.updateStatus(RandomUtil.randomInt(10), 1);
    }

    /**
     * Method: updateStatusGroup(int[] ids, String newStatus)
     */
    @Test
    public void testUpdateStatusGroup() {

    }

    /**
     * Method: delete(int id)
     */
    @Test
    public void testDelete() {
        blackListService.delete(RandomUtil.randomInt(10));
    }

    @Test
    public void testRefreshCache() {
        blackListService.refreshCache();
    }
} 
