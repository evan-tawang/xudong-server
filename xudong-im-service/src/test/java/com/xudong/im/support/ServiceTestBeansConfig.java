package com.xudong.im.support;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created on 2017/7/18.
 *
 * @author evan.shen
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.xudong.im.config",
        "com.xudong.im.data",
        "com.xudong.im.cache",
        "com.xudong.im.manage",
        "com.xudong.im.service"
})//jdbc扫描包
public class ServiceTestBeansConfig {
}
