package com.rd.zhongqipiaoetong.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rd.zhongqipiaoetong.MainAct;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.account.activity.MyCouponAct;
import com.rd.zhongqipiaoetong.module.account.activity.MyExpAct;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountAct;
import com.rd.zhongqipiaoetong.module.more.activity.InvitationAct;
import com.rd.zhongqipiaoetong.module.more.activity.InvitationTypeBAct;
import com.rd.zhongqipiaoetong.module.product.activity.FinancingDetailAct;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.module.user.activity.RegisterFirstAct;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 6/1/16.
 */
public class RDWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.equals("rdthron://app/login")) {
            ActivityUtils.peek().finish();
            ActivityUtils.push(LoginAct.class);
        } else if (url.equals("rdthron://app/user/coupon")) {
            ActivityUtils.peek().finish();
            ActivityUtils.push(MyCouponAct.class);
        } else if (url.equals("rdthron://app/user/exp")) {
            ActivityUtils.peek().finish();
            ActivityUtils.push(MyExpAct.class);
        } else if (url.equals("rdthron://app/investList")) {
            Intent intent = new Intent();
            intent.putExtra(BundleKeys.IS_PRODUCT, true);
            ActivityUtils.push(MainAct.class, intent);
        } else if (url.equals("rdthron://app/user/invitation")) {
            ActivityUtils.peek().finish();
            if(BaseParams.inviteType == 1){
                ActivityUtils.push(InvitationAct.class);
            }else if(BaseParams.inviteType == 2){
                ActivityUtils.push(InvitationTypeBAct.class);
            }
        } else if (url.equals("rdthron://app/user/paymentAccount")) {
            ActivityUtils.peek().finish();
            Intent intent = new Intent();
            ActivityUtils.push(PaymentAccountAct.class, intent, BundleKeys.REQUEST_CODE_OPEN);
        } else if (url.contains("rdthron://app/investDetail")) {
            ActivityUtils.peek().finish();
            url = url.substring(url.indexOf("?"));
            String[] params = url.split("=");
            Intent   intent = new Intent();
            if (params.length < 2) {
                intent.putExtra(BundleKeys.ID, "0");
            } else {
                intent.putExtra(BundleKeys.ID, params[1]);
            }
            ActivityUtils.push(FinancingDetailAct.class, intent);
        } else if (url.equals("mobile://app?goto=home")) {
            ActivityUtils.push(MainAct.class);
        }
        else if (url.equals("rdthron://app/user/login")) {//中秋活动跳登录
            Intent intent = new Intent();
            intent.putExtra(BundleKeys.H5,true);
            ActivityUtils.push(LoginAct.class,intent);
        }
        else if (url.equals("rdthron://app/user/account")) {
            ActivityUtils.peek().finish();
            Intent intent = new Intent();
            intent.putExtra(BundleKeys.IS_SHOW, true);
            ActivityUtils.push(MainAct.class, intent);
        } else {
            view.requestFocus();
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        return true;
    }

    // 开始加载网页时要做的工作
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    // 加载完成时要做的工作
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    // 加载错误时要做的工作
    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();// 接受所有网站的证书
    }
}
