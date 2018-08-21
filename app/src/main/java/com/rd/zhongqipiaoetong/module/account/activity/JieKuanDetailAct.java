package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountJiekuanguanliDetailActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.JieKuanDetailVM;

/**
 * Created by pzw on 2018/1/22.
 */
public class JieKuanDetailAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String                               id      = getIntent().getStringExtra(BundleKeys.ID);
        AccountJiekuanguanliDetailActBinding binding = DataBindingUtil.setContentView(this, R.layout.account_jiekuanguanli_detail_act);
        binding.setViewModel(new JieKuanDetailVM(binding, id));
    }
}
