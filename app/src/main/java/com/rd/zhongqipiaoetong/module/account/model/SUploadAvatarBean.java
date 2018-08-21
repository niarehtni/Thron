package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Created by xyy on 2016/11/7.
 */
public class SUploadAvatarBean{
    private String headPortraitUrl;
    private String oauthToken;
    private String userId;

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
