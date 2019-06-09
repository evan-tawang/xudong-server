package com.xudong.im.session;

import com.xudong.core.cache.AbstractCache;
import com.xudong.core.cache.RedisTemplateCreator;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author evan.shen
 * @since 2018/1/10
 */
@Component
public class StaffAgentKeyCache extends AbstractCache<String> {
    private static final String CACHE_NAME = StaffAgentKeyCache.class.getSimpleName();

    private final static int REDIS_DATABASE_INDEX = 12;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplateCreator redisTemplateCreator;

    @PostConstruct
    public void init() {
        super.init(CACHE_NAME, redisTemplateCreator, REDIS_DATABASE_INDEX, cacheManager);
    }
}
