package com.rd.zhongqipiaoetong.module.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountWithdrawActBinding;
import com.rd.zhongqipiaoetong.module.account.model.BankCardMo;
import com.rd.zhongqipiaoetong.module.account.viewmodel.WithdrawVM;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/17 14:18
 * <p/>
 * Description: 提现界面
 */
public class WithdrawAct extends BaseActivity {
    private WithdrawVM withdrawVM;

    /**
     * 三方控制器
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountWithdrawActBinding binding = DataBindingUtil.setContentView(this, R.layout.account_withdraw_act);
        withdrawVM = new WithdrawVM(binding);
        binding.setViewModel(withdrawVM);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.withdraw_title);
        setRightText(R.string.record, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.ISWITHDRAW, true);
                ActivityUtils.push(FinancialRecordsAct.class, intent);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PayCallBack payCallBack = new PayCallBack() {
            @Override
            public void callBack(boolean status) {
                if (status) {
                    ActivityUtils.pop(WithdrawAct.this, BundleKeys.RESULT_CODE_WITHDRAW, new Intent());
                } else {
                    withdrawVM.req_data();
                }
            }
        };
        if (RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_WITHDRAW, requestCode, resultCode, data, payCallBack)) {//三方回调
            ActivityUtils.pop(this, BundleKeys.RESULT_CODE_WITHDRAW, new Intent());
        }
        if (requestCode == BundleKeys.REQUEST_CODE_BANKLIST && resultCode == Activity.RESULT_OK) {//选择银行卡返回
            withdrawVM.upBankCard((BankCardMo) data.getParcelableExtra(BundleKeys.BANKCARDMO));
        }
    }
}