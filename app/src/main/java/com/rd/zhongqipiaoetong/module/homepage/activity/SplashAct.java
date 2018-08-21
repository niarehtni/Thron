package com.rd.zhongqipiaoetong.module.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.rd.zhongqipiaoetong.MainAct;
import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.module.account.model.AreaVersionMo;
import com.rd.zhongqipiaoetong.module.account.model.LoginingMo;
import com.rd.zhongqipiaoetong.module.homepage.model.RepayMo;
import com.rd.zhongqipiaoetong.module.homepage.model.ServerModel;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.api.ExtraService;
import com.rd.zhongqipiaoetong.network.api.ImeiService;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;
import com.rd.zhongqipiaoetong.utils.SPUtil;
import com.rd.zhongqipiaoetong.utils.UrlUtils;
import com.rd.zhongqipiaoetong.utils.Utils;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/30 15:06
 * <p/>
 * Description: 启动页
 */
public class SplashAct extends BaseActivity {
    // 跳转引导页
    private static final int GO_GUIDE      = 0x01;
    // 跳转首页
    private static final int GO_MAIN       = 0x02;
    // 页面跳转逻辑
    private static final int DO_HANDLER    = 0x99;
    // 最小显示时间
    private static final int SHOW_TIME_MIN = 3000;
    // 开始时间
    private static long          mStartTime;
    private        SplashHandler mHandler;
    // 获取时候标识IMEI
    private        String        imei;
    //IMEI实体类
    private        LoginingMo    registerMO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setImageBitmap(Utils.readBitmap(this, R.drawable.splash));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_guide_in));
        setContentView(imageView);
//        imei = SPUtil.imeiSave(SplashAct.this);
//        requestRegister();
        req_servers();
        req_version();
        mHandler = new SplashHandler(this);
        // 记录开始时间
        mStartTime = System.currentTimeMillis();
        // 获取首页banner图
        req_getBanner();
    }

    private void requestRegister() {
        //        System.out.println("手机标识 ---  " + imei);
        //        System.out.println("手机LANREN ---  " + RequestParams.LANREN_IMEI_NUM);
        registerMO = new LoginingMo();
        registerMO.setAckToken(imei);
        registerMO.setAckAppkey(RequestParams.ACKAPPKEY_NUM);
        Call<LoginingMo> call = RDClient.getService(ImeiService.class).appStart(registerMO);
        call.enqueue(new RequestCallBack<LoginingMo>() {
            @Override
            public void onSuccess(Call<LoginingMo> call, Response<LoginingMo> response) {

            }
        });
    }

    /**
     * 发送获取banner图请求
     */
    private void req_getBanner() {
        mHandler.sendEmptyMessage(DO_HANDLER);
    }

    /**
     * 获取服务器地址
     */
    private void req_servers() {
        Call<ServerModel> call = RDClient.getService(ExtraService.class).servers();
        call.enqueue(new RequestCallBack<ServerModel>() {
            @Override
            public void onSuccess(Call<ServerModel> call, Response<ServerModel> response) {
                MyApplication.getInstance().setAppServer(response.body().getAppServer());
                MyApplication.getInstance().setImageServer(response.body().getImageServer());
                RDClient.getInstance().updataRetrofit();
            }
        });
    }

    /**
     * 获取还款方式
     */
    private static final String VERSION_REPAY = "repayTypes";

    private void req_version() {
        Call<AreaVersionMo> call = RDClient.getService(ExtraService.class).repayTypes();
        call.enqueue(new RequestCallBack<AreaVersionMo>() {
            @Override
            public void onSuccess(Call<AreaVersionMo> call, Response<AreaVersionMo> response) {
                AreaVersionMo localmo = SPUtil.getEntity(AreaVersionMo.class, VERSION_REPAY, null);
                if (localmo == null || response.body().getVersion() > localmo.getVersion()) {
                    repayTypes(response.body());
                }
            }
        });
    }

    /**
     * repayTypes过期，重新获取，并保存最新信息
     *
     * @param versionMo
     */
    private void repayTypes(final AreaVersionMo versionMo) {

        Call<RepayMo> call = RDClient.getService(ExtraService.class).repay(UrlUtils.getAddress() + "/" + versionMo.getJson());
        call.enqueue(new RequestCallBack<RepayMo>() {
            @Override
            public void onSuccess(Call<RepayMo> call, Response<RepayMo> response) {
                SPUtil.setEntity(response.body());
                SPUtil.setEntity(versionMo, VERSION_REPAY);
            }
        });
    }

    /**
     * Handler:跳转到不同界面
     */
    private static class SplashHandler extends Handler {
        WeakReference<SplashAct> act;

        public SplashHandler(SplashAct act) {
            this.act = new WeakReference<>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 计算一下总共花费的时间
            long loadingTime = System.currentTimeMillis() - mStartTime;
            switch (msg.what) {
                case DO_HANDLER:
                    // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
                    boolean isFirstIn = SPUtil.getBoolean(BaseParams.SP_IS_FIRST_INE + "-" + BaseParams.getVersion(), true);
                    // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面 且引导页开启
                    if (FeatureUtils.getEnableGuideFeature() && isFirstIn) {
                        sendEmptyMessage(GO_GUIDE);
                    } else {
                        sendEmptyMessage(GO_MAIN);
                    }
                    break;

                case GO_GUIDE:
                    // 如果比最小显示时间还短，就延时进入GuideActivity，否则直接进入
                    if (loadingTime < SHOW_TIME_MIN) {
                        postDelayed(goToGuideActivity, SHOW_TIME_MIN - loadingTime);
                    } else {
                        post(goToGuideActivity);
                    }
                    break;

                case GO_MAIN:
                    // 如果比最小显示时间还短，就延时进入HomeActivity，否则直接进入
                    if (loadingTime < SHOW_TIME_MIN) {
                        postDelayed(goToMainActivity, SHOW_TIME_MIN - loadingTime);
                    } else {
                        post(goToMainActivity);
                    }
                    break;
            }
        }

        // 进入 GuideAct
        Runnable goToGuideActivity = new Runnable() {
            @Override
            public void run() {
                act.get().startActivity(new Intent(act.get(), GuideAct.class));
                act.get().finish();
            }
        };
        // 进入 MainAct
        Runnable goToMainActivity  = new Runnable() {
            @Override
            public void run() {
                act.get().startActivity(new Intent(act.get(), MainAct.class));
                act.get().finish();
            }
        };
    }
}