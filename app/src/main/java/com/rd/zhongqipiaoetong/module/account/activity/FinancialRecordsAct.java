package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.common.ui.BaseViewPagerVM;
import com.rd.zhongqipiaoetong.databinding.CommonViewPagerBinding;
import com.rd.zhongqipiaoetong.module.account.fragment.CashRecordFrag;
import com.rd.zhongqipiaoetong.module.account.fragment.FinancialRecordFrag;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/21 15:14
 * <p/>
 * Description: 充值 + 提现记录
 * 充值记录({@link FinancialRecordFrag})
 * <p/>
 * 提现记录({@link FinancialRecordFrag})
 */
public class FinancialRecordsAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonViewPagerBinding binding = DataBindingUtil.setContentView(this, R.layout.common_view_pager);

        BaseViewPagerVM viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.financialRecordsActTitles), getSupportFragmentManager());
        binding.setViewModel(viewModel);
        viewModel.items.add(new CashRecordFrag());
        // 0 - 充值记录
        viewModel.items.add(new FinancialRecordFrag().create(Constant.NUMBER_0));
        // 1 - 提现记录
        viewModel.items.add(new FinancialRecordFrag().create(Constant.NUMBER_1));
        binding.executePendingBindings();

        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(viewModel.items.size() - 1);
        //判断是否由提现页面进入
        binding.tabs.setBackgroundResource(R.color.white);
        if (getIntent().getBooleanExtra(BundleKeys.ISRECHARGE, false)) {
            binding.pager.setCurrentItem(1);
        } else if (getIntent().getBooleanExtra(BundleKeys.ISWITHDRAW, false)) {
            binding.pager.setCurrentItem(2);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.account_log);
    }
}