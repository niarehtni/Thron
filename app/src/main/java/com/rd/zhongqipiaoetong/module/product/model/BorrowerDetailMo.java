package com.rd.zhongqipiaoetong.module.product.model;

import java.util.List;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/2 10:20
 * <p/>
 * Description:借款人资料
 */
public class BorrowerDetailMo {
    /**
     * 用户名字
     */
    private String                 name;
    /**
     * 年龄
     */
    private String                 age;
    /**
     * 学历
     */
    private String                 education;
    /**
     * 婚姻状态 0：未婚 1：已婚
     */
    private String                 marriage;
    /**
     * 户籍城市
     */
    private String                 city;
    /**
     * 工作年限
     */
    private String                 workTime;
    /**
     * 收入
     */
    private String                 income;
    /**
     * 是否有车 0：无  1：有
     */
    private String                 car;
    /**
     * 是否有房 0：无 1：有
     */
    private String                 house;
    /**
     * 用户资料审核状态列表
     */
    private List<AuthenticationMo> list;

    public BorrowerDetailMo() {
    }

    public BorrowerDetailMo(String name, String age, String education, String marriage, String city, String workTime, String income, String car, String
            house, List<AuthenticationMo> list) {
        this.name = name;
        this.age = age;
        this.education = education;
        this.marriage = marriage;
        this.city = city;
        this.workTime = workTime;
        this.income = income;
        this.car = car;
        this.house = house;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public List<AuthenticationMo> getList() {
        return list;
    }

    public void setList(List<AuthenticationMo> list) {
        this.list = list;
    }
}