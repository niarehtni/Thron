package com.rd.zhongqipiaoetong.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rd.zhongqipiaoetong.common.FeatureConfig;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 6/1/16.
 */
public class RdWebView extends WebView {
    public RdWebView(Context context) {
        super(context);
        intWebView(context);
    }

    public RdWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intWebView(context);
    }

    public RdWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intWebView(context);
    }

    private void intWebView(final Context context) {
        WebSettings setting = this.getSettings();
        setting.setSupportZoom(false);//不支持缩放
        setting.setBuiltInZoomControls(false);//设置不显示缩放按钮
        setting.setUseWideViewPort(false);///关键点
        setting.setJavaScriptEnabled(true);//设置支持javascript脚本
        setting.setDefaultTextEncodingName("utf-8");
        this.addJavascriptInterface(new WebReturn(context), "webReturn");
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if(FeatureConfig.enableShareSdkFeature == 1){
            this.addJavascriptInterface(new ShareDialogReturn(context),"shareDialogReturn");
        }

        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        this.setWebViewClient(new RDWebViewClient());
    }
}
