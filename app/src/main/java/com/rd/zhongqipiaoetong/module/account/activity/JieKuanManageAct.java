package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountJiekuanguanliActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.JieKuanManageVM;

/**
 * Created by pzw on 2018/1/19.
 */
public class JieKuanManageAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountJiekuanguanliActBinding binding = DataBindingUtil.setContentView(this, R.layout.account_jiekuanguanli_act);
        JieKuanManageVM viewModel = new JieKuanManageVM(binding);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.account_loan_manage);
    }
}
