package com.rd.zhongqipiaoetong.module.account.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.activity.InvestmentRecordAct;
import com.rd.zhongqipiaoetong.module.account.viewmodel.InvestmentFinancingVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/7 17:40
 * <p/>
 * Description: 投资记录{@link InvestmentRecordAct} - 定期理财
 */
public class InvestmentFinancingFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding binding;
    private InvestmentFinancingVM     financingVM;
    /**
     * 0 - 定期理财
     * 1 - 债权转让
     */
    private int                       type;

    public Fragment create(int type) {
        this.type = type;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);

        financingVM = new InvestmentFinancingVM(type, getResources().getStringArray(R.array.accountInvestmentRecordTips));
        binding.setViewModel(financingVM);
        binding.executePendingBindings();
        binding.recycler.setNestedScrollingEnabled(false);
        ready();
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        // 如果加载页面可见，则表示初次进入界面，需要请求数据
        if (financingVM.emptyState.isLoading()) {
            financingVM.req_data(financingVM.getPtrFrame());
        }
    }
}