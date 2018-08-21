package com.rd.zhongqipiaoetong.module.user.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.UserLoginActBinding;
import com.rd.zhongqipiaoetong.module.user.viewmodel.LoginVM;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/16 上午10:28
 * <p/>
 * Description: 登录
 */
public class LoginAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserLoginActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_login_act);
        binding.setViewModel(new LoginVM(binding, getIntent().getBooleanExtra(BundleKeys.H5, false)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.login_title);
    }
}