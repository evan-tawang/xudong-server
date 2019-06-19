package com.xudong.core.util;


import com.xudong.im.domain.user.support.UserAgent;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户权限判断工具
 *
 * @author evan.shen
 * @since 2017/11/24
 */
public class UserAuthUtil {

    /**
     * 是否需要判断权限
     *
     * @param userAgent
     * @return
     */
    public static boolean isNeedAuth(UserAgent userAgent) {
        boolean returnV = true;
//        if (userAgent.isSystemAdmin()) {//平台超级管理，拥有全部权限
//            returnV = false;
//        }
        return returnV;
    }

    /**
     * @param userFunctions  用户的权限,支持带*的模式匹配
     * @param allowfunctions 允许的权限
     * @return
     */
    public static boolean haveOneOfFunctions(Set<String> userFunctions, Set<String> allowfunctions) {

        if (!Collections.disjoint(userFunctions, allowfunctions)) {
            return true;
        }

        for (String userFunction : userFunctions) {
            Pattern pattern = Pattern.compile(userFunction);
            for (String allowfunction : allowfunctions) {
                if (match(pattern, allowfunction)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param userFunctions 用户的权限, 支持带*的模式匹配
     * @param allowfunction 允许的权限
     * @return
     */
    public static boolean isMatch(Set<String> userFunctions, String allowfunction) {
        if (userFunctions.contains(allowfunction)) {
            return true;
        }

        for (String userFunction : userFunctions) {
            Pattern pattern = Pattern.compile(userFunction);
            if (match(pattern, allowfunction)) {
                return true;
            }
        }
        return false;
    }

    private static boolean match(Pattern pattern, String allowfunction) {
        Matcher m = pattern.matcher(allowfunction);
        if (m.matches()) {
            return true;
        }
        return false;
    }
}
