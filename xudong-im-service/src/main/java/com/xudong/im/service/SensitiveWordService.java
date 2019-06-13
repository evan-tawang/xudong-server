package com.xudong.im.service;

import com.xudong.im.cache.SensitiveWordCache;
import com.xudong.im.manage.SensitiveWordManage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Evan.Shen
 * @since 2019-06-13
 */
@Service
public class SensitiveWordService {

    @Autowired
    private SensitiveWordCache sensitiveWordCache;

    @Autowired
    private SensitiveWordManage sensitiveWordManage;

    public String filter(String content) {
        String sensitiveWord = get();
        return content;
    }

    private String get() {
        String returnV = sensitiveWordCache.get();

        if (StringUtils.isBlank(returnV)) {
            returnV = sensitiveWordManage.get();
            sensitiveWordCache.put(returnV);
        }

        return returnV;
    }
}
