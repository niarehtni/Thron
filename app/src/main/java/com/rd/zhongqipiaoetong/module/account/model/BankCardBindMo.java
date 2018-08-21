package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.module.account.viewmodel.BankCardBindVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/24 17:47
 * <p/>
 * Description: 绑定银行卡({@link BankCardBindVM})信息
 */
public class BankCardBindMo {
    /**
     * 银行简称
     */
    private String bankAcronym;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行账号
     */
    private String bankCardNum;
    /**
     * 开户名
     */
    private String realname;
    /**
     * 支行
     */
    private String branch;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * area
     */
    private String area;
    /**
     * type  是否需要照片
     * 0 – 三方不需要
     * 1 – 三方需要
     */
    private String type;
    /**
     * 正面照
     */
    private String positivePhoto;
    /**
     * 反面照
     */
    private String negativePhoto;

    public BankCardBindMo() {
    }

    public String getBankAcronym() {
        return bankAcronym;
    }

    public void setBankAcronym(String bankAcronym) {
        this.bankAcronym = bankAcronym;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPositivePhoto() {
        return positivePhoto;
    }

    public void setPositivePhoto(String positivePhoto) {
        this.positivePhoto = positivePhoto;
    }

    public String getNegativePhoto() {
        return negativePhoto;
    }

    public void setNegativePhoto(String negativePhoto) {
        this.negativePhoto = negativePhoto;
    }
}