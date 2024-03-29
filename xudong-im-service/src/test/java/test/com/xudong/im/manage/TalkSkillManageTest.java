package test.com.xudong.im.manage;


import com.xudong.im.domain.help.TalkSkill;
import com.xudong.im.domain.help.TalkSkillQuery;
import com.xudong.im.manage.TalkSkillManage;
import com.xudong.core.util.RandomUtil;
import org.evanframework.dto.PageResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.ServiceTestCaseSupport;
import test.com.xudong.im.testdata.TalkSkillTestData;

/**
 * @author Evan.Shen
 * @since 2019/6/7
 */
public class TalkSkillManageTest extends ServiceTestCaseSupport {
    @Autowired
    private TalkSkillManage talkSkillManage;


    /**
     * Method: add(TalkSkill o)
     */
    @Test
    public void testAdd() {
        TalkSkill o = TalkSkillTestData.random();
        int id = talkSkillManage.add(o);
        LOGGER.info("====>>testAdd:" + o);
    }

    /**
     * Method: update(TalkSkill o)
     */
    @Test
    public void testUpdate() {
        TalkSkill o = TalkSkillTestData.random();
        o.setId(RandomUtil.randomInt(10));
        talkSkillManage.update(o);
    }

    /**
     * Method: getForList(TalkSkillQuery talkSkillQuery)
     */
    @Test
    public void testGetForList() {
        TalkSkillQuery query = new TalkSkillQuery();
        PageResult<TalkSkill> result = talkSkillManage.getForList(query);
        LOGGER.info("====>>testGetForList:" + result);
    }

    /**
     * Method: getOne(int id)
     */
    @Test
    public void testGetOne() {
        TalkSkill result = talkSkillManage.getOne(RandomUtil.randomInt(10));
        LOGGER.info("====>>testGetOne:" + result);

    }

    /**
     * Method: updateStatus(int id, String newStatus)
     */
    @Test
    public void testUpdateStatus() {
        talkSkillManage.updateStatus(RandomUtil.randomInt(10), 2);
        talkSkillManage.updateStatus(RandomUtil.randomInt(10), 2);
        talkSkillManage.updateStatus(RandomUtil.randomInt(10), 2);

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
        talkSkillManage.delete(RandomUtil.randomInt(10));
        talkSkillManage.delete(RandomUtil.randomInt(10));
        talkSkillManage.delete(RandomUtil.randomInt(10));
        talkSkillManage.delete(RandomUtil.randomInt(10));
        talkSkillManage.delete(RandomUtil.randomInt(10));
        talkSkillManage.delete(RandomUtil.randomInt(10));
    }

    @Test
    public void testRefreshCache() {
        talkSkillManage.refreshCache();
    }
}
