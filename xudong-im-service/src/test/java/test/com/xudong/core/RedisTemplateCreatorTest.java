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
import test.com.xudong.im.support.CacheTestBeansConfig;

/**
 * @author evan.shen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CacheTestBeansConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RedisTemplateCreatorTest {

    private static int[] databaseIndexes = new int[]{1, 10, 6, 9, 2, 5, 4, 3, 7, 8, 12, 15, 11, 14};
    private RedisTemplateCreator redisTemplateCreator;
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;
    @Autowired
    private RedisTemplate redisTemplate;

    @Before
    public void init() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        JedisPoolConfig config = new JedisPoolConfig();
//        factory.setPoolConfig(config);
        redisTemplateCreator = new RedisTemplateCreator(jedisConnectionFactory);
    }

    @Test
    public void test1() {
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

    @Test
    public void test2() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            Thread thread = new Thread(new PutThread(redisTemplateCreator, i));
            //thread.setDaemon(false);
            thread.start();
        }

        Thread.sleep(99999999999999L);
    }
}

class PutThread implements Runnable {
    private RedisTemplateCreator redisTemplateCreator;
    private int databaseIndex;
    private RedisTemplate redisTemplate;

    public PutThread(RedisTemplateCreator redisTemplateCreator, int databaseIndex) {
        this.redisTemplateCreator = redisTemplateCreator;
        this.databaseIndex = databaseIndex;
        this.redisTemplate = redisTemplateCreator.getRedisTemplate(databaseIndex);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            redisTemplate.opsForValue().set("key" + i, "value");
            if (i % 100 == 0) {
                JedisConnectionFactory redisConnectionFactory = (JedisConnectionFactory) this.redisTemplate.getConnectionFactory();
                System.out.println("====>pub database " + redisConnectionFactory.getDatabase());
            }
        }
    }
}
