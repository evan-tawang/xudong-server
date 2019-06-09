package com.xudong.im.session;


import com.xudong.core.cache.AbstractCache;
import com.xudong.core.cache.RedisTemplateCreator;
import com.xudong.im.domain.user.StaffAgent;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 平台登录用户缓存
 * <p>
 * create at 2014年4月15日 上午12:33:42
 *
 * @author Evan.Shen
 * @see
 * @since %I%, %G%
 */
@Component
public class StaffAgentCache extends AbstractCache<StaffAgent> {
    private static final String CACHE_NAME = StaffAgentCache.class.getSimpleName();

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
