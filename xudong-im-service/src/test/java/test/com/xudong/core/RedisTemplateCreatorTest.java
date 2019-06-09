package test.com.xudong.core;

import com.xudong.core.cache.RedisTemplateCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPoolConfig;
import test.com.xudong.im.support.CacheTestBeansConfig;

/**
 * @author evan.shen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CacheTestBeansConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RedisTemplateCreatorTest  {

    private RedisTemplateCreator redisTemplateCreator;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Before
    public void init() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        JedisPoolConfig config = new JedisPoolConfig();
//        factory.setPoolConfig(config);
        redisTemplateCreator = new RedisTemplateCreator(jedisConnectionFactory);
    }

    @Test
    public void test() {
        int[] databaseIndexes = new int[]{1, 10, 6, 9, 2, 5, 4, 3, 7, 8, 12, 15, 11, 14};

        long begin = System.currentTimeMillis();
        System.out.println(begin);
        long end;
        long begin1 = 0;
        for (int i = 0; i < 1000; i++) {
            for (int j : databaseIndexes) {
                RedisTemplate redisTemplate = redisTemplateCreator.getRedisTemplate(j);
                redisTemplate.opsForValue().set("key" + i + "_" + j, "value" + i + "_" + j);
            }

            if (i % 100 == 0) {
                end = System.currentTimeMillis();
                System.out.println(end - begin1);
                begin1 = end;
            }
        }
        end = System.currentTimeMillis();
        System.out.println(end - begin);
    }
}
