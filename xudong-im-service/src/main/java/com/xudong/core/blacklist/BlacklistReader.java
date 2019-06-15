package com.xudong.core.blacklist;

import java.util.List;

/**
 * @author Evan.Shen
 * @since 2019-06-14
 */
@Deprecated
public interface BlacklistReader {
    /**
     * 获取黑名单正则列表
     *
     * @return
     */
    List<String> getMatchingRegexList();
}
