package test.com.xudong.im.cache;

import com.xudong.core.util.RandomUtil;
import com.xudong.im.cache.TalkSkillCache;
import com.xudong.im.domain.help.TalkSkill;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.CacheTestCaseSupport;
import test.com.xudong.im.testdata.TalkSkillTestData;

import java.util.List;

/**
 * TalkSkillCache Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>六月 14, 2019</pre>
 */
public class TalkSkillCacheTest extends CacheTestCaseSupport {

    @Autowired
    private TalkSkillCache cache;

    /**
     * Method: put(TalkSkill o)
     */
    @Test
    public void testPut() {
        TalkSkill o = TalkSkillTestData.random();
        o.setId(RandomUtil.randomInt(20));

        cache.put(o);
    }

    /**
     * Method: getList()
     */
    @Test
    public void testGetList() {
        List<TalkSkill> list = cache.getList();
        LOGGER.info("====testGetList:" + list);
    }

    /**
     * Method: getOne(Integer id)
     */
    @Test
    public void testGetOne() {
//TODO: Test goes here... 
    }

    /**
     * Method: remove(Integer id)
     */
    @Test
    public void testRemove() {

    }

    /**
     * Method: clear()
     */
    @Test
    public void testClear() {

    }
} 
