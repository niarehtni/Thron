package com.rd.zhongqipiaoetong.module.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountPaymentAccountActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.PaymentAccountVM;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/25 18:00
 * <p/>
 * Description: 支付账户
 */
public class PaymentAccountAct extends BaseActivity {
    private AccountPaymentAccountActBinding binding;
    private PaymentAccountVM                paymentAccountVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.account_payment_account_act);
        paymentAccountVM = new PaymentAccountVM(binding);
        binding.setViewModel(paymentAccountVM);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.payment_account_title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_REGISTER, requestCode, resultCode, data, null)) {
            ActivityUtils.pop(this, BundleKeys.RESULT_CODE_OPEN, new Intent());
        }
        if (resultCode == 999) {
            ActivityUtils.pop(this, BundleKeys.RESULT_CODE_OPEN, new Intent());
        }
    }
}