package com.xudong.core.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author evan.shen
 * @since 2017/8/28

 */
public class SignAuthUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignAuthUtil.class);

    /**
     * @param request
     * @param secret  秘钥
     * @return
     */
    public static boolean validateSign(HttpServletRequest request, String secret) {
        String signInRequest = request.getHeader("sign");

        if (StringUtils.isBlank(signInRequest)) {
            LOGGER.warn(">>>> 接口[{}]缺少sign", request.getRequestURI());
            return false;
        }

        StringBuilder tmp = new StringBuilder();

        tmp.append(secret);
        String random = request.getHeader("random");
        tmp.append(random);
        String signInServer = DigestUtils.sha1Hex(tmp.toString());

        return StringUtils.equalsIgnoreCase(signInServer, signInRequest);
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.sha256Hex("1234"));
    }
}
