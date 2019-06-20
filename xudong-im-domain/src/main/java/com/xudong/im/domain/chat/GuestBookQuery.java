package com.xudong.im.domain.chat;

import org.evanframework.query.AbstractQueryParam;
import org.evanframework.query.QueryParam;

import java.io.Serializable;

/**
 * 留言
 */
public class GuestBookQuery extends AbstractQueryParam implements QueryParam, Serializable {

    private String queryKey;

    /**
     *
     */
    public String getQueryKey() {
        return queryKey;
    }

    /***/
    public void setQueryKey(String queryKey) {
        this.queryKey = queryKey;
    }
}
