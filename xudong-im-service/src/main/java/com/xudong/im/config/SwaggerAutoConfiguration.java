package com.xudong.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author evan.shen
 * @since 2018/6/25
 */
@Configuration
public class SwaggerAutoConfiguration {
    @Bean
    public Docket swaggerApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Api document")
                .version("1.0")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                //.globalOperationParameters(pars)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xudong"))
                .build();
    }
}
