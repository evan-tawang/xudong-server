package com.xudong.im.cache;

import com.alibaba.fastjson.JSONArray;
import com.xudong.core.cache.RedisTemplateCreator;
import com.xudong.im.domain.chat.SessionBO;
import com.xudong.im.domain.chat.SessionBOList;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 聊天会话缓存
 */
@Component
public class ChatSessionCache {
    private static final String CACHE_NAME = ChatSessionCache.class.getSimpleName();
    private final static int REDIS_DATABASE_INDEX = 4;

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private RedisTemplateCreator redisTemplateCreator;
    private RedisTemplate redisTemplate;
    private BoundHashOperations<String, String, List<SessionBO>> hashOperations;

    @PostConstruct
    public void init() {
        redisTemplate = redisTemplateCreator.getRedisTemplate(REDIS_DATABASE_INDEX);
        hashOperations = redisTemplate.boundHashOps(CACHE_NAME);
    }

    public Set<String> get(String key) {
        List<SessionBO> list = getValues(key);
        if(CollectionUtils.isEmpty(list)){
            return new HashSet<>();
        }
        return list.stream().filter(bo->bo.getSessionId() != null).map(SessionBO::getSessionId).collect(Collectors.toSet());
    }

    public void put(String key, String value) {
        List<SessionBO> list = getValues(key);

        if(list == null){
            list = new SessionBOList();
        }
        list.add(new SessionBO(key, value));

        hashOperations.put(key, list);
    }

    public void remove(String key, String value) {
        List<SessionBO> list = getValues(key);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        list.remove(new SessionBO(key, value));

        if (CollectionUtils.isEmpty(list)) {
            hashOperations.delete(key);
        } else {
            hashOperations.put(key, list);
        }
    }

    public Map<String, List<String>> getAll() {
        Map<String, List<String>> map = new HashMap<>();

        List<SessionBO> sessionBOList = getAllValues();
        if (CollectionUtils.isEmpty(sessionBOList)) {
            return map;
        }

        for (SessionBO bo : sessionBOList) {
            List<String> list = map.get(bo.getStaffId());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(bo.getSessionId());
            map.put(bo.getStaffId(),list);
        }
        return map;
    }

    public List<SessionBO> getValues(String key){
        Object jsonArray = hashOperations.get(key);
        return JSONArray.parseArray(JSONArray.toJSONString(jsonArray), SessionBO.class);
    }

    public List<SessionBO> getAllValues(){
        List values = hashOperations.values();
        if(CollectionUtils.isEmpty(values)){
            return new ArrayList<>();
        }
        return JSONArray.parseArray(JSONArray.toJSONString(values.get(0)), SessionBO.class);
    }
}