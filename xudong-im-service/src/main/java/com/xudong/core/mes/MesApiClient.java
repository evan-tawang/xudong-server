package com.xudong.core.mes;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Evan.Shen
 * @since 2019-06-25
 */
public class MesApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MesApiClient.class);

    private RestTemplate restTemplate;
    private MesApiProperties mesProperties;


    public MesApiClient(RestTemplate restTemplate, MesApiProperties mesProperties) {
        this.restTemplate = restTemplate;
        this.mesProperties = mesProperties;
    }

    public MesApiResult staffLogin(String account, String pwdMd5) {
        String url = mesProperties.getStaffLogin() + "?UserName={0}&Password={1}";
        String result = restTemplate.postForObject(url, null, String.class, account, pwdMd5);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("=======>> All mes api [login],account [account], result [{}]", result);
        }

        MesApiResult result2 = JSON.parseObject(result, MesApiResult.class);

        return result2;
    }

}
