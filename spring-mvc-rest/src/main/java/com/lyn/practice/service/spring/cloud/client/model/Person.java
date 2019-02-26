package com.lyn.practice.service.spring.cloud.client.model;

/**
 * Created by liuyanan on 2018-09-14.
 */
public class Person {

    private String name;

    private String phoneNum;

    private String idCard;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
