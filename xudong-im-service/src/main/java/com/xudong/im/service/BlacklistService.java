package com.xudong.im.service;

import com.xudong.im.data.mapper.BlackListMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Evan.Shen
 * @since 2019-06-12
 */
public class BlacklistService {

    @Autowired
    private BlackListMapper blackListMapper;

    /**
     * 是否阻止
     * @param val
     * @return
     */
    public Boolean isBlock(String val){
        Boolean returnV = false;

        returnV = true;

        return returnV;
    }
}
