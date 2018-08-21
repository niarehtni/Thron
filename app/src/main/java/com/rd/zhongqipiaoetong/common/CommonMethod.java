package com.rd.zhongqipiaoetong.common;

import android.net.Uri;
import android.text.InputFilter;
import android.text.Spanned;

import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.utils.UrlUtils;
import com.rd.zhongqipiaoetong.utils.encryption.MD5Util;
import com.rd.zhongqipiaoetong.utils.statistics.DeviceInfoUtils;

import java.text.DecimalFormat;

/**
 * 一些通用的自定义方法
 * Created by chenwei on 16/6/7.
 */
public class CommonMethod {
    /**
     * 计算收益
     */
    public static double calculate(String rate, String time, String tender,
                                   int repayment_type, Boolean isDay) {

        double yearRate  = 0;// 年利率
        double money     = 0;// 投资金额
        double earnMoney = 0; // 收益
        double limitTime = 0;
        int    type      = -1;

        yearRate = Double.valueOf(rate);
        money = Double.valueOf(tender);
        limitTime = Double.valueOf(time);
        type = Integer.valueOf(repayment_type);
        switch (type) {
            case 1:
                type = 0;
                break;
            case 2:
                type = 3;
                break;
            case 3:
                type = 2;
                break;
            case 4:
                type = 3;
                break;
            case 5:
                type = 3;
                break;
            case 6:
                type = 0;
                break;
            default:
                type = 3;
                break;
        }
        if (isDay) {
            type = 4;
        }
        if (limitTime > 0 && yearRate > 0) {
            System.out.println("tyoe" + type);
            System.out.println("money" + money);
            switch (type) {
                case 0:// 按月分期还款
                    earnMoney = money * yearRate / 1200.00
                            * Math.pow((1 + yearRate / 1200.00), limitTime)
                            / (Math.pow((1 + yearRate / 1200.00), limitTime) - 1)
                            * limitTime - money;
                    break;
                case 1:// 按季还本息
                    double season = Math.ceil(limitTime / 3.00);
                    earnMoney = money * yearRate * (1 + season) / 800.00;
                    break;
                case 2:// 按月还息到期还本
                    earnMoney = money * yearRate * limitTime / 1200.00;
                    break;
                case 3:// 一次性还款
                    earnMoney = money * yearRate * limitTime / 1200.00;
                    break;
                case 4:// 按日
                    earnMoney = money * yearRate * limitTime / 36000.00;
                    break;
                default:
                    break;
            }
            System.out.println("earnMoney" + earnMoney);
            DecimalFormat df = new DecimalFormat("#.00");
            System.out.println("earnMoney" + df.format(earnMoney));
            earnMoney = Double.parseDouble(df.format(earnMoney));
            return earnMoney;
        } else {
            return money * 100 / 100.00;
        }
    }

    public static String getUrl(String url) {
        //已登录用户的信息类
        OauthTokenMo mo = SharedInfo.getInstance().getValue(OauthTokenMo.class);
        // 时间戳
        String ts = String.valueOf(System.currentTimeMillis());
        // appsecrt拼接ts的字符串后进行MD5（32）
        String signa = MD5Util.MD5Encode(BaseParams.APP_SECRET + ts, "UTF-8");
        // 得到的MD5串拼接appkey再次MD5，所得结果转大写
        signa = MD5Util.MD5Encode(signa + BaseParams.APP_KEY, "UTF-8").toUpperCase();

        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(UrlUtils.getAddress() + url);
        builder.appendQueryParameter(RequestParams.APP_KEY, BaseParams.APP_KEY);
        builder.appendQueryParameter(RequestParams.SIGNA, signa);
        builder.appendQueryParameter(RequestParams.TS, ts);
        builder.appendQueryParameter(RequestParams.CLIENT, BaseParams.MOBILE_TYPE);
        builder.appendQueryParameter(RequestParams.VERSION_NUMBER, DeviceInfoUtils.getVersionName(MyApplication.getInstance()));
        if (MyApplication.getInstance().isLand()) {
            builder.appendQueryParameter(RequestParams.TOKEN, mo.getOauthToken());
            builder.appendQueryParameter(RequestParams.USER_ID, mo.getUserId());
        }
        //        builder.appendQueryParameter(RequestParams.UUID, uuid);

        return builder.build().toString();
    }

