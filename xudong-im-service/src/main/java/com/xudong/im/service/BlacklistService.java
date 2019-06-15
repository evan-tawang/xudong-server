package com.xudong.im.service;

import com.xudong.core.blacklist.BlacklistIniter;
import com.xudong.im.cache.BlackListEHCache;
import com.xudong.im.cache.BlackListRedis;
import com.xudong.im.domain.BlacklistMatchingRegexList;
import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.manage.BlackListManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Evan.Shen
 * @since 2019-06-12
 */
@Service
public class BlacklistService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlacklistService.class);

    @Autowired
    private BlackListManage blackListManage;

    @Autowired
    private BlackListRedis blackListRedis;

    @Autowired
    private BlackListEHCache blackListEHCache;

    private BlacklistIniter blacklistIniter = new BlacklistIniter();

    @PostConstruct
    private void init() {
        //blacklistUtil = new BlacklistUtil(blackListManage);
        //LOGGER.info(blackListManage + "");
    }


    /**
     * 是否阻止
     *
     * @param val
     * @return
     */
    public boolean isBlock(String val) {
        Assert.hasLength(val, "要检查的值不能为空");

        List<String> matchingRegexList = getMatchingRegexList();

        if (null == matchingRegexList || matchingRegexList.size() == 0) {
            return false;
        }

        for (String regex : matchingRegexList) {
            if (val.matches(regex)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getMatchingRegexList() {
        BlacklistMatchingRegexList matchingRegexList = blackListEHCache.get();

        if (matchingRegexList == null || matchingRegexList.isEmpty()) {
            List<BlackList> list = blackListRedis.getList();

            if (list == null || list.isEmpty()) {
                list = blackListManage.getNormal();
                for (BlackList e : list) {
                    blackListRedis.put(e);
                }
            }
            matchingRegexList = blacklistIniter.init(list);

            blackListEHCache.put(matchingRegexList);
        }
        return matchingRegexList;
    }
}
