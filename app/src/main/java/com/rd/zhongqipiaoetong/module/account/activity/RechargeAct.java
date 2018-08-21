package com.rd.zhongqipiaoetong.module.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountRechargeActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.RechargeVM;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/17 14:18
 * <p/>
 * Description: 充值界面
 */
public class RechargeAct extends BaseActivity {
    RechargeVM rechargeVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountRechargeActBinding binding = DataBindingUtil.setContentView(this, R.layout.account_recharge_act);
        rechargeVM = new RechargeVM();
        binding.rechargeMoney.setFilters(new InputFilter[]{EditTextFormat.getLengthFilter(), new InputFilter.LengthFilter(9) {
        }});
        binding.setViewModel(rechargeVM);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.recharge_title);
        setRightText(R.string.record, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.ISRECHARGE, true);
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
                rechargeVM.req_toRecharage();
            }
        };
        if (requestCode == PayController.REQUEST_CODE_AUTH) {
            RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_AUTH, requestCode, resultCode, data, payCallBack);
            return;
        }
        if (RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_RECHARGE, requestCode, resultCode, data, payCallBack)) {
            ActivityUtils.pop(this, BundleKeys.RESULT_CODE_RECHARGE, new Intent());
        }
    }
}