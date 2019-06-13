package com.xudong.im.manage;

import com.xudong.im.cache.TalkSkillCache;
import com.xudong.im.constant.CommonConstant;
import com.xudong.im.data.mapper.TalkSkillMapper;
import com.xudong.im.domain.help.TalkSkill;
import com.xudong.im.domain.help.TalkSkillQuery;
import com.xudong.im.enums.TalkSkillStatusEnum;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用话术管理
 */
@Service
public class TalkSkillManage {
    @Autowired
    private TalkSkillMapper talkSkillMapper;

    @Autowired
    private TalkSkillCache talkSkillCache;

    public PageResult<TalkSkill> getForList(TalkSkillQuery talkSkillQuery) {
        int count = talkSkillMapper.queryCount(talkSkillQuery);
        List<TalkSkill> list;
        if (count > 0) {
            list = talkSkillMapper.queryList(talkSkillQuery);
        } else {
            list = new ArrayList<>();
        }
        return PageResult.create(talkSkillQuery, list, count);

    }

    public TalkSkill getOne(int id) {
        return talkSkillMapper.load(id);
    }

    @Transactional
    public int add(TalkSkill o) {
        talkSkillMapper.insert(o);
        talkSkillCache.put(o);

        return o.getId();
    }

    @Transactional
    public void update(TalkSkill o) {
        TalkSkill old = talkSkillMapper.load(o.getId());
        if (old != null) {
            talkSkillMapper.update(o);
            if (TalkSkillStatusEnum.NORMAL.getValue().equals(old.getStatus())) {
                talkSkillCache.put(o);
            }
        }
    }

    @Transactional
    public void updateStatus(int id, int newStatus) {
        talkSkillMapper.updateStatus(id, newStatus);

        if (TalkSkillStatusEnum.NORMAL.getValue().equals(newStatus)) {
            TalkSkill o = talkSkillMapper.load(id);
            talkSkillCache.put(o);
        } else {
            talkSkillCache.remove(id);
        }
    }

    public void updateStatusGroup(int[] ids, int newStatus) {
        for (int id : ids) {
            updateStatus(id, newStatus);
        }
    }

    public void delete(int id) {
        talkSkillMapper.updateIsDeleted(id, CommonConstant.DELETED_TAG);
        talkSkillCache.remove(id);
    }
}
