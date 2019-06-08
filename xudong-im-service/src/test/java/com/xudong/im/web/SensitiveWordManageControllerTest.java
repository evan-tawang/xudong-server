package com.xudong.im.web;

import com.xudong.im.support.WebTestCaseSupport;
import com.xudong.im.util.RandomUtil;
import org.evanframework.dto.ApiResponse;
import org.junit.Test;

/**
 * @author Evan.Shen
 * @since 2019/6/8
 */
public class SensitiveWordManageControllerTest extends WebTestCaseSupport {


    @Test
    public void test() {
        String urlGet = getFullApiUri("/sensitiveWord/manage/get");
        String urlSave = getFullApiUri("/sensitiveWord/manage/save?words={0}");

        ApiResponse response = restTemplate.getForObject(urlGet, ApiResponse.class);

        LOGGER.info("====>> before update: " + response);

        response = restTemplate.postForObject(urlSave, null, ApiResponse.class, RandomUtil.randomString(5) + "," + RandomUtil.randomString(5));

        LOGGER.info("====>> updated: " + response);

        response = restTemplate.getForObject(urlGet, ApiResponse.class);

        LOGGER.info("====>> after update: " + response);
    }

//    private void printInfo(String[] words) {
//        for (String word : words) {
//            LOGGER.info("====>> " + word);
//        }
//    }

}
