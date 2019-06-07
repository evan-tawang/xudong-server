package com.xudong.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.xudong.im.config",
        "com.xudong.im.data",
        "com.xudong.im.cache",
        "com.xudong.im.manage",
        "com.xudong.im.service",
        "com.xudong.im.web",

})
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
