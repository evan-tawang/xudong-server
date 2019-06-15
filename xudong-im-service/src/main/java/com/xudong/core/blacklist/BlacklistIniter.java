package com.xudong.core.blacklist;

import com.xudong.im.domain.BlacklistMatchingRegexList;
import com.xudong.im.domain.limit.BlackList;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 黑名单初始化器
 *
 * @author Evan.Shen
 * @since 2019-06-14
 */
public class BlacklistIniter {
    public BlacklistMatchingRegexList init(List<BlackList> blackLists) {
//        //分别获取三种配置方式配置的IP
//        String allowIP = "";
//        String allowIPRange = "";
//        String allowIPWildcard = "";
//
//        //对用户配置的三种方式的IP白名单进行格式校验
//        if (!validate(allowIP, allowIPRange, allowIPWildcard)) {
//            throw new RuntimeException("IP名单格式定义异常!");
//        }

        BlacklistMatchingRegexList matchingRegexList = new BlacklistMatchingRegexList();

        for (BlackList blackList : blackLists) {
            String content = blackList.getContent();
            if (StringUtils.containsAny(content, '.')) { //ip
                if (StringUtils.containsAny(content, '-')) { //ip段
                    String[] addrParts = content.split("-");

                    if (null != addrParts && addrParts.length > 0 && addrParts.length <= 2) {
                        String from = addrParts[0];
                        String to = addrParts[1];
                        String prefix = from.substring(0, from.lastIndexOf(".") + 1);

                        int start = Integer.parseInt(from.substring(from.lastIndexOf(".") + 1, from.length()));
                        int end = Integer.parseInt(to.substring(to.lastIndexOf(".") + 1, to.length()));

                        for (int i = start; i <= end; i++) {
                            matchingRegexList.add(prefix + i);
                        }

                    } else {
                        throw new RuntimeException("IP列表格式定义异常!");
                    }
                } else if (StringUtils.containsAny(content, '*')) {//ip通配符
                    if (content.indexOf("*") != -1) {
                        //将*,替换成匹配单端ip地址的正则表达式
                        content = content.replaceAll("\\*", "(1\\\\d{1,2}|2[0-4]\\\\d|25[0-5]|\\\\d{1,2})");
                        content = content.replaceAll("\\.", "\\\\.");//对.进行转义
                        matchingRegexList.add(content);
                    } else {
                        throw new RuntimeException("IP白名单格式定义异常!");
                    }
                } else { //单个ip
                    matchingRegexList.add(content);
                }
            }else{
                matchingRegexList.add(content);
            }
        }

        return matchingRegexList;
    }

    private boolean validate(String allowIP, String allowIPRange, String allowIPWildcard) {

        //匹配IP地址每一段的正则
        String regx = "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})";
        //把四段用点连接起来,那就是匹配整个ip地址的表达式
        String ipRegx = regx + "\\." + regx + "\\." + regx + "\\." + regx;

        //校验第一种配置方式配置的IP白名单格式是否正确
        Pattern pattern = Pattern.compile("(" + ipRegx + ")|(" + ipRegx + "(,|;))*");
        if (!this.validate(allowIP, pattern)) {
            return false;
        }

        //校验第二种配置方式配置的的IP白名单格式是否正确
        pattern = Pattern.compile("(" + ipRegx + ")\\-(" + ipRegx + ")|" + "((" + ipRegx + ")\\-(" + ipRegx + ")(,|;))*");
        if (!this.validate(allowIPRange, pattern)) {
            return false;
        }

        //校验第三种配置方式配置的的IP白名单格式是否正确
        pattern = Pattern.compile("(" + regx + "\\." + regx + "\\." + regx + "\\." + "\\*)|" + "(" + regx + "\\." + regx + "\\." + regx + "\\." + "\\*(,|;))*");
        if (!this.validate(allowIPWildcard, pattern)) {
            return false;
        }
        return true;
    }

    //校验用户配置的ip列表格式是否正确
    private boolean validate(String allowIP, Pattern pattern) {
        //如果为空则不做处理
        if (null != allowIP && !allowIP.trim().equals("")) {
            StringBuilder sb = new StringBuilder(allowIP);

            //如果用户配置的IP配置了多个,但没有以分号结尾,这里就给它加上分号
            if (!allowIP.endsWith(";")) {
                sb.append(";");
            }
            //如果不匹配
            if (!pattern.matcher(sb).matches()) {
                return false;
            }
        }
        return true;
    }
}
