package com.xudong.im.support;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Created on 2017/9/20.
 *
 * @author evan.shen
 * @since
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.xudong.im.data.mongo"
}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
})})
public class MongoDBTestBeansConfig {

}
