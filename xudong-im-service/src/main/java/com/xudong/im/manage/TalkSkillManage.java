package com.xudong.im.manage;

import com.xudong.im.cache.TalkSkillCache;
import com.xudong.im.constant.CommonConstant;
import com.xudong.im.data.mapper.TalkSkillMapper;
import com.xudong.im.domain.help.TalkSkill;
import com.xudong.im.domain.help.TalkSkillQuery;
import com.xudong.im.enums.TalkSkillStatusEnum;
import org.evanframework.dto.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用话术管理
 */
@Service
public class TalkSkillManage {
    private final static String MODULE_NAME = "常用话术";
    private final static Logger LOGGER = LoggerFactory.getLogger(TalkSkillManage.class);

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
        Assert.notNull(o, "添加" + MODULE_NAME + "时，内容不能为空");
        Assert.hasLength(o.getContent(), "添加" + MODULE_NAME + "时，内容不能为空");

        talkSkillMapper.insert(o);
        talkSkillCache.put(o);

        return o.getId();
    }

    @Transactional
    public void update(TalkSkill o) {
        Assert.notNull(o, "修改" + MODULE_NAME + "时，内容不能为空");
        Assert.notNull(o.getId(), "修改" + MODULE_NAME + "时，id不能为空");
        Assert.hasLength(o.getContent(), "修改" + MODULE_NAME + "时，内容不能为空");

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
        if (id == 0) {
            throw new IllegalArgumentException("修改" + MODULE_NAME + "状态时，id不能为空或0");
        }
        if (newStatus == 0) {
            throw new IllegalArgumentException("修改" + MODULE_NAME + "状态时，状态不能为空或0");
        }

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
        if (id == 0) {
            throw new IllegalArgumentException("删除" + MODULE_NAME + "状态时，id不能为空或0");
        }

        talkSkillMapper.updateIsDeleted(id, CommonConstant.DELETED_TAG);
        talkSkillCache.remove(id);
    }

    public void refreshCache() {
        talkSkillCache.clear();

        List<TalkSkill> list = getFromDB();

        for (TalkSkill o : list) {
            talkSkillCache.put(o);
        }
    }

    private List<TalkSkill> getFromDB() {
        TalkSkillQuery talkSkillQuery = new TalkSkillQuery();
        talkSkillQuery.setStatus(TalkSkillStatusEnum.NORMAL.getValue());
        return talkSkillMapper.queryList(talkSkillQuery);
    }
}
