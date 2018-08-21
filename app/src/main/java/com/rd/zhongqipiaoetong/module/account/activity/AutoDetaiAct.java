package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountAutoDetailActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.AutoDetailVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/28 上午10:51
 * <p/>
 * Description: 投标参数详情
 */
public class AutoDetaiAct extends BaseActivity {
    AutoDetailVM autoDetailVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountAutoDetailActBinding binding = DataBindingUtil.setContentView(this, R.layout.account_auto_detail_act);
        autoDetailVM = new AutoDetailVM();
        binding.setViewModel(autoDetailVM);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.auto_detail_title);
        autoDetailVM.initAuto();
    }
}

