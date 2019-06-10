package com.xudong.im.config.http;

import com.xudong.core.restful.TokenClientHttpRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * RestTemplate自动配置，各springboot的通讯，统一采用该RestTemplate
 *
 * @author evan.shen
 * @since 2017/11/1
 */
@Configuration
@ConditionalOnClass({ClientHttpRequestFactory.class, RestTemplate.class})
@EnableConfigurationProperties({ClientHttpRequestFactoryProperties.class})
public class RestTemplateAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateAutoConfiguration.class);

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(ClientHttpRequestFactoryProperties clientHttpRequestFactoryProperties) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(clientHttpRequestFactoryProperties.getReadTimeout());
        factory.setConnectTimeout(clientHttpRequestFactoryProperties.getConnectTimeout());
        return factory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory simpleClientHttpRequestFactory, ClientHttpRequestInterceptor tokenClientHttpRequestInterceptor) {
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(tokenClientHttpRequestInterceptor);

        restTemplate.setInterceptors(interceptors);

        LOGGER.info(">>>> RestTemplate inited");

        return restTemplate;
    }

    @Bean
    public TokenClientHttpRequestInterceptor tokenClientHttpRequestInterceptor() {
        TokenClientHttpRequestInterceptor tokenClientHttpRequestInterceptor = new TokenClientHttpRequestInterceptor();
        return tokenClientHttpRequestInterceptor;
    }
}
