package com.rd.zhongqipiaoetong.module.user.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.UserRegisterFirstActBinding;
import com.rd.zhongqipiaoetong.module.user.viewmodel.RegisterFirstVM;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/17 下午3:37
 * <p/>
 * Description: 注册页面
 */
public class RegisterFirstAct extends BaseActivity {
    private UserRegisterFirstActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_register_first_act);
        binding.setViewModel(new RegisterFirstVM(binding,getIntent().getBooleanExtra(BundleKeys.H5,false)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.register_title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.registerTimebtn.onDestroy();
    }
}