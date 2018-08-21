package com.rd.zhongqipiaoetong.payment;

import com.rd.zhongqipiaoetong.module.account.model.AppPaymentMo;
import com.rd.zhongqipiaoetong.network.RequestCallBack;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 16/8/12 下午5:30
 * <p/>
 * Description:
 */
public class PayRequestCallBack extends RequestCallBack<AppPaymentMo> {
    private PayCallBack payCallBack;

    public PayRequestCallBack(PayCallBack payCallBack) {
        this.payCallBack = payCallBack;
    }

    @Override
    public void onSuccess(Call<AppPaymentMo> call, Response<AppPaymentMo> response) {
        if (payCallBack != null) {
            payCallBack.callBack(true);
        }
    }

    @Override
    public void onFailure(Call<AppPaymentMo> call, Throwable t) {
        if (payCallBack != null)
            payCallBack.callBack(false);
        super.onFailure(call, t);
    }
}
