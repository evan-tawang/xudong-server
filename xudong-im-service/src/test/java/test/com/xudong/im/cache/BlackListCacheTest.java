package test.com.xudong.im.cache;

import com.xudong.im.cache.BlackListRedis;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.CacheTestCaseSupport;

public class BlackListCacheTest extends CacheTestCaseSupport {

    @Autowired
    private BlackListRedis blackListCache;

    @Test
    public void test() {
//        blackListCache.put(1,new BlackList(1));
//        System.out.println( blackListCache.get(1));
    }
}
