package com.xudong.im.config.mes;

import com.xudong.core.mes.MesApiClient;
import com.xudong.core.mes.MesApiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Evan.Shen
 * @since 2019-06-25
 */
@Configuration
@EnableConfigurationProperties({MesApiProperties.class})
public class MesAutoConfigutation {
    private static final Logger LOGGER = LoggerFactory.getLogger(MesAutoConfigutation.class);

//    @Autowired//
//    private RestTemplate restTemplate;

    @Bean
    public MesApiClient mesApiClient(ClientHttpRequestFactory simpleClientHttpRequestFactory, MesApiProperties mesProperties) {
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        MesApiClient mesApiClient = new MesApiClient(restTemplate,mesProperties);
        LOGGER.info(">>>> MesApiClient Caller inited, {}", mesProperties);
        return mesApiClient;
    }
}
