package test.com.xudong.im.data.mapper;

import com.xudong.im.data.mapper.SensitiveWordMapper;
import com.xudong.im.domain.limit.SensitiveWord;
import com.xudong.im.domain.limit.SensitiveWordQuery;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.MySQLTestCaseSupport;

import java.util.List;

public class SensitiveWordMapperTest extends MySQLTestCaseSupport {
	private static final Logger log = LoggerFactory.getLogger(SensitiveWordMapper.class);

	@Autowired
	private SensitiveWordMapper sensitiveWordMapper;

	@Test
	public void testLoad() {
		SensitiveWord sensitiveWord = sensitiveWordMapper.load(RandomUtils.nextInt(1,5));
		log.info("=======>> testLoad " + sensitiveWord);
	}

	@Test
	public void testInsert() {
		SensitiveWord sensitiveWord = new SensitiveWord();

		sensitiveWordMapper.insert(sensitiveWord);
        log.info("=======>> testInsert " + sensitiveWord);
	}

	@Test
	public void testUpdate() {
		SensitiveWord sensitiveWord = new SensitiveWord();

		sensitiveWord.setId(RandomUtils.nextInt(1,5));

		sensitiveWordMapper.update(sensitiveWord);
	}

	@Test
	public void testUpdateStatus() {
		sensitiveWordMapper.updateStatus(RandomUtils.nextInt(1,5), 2);
	}

    @Test
    public void testUpdateIsDeleted() {
        sensitiveWordMapper.updateIsDeleted(RandomUtils.nextInt(1,5), 1);
    }

	@Test
	public void testDelete() {
		sensitiveWordMapper.delete(1);
	}

	@Test
	public void testQueryList() {
		SensitiveWordQuery query = new SensitiveWordQuery();

		List<SensitiveWord> list =  sensitiveWordMapper.queryList(query);

        log.info("=======>> testQueryList " + list);
	}

	@Test
	public void testQueryCount() {
		SensitiveWordQuery query = new SensitiveWordQuery();

		int count = sensitiveWordMapper.queryCount(query);

        log.info("=======>> testQueryCount " + count);
	}
}