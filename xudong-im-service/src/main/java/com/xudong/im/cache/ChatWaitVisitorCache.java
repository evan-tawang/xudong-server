package com.xudong.im.cache;


import com.xudong.core.cache.RedisTemplateCreator;
import com.xudong.im.domain.chat.ChatCreateSessionDTO;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 会话等待缓存
 */
@Component
public class ChatWaitVisitorCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatWaitVisitorCache.class);

    private static final String CACHE_NAME = ChatWaitVisitorCache.class.getSimpleName();

    private final static int REDIS_DATABASE_INDEX = 5;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplateCreator redisTemplateCreator;

    private RedisTemplate redisTemplate;

    private BoundHashOperations<String, String, ChatCreateSessionDTO> hashOperations;

    @PostConstruct
    public void init() {
        redisTemplate = redisTemplateCreator.getRedisTemplate(REDIS_DATABASE_INDEX);
        hashOperations = redisTemplate.boundHashOps(CACHE_NAME);

        LOGGER.info(">>>> ChatWaitVisitorCache inited, Redis database index is [{}]", REDIS_DATABASE_INDEX);
    }

    public ChatCreateSessionDTO get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return hashOperations.get(key);
    }

    public void put(String key, ChatCreateSessionDTO dto) {
        hashOperations.put(key, dto);
    }
}
