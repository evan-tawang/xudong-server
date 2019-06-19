package com.xudong.im.session.cache;


import com.xudong.core.cache.AbstractCache;
import com.xudong.core.cache.RedisTemplateCreator;
import com.xudong.im.domain.user.StaffAgent;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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
    private final static int REDIS_DATABASE_INDEX = 5;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplateCreator redisTemplateCreator;

    private RedisTemplate redisTemplate;
    private BoundHashOperations<String, String, StaffAgent> hashOperations;

    @PostConstruct
    public void init() {
        super.init(CACHE_NAME, redisTemplateCreator, REDIS_DATABASE_INDEX, cacheManager);

        redisTemplate = redisTemplateCreator.getRedisTemplate(REDIS_DATABASE_INDEX);
        hashOperations = redisTemplate.boundHashOps(CACHE_NAME);
    }

    public List<StaffAgent> getAll() {
        List<StaffAgent> list = hashOperations.values();
        return list;
    }

    /**
     * @param status
     * @return
     * @see com.xudong.im.enums.OnlineStatusEnum
     */
    public List<StaffAgent> getByOnlineStatus(Integer status) {
        List<StaffAgent> list = getAll();
        List<StaffAgent> list2 = new ArrayList<>();
        for (StaffAgent e : list) {
            if (status.equals(e.getOnlineStatus())) {
                list2.add(e);
            }
        }

        return list2;
    }

}