    public static String getUrlWithoutBase(String url) {
        //已登录用户的信息类
        OauthTokenMo mo = SharedInfo.getInstance().getValue(OauthTokenMo.class);
        // 时间戳
        String ts = String.valueOf(System.currentTimeMillis());
        // appsecrt拼接ts的字符串后进行MD5（32）
        String signa = MD5Util.MD5Encode(BaseParams.APP_SECRET + ts, "UTF-8");
        // 得到的MD5串拼接appkey再次MD5，所得结果转大写
        signa = MD5Util.MD5Encode(signa + BaseParams.APP_KEY, "UTF-8").toUpperCase();
        Uri.Builder builder = new Uri.Builder();
        if(url.contains(BaseParams.URL_ADDRESS)){
            if (url.contains("?")) {
                url = url + "&" + RequestParams.APP_KEY + "=" + BaseParams.APP_KEY
                        + "&" + RequestParams.SIGNA + "=" + signa
                        + "&" + RequestParams.TS + "=" + ts
                        + "&" + RequestParams.CLIENT + "=" + BaseParams.MOBILE_TYPE
                        + "&" + RequestParams.VERSION_NUMBER + "=" + DeviceInfoUtils.getVersionName(MyApplication.getInstance());
                if (MyApplication.getInstance().isLand()) {
                    url = url + "&" + RequestParams.TOKEN + "=" + mo.getOauthToken()
                            + "&" + RequestParams.USER_ID + "=" + mo.getUserId();
                }
                builder.encodedPath(url);
            } else {
                builder.encodedPath(url);
                builder.appendQueryParameter(RequestParams.APP_KEY, BaseParams.APP_KEY);
                builder.appendQueryParameter(RequestParams.SIGNA, signa);
                builder.appendQueryParameter(RequestParams.TS, ts);
                builder.appendQueryParameter(RequestParams.CLIENT, BaseParams.MOBILE_TYPE);
                builder.appendQueryParameter(RequestParams.VERSION_NUMBER, DeviceInfoUtils.getVersionName(MyApplication.getInstance()));
                if (MyApplication.getInstance().isLand()) {
                    builder.appendQueryParameter(RequestParams.TOKEN, mo.getOauthToken());
                    builder.appendQueryParameter(RequestParams.USER_ID, mo.getUserId());
                }
            }
        }else{
            builder.encodedPath(url);
        }
        return builder.build().toString();
    }

    public static String getUrlExtraButton(String url) {
        //已登录用户的信息类
        OauthTokenMo mo = SharedInfo.getInstance().getValue(OauthTokenMo.class);
        // 时间戳
        String ts = String.valueOf(System.currentTimeMillis());
        // appsecrt拼接ts的字符串后进行MD5（32）
        String signa = MD5Util.MD5Encode(BaseParams.APP_SECRET + ts, "UTF-8");
        // 得到的MD5串拼接appkey再次MD5，所得结果转大写
        signa = MD5Util.MD5Encode(signa + BaseParams.APP_KEY, "UTF-8").toUpperCase();

        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(url);
        builder.appendQueryParameter(RequestParams.APP_KEY, BaseParams.APP_KEY);
        builder.appendQueryParameter(RequestParams.SIGNA, signa);
        builder.appendQueryParameter(RequestParams.TS, ts);
        builder.appendQueryParameter(RequestParams.CLIENT, BaseParams.MOBILE_TYPE);
        builder.appendQueryParameter(RequestParams.VERSION_NUMBER, DeviceInfoUtils.getVersionName(MyApplication.getInstance()));
        builder.appendQueryParameter(RequestParams.ISLOGON, MyApplication.getInstance().isLand() ? "true" : "false");
        //        builder.appendQueryParameter(RequestParams.UUID, uuid);
        return builder.build().toString();
    }

    //设置无法输入空格 , .
    public static InputFilter[] getEdFilter() {
        InputFilter[] FilterArray = new InputFilter[2];
        FilterArray[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (source.equals(" ") || source.equals("、") || source.equals("。")) {
                    return "";
                }

                return null;
            }
        };

        FilterArray[1] = new InputFilter.LengthFilter(16);

        return FilterArray;
    }
}
