package com.rd.zhongqipiaoetong.network.entity;

/**
 * Created by pzw on 2017/6/14.
 */
public class VersionMo {
    private String androidVersion;
    private String bootVersion;
    private String corpId;
    private String id;
    private String iosVersion;
    private String startupVersion;
    private String downloadUrl;

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getBootVersion() {
        return bootVersion;
    }

    public void setBootVersion(String bootVersion) {
        this.bootVersion = bootVersion;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getStartupVersion() {
        return startupVersion;
    }

    public void setStartupVersion(String startupVersion) {
        this.startupVersion = startupVersion;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
