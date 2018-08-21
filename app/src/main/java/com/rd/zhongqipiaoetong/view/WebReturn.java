package com.rd.zhongqipiaoetong.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.webkit.JavascriptInterface;

import com.rd.zhongqipiaoetong.MainAct;
import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.product.activity.FinancingDetailAct;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.Utils;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 6/1/16.
 */
public class WebReturn {
    private  Context              context;
    public WebReturn(Context context) {
        this.context = context;
    }

    Handler mHandler = new Handler();

    @JavascriptInterface
    public void webReturn(final String status, final String remsg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (status.equals("1")) {
                    Activity activity = (Activity) context;
                    Intent   intent   = new Intent();
                    activity.setResult(Activity.RESULT_OK, intent);
                    activity.finish();
                } else {
                    Utils.toast(remsg);
                }
            }
        });
    }

    @JavascriptInterface
    public void investNow(final String projectId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (projectId == null || "".equals(projectId)) {
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.IS_PRODUCT, true);
                    ActivityUtils.push(MainAct.class, intent);
                } else {
                    if (MyApplication.getInstance().isLand()) {
                        Intent intent = new Intent();
                        intent.putExtra(BundleKeys.ID, projectId);
                        intent.putExtra(BundleKeys.NAME, "");
                        ActivityUtils.push(FinancingDetailAct.class, intent);
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra(BundleKeys.H5, true);
                        ActivityUtils.push(LoginAct.class, intent);
                    }
                }
            }
        });
    }

}
