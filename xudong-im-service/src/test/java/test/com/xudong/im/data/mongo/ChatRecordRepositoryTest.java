package test.com.xudong.im.data.mongo;

import com.xudong.core.util.RandomUtil;
import com.xudong.im.data.mongo.ChatRecordRepository;
import com.xudong.im.domain.chat.ChatRecord;
import com.xudong.im.domain.chat.ChatRecordQuery;
import org.apache.commons.lang3.time.DateUtils;
import org.evanframework.dto.PageResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.MongoDBTestCaseSupport;

import java.util.Date;

/**
 * 聊天记录
 */
public class ChatRecordRepositoryTest extends MongoDBTestCaseSupport {

    @Autowired
    private ChatRecordRepository chatRecordRepository;

    @Test
    public void testInsert() {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setSessionId(RandomUtil.randomName("session_"));
        chatRecord.setContent("你好");
        chatRecordRepository.insert(chatRecord);
        System.out.println(chatRecord.toString());
    }

    @Test
    public void getQueryPage() {
        Date today = new Date();

        ChatRecordQuery chatRecordQuery = new ChatRecordQuery();
        chatRecordQuery.setBeginDate(DateUtils.addDays(today, -2));
        PageResult<ChatRecord> result = chatRecordRepository.queryPage(chatRecordQuery);
        System.out.println(">>>>>>>> all:" + result.getRecordCount());

        chatRecordQuery = new ChatRecordQuery();
        //chatRecordQuery.setBeginDate(new Date());
        chatRecordQuery.setEndDate(DateUtils.addDays(today, -1));
        result = chatRecordRepository.queryPage(chatRecordQuery);
        System.out.println(">>>>>>>> " + result.getRecordCount());

        chatRecordQuery = new ChatRecordQuery();
        chatRecordQuery.setBeginDate(DateUtils.addDays(today, -7));
        chatRecordQuery.setEndDate(DateUtils.addDays(today, -1));
        result = chatRecordRepository.queryPage(chatRecordQuery);
        System.out.println(">>>>>>>> " + result.getRecordCount());
    }
}
