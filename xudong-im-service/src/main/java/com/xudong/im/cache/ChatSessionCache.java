package com.xudong.im.cache;

import com.xudong.core.cache.AbstractCache;
import com.xudong.core.cache.RedisTemplateCreator;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * 聊天会话缓存
 */
@Component
public class ChatSessionCache extends AbstractCache<HashSet> {
    private static final String CACHE_NAME = ChatSessionCache.class.getSimpleName();
    private final static int REDIS_DATABASE_INDEX = 4;

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private RedisTemplateCreator redisTemplateCreator;

    @PostConstruct
    public void init() {
        super.init(CACHE_NAME, redisTemplateCreator, REDIS_DATABASE_INDEX, cacheManager);
    }

    public synchronized void put(String key, String value) {
        HashSet<String> values = get(key);
        if (CollectionUtils.isEmpty(values)) {
            values = new HashSet<>();
        }
        values.add(value);
        put(key, values);
    }

    public synchronized void remove(String key, String value) {
        HashSet<String> values = get(key);
        if (CollectionUtils.isEmpty(values)) {
            return;
        }
        values.remove(value);
        super.put(key, values);
    }
}