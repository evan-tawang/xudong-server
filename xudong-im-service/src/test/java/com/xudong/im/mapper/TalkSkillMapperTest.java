package com.xudong.im.mapper;

import com.xudong.im.data.mapper.TalkSkillMapper;
import com.xudong.im.domain.help.TalkSkill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TalkSkillMapperTest {

    @Autowired
    private TalkSkillMapper talkSkillMapper;

    @Test
    public void testLoad(){
        TalkSkill talkSkill = talkSkillMapper.load(1);
        System.out.println(talkSkill);
    }
}
