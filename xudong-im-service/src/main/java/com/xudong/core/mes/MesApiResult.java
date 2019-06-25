package com.xudong.core.mes;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * @author Evan.Shen
 * @since 2019-06-25
 */
public class MesApiResult extends HashMap<String, String> {

    public String getUserId() {
        return super.get("UserId");
    }

    public boolean isSuccess() {
        String result = super.get("Result");

        if (result != null) {
            return "ok".equals(StringUtils.lowerCase(result));
        } else {
            return false;
        }
    }

    public String getMsg() {
        return super.get("Msg");
    }
}
