package com.xudong.im.config.service;

import com.xudong.core.sensitiveword.SensitiveWordIniter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author Evan.Shen
 * @since 2019-06-19
 */
@Configuration
public class ServiceBeansConfiguration {
    @Bean
    public SensitiveWordIniter sensitiveWordIniter() {
        return new SensitiveWordIniter();
    }

    /**
     * url处理工具
     */
    @Bean
    public UrlPathHelper urlPathHelper() {
        return new UrlPathHelper();
    }
}
