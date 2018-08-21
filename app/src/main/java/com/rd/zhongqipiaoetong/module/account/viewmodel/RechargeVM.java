package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.rd.zhongqipiaoetong.module.account.activity.RechargeAct;
import com.rd.zhongqipiaoetong.module.account.model.BankCardMo;
import com.rd.zhongqipiaoetong.module.account.model.RechargeMo;
import com.rd.zhongqipiaoetong.network.InitRequestCallBack;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.api.PayService;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.payment.ToPaymentMo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/17 14:36
 * <p/>
 * Description: 提现VM({@link RechargeAct})
 */
public class RechargeVM {
    // 充值按钮是否显示
    public final ObservableBoolean           enable       = new ObservableBoolean(false);
    // 充值初始化信息
    public final ObservableField<RechargeMo> item         = new ObservableField<>();
    // 初始化银行卡
    public final ObservableField<BankCardMo> selecteditem = new ObservableField<>();
    public final ObservableField<String>     url          = new ObservableField<>();
    /**
     * 充值金额
     */
    private String cash;

    public RechargeVM() {
//        url.set(UrlUtils.getAddress());
        req_toRecharage();
    }

    /**
     * 充值金额限制
     */
    public void input(Editable s) {
        if (s != null && !TextUtils.isEmpty(s.toString())) {
            cash = s.toString();
            Double str = Double.valueOf(cash);
            if (str >= item.get().getMinInvest()) {
                if (!enable.get()) {
                    enable.set(true);
                }
            } else {
                if (enable.get()) {
                    enable.set(false);
                }
            }
        } else {
            if (enable.get()) {
                enable.set(false);
            }
        }
    }

    /**
     * 充值
     */
    public void submit(View view) {
        if (item.get() != null) {
            ToPaymentMo toPaymentMo = new ToPaymentMo();
            toPaymentMo.setAuthorize(item.get().isAuthorizated());
            toPaymentMo.setAuthorizeType(item.get().getAuthorizeType());
            if (item.get().getBankCardList() != null && item.get().getBankCardList().size() > 0) {
                toPaymentMo.setIsFastPayment(item.get().getBankCardList().get(0).getBindingStatus());
            }
//            if (!RDPayment.getInstance().getPayController().toAuth(toPaymentMo, new ToPaymentCheck(null), true)) {
//                return;
//            }
            res_doRecharage();
        }
    }

    /**
     * 网络请求，充值初始化
     */
    public void req_toRecharage() {
        Call<RechargeMo> call = RDClient.getService(PayService.class).toRecharge();
        call.enqueue(new InitRequestCallBack<RechargeMo>() {
            @Override
            public void onSuccess(Call<RechargeMo> call, Response<RechargeMo> response) {

                item.set(response.body());
                ToPaymentMo toPaymentMo = new ToPaymentMo();
                toPaymentMo.setAuthorize(item.get().isAuthorizated());
                toPaymentMo.setAuthorizeType(item.get().getAuthorizeType());
                if (item.get().getBankCardList() != null && item.get().getBankCardList().size() > 0) {
                    toPaymentMo.setIsFastPayment(item.get().getBankCardList().get(0).getBindingStatus());
                }
                url.set(response.body().getHost());
//                if (!RDPayment.getInstance().getPayController().toAuth(toPaymentMo, new ToPaymentCheck(null), true)) {
//                    return;
//                }
            }
        });
    }

    /**
     * 充值
     */
    private void res_doRecharage() {
        HashMap<String, Object> map = new HashMap<>();
//        map.put(RequestParams.SESSION_ID, item.get().getSessionId());
        map.put(RequestParams.CASH, cash);
//        if (item.get().getBankCardList() != null && item.get().getBankCardList().size() > 0) {
//            map.put(RequestParams.BANKCODE, item.get().getBankCardList().get(0).getId());
//        }
        RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_RECHARGE, map, new PayCallBack() {
            @Override
            public void callBack(boolean isSuccess) {
                req_toRecharage();
            }
        });
    }
}