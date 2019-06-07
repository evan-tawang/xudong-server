package com.xudong.im.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2017/7/18.
 *
 * @author evan.shen
 */
@Configuration
@ComponentScan(basePackages = {
        "com.xudong.im.web"

})
public class WebApiAutoConfiguraion {

}

