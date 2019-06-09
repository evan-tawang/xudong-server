package test.com.xudong.im.data.mapper;

import com.xudong.im.data.mapper.BlackListMapper;
import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.domain.limit.BlackListQuery;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.MySQLTestCaseSupport;

import java.util.List;

public class BlackListMapperTest extends MySQLTestCaseSupport {
	private static final Logger log = LoggerFactory.getLogger(BlackListMapper.class);

	@Autowired
	private BlackListMapper blackListMapper;

	@Test
	public void testLoad() {
		BlackList blackList = blackListMapper.load(RandomUtils.nextInt(1,5));
		log.info("=======>> testLoad " + blackList);
	}

	@Test
	public void testInsert() {
		BlackList blackList = new BlackList();

		blackListMapper.insert(blackList);
        log.info("=======>> testInsert " + blackList);
	}

	@Test
	public void testUpdate() {
		BlackList blackList = new BlackList();

		blackList.setId(RandomUtils.nextInt(1,5));

		blackListMapper.update(blackList);
	}

	@Test
	public void testUpdateStatus() {
		blackListMapper.updateStatus(RandomUtils.nextInt(1,5), 2);
	}

    @Test
    public void testUpdateIsDeleted() {
        blackListMapper.updateIsDeleted(RandomUtils.nextInt(1,5), 1);
    }

	@Test
	public void testDelete() {
		blackListMapper.delete(1);
	}

	@Test
	public void testQueryList() {
		BlackListQuery query = new BlackListQuery();

		List<BlackList> list =  blackListMapper.queryList(query);

        log.info("=======>> testQueryList " + list);
	}

	@Test
	public void testQueryCount() {
		BlackListQuery query = new BlackListQuery();

		int count = blackListMapper.queryCount(query);

        log.info("=======>> testQueryCount " + count);
	}
}