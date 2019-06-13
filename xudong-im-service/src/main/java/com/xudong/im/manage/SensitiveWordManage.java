package com.xudong.im.manage;

import com.xudong.core.sensitiveword.SensitiveWordIniter;
import com.xudong.im.data.mapper.SensitiveWordMapper;
import com.xudong.im.domain.limit.SensitiveWord;
import com.xudong.im.domain.limit.SensitiveWordQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 敏感词管理
 *
 * @author Evan.Shen
 * @since 2019/6/7
 */
@Service
public class SensitiveWordManage {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveWordManage.class);

//    @Autowired
////    private SensitiveWordCache sensitiveWordCache;

    @Autowired
    private SensitiveWordMapper sensitiveWordMapper;

    @Autowired
    private SensitiveWordIniter sensitiveWordIniter;

    @Transactional
    public void save(String words) {
        List<SensitiveWord> list = sensitiveWordMapper.queryList(new SensitiveWordQuery());

        SensitiveWord o = new SensitiveWord();
        o.setWords(words);

        if (list.isEmpty()) {
            sensitiveWordMapper.insert(o);
        } else {
            o.setId(list.get(0).getId());
            sensitiveWordMapper.update(o);
        }

        sensitiveWordIniter.initKeyWord(words);
    }

    public String get() {
        List<SensitiveWord> list = sensitiveWordMapper.queryList(new SensitiveWordQuery());
        if (list.isEmpty()) {
            return "";
        } else {
            SensitiveWord o = list.get(0);
            String words = o.getWords();
            return words;
        }
    }

//    public String[] getForArray() {
//        String words = get();
//
//        if (StringUtils.isBlank(words)) {
//            return new String[]{};
//        } else {
//            return words.split(",");
//        }
//    }

    public void refreshCache() {
        String words = get();
        sensitiveWordIniter.initKeyWord(words);
    }
}
