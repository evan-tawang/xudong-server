package com.xudong.im.data.mapper;

import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.domain.limit.BlackListQuery;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface BlackListMapper {
    /***/
    BlackList load(Integer id);

    /***/
    void insert(BlackList blackList);

    /***/
    void update(BlackList blackList);

    /***/
    void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

    /***/
    void updateIsDeleted(@Param("id") Integer id, @Param("isDeleted") int isDeleted);

    /***/
    void delete(Integer id);

    /***/
    List<BlackList> queryList(BlackListQuery blackListQuery);

    /***/
    int queryCount(BlackListQuery blackListQuery);


    BlackList getByContent(String content);
}