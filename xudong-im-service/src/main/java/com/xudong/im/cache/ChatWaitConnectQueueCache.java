package com.xudong.im.cache;


import com.xudong.core.cache.RedisTemplateCreator;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 会话等待缓存
 */
@Component
public class ChatWaitConnectQueueCache {
    private static final String CACHE_NAME = ChatWaitConnectQueueCache.class.getSimpleName();

    private final static int REDIS_DATABASE_INDEX = 4;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplateCreator redisTemplateCreator;

    private RedisTemplate redisTemplate;

    private BoundListOperations<String, String> listOperations;

    @PostConstruct
    public void init() {
        redisTemplate = redisTemplateCreator.getRedisTemplate(REDIS_DATABASE_INDEX);
        listOperations = redisTemplate.boundListOps(CACHE_NAME);
    }

    public String rightPop() {
        return listOperations.rightPop();
    }

    public void leftPush(String key) {
        listOperations.leftPush(key);
    }
}
