package com.xudong.im.cache;

import com.xudong.core.cache.AbstractCache;
import com.xudong.core.cache.RedisTemplateCreator;
import com.xudong.im.domain.limit.BlackList;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BlackListCache extends AbstractCache<BlackList> {
    private static final Class CLZ = BlackList.class;
    private static final String CACHE_NAME = BlackListCache.class.getSimpleName();

    private final static int REDIS_DATABASE_INDEX = 8;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplateCreator redisTemplateCreator;

    @PostConstruct
    public void init() {
        super.init(CACHE_NAME, redisTemplateCreator, REDIS_DATABASE_INDEX, cacheManager);

        setRedisExpireSeconds(14400);
    }


}
