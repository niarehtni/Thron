package com.rd.zhongqipiaoetong.network.interceptor;

import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.utils.encryption.MD5Util;
import com.rd.zhongqipiaoetong.utils.statistics.DeviceInfoUtils;

import java.util.Map;

import okhttp3.Interceptor;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 17:59
 * <p/>
 * Description: 拦截器 - 用于添加签名参数
 */
public class BasicParamsInject {
    private static final String CHARSET_NAME = "UTF-8";
    private BasicParamsInterceptor interceptor;
    private String                 ts;
    private boolean isSetTs = false;

    private BasicParamsInject() {
    }

    public static BasicParamsInject getInstance() {
        return new BasicParamsInject();
    }

    public Interceptor getInterceptor() {

        if (interceptor == null) {
            upDataInterceptor();
        }
        return interceptor;
    }

    public void upDataInterceptor() {

        interceptor = new BasicParamsInterceptor.Builder()
                .addParam(RequestParams.APP_KEY, BaseParams.APP_KEY)
                .addParam(RequestParams.CLIENT, BaseParams.MOBILE_TYPE)
                .addParam(RequestParams.VERSION_NUMBER, DeviceInfoUtils.getVersionName(MyApplication.getInstance()))
                .addParam(RequestParams.TOKEN, getToken())
                .addParam(RequestParams.USER_ID, getUserId())
                .build();
        interceptor.setIBasicDynamic(new IBasicDynamic() {
            @Override
            public void dynamicParams(Map<String, String> paramsMap) {
                // 时间戳
                getTime(paramsMap);
            }
        });
    }

    private synchronized void getTime(Map<String, String> paramsMap) {
        if (isSetTs) {
            isSetTs = false;
        } else {
            ts = String.valueOf(System.currentTimeMillis());
        }
        // 签名(加密算法)
        String signa = getSigna(ts);
        paramsMap.put(RequestParams.SIGNA, signa);
        paramsMap.put(RequestParams.TS, ts);
    }

    public void setTs(String ts) {
        this.ts = ts;
        isSetTs = true;
    }

    /**
     * 一般接口调用-signa签名生成规则
     *
     * @param ts
     *         时间戳
     */
    private String getSigna(String ts) {
        // appsecrt拼接ts的字符串后进行MD5（32）
        String signa = MD5Util.MD5Encode(BaseParams.APP_SECRET + ts, CHARSET_NAME);
        // 得到的MD5串拼接appkey再次MD5，所得结果转大写
        signa = MD5Util.MD5Encode(signa + BaseParams.APP_KEY, CHARSET_NAME).toUpperCase();
        return signa;
    }

    /**
     * 获取oauthToken
     */
    private String getToken() {
        OauthTokenMo mo = SharedInfo.getInstance().getValue(OauthTokenMo.class);
        if (mo != null) {
            return mo.getOauthToken();
        }
        return "";
    }

    /**
     * 获取userId
     */
    private String getUserId() {
        OauthTokenMo mo = SharedInfo.getInstance().getValue(OauthTokenMo.class);
        if (mo != null) {
            return mo.getUserId();
        }
        return "";
    }
}