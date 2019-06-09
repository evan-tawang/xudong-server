package com.xudong.im.config.cache;

import com.xudong.core.cache.RedisTemplateCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@ConditionalOnClass({JedisConnectionFactory.class, RedisTemplate.class})
@ConditionalOnProperty(prefix = "spring.redis", name = {"host"})
public class RedisAutoConfiguration {

    /**
     * redisTemplateCreator 支持选库
     */
    @Bean(name = "redisTemplateCreator", destroyMethod = "destroy")
    public RedisTemplateCreator redisTemplateCreator(JedisConnectionFactory redisConnectionFactory) {
        RedisTemplateCreator redisTemplateCreator = new RedisTemplateCreator(redisConnectionFactory);
        return redisTemplateCreator;
    }

//    /**
//     * 直接使用redisTemplate，数据库根据配置文件配置，默认0
//     */
//    @Bean
//    public RedisTemplate<Serializable, Serializable> redisTemplate(JedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Serializable, Serializable> template = new RedisTemplate();
//        //设置序列化Key的实例化对象
//        template.setKeySerializer(new StringRedisSerializer());
//        //设置序列化Value的实例化对象
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
//
//    /**
//     * 直接使用stringRedisTemplate，数据库根据配置文件配置，默认0
//     */
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory redisConnectionFactory) {
//        StringRedisTemplate template = new StringRedisTemplate();
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }

//    /**
//     * 对redisTemplate的封装，数据库根据配置文件配置，默认0
//     */
//    @Bean
//    public RedisUtil redisUtil(RedisTemplate<Serializable, Serializable> redisTemplate) {
//        RedisUtil redisUtil = new RedisUtil(redisTemplate);
//        return redisUtil;
//    }
}
