package com.xudong.core.mes;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MES 系统配置
 * @author Evan.Shen
 * @since 2019-06-25
 */
@ConfigurationProperties("mes.api")
public class MesApiProperties {

    private String staffLogin;

    /**
     *
     */
    public String getStaffLogin() {
        return staffLogin;
    }

    /***/
    public void setStaffLogin(String staffLogin) {
        this.staffLogin = staffLogin;
    }

    @Override
    public String toString() {
        return "MesApiProperties{" +
                "staffLogin='" + staffLogin + '\'' +
                '}';
    }
}
