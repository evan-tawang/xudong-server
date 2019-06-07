package com.xudong.im.testdata;

import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.util.RandomUtil;

import java.math.BigDecimal;

/**
 * @author Evan.Shen
 * @since 2019/6/7
 */
public class BlackListTestData {
    public static BlackList random() {
        BlackList data = new BlackList();

        data.setContent(RandomUtil.randomName("TestContent"));
        data.setStatus(1);

        return data;
    }
}
