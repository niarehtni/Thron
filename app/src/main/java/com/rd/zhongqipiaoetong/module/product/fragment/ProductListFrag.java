package com.rd.zhongqipiaoetong.module.product.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.MainVM;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseFragment;
import com.rd.zhongqipiaoetong.common.ui.BaseViewPagerVM;
import com.rd.zhongqipiaoetong.databinding.CommonViewPagerBinding;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/7 16:49
 * <p/>
 * Description:产品列表Frag({@link MainVM#mProductListFrag})
 * 定期理财({@link FinanceListFrag})
 * <p/>
 * 债权转让({@link CreditorListFrag})
 */
public class ProductListFrag extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CommonViewPagerBinding binding = DataBindingUtil.inflate(inflater, R.layout.common_view_pager, container, false);
        BaseViewPagerVM        viewModel;
        if (FeatureUtils.getNeedbond()) {
            if(FeatureUtils.getEnableFlowInvestFeature()){
                viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.productListTitles), getChildFragmentManager());
            } else {
                viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.productListNoFlowTitles), getChildFragmentManager());
            }
        } else {
            if(FeatureUtils.getEnableFlowInvestFeature()){
                viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.productListNoBondTitles), getChildFragmentManager());
            } else {
                viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.productListNoOtherTitles), getChildFragmentManager());
            }
        }
        // 定期理财
        viewModel.items.add(new FinanceListFrag());
        if (FeatureUtils.getNeedbond()) {
            // 债权转让
            viewModel.items.add(new CreditorListFrag());
        }
        if(FeatureUtils.getEnableFlowInvestFeature()){
            //即息理财
            viewModel.items.add(new FlowListFrag());
        }

        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(viewModel.items.size() - 1);
        binding.titleBar.appbar.setTitle(R.string.product_list_title);
        binding.titleBar.appbar.setBackOption(false);
        return binding.getRoot();
    }
}