package com.xudong.im.cache;

import com.sun.deploy.util.BlackList;
import com.xudong.im.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class BlackListCache {
    private static final Class CLZ = BlackList.class;
    private static final String CACHE_NAME = BlackListCache.class.getSimpleName();

    @Autowired
    private RedisUtil redisUtil;

    public BlackList get(Serializable key){
        return redisUtil.get(CACHE_NAME,key,BlackList.class);
    }

    public void put(Serializable key,BlackList blackList) {
        redisUtil.put(CACHE_NAME,key,blackList,1000*10);
    }
}
