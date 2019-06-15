package com.xudong.im.cache;

import com.xudong.core.cache.RedisTemplateCreator;
import com.xudong.im.domain.limit.BlackList;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

//用于缓存黑名单原始数据库的数据
@Component
public class BlackListRedis {
    private static final String CACHE_NAME = BlackListRedis.class.getSimpleName();

    private final static int REDIS_DATABASE_INDEX = 8;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplateCreator redisTemplateCreator;

    private RedisTemplate redisTemplate;

    private BoundHashOperations<String, String, BlackList> hashOperations;
    //private BoundZSetOperations<String, BlackList> zSetOperations;

    @PostConstruct
    public void init() {
        redisTemplate = redisTemplateCreator.getRedisTemplate(REDIS_DATABASE_INDEX);
        //zSetOperations = redisTemplate.boundZSetOps(CACHE_NAME);
        hashOperations = redisTemplate.boundHashOps(CACHE_NAME);
    }

    public List<String> getMatchingRegexList() {
        return null;
    }

    //
    public void put(BlackList o) {
        hashOperations.put(o.getId() + "", o);
    }

    public List<BlackList> getList() {
        return hashOperations.values();
    }

//    public BlackList getOne(Integer id) {
//        return hashOperations.get(id + "");
//    }

    public void remove(Integer id) {
        hashOperations.delete(id + "");
    }

    public void clear() {
        Set<String> key = hashOperations.keys();
        if (!key.isEmpty()) {
            hashOperations.delete(key.toArray());
        }
    }
}
