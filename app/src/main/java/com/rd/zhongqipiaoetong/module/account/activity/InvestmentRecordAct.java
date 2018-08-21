package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.common.ui.BaseViewPagerVM;
import com.rd.zhongqipiaoetong.databinding.CommonViewPagerBinding;
import com.rd.zhongqipiaoetong.module.account.fragment.FlowFinancingFrag;
import com.rd.zhongqipiaoetong.module.account.fragment.InvestmentFinancingFrag;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/26 13:39
 * <p/>
 * Description: 投资记录
 * <p/>
 * 定期理财{@link InvestmentFinancingFrag}
 * <p/>
 * 债权转让{@link }
 */
public class InvestmentRecordAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonViewPagerBinding binding = DataBindingUtil.setContentView(this, R.layout.common_view_pager);
        BaseViewPagerVM        viewModel;
        if (FeatureUtils.getNeedbond()) {
            if(FeatureUtils.getEnableFlowInvestFeature()){
                viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.investmentRecordTitles), getSupportFragmentManager());
            } else {
                viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.investmentRecordNoFlowTitles), getSupportFragmentManager());
            }
        } else {
            if(FeatureUtils.getEnableFlowInvestFeature()){
                viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.investmentRecordNoBondTitles), getSupportFragmentManager());
            } else {
                viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.investmentRecordNoOtherTitles), getSupportFragmentManager());
            }
        }
        binding.setViewModel(viewModel);
        // 定期理财
        viewModel.items.add(new InvestmentFinancingFrag().create(0));
        //        viewModel.items.add(new InvestmentReceivedFrag());

        if (FeatureUtils.getNeedbond()) {
            viewModel.items.add(new InvestmentFinancingFrag().create(1));
        }
        if(FeatureUtils.getEnableFlowInvestFeature()){
            viewModel.items.add(new FlowFinancingFrag());
        }
        // 债权转让
        binding.executePendingBindings();

        binding.tabs.setupWithViewPager(binding.pager);
        binding.tabs.setBackgroundResource(R.color.white);
        binding.pager.setOffscreenPageLimit(viewModel.items.size() - 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.ir_title);
    }
}