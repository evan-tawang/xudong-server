package test.com.xudong.im.testdata;

import com.xudong.im.domain.help.TalkSkill;
import com.xudong.core.util.RandomUtil;

/**
 * @author Evan.Shen
 * @since 2019/6/7
 */
public class TalkSkillTestData {
    public static TalkSkill random() {
        TalkSkill data = new TalkSkill();

        data.setContent(RandomUtil.randomName("TestContent"));
        data.setStatus(1);

        return data;
    }
}
