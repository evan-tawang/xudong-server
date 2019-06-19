package test.com.xudong.im.support;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created on 2017/7/18.
 *
 * @author evan.shen
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.xudong.im.config.cache",
        "com.xudong.im.config.database",
        "com.xudong.im.config.service",
        "com.xudong.im.data",
        "com.xudong.im.cache",
        "com.xudong.im.manage",
        "com.xudong.im.service",
        "com.xudong.core.websocket"
})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ServiceTestBeansConfig {
}
