package com.xudong.im.data.mongo;

import com.xudong.im.domain.chat.ChatRecord;
import com.xudong.im.support.MongoDBTestCaseSupport;
import com.xudong.core.util.RandomUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 聊天记录
 */
public class ChatRecordRepositoryTest extends MongoDBTestCaseSupport {

    @Autowired
    private ChatRecordRepository chatRecordRepository;

    @Test
    public void testInsert(){
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setSessionId(RandomUtil.randomName("session_"));
        chatRecord.setContent("你好");
        chatRecordRepository.insert(chatRecord);
        System.out.println(chatRecord.toString());
    }
}
