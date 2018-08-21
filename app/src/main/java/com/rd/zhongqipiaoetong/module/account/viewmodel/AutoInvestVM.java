package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.account.activity.AutoDetaiAct;
import com.rd.zhongqipiaoetong.module.account.activity.AutoInvestLogAct;
import com.rd.zhongqipiaoetong.module.account.activity.AutoSetupAct;
import com.rd.zhongqipiaoetong.module.account.model.AccountMo;
import com.rd.zhongqipiaoetong.module.account.model.AutoTenderDetailMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.payment.ToPaymentCheck;
import com.rd.zhongqipiaoetong.payment.ToPaymentMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.utils.Utils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/27 下午5:42
 * <p/>
 * Description:
 */
public class AutoInvestVM {
    public ObservableField<Boolean> enable   = new ObservableField<>(false);
    public ObservableField<String>  useMoney = new ObservableField<>();
    private AutoTenderDetailMo mo;

    public AutoInvestVM() {
        req_data();
    }

    /**
     * 自动投标初始化
     */
    public void initAuto() {
        Call<AutoTenderDetailMo> call = RDClient.getService(AccountService.class).autoTenderDetail();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<AutoTenderDetailMo>() {
            @Override
            public void onSuccess(Call<AutoTenderDetailMo> call, Response<AutoTenderDetailMo> response) {
                enable.set(response.body().isEnable());
                mo = response.body();
                ToPaymentMo toPaymentMo = new ToPaymentMo();
                toPaymentMo.setAuthorize(mo.isAuthorizated());
                toPaymentMo.setAuthorizeType(mo.getAuthorizeType());
                if (!RDPayment.getInstance().getPayController().toAuth(toPaymentMo, new ToPaymentCheck(null), true)) {
                    return;
                }
            }
        });
    }

    /**
     * 个人账户信息
     */
    public void req_data() {
        Call<AccountMo> call = RDClient.getService(AccountService.class).basic();
        call.enqueue(new RequestCallBack<AccountMo>() {
            @Override
            public void onSuccess(Call<AccountMo> call, Response<AccountMo> response) {
                useMoney.set(response.body().getBalanceAvailable());
            }
        });
    }

    /**
     * 改变自动投标状态
     */
    public void req_status(boolean enable) {
        Call<HttpResult> call = RDClient.getService(AccountService.class).autoStatus(enable);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                initAuto();
            }
        });
    }

    /**
     * 自动投资记录onClick
     */
    public void onAutoLogClick(View v) {
        ActivityUtils.push(AutoInvestLogAct.class);
    }

    /**
     * 自动投资详情onClick
     */
    public void onAutoDetailClick(View v) {
        ActivityUtils.push(AutoDetaiAct.class);
    }

    public void onAutoStatusClick(View v) {
        if (enable.get()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(RequestParams.enable, false);
            RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_AUTO_OFF, map, new PayCallBack() {
                @Override
                public void callBack(boolean isSuccess) {
                    initAuto();
                }
            });
        } else {
            if (DisplayFormat.stringToDouble(useMoney.get()) < 300) {
                Utils.toast(R.string.auto_invest_toast16);
                return;
            }
            if (mo != null) {
                ToPaymentMo toPaymentMo = new ToPaymentMo();
                toPaymentMo.setAuthorize(mo.isAuthorizated());
                toPaymentMo.setAuthorizeType(mo.getAuthorizeType());
                if (!RDPayment.getInstance().getPayController().toAuth(toPaymentMo, new ToPaymentCheck(null), true)) {
                    return;
                }
            }
            Intent intent = new Intent();
            intent.putExtra(BundleKeys.ISFIRST, true);
            ActivityUtils.push(AutoSetupAct.class, intent);
        }
    }
}
