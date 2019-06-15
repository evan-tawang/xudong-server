package com.xudong.im.config;

import com.xudong.core.sensitiveword.SensitiveWordIniter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2017/7/18.
 *
 * @author evan.shen
 */
@Configuration
public class ServiceAutoConfiguration {
    @Bean
    public SensitiveWordIniter sensitiveWordIniter() {
        return new SensitiveWordIniter();
    }
}
