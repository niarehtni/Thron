package com.rd.zhongqipiaoetong.module.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputFilter;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountAutoSetupActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.AutoSetupVM;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/28 下午3:09
 * <p/>
 * Description: 自动投资设置
 */
public class AutoSetupAct extends BaseActivity {
    private boolean isfirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isfirst = getIntent().getBooleanExtra(BundleKeys.ISFIRST, false);
        AccountAutoSetupActBinding binding = DataBindingUtil.setContentView(this, R.layout.account_auto_setup_act);
        binding.autoSetupApr.setFilters(new InputFilter[]{EditTextFormat.getLengthFilter(), new InputFilter.LengthFilter(5) {
        }});
        binding.setViewModel(new AutoSetupVM(binding, isfirst));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.auto_setup_title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PayCallBack payCallBack = new PayCallBack() {
            @Override
            public void callBack(boolean status) {
                if (status) {
                    ActivityUtils.pop(AutoSetupAct.this);
                }
            }
        };
        RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_AUTO_ON, requestCode, resultCode, data, payCallBack);
    }
}
