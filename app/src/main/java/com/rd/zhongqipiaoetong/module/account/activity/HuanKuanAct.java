package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountRepatmentListActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.HuanKuanVM;

/**
 * Created by pzw on 2018/1/22.
 */
public class HuanKuanAct extends BaseActivity {
    AccountRepatmentListActBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.account_repatment_list_act);
        binding.setViewModel(new HuanKuanVM(binding));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.account_repayment_manage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(binding.ptrAll != null){
            binding.ptrAll.autoRefresh();
        }
    }
}
