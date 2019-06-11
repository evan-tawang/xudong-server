package com.xudong.im.manage;

import com.xudong.im.data.mapper.SensitiveWordMapper;
import com.xudong.im.domain.limit.SensitiveWord;
import com.xudong.im.domain.limit.SensitiveWordQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 敏感词管理
 * @author Evan.Shen
 * @since 2019/6/7
 */
@Service
public class SensitiveWordManage {
    @Autowired
    private SensitiveWordMapper sensitiveWordMapper;

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
    }

    public String[] get() {
        List<SensitiveWord> list = sensitiveWordMapper.queryList(new SensitiveWordQuery());
        if (list.isEmpty()) {
            return new String[]{};
        } else {
            SensitiveWord o = list.get(0);
            String words = o.getWords();

            if (StringUtils.isBlank(words)) {
                return new String[]{};
            } else {
                return words.split(",");
            }
        }
    }
}
