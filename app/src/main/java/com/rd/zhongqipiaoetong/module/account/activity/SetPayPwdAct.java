package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountSetpaypwdActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.SetPayPwdVM;

/**
 * Description: 设置交易密码
 */
public class SetPayPwdAct extends BaseActivity {
    private AccountSetpaypwdActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.account_setpaypwd_act);
        binding.setViewmodel(new SetPayPwdVM(binding));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.setpaypwd_title);
    }
}