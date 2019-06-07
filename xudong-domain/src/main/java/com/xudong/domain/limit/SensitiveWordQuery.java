package com.xudong.domain.limit;


import org.evanframework.query.AbstractQueryParam;
import org.evanframework.query.QueryParam;

import java.io.Serializable;
import java.util.Date;

/**
 * 敏感词查询对象
 */
public class SensitiveWordQuery extends AbstractQueryParam implements QueryParam, Serializable {
    private static final long serialVersionUID = 15593388464301L;

    private Integer[] idArray;//
    private Date gmtModifyFrom;//
    private Date gmtModifyTo;//
    private String words;//敏感词(逗号分隔)

    /****/
    public Integer[] getIdArray() {
        return idArray;
    }

    /****/
    public void setIdArray(Integer... idArray) {
        this.idArray = idArray;
    }

    /****/
    public Date getGmtModifyFrom() {
        return gmtModifyFrom;
    }

    /****/
    public void setGmtModifyFrom(Date gmtModifyFrom) {
        this.gmtModifyFrom = gmtModifyFrom;
    }

    /****/
    public Date getGmtModifyTo() {
        return gmtModifyTo;
    }

    /****/
    public void setGmtModifyTo(Date gmtModifyTo) {
        this.gmtModifyTo = gmtModifyTo;
    }

    /***敏感词(逗号分隔)*/
    public String getWords() {
        return words;
    }

    /***敏感词(逗号分隔)*/
    public void setWords(String words) {
        this.words = words;
    }
}
