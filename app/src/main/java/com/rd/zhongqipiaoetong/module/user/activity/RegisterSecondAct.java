package com.rd.zhongqipiaoetong.module.user.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Toast;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.UserRegisterSecondActBinding;
import com.rd.zhongqipiaoetong.module.user.viewmodel.RegisterSecondVM;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/17 下午7:37
 * <p/>
 * Description: 注册设置密码界面
 */
public class RegisterSecondAct extends BaseActivity {
    private UserRegisterSecondActBinding binding;
    private Context                     content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_register_second_act);
        String phone = getIntent().getStringExtra(BundleKeys.PHONE);
        String code  = getIntent().getStringExtra(BundleKeys.CODE);
        binding.setViewModel(new RegisterSecondVM(this,phone, code, binding, getIntent().getBooleanExtra(BundleKeys.H5,false)));
        binding.registerPwd.setFilters(new InputFilter[]{EditTextFormat.getBlankFilter(), new InputFilter.LengthFilter(16)});
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.register_set_password);
    }
}