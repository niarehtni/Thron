package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.module.account.viewmodel.PersonInfoVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/24 11:16
 * <p/>
 * Description: 用户信息({@link PersonInfoVM})
 */
public class PersonInfoMo {
    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 脱敏后的用户名
     */
    private String hide_username;

    /**
     * 脱敏后的邮箱
     */
    private String hide_email;

    /**
     * 脱敏后的手机
     */
    private String hide_phone;

    /**
     * 实名状态 1：实名 0：未实名
     */
    private int realNameStatus;

    /**
     * 身份证号
     */
    private String card_id;

    /**
     * 脱敏后的姓名
     */
    private String hide_realname;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 邮箱绑定 1：绑定 0：未绑定
     */
    private int emailStatus;

    /**
     * 手机绑定 1：绑定 0：未绑定
     */
    private int phone_status;

    /**
     * 是否设置支付密码 1:设置 0：未设置
     */
    private int has_set_paypwd;

    /**
     * 支付账户是否被锁定 1：锁定 0 未锁定
     */
    private int payPwdLocked;

    /**
     * 银行卡个数
     */
    private int bankNum;

    /**
     * 头像地址
     */
    private String qiniuDomain;
    private String headPortraitUrl;

    private String infoStatus;

    public String getInfoStatus() {
        return infoStatus;
    }

    public void setInfoStatus(String infoStatus) {
        this.infoStatus = infoStatus;
    }

    public int getRealNameStatus() {
        return realNameStatus;
    }

    public void setRealNameStatus(int realNameStatus) {
        this.realNameStatus = realNameStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHide_username() {
        return hide_username;
    }

    public void setHide_username(String hide_username) {
        this.hide_username = hide_username;
    }

    public String getHide_email() {
        return hide_email;
    }

    public void setHide_email(String hide_email) {
        this.hide_email = hide_email;
    }

    public String getHide_phone() {
        return hide_phone;
    }

    public void setHide_phone(String hide_phone) {
        this.hide_phone = hide_phone;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getHide_realname() {
        return hide_realname;
    }

    public void setHide_realname(String hide_realname) {
        this.hide_realname = hide_realname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(int emailStatus) {
        this.emailStatus = emailStatus;
    }

    public int getPhone_status() {
        return phone_status;
    }

    public void setPhone_status(int phone_status) {
        this.phone_status = phone_status;
    }

    public int getHas_set_paypwd() {
        return has_set_paypwd;
    }

    public void setHas_set_paypwd(int has_set_paypwd) {
        this.has_set_paypwd = has_set_paypwd;
    }

    public int getPayPwdLocked() {
        return payPwdLocked;
    }

    public void setPayPwdLocked(int payPwdLocked) {
        this.payPwdLocked = payPwdLocked;
    }

    public int getBankNum() {
        return bankNum;
    }

    public void setBankNum(int bankNum) {
        this.bankNum = bankNum;
    }

    public String getQiniuDomain() {
        return qiniuDomain;
    }

    public void setQiniuDomain(String qiniuDomain) {
        this.qiniuDomain = qiniuDomain;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public String getHeadUrl(){
        return qiniuDomain + headPortraitUrl;
    }
}