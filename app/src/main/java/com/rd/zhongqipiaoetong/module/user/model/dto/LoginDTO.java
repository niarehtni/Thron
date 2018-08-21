package com.rd.zhongqipiaoetong.module.user.model.dto;

/**
 * Created by Administrator on 2017/3/20.
 */
public class LoginDTO {

    private String username;
    private String password;
    private String identifier;
    private String clientId;
    private String appCode;

    public LoginDTO(String username, String password, String identifier, String clientId) {
        this.username = username;
        this.password = password;
        this.identifier = identifier;
        this.clientId = clientId;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
