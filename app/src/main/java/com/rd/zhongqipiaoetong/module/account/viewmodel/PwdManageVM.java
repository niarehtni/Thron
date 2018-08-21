package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.module.account.activity.ModfiyLoginPwdAct;
import com.rd.zhongqipiaoetong.module.account.activity.ModfiyPayPwdAct;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountAct;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountOCRAct;
import com.rd.zhongqipiaoetong.module.account.activity.PwdManageAct;
import com.rd.zhongqipiaoetong.module.account.activity.SetPayPwdAct;
import com.rd.zhongqipiaoetong.module.account.model.PersonInfoMo;
import com.rd.zhongqipiaoetong.module.gesturelock.activity.LockModifyPwdAct;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/25 17:40
 * <p/>
 * Description: 密码管理VM({@link PwdManageAct})
 */
public class PwdManageVM {
    public  ObservableField<Integer> hasSetPayPwd = new ObservableField<>();
    private PersonInfoMo             personInfoMo = null;

    public PwdManageVM(int hasSetPayPwd) {
        this.hasSetPayPwd.set(hasSetPayPwd);
        req_securityInfo();
    }

    /**
     * 网络请求
     */
    private void req_securityInfo() {
        Call<PersonInfoMo> call = RDClient.getService(AccountService.class).securityInfo();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<PersonInfoMo>() {
            @Override
            public void onSuccess(Call<PersonInfoMo> call, Response<PersonInfoMo> response) {
                personInfoMo = response.body();
                SharedInfo.getInstance().setValue(PersonInfoMo.class, response.body());
            }
        });
    }

    /**
     * 修改登录密码
     */
    public void onModifyLogin(View view) {
        ActivityUtils.push(ModfiyLoginPwdAct.class);
    }

    /**
     * 修改支付密码/设置支付密码
     */
    public void onModifyPayment(View view) {
        if (personInfoMo == null) {
            return;
        }
        if (personInfoMo.getRealNameStatus() != 1) {
            Utils.toast("请先实名认证");
            if(FeatureConfig.enableCertOCRFeature == 1){
                ActivityUtils.push(PaymentAccountOCRAct.class);
            }else{
                ActivityUtils.push(PaymentAccountAct.class);
            }
            return;
        }

        if (hasSetPayPwd.get() == 1) {
            ActivityUtils.push(ModfiyPayPwdAct.class);
        } else {
            //            Intent intent = new Intent();
            ActivityUtils.push(ActivityUtils.peek(), SetPayPwdAct.class, BundleKeys.REQUEST_CODE_SETPAYPWD);
        }
    }

    /**
     * 修改手势密码
     */
    public void onModifyGesture(View view) {
        ActivityUtils.push(LockModifyPwdAct.class);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BundleKeys.REQUEST_CODE_SETPAYPWD && resultCode == Activity.RESULT_OK) {
            if (data.getBooleanExtra(BundleKeys.HASSETPAYPWD_SUCCESSS, false)) {
                hasSetPayPwd.set(1);
            } else {
                hasSetPayPwd.set(0);
            }
        }
    }
}