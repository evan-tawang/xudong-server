package com.xudong.im.config.cache;

import net.sf.ehcache.CacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author evan.shen
 * @since 2018/2/6
 */
@Configuration
@ConditionalOnClass({CacheManager.class})
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class EHCacheAutoConfiguration {
    /**
     * ehcache,如果有使用本地缓存的场景可用
     */
    @Bean(destroyMethod = "shutdown")
    public CacheManager cacheManager() {
        CacheManager cacheManager = new CacheManager();
        return cacheManager;
    }
}
