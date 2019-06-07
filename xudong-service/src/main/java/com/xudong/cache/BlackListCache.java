package com.xudong.cache;



import com.sun.deploy.util.BlackList;
import com.xudong.util.RedisUtil;

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
