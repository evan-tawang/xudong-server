package com.xudong.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author Evan.Shen
 * @since 2019-06-19
 */
@Configuration
public class UtilBeansConfiguration {
    /**
     * url处理工具
     */
    @Bean
    public UrlPathHelper urlPathHelper() {
        return new UrlPathHelper();
    }
}
