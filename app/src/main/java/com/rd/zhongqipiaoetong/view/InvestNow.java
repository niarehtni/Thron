package com.rd.zhongqipiaoetong.view;

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
 * Created by pzw on 2017/8/29.
 */
public class InvestNow {
    private Context context;

    public InvestNow(Context context) {
        this.context = context;
    }

    Handler mHandler = new Handler();

    @JavascriptInterface
    public void investNow(final String projectId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Utils.toast("run to here");
                if (projectId == null || "".equals(projectId)) {
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.IS_PRODUCT, true);
                    ActivityUtils.push(MainAct.class, intent);
                } else {
                    if(MyApplication.getInstance().isLand()){
                        Intent intent = new Intent();
                        intent.putExtra(BundleKeys.ID, projectId);
                        intent.putExtra(BundleKeys.NAME, "");
                        ActivityUtils.push(FinancingDetailAct.class, intent);
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra(BundleKeys.H5,true);
                        ActivityUtils.push(LoginAct.class, intent);
                    }
                }
            }
        });
    }
}
