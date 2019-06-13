package test.com.xudong.im.service;

import com.xudong.im.domain.help.TalkSkill;
import com.xudong.im.service.TalkSkillService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.ServiceTestCaseSupport;

import java.util.List;

/**
 * @author Evan.Shen
 * @since 2019-06-13
 */
public class TalkSkillServiceTest extends ServiceTestCaseSupport {
    @Autowired
    private TalkSkillService talkSkillService;

    @Test
    public void test() {
        List<TalkSkill> list = talkSkillService.getForList();

        for (TalkSkill o : list){
            System.out.println("====>" + o);
        }
    }
}
