package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountModfiypwdActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.ModifyLoginPwdVM;

/**
 * Description: 修改登陆密码页面
 */
public class ModfiyLoginPwdAct extends BaseActivity {
    private AccountModfiypwdActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.account_modfiypwd_act);
        binding.setViewmodel(new ModifyLoginPwdVM(binding));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.pwd_modify_login);
    }
}