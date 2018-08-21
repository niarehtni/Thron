package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountLoanDetailActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.LoanDetailVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/14 15:29
 * <p/>
 * Description: 借款详情
 */
public class LoanDetailAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountLoanDetailActBinding binding = DataBindingUtil.setContentView(this, R.layout.account_loan_detail_act);
        binding.setViewModel(new LoanDetailVM());
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.loan_detail_title);
    }
}