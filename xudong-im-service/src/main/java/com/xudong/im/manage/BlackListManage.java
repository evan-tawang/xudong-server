package com.xudong.im.manage;

import com.xudong.core.blacklist.BlacklistIniter;
import com.xudong.core.blacklist.BlacklistReader;
import com.xudong.im.cache.BlackListEHCache;
import com.xudong.im.cache.BlackListRedis;
import com.xudong.im.constant.CommonConstant;
import com.xudong.im.data.mapper.BlackListMapper;
import com.xudong.im.domain.BlacklistMatchingRegexList;
import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.domain.limit.BlackListQuery;
import com.xudong.im.enums.BlacklistStatusEnum;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
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
 * 黑名单管理
 *
 * @author Evan.Shen
 * @since 2019/6/7
 */
@Service
public class BlackListManage  {
    private final static String MODULE_NAME = "黑名单";
    private final static Logger LOGGER = LoggerFactory.getLogger(BlackListManage.class);

    @Autowired
    private BlackListMapper blackListMapper;

    @Autowired
    private BlackListRedis blackListRedis;

    @Autowired
    private BlackListEHCache blackListEHCache;

    /**
     * 分页
     *
     * @param blackListQuery
     * @return
     */
    public PageResult<BlackList> getForList(BlackListQuery blackListQuery) {
        int count = blackListMapper.queryCount(blackListQuery);
        List<BlackList> list;
        if (count > 0) {
            list = blackListMapper.queryList(blackListQuery);
        } else {
            list = new ArrayList<>();
        }
        return PageResult.create(blackListQuery, list, count);

    }

    /**
     * 单个
     *
     * @param id
     * @return
     */
    public BlackList getOne(int id) {
        return blackListMapper.load(id);
    }

    @Transactional
    public int add(BlackList o) {
        Assert.notNull(o, "添加" + MODULE_NAME + "时，内容不能为空");
        Assert.hasLength(o.getContent(), "添加" + MODULE_NAME + "时，内容不能为空");

        blackListMapper.insert(o);
        blackListRedis.put(o);

        return o.getId();
    }

    /**
     * 批量添加
     *
     * @param blackListContents 需要添加的黑名单，多个以半角逗号分割
     */
    @Transactional
    public void addGroup(String blackListContents) {
        Assert.hasLength(blackListContents, "添加" + MODULE_NAME + "时，内容不能为空");

        blackListContents = StringUtils.replace(
                StringUtils.replace(blackListContents, "\n", ",")
                , "，", ",");

        String[] contents = blackListContents.split(",");
        for (String content : contents) {
            BlackList o = new BlackList();
            o.setContent(content);
            blackListMapper.insert(o);
            blackListRedis.put(o);
        }
    }

    @Transactional
    public void update(BlackList o) {
        Assert.notNull(o, "修改" + MODULE_NAME + "时，内容不能为空");
        Assert.notNull(o.getId(), "修改" + MODULE_NAME + "时，id不能为空");
        Assert.hasLength(o.getContent(), "修改" + MODULE_NAME + "时，内容不能为空");

        BlackList old = blackListMapper.load(o.getId());
        if (old != null) {
            blackListMapper.update(o);
            if (BlacklistStatusEnum.NORMAL.getValue().equals(old.getStatus())) {
                blackListRedis.put(o);
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

        blackListMapper.updateStatus(id, newStatus);

        if (BlacklistStatusEnum.NORMAL.getValue().equals(newStatus)) {
            BlackList o = blackListMapper.load(id);
            blackListRedis.put(o);
        } else {
            blackListRedis.remove(id);
        }
    }

    /**
     * 批量更新状态
     *
     * @param ids
     * @param newStatus
     */
    public void updateStatusGroup(int[] ids, int newStatus) {
        for (int id : ids) {
            updateStatus(id, newStatus);
        }
    }

    public void delete(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("删除" + MODULE_NAME + "状态时，id不能为空或0");
        }

        blackListMapper.updateIsDeleted(id, CommonConstant.DELETED_TAG);
        blackListRedis.remove(id);
    }

    public void refreshCache() {
        blackListRedis.clear();
        List<BlackList> list = getNormal();
        //List<String> matchingRegexList = blacklistIniter.init(list);
        for (BlackList e : list) {
            blackListRedis.put(e);
        }
    }

    public List<BlackList> getNormal() {
        BlackListQuery query = new BlackListQuery();
        query.setStatus(BlacklistStatusEnum.NORMAL.getValue());
        return blackListMapper.queryList(query);
    }

    /**
     *
     * @param val
     */
    public void setBlock(String val) {
        if(StringUtils.isEmpty(val)){
            return;
        }

        BlackList o = blackListMapper.getByContent(val);

        if(o == null){
            o = new BlackList();
            o.setContent(val);
            add(o);
        }else if (BlacklistStatusEnum.STOP.getValue().equals(o.getStatus())) {
            updateStatus(o.getId(),BlacklistStatusEnum.NORMAL.getValue());
        }
    }
}
