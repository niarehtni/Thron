package com.rd.zhongqipiaoetong.module.user.model;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/18 下午3:15
 * <p/>
 * Description: 登录信息
 */
public class OauthTokenMo {
    /**
     * 授权有效时间，单位秒
     */
    private String expiresIn;
    /**
     * 授权口令
     */
    private String oauthToken;
    /**
     * 刷新授权口令
     */
    private String refreshToken;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 隐藏 用户名
     */
    private String hideUserName;

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHideUserName() {
        return hideUserName;
    }

    public void setHideUserName(String hideUserName) {
        this.hideUserName = hideUserName;
    }
}