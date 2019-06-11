package com.xudong.im.session.cache.support;

import com.xudong.core.cache.AbstractCache;
import com.xudong.core.cache.RedisTemplateCreator;
import com.xudong.im.domain.user.support.UserAgent;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Evan.Shen
 * @since 2019/6/10
 */
@Deprecated
public class UserAgentCache extends AbstractCache<UserAgent> {
    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    protected RedisTemplateCreator redisTemplateCreator;

    public void init(int redisDatabaseIndex) {
        super.init(this.getClass().getSimpleName(), redisTemplateCreator, redisDatabaseIndex, cacheManager);
    }

}
