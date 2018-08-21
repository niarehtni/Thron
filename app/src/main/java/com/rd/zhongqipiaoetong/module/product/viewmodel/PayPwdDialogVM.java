package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.databinding.ObservableField;

import com.rd.zhongqipiaoetong.utils.DisplayFormat;

/**
 * <p/>
 * Description: 投标密码输入框的VM
 */
public class PayPwdDialogVM {
    //红包金额
    public final ObservableField<String> redAmount = new ObservableField<>();
    //加息券
    public final ObservableField<String> upRate    = new ObservableField<>();
    //体验券总额
    public final ObservableField<String> expAmount = new ObservableField<>();
    //投资金额
    public final ObservableField<String> tender    = new ObservableField<>();
    //实际支付
    public final ObservableField<String> actualPay = new ObservableField<>();

    /**
     * @param redAmount
     *         红包金额
     * @param expAmount
     *         体验券总额
     * @param upRate
     *         加息券
     * @param tender
     *         用户投资金额(输入框中的值)
     */
    public PayPwdDialogVM(String redAmount, String expAmount, String upRate, String tender) {
        refreshVm(redAmount, expAmount, upRate, tender);
    }

    public void refreshVm(String redAmount, String expAmount, String upRate, String tender) {
        this.redAmount.set(DisplayFormat.doubleFormat(redAmount));
        this.expAmount.set(DisplayFormat.doubleFormat(expAmount));
        this.upRate.set(upRate);
        this.actualPay.set(DisplayFormat.doubleFormat((Double.valueOf(tender) - Double.valueOf(redAmount))));
        this.tender.set(DisplayFormat.doubleFormat((Double.valueOf(tender) + Double.valueOf(expAmount))));
    }
}