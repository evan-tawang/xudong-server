package com.xudong.im.manage;

import com.xudong.im.cache.BlackListCache;
import com.xudong.im.constant.CommonConstant;
import com.xudong.im.data.mapper.BlackListMapper;
import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.domain.limit.BlackListQuery;
import com.xudong.im.enums.BlacklistStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.evanframework.dto.PageResult;
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
public class BlackListManage {
    @Autowired
    private BlackListMapper blackListMapper;

    @Autowired
    private BlackListCache blackListCache;

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
        Assert.notNull(o, "添加黑名单时，内容不能为空");
        Assert.hasLength(o.getContent(), "添加黑名单时，内容不能为空");

        blackListMapper.insert(o);
        blackListCache.put(o);

        return o.getId();
    }

    /**
     * 批量添加
     *
     * @param blackListContents 需要添加的黑名单，多个以半角逗号分割
     */
    @Transactional
    public void addGroup(String blackListContents) {
        Assert.hasLength(blackListContents, "添加黑名单时，内容不能为空");

        blackListContents = StringUtils.replace(blackListContents, "\n", ",");

        String[] contents = blackListContents.split(",");
        for (String content : contents) {
            BlackList o = new BlackList();
            o.setContent(content);
            blackListMapper.insert(o);
            blackListCache.put(o);
        }
    }

    @Transactional
    public void update(BlackList o) {
        Assert.notNull(o, "修改黑名单时，内容不能为空");
        Assert.hasLength(o.getContent(), "修改黑名单时，内容不能为空");

        BlackList old = blackListMapper.load(o.getId());
        if (old != null) {
            blackListMapper.update(o);
            if (BlacklistStatusEnum.NORMAL.getValue().equals(old.getStatus())) {
                blackListCache.put(o);
            }
        }
    }

    @Transactional
    public void updateStatus(int id, int newStatus) {
        if(id == 0){
            throw new IllegalArgumentException("修改黑名单状态时，id不能为空或0");
        }

        if(newStatus == 0){
            throw new IllegalArgumentException("修改黑名单状态时，状态不能为空或0");
        }

        blackListMapper.updateStatus(id, newStatus);

        if (BlacklistStatusEnum.NORMAL.getValue().equals(newStatus)) {
            BlackList o = blackListMapper.load(id);
            blackListCache.put(o);
        } else {
            blackListCache.remove(id);
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
        if(id == 0){
            throw new IllegalArgumentException("删除黑名单状态时，id不能为空或0");
        }

        blackListMapper.updateIsDeleted(id, CommonConstant.DELETED_TAG);
        blackListCache.remove(id);
    }

    public void refreshCache() {
        blackListCache.clear();

        List<BlackList> list = getFromDB();

        for (BlackList o : list) {
            blackListCache.put(o);
        }
    }

    private List<BlackList> getFromDB() {
        BlackListQuery query = new BlackListQuery();
        query.setStatus(BlacklistStatusEnum.NORMAL.getValue());
        return blackListMapper.queryList(query);
    }
}
