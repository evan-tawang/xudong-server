package com.xudong.im.manage;


import com.xudong.im.domain.help.TalkSkill;
import com.xudong.im.domain.help.TalkSkillQuery;
import com.xudong.im.support.ServiceTestCaseSupport;
import com.xudong.im.testdata.TalkSkillTestData;
import com.xudong.im.util.RandomUtil;
import org.evanframework.dto.PageResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Evan.Shen
 * @since 2019/6/7
 */
public class TalkSkillManageTest extends ServiceTestCaseSupport {
    @Autowired
    private TalkSkillMange talkSkillService;

    /**
     * Method: add(TalkSkill o)
     */
    @Test
    public void testAdd() {
        TalkSkill o = TalkSkillTestData.random();
        int id = talkSkillService.add(o);
        LOGGER.info("====>>testAdd:" + o);
    }

    /**
     * Method: update(TalkSkill o)
     */
    @Test
    public void testUpdate() {
        TalkSkill o = TalkSkillTestData.random();
        o.setId(RandomUtil.randomInt(10));
        talkSkillService.update(o);
    }

    /**
     * Method: getForList(TalkSkillQuery talkSkillQuery)
     */
    @Test
    public void testGetForList() {
        TalkSkillQuery query = new TalkSkillQuery();
        PageResult<TalkSkill> result = talkSkillService.getForList(query);
        LOGGER.info("====>>testGetForList:" + result);
    }

    /**
     * Method: getOne(int id)
     */
    @Test
    public void testGetOne() {
        TalkSkill result = talkSkillService.getOne(RandomUtil.randomInt(10));
        LOGGER.info("====>>testGetOne:" + result);

    }

    /**
     * Method: updateStatus(int id, String newStatus)
     */
    @Test
    public void testUpdateStatus() {
        talkSkillService.updateStatus(RandomUtil.randomInt(10), 1);
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
        talkSkillService.delete(RandomUtil.randomInt(10));
    }
}
