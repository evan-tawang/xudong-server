package com.xudong.im.config;

import com.xudong.core.sensitiveword.SensitiveWordIniter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Evan.Shen
 * @since 2019-06-13
 */
@Configuration
public class SensitiveWordAutoConfiguration {
    @Bean
    public SensitiveWordIniter sensitiveWordIniter() {
        return new SensitiveWordIniter();
    }
}
