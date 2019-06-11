package com.xudong.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan(basePackages = {
        "com.xudong.im.config",
        "com.xudong.im.data",
        "com.xudong.im.cache",
        "com.xudong.im.manage",
        "com.xudong.im.service",
        "com.xudong.im.session",
        "com.xudong.im.web",
        "com.xudong.core.websocket",
})
@EnableWebSocket
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
