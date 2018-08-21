package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountModfiypaypwdActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.ModifyPayPwdVM;

/**
 * Description: 修改登陆密码页面
 */
public class ModfiyPayPwdAct extends BaseActivity {
    private AccountModfiypaypwdActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.account_modfiypaypwd_act);
        binding.setViewmodel(new ModifyPayPwdVM(binding));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.pwd_modify_payment);
    }
}