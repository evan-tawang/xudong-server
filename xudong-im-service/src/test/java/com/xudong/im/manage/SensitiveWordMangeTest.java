package com.xudong.im.manage;

import com.xudong.im.support.ServiceTestCaseSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Evan.Shen
 * @since 2019/6/7
 */
public class SensitiveWordMangeTest extends ServiceTestCaseSupport {
    @Autowired
    private SensitiveWordMange sensitiveWordMange;

    @Test
    public void testSave(){
        sensitiveWordMange.save("198,123");
    }

    @Test
    public void testGet(){
        String[] words = sensitiveWordMange.get();
        for(String word : words){
            LOGGER.info("====>> testGet: " + word);
        }
    }
}
