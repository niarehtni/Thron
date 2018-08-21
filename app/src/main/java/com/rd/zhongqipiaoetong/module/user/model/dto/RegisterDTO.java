package com.rd.zhongqipiaoetong.module.user.model.dto;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.zhongqipiaoetong.BR;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/29 17:58
 * <p/>
 * Description: 用户注册，网络请求入参
 */
public class RegisterDTO extends BaseObservable {
    /** 用户名 */
    private String username;
    /** 手机号 */
    private String phone;
    /** 密码 */
    private String password;
    /** 验证码 */
    private String validcode;
    /** 邀请人手机号 */
    private String userInviteCode;
    /**
     * 爱财帮ackAppkey
     */
    private String ackAppkey;
    /**
     * 爱财帮ackToken
     */
    private String ackToken;
    private String identifier;
    private String clientId;

    private String appCode;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Bindable
    public void setAckToken(String ackToken) {
        this.ackToken = ackToken;
    }

    public String getAckToken() {
        return ackToken;
    }

    @Bindable
    public String getAckAppkey() {
        return ackAppkey;
    }

    public void setAckAppkey(String ackAppkey) {
        this.ackAppkey = ackAppkey;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public RegisterDTO(String phone, String pwd, String code, String ackToken, String ackAppkey, String identifier, String clientId) {
        this.phone = phone;
        this.clientId = clientId;
        this.password = pwd;
        this.validcode = code;
        this.ackToken = ackToken;
        this.ackAppkey = ackAppkey;
        this.identifier = identifier;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidcode() {
        return validcode;
    }

    public void setValidcode(String validcode) {
        this.validcode = validcode;
    }

    public String getUserInviteCode() {
        return userInviteCode;
    }

    public void setUserInviteCode(String userInviteCode) {
        this.userInviteCode = userInviteCode;
    }
}