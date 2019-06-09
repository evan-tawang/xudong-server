package test.com.xudong.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Evan.Shen
 * @since 2019/6/9
 */
@EnableAutoConfiguration()
@ComponentScan(basePackages = {"com.xudong.im.config"
})
public class RedisTest {

}
