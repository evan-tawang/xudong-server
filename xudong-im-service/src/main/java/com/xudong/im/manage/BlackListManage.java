package com.xudong.im.manage;

import com.xudong.im.constant.CommonConstant;
import com.xudong.im.data.mapper.BlackListMapper;
import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.domain.limit.BlackListQuery;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 黑名单管理
 * @author Evan.Shen
 * @since 2019/6/7
 */
@Service
public class BlackListManage {
    @Autowired
    private BlackListMapper blackListMapper;

    /**
     * 分页
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
     * @param id
     * @return
     */
    public BlackList getOne(int id) {
        return blackListMapper.load(id);
    }

    @Transactional
    public int add(BlackList o) {
        blackListMapper.insert(o);
        return o.getId();
    }

    /**
     * 批量添加
     * @param blackListContents 需要添加的黑名单，多个以半角逗号分割
     */
    @Transactional
    public void addGroup(String blackListContents) {
        String[] contents = blackListContents.split(",");
        for (String content : contents) {
            BlackList o = new BlackList();
            o.setContent(content);
            blackListMapper.insert(o);
        }
    }

    @Transactional
    public void update(BlackList o) {
        blackListMapper.update(o);
    }

    @Transactional
    public void updateStatus(int id, int newStatus) {
        blackListMapper.updateStatus(id, newStatus);
    }

    /**
     * 批量更新状态
     * @param ids
     * @param newStatus
     */
    public void updateStatusGroup(int[] ids, int newStatus) {
        for (int id : ids) {
            updateStatus(id, newStatus);
        }
    }

    public void delete(int id) {
        blackListMapper.updateIsDeleted(id, CommonConstant.DELETED_TAG);
    }
}
