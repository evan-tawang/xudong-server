package com.xudong.im.service;

import com.xudong.core.sensitiveword.SensitiveWordIniter;
import com.xudong.core.sensitiveword.SensitiveWordFilter;
import com.xudong.im.manage.SensitiveWordManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Evan.Shen
 * @since 2019-06-13
 */
@Service
public class SensitiveWordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveWordService.class);

    @Autowired
    private SensitiveWordIniter sensitiveWordIniter;

    @Autowired
    private SensitiveWordManage sensitiveWordManage;

    private SensitiveWordFilter sensitivewordFilter;

    @PostConstruct
    private void init() {
        String words = sensitiveWordManage.get();
        sensitiveWordIniter.initKeyWord(words);
        sensitivewordFilter = new SensitiveWordFilter(sensitiveWordIniter);
    }

    public String filter(String content) {
        String result = sensitivewordFilter.replaceSensitiveWord(content, 2, "*");
        return result;
    }

//    private String get() {
//        String returnV = sensitiveWordCache.get();
//
//        if (StringUtils.isBlank(returnV)) {
//            returnV = sensitiveWordManage.get();
//            sensitiveWordCache.put(returnV);
//        }
//
//        return returnV;
//    }
}
