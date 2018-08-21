package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountResetpaypwdActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.ResetPayPwdVM;

/**
 * Description: 重置支付密码
 */
public class ResetPayPwdAct extends BaseActivity {
    private AccountResetpaypwdActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.account_resetpaypwd_act);
        binding.setViewmodel(new ResetPayPwdVM(binding));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.resetpaypwd_title);
    }
}