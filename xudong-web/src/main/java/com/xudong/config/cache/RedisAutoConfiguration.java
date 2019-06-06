package com.xudong.config.cache;

import com.xudong.util.RedisUtil;
import net.sf.ehcache.CacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
@ConditionalOnClass({JedisConnectionFactory.class, RedisTemplate.class, CacheManager.class})
@ConditionalOnProperty(prefix = "spring.redis", name = {"host"})
public class RedisAutoConfiguration {

    /**
     * 直接使用redisTemplate，数据库根据配置文件配置，默认0
     */
    @Bean
    public RedisTemplate<Serializable, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Serializable, Serializable> template = new RedisTemplate();
        //设置序列化Key的实例化对象
        template.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * 直接使用stringRedisTemplate，数据库根据配置文件配置，默认0
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


    /**
     * 对redisTemplate的封装，数据库根据配置文件配置，默认0
     */
    @Bean
    public RedisUtil redisUtil(RedisTemplate<Serializable, Serializable> redisTemplate) {
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        return redisUtil;
    }
}
