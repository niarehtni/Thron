package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountEmailBindBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.EmailBindVM;

public class EmailBindAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountEmailBindBinding binding = DataBindingUtil.setContentView(this, R.layout.account_email_bind);
        binding.setViewModel(new EmailBindVM(binding));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.email_title);
    }
}
