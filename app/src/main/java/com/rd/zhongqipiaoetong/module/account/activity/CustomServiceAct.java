package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.CustomServiceBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.CustomServiceVM;

/**
 * Created by pzw on 2017/6/21.
 */
public class CustomServiceAct extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomServiceBinding binding = DataBindingUtil.setContentView(this, R.layout.custom_service);
        binding.setViewModel(new CustomServiceVM());
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.more_service));
    }
}
