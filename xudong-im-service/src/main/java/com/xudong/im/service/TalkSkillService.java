package com.xudong.im.service;

import com.xudong.im.cache.TalkSkillCache;
import com.xudong.im.data.mapper.TalkSkillMapper;
import com.xudong.im.domain.help.TalkSkill;
import com.xudong.im.domain.help.TalkSkillQuery;
import com.xudong.im.enums.TalkSkillStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(TalkSkillService.class);

    @Autowired
    private TalkSkillMapper talkSkillMapper;

    @Autowired
    private TalkSkillCache talkSkillCache;

    public List<TalkSkill> getForList() {
        List<TalkSkill> list = talkSkillCache.getList();

        if (list == null || list.isEmpty()) {
            list = getFromDB();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>> get [{}] talkskills from database", list.size());
            }
            for (TalkSkill o : list) {
                talkSkillCache.put(o);
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>> get [{}] talkskills from cache", list.size());
            }
        }

        return list;
    }

    private List<TalkSkill> getFromDB() {
        TalkSkillQuery talkSkillQuery = new TalkSkillQuery();
        talkSkillQuery.setStatus(TalkSkillStatusEnum.NORMAL.getValue());
        return talkSkillMapper.queryList(talkSkillQuery);
    }
}
