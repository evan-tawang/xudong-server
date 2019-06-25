package com.xudong.im.domain.user;

import java.io.Serializable;

/**
 * @author Evan.Shen
 * @since 2019-06-25
 */
public class VisitorLoginDTO implements Serializable {

    private static final long serialVersionUID = 8776429104149356562L;

    private String customerId;

    private String customerName;

    private String customerTel;

    /**
     *
     */
    public String getCustomerId() {
        return customerId;
    }

    /***/
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     *
     */
    public String getCustomerName() {
        return customerName;
    }

    /***/
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     */
    public String getCustomerTel() {
        return customerTel;
    }

    /***/
    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    @Override
    public String toString() {
        return "VisitorLoginDTO{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerTel='" + customerTel + '\'' +
                '}';
    }
}
