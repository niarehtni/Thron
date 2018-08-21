package com.rd.zhongqipiaoetong.network.interceptor;

import android.util.Log;

import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.utils.MDUtil;
import com.rd.zhongqipiaoetong.utils.encryption.MD5Util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Interceptor;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 17:59
 * <p/>
 * Description: 拦截器 - 用于添加签名参数
 */
public class FridayParamsInject {
    private static final String CHARSET_NAME = "UTF-8";
    private BasicParamsInterceptor interceptor;
    private String                 ts;
    private boolean isSetTs = false;

    private FridayParamsInject() {
    }

    public static FridayParamsInject getInstance() {
        return new FridayParamsInject();
    }

    public Interceptor getInterceptor() {

        if (interceptor == null) {
            upDataInterceptor();
        }
        return interceptor;
    }

    public void upDataInterceptor() {

        interceptor = new BasicParamsInterceptor.Builder()
                .addParam(RequestParams.APP_KEY, BaseParams.FRIDAY_APPKEY)
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
        ts = String.valueOf(System.currentTimeMillis() / 1000);
        paramsMap.put(RequestParams.TS, ts);
        // 签名(加密算法)
        String signa = getSign(paramsMap);
        paramsMap.put(RequestParams.SIGNA, signa);
    }

    public void setTs(String ts) {
        this.ts = ts;
        isSetTs = true;
    }

    private String getSign(Map<String, String> map) {
        if (map.containsKey(RequestParams.SIGNA)) {
            map.remove(RequestParams.SIGNA);
        }
        TreeMap<String, String> treeMap = new TreeMap<>(map);
        String                  signa   = "";
        try {
            Iterator      it = treeMap.entrySet().iterator();
            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (null != entry.getValue()) {
                    sb.append(entry.getKey()).append("=").append(URLDecoder.decode(entry.getValue().toString(), "utf-8"));
                }
            }
            // 所有请求参数排序后的字符串后进行MD5（32）
            Log.e("Sign String", sb.toString());
            signa = MDUtil.encode(MDUtil.TYPE.MD5, sb.toString());
            Log.e("Sign First", signa);
            // 得到的MD5串拼接appsecret再次MD5，所得结果转大写
            signa = MDUtil.encode(MDUtil.TYPE.MD5, signa + BaseParams.FRIDAY_APPSECRET).toUpperCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return signa;
    }

    /**
     * 一般接口调用-signa签名生成规则
     *
     * @param ts
     *         时间戳
     */
    private String getSigna(String ts) {
        // appsecrt拼接ts的字符串后进行MD5（32）
        String signa = MD5Util.MD5Encode(BaseParams.FRIDAY_APPSECRET + ts, CHARSET_NAME);
        // 得到的MD5串拼接appkey再次MD5，所得结果转大写
        signa = MD5Util.MD5Encode(signa + BaseParams.FRIDAY_APPKEY, CHARSET_NAME).toUpperCase();
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