package com.xudong.im.support;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created on 2017/9/20.
 *
 * @author evan.shen
 * @since
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.xudong.im.data.mongo"
})
public class MongoDBTestBeansConfig {

}
