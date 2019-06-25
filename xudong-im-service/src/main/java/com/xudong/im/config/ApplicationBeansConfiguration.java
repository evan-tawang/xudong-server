package com.xudong.im.config;

import com.xudong.im.session.UserAgentSession;
import com.xudong.im.session.auth.UserAuthInterceptor;
import com.xudong.im.session.reader.UserAgentReadFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 2017/7/18.
 *
 * @author evan.shen
 */
@Configuration
public class ApplicationBeansConfiguration implements WebMvcConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationBeansConfiguration.class);

    @Autowired
    private UrlPathHelper urlPathHelper;

    @Autowired
    private UserAgentSession userAgentSession;

    @Bean
    public FilterRegistrationBean loginUserFilterBean(UrlPathHelper urlPathHelper, UserAgentSession userAgentSession) {
        UserAgentReadFilter filter = new UserAgentReadFilter();
        filter.setUrlPathHelper(urlPathHelper);
        filter.setUserAgentSession(userAgentSession);

        FilterRegistrationBean loginUserFilterBean = new FilterRegistrationBean();

        loginUserFilterBean.setFilter(filter);

        loginUserFilterBean.setOrder(2);

        List<String> urlPatterns = new ArrayList<>();

        urlPatterns.add("/*");
        loginUserFilterBean.setUrlPatterns(urlPatterns);

        return loginUserFilterBean;
    }

    /**
     * 用户权限判断
     *
     * @return
     */
    @Bean(initMethod = "init")
    public UserAuthInterceptor userAuthInterceptor() {
        UserAuthInterceptor interceptor = new UserAuthInterceptor();
        interceptor.setUrlPathHelper(urlPathHelper);
        interceptor.setUserAgentSession(userAgentSession);
        return interceptor;
    }

    /**
     * 增加用户权限控制拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration userAuthInterceptorRegistration = registry.addInterceptor(userAuthInterceptor());

        userAuthInterceptorRegistration.addPathPatterns("/**");

        Set<String> set = new HashSet<>();

        set.add("/**/login");
        set.add("/**/login/debug");
        set.add("/**/logout");

        for (String e : set) {
            userAuthInterceptorRegistration.excludePathPatterns(e);
        }

        LOGGER.info(">>>> UserAuthInterceptor added, excludes {}", set);
    }

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
