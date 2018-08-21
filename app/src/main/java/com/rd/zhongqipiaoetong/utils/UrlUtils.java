package com.rd.zhongqipiaoetong.utils;

import android.net.Uri;

import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.utils.encryption.MD5Util;
import com.rd.zhongqipiaoetong.utils.statistics.DeviceInfoUtils;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 7/12/16.
 */
public class UrlUtils {
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 获取Url Host
     *
     * @param url
     *
     * @return
     */
    public static String getHost(String url) {
        if (url == null || url.trim().equals("")) {
            return "";
        }
        String  host    = "";
        Pattern p       = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            host = matcher.group();
        }
        return host;
    }

    /**
     * 获取Url Host最后位 位置
     *
     * @param url
     *
     * @return
     */
    public static int getRegionEnd(String url) {
        if (url == null || url.trim().equals("")) {
            return -1;
        }
        Pattern p       = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            return matcher.end();
        }
        return -1;
    }

    /**
     * 获取Url Host 带 http或https
     *
     * @param url
     *
     * @return
     */
    public static String getHostUrl(String url) {
        return url.substring(0, getRegionEnd(url));
    }

    public static int getGroupCount(String url) {
        return getHost(url).length();
    }

    /**
     * 请求地址
     */
    public static String getULTRON() {
        return getAddress() + BaseParams.URL_SCHEME;
    }

    /**
     * 服务器地址
     */
    public static String getAddress() {
        if (BaseParams.IS_DEBUG) {
            return BaseParams.URL_ADDRESS;
        }
        if (MyApplication.getInstance().getAppServer() == null) {
            return BaseParams.URL_ADDRESS;
        }
        return MyApplication.getInstance().getAppServer();
    }

    public static String getImageServer() {
        if (MyApplication.getInstance().getImageServer() == null) {
            return BaseParams.URL_ADDRESS;
        }
        return MyApplication.getInstance().getImageServer();
    }

    public static String getUrl2Image() {
        return getImageServer() + BaseParams.TOIMAGE;
    }
    public static String getUrl3Image(){
        return getImageServer() + BaseParams.TOIMAGE;
    }

    /**
     * 一般接口调用-signa签名生成规则
     *
     * @param ts
     *         时间戳
     */
    private static String getSigna(String ts) {
        // appsecrt拼接ts的字符串后进行MD5（32）
        String signa = MD5Util.MD5Encode(BaseParams.APP_SECRET + ts, CHARSET_NAME);
        // 得到的MD5串拼接appkey再次MD5，所得结果转大写
        signa = MD5Util.MD5Encode(signa + BaseParams.APP_KEY, CHARSET_NAME).toUpperCase();
        return signa;
    }

    /**
     * 设置web页面URL
     *
     * @param host ts为秒
     *
     * @return
     */
    public static String getSignaWebUrl(String host) {
        // 时间戳
        String ts = String.valueOf(System.currentTimeMillis() / 1000);
        // 签名(加密算法)
        String      signa   = getSigna(ts);
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(host);
        builder.appendQueryParameter(RequestParams.APP_KEY, BaseParams.APP_KEY)
                .appendQueryParameter(RequestParams.SIGNA, signa)
                .appendQueryParameter(RequestParams.TS, ts)
                .appendQueryParameter(RequestParams.CLIENT,"ANDROID")
                .appendQueryParameter(RequestParams.TOKEN, getToken())
                .appendQueryParameter(RequestParams.USER_ID, getUserId())
                .appendQueryParameter(RequestParams.VERSION_NUMBER, DeviceInfoUtils.getVersionName(MyApplication.getInstance()));
//                .appendQueryParameter(RequestParams.MOBILE_TYPE, BaseParams.MOBILE_TYPE);
        String buildUrl = builder.build().toString();
        return buildUrl;
//                urlReplace(buildUrl);
    }

    /**
     * 设置web页面URL
     *
     * @param host ts为毫秒
     *
     * @return
     */
    public static String getSignaWebUrlOther(String host) {
        // 时间戳
        String ts = String.valueOf(System.currentTimeMillis());
        // 签名(加密算法)
        String      signa   = getSigna(ts);
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(host);
        builder.appendQueryParameter(RequestParams.APP_KEY, BaseParams.APP_KEY)
                .appendQueryParameter(RequestParams.SIGNA, signa)
                .appendQueryParameter(RequestParams.TS, ts)
                .appendQueryParameter(RequestParams.CLIENT,"ANDROID")
                .appendQueryParameter(RequestParams.TOKEN, getToken())
                .appendQueryParameter(RequestParams.USER_ID, getUserId())
                .appendQueryParameter(RequestParams.VERSION_NUMBER, DeviceInfoUtils.getVersionName(MyApplication.getInstance()));
        //                .appendQueryParameter(RequestParams.MOBILE_TYPE, BaseParams.MOBILE_TYPE);
        String buildUrl = builder.build().toString();
        return buildUrl;
        //                urlReplace(buildUrl);
    }

    /**
     * url 替换错误拼接
     *
     * @param url
     *
     * @return
     */
    private static String urlReplace(String url) {
        url = url.replace("?", "&");
        url = url.replace("html&", "html?");
        return url;
    }

    /**
     * 默认拼接地址
     *
     * @param api
     *
     * @return
     */
    public static String getDefaultAPI(String api) {
        return getULTRON() + api;
    }

    /**
     * 获取oauthToken
     */
    private static String getToken() {
        OauthTokenMo mo = SharedInfo.getInstance().getValue(OauthTokenMo.class);
        if (mo != null) {
            return mo.getOauthToken();
        }
        return "";
    }

    /**
     * 获取userId
     */
    private static String getUserId() {
        OauthTokenMo mo = SharedInfo.getInstance().getValue(OauthTokenMo.class);
        if (mo != null) {
            return mo.getUserId();
        }
        return "";
    }

    /**
     * 拼接url  param
     *
     * @param paramMap
     *
     * @return
     */
    public static String getUrlParams(TreeMap<String, Object> paramMap) {
        String urlParam = "";
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            urlParam = urlParam + "&" + entry.getKey() + "=" + String.valueOf(entry.getValue());
        }
        return urlParam;
    }
}
