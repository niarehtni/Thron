package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/19/16.
 */
public class AppPaymentMo {
    /**
     * 跳转到三方的URL,EscapeUrlEncoded
     */
    private String              url;
    /**
     * 传递给三方SDK的数据
     */
    private Map<String, Object> paymentData;
    /**
     * 其他数据
     */
    private Map<String, Object> extraData;
    /**
     * 爱财帮appkey
     */
    private String aicaike_appkey;
    /**
     * 爱财帮ackToken
     */
    private String ackToken;
    //------------------华丽分割线----------------------------------

    public AppPaymentMo() {
        this.url = "";
        this.paymentData = new HashMap<String, Object>();
        this.extraData = new HashMap<String, Object>();
    }

    public String getAckToken() {
        return ackToken;
    }

    public void setAckToken(String ackToken) {
        this.ackToken = ackToken;
    }

    public String getAicaike_appkey() {

        return aicaike_appkey;
    }

    public void setAicaike_appkey(String aicaike_appkey) {
        this.aicaike_appkey = aicaike_appkey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(Map<String, Object> paymentData) {
        this.paymentData = paymentData;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }

    public String extraData2Json() {
        return Utils.mapData2Json(extraData);
    }

    public String paymentData2Json() {
        return Utils.mapData2Json(paymentData);
    }

}
