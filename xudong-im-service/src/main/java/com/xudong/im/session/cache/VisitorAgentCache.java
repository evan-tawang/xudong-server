package com.xudong.im.session.cache;


import com.xudong.core.cache.AbstractCache;
import com.xudong.core.cache.RedisTemplateCreator;
import com.xudong.im.domain.user.VisitorAgent;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Evan.Shen
 * @since 2019/6/10
 */
@Component
public class VisitorAgentCache extends AbstractCache<VisitorAgent> {
    private final static int REDIS_DATABASE_INDEX = 6;


    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplateCreator redisTemplateCreator;

    @PostConstruct
    public void init() {
        super.init(VisitorAgentCache.class.getSimpleName(), redisTemplateCreator, REDIS_DATABASE_INDEX, cacheManager);
    }
}
