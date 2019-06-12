package com.xudong.im.service;

import com.xudong.im.data.mapper.TalkSkillMapper;
import com.xudong.im.domain.help.TalkSkill;
import com.xudong.im.domain.help.TalkSkillQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 常用话术服务
 *
 * @author Evan.Shen
 * @since 2019-06-12
 */
@Service
public class TalkSkillService {

    @Autowired
    private TalkSkillMapper talkSkillMapper;

    public List<TalkSkill> getForList() {
        TalkSkillQuery talkSkillQuery = new TalkSkillQuery();

        List list = talkSkillMapper.queryList(talkSkillQuery);

        return list;
    }
}
