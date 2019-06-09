package test.com.xudong.im.data.mapper;

import com.xudong.im.data.mapper.TalkSkillMapper;
import com.xudong.im.domain.help.TalkSkill;
import com.xudong.im.domain.help.TalkSkillQuery;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.MySQLTestCaseSupport;

import java.util.List;

public class TalkSkillMapperTest extends MySQLTestCaseSupport {
	private static final Logger log = LoggerFactory.getLogger(TalkSkillMapper.class);

	@Autowired
	private TalkSkillMapper talkSkillMapper;

	@Test
	public void testLoad() {
		TalkSkill talkSkill = talkSkillMapper.load(RandomUtils.nextInt(1,5));
		log.info("=======>> testLoad " + talkSkill);
	}

	@Test
	public void testInsert() {
		TalkSkill talkSkill = new TalkSkill();

		talkSkillMapper.insert(talkSkill);
        log.info("=======>> testInsert " + talkSkill);
	}

	@Test
	public void testUpdate() {
		TalkSkill talkSkill = new TalkSkill();

		talkSkill.setId(RandomUtils.nextInt(1,5));

		talkSkillMapper.update(talkSkill);
	}

	@Test
	public void testUpdateStatus() {
		talkSkillMapper.updateStatus(RandomUtils.nextInt(1,5), 2);
	}

    @Test
    public void testUpdateIsDeleted() {
        talkSkillMapper.updateIsDeleted(RandomUtils.nextInt(1,5), 1);
    }

	@Test
	public void testDelete() {
		talkSkillMapper.delete(1);
	}

	@Test
	public void testQueryList() {
		TalkSkillQuery query = new TalkSkillQuery();

		List<TalkSkill> list =  talkSkillMapper.queryList(query);

        log.info("=======>> testQueryList " + list);
	}

	@Test
	public void testQueryCount() {
		TalkSkillQuery query = new TalkSkillQuery();

		int count = talkSkillMapper.queryCount(query);

        log.info("=======>> testQueryCount " + count);
	}
}