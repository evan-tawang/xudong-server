package com.xudong.im.cache;

import com.xudong.core.cache.EHCacheUtil;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author Evan.Shen
 * @since 2019-06-13
 */
//@Component
@Deprecated
public class SensitiveWordCache { //敏感词过滤cache不采用redis，不继承AbstractCache

    private static final String CACHE_NAME = SensitiveWordCache.class.getSimpleName();
    private static final String CACHE_KEY = CACHE_NAME + "_KEY";

    private EHCacheUtil ehCacheUtil;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void init() {

        CacheConfiguration conf = new CacheConfiguration();

        conf.setName(SensitiveWordCache.class.getSimpleName());

        if (conf.getTimeToIdleSeconds() == 0) {
            conf.setTimeToIdleSeconds(300);
        }
        if (conf.getTimeToLiveSeconds() == 0) {
            conf.setTimeToLiveSeconds(300);
        }

        ehCacheUtil = new EHCacheUtil(cacheManager, conf);
    }

    /**
     * @param value
     */
    public void put(String value) {
        ehCacheUtil.put(CACHE_KEY, value);
    }

    /**
     * @return
     */
    public String get() {
        return ehCacheUtil.get(CACHE_KEY, String.class);
    }

    /**
     * 移除
     */
    public void remove() {
        ehCacheUtil.remove(CACHE_KEY);
    }
}
