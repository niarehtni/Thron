package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountDoTransferActBinding;
import com.rd.zhongqipiaoetong.module.account.model.TransferMo;
import com.rd.zhongqipiaoetong.module.account.viewmodel.DoTransferVM;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/21 下午8:22
 * <p/>
 * Description: 债权转让操作界面
 */
public class DoTransferAct extends BaseActivity {
    private AccountDoTransferActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.account_do_transfer_act);
        String feeType = getIntent().getStringExtra(BundleKeys.FEETYPE);
        double maxRate = getIntent().getDoubleExtra(BundleKeys.DISCOUNTRATEMAX,0);
        double minRate = getIntent().getDoubleExtra(BundleKeys.DISCOUNTRATEMIN,0);
        double sellFee = getIntent().getDoubleExtra(BundleKeys.SELLFEE,0);
        binding.setViewModel(new DoTransferVM(binding, (TransferMo) getIntent().getParcelableExtra(BundleKeys.TRANSFERMO),maxRate,minRate,sellFee,feeType));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.transfer_title);
    }
}