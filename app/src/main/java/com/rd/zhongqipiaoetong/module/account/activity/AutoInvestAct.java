package com.rd.zhongqipiaoetong.module.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountAutoInvestActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.AutoInvestVM;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/27 下午5:38
 * <p/>
 * Description: 自动投标
 */
public class AutoInvestAct extends BaseActivity {
    AccountAutoInvestActBinding binding;
    AutoInvestVM                autoInvestVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.account_auto_invest_act);
        autoInvestVM = new AutoInvestVM();
        binding.setViewModel(autoInvestVM);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.auto_title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoInvestVM.initAuto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PayCallBack payCallBack = new PayCallBack() {
            @Override
            public void callBack(boolean status) {
                autoInvestVM.initAuto();
            }
        };
        RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_AUTO_ON, requestCode, resultCode, data, payCallBack);
    }
}
