package com.xudong.cache;

import com.xudong.domain.limit.BlackList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BlackListCacheTest {

    @Autowired
    private BlackListCache blackListCache;

    @Test
    public void test(){
        blackListCache.put(1,new BlackList(1));
        System.out.println( blackListCache.get(1));
    }
}
