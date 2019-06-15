package com.xudong.im.cache;

import com.xudong.core.cache.EHCacheUtil;
import com.xudong.im.domain.BlacklistMatchingRegexList;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Evan.Shen
 * @since 2019-06-15
 */

/**
 * 用于缓存黑名单初始化之后的所有规则，每次过滤时从这里读取，这里没有或者过期则从redis读取原始数据然后重新初始化后再放到EhCache
 */
@Component
public class BlackListEHCache {
    private static final String CACHE_NAME = BlackListEHCache.class.getSimpleName();
    private static final String CACHE_KEY = CACHE_NAME + "_KEY";

    private EHCacheUtil ehCacheUtil;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void init() {

        CacheConfiguration conf = new CacheConfiguration();

        conf.setName(CACHE_NAME);

        if (conf.getTimeToIdleSeconds() == 0) {
            conf.setTimeToIdleSeconds(180);
        }
        if (conf.getTimeToLiveSeconds() == 0) {
            conf.setTimeToLiveSeconds(180);
        }

        ehCacheUtil = new EHCacheUtil(cacheManager, conf);
    }

    /**
     * @param value
     */
    public void put(BlacklistMatchingRegexList value) {
        ehCacheUtil.put(CACHE_KEY, value);
    }

    /**
     * @return
     */
    public BlacklistMatchingRegexList get() {
        return ehCacheUtil.get(CACHE_KEY, BlacklistMatchingRegexList.class);
    }

    /**
     * 移除
     */
    public void remove() {
        ehCacheUtil.remove(CACHE_KEY);
    }
}
