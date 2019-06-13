package com.xudong.im.cache;

import com.xudong.core.cache.RedisTemplateCreator;
import com.xudong.im.domain.help.TalkSkill;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Evan.Shen
 * @since 2019-06-13
 */

@Component
public class TalkSkillCache {
    private static final String CACHE_NAME = TalkSkillCache.class.getSimpleName();

    private final static int REDIS_DATABASE_INDEX = 6;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplateCreator redisTemplateCreator;

    private RedisTemplate redisTemplate;

    private BoundHashOperations<String, Integer, TalkSkill> hashOperations;
    private BoundZSetOperations<String, TalkSkill> zSetOperations;

    @PostConstruct
    public void init() {
        redisTemplate = redisTemplateCreator.getRedisTemplate(REDIS_DATABASE_INDEX);
        zSetOperations = redisTemplate.boundZSetOps(CACHE_NAME);
        hashOperations = redisTemplate.boundHashOps(CACHE_NAME);
    }

    public void put(TalkSkill o) {
        hashOperations.put(o.getId(), o);
    }

    public List<TalkSkill> getList() {
        return hashOperations.values();
    }

    public TalkSkill getOne(Integer id) {
        return hashOperations.get(id);
    }

    public void remove(Integer id) {
        hashOperations.delete(id);
    }
}