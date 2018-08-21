package com.rd.zhongqipiaoetong.module.account.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.activity.FinancialRecordsAct;
import com.rd.zhongqipiaoetong.module.account.viewmodel.FinancialRecordVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/21 15:24
 * <p/>
 * Description: 资金记录({@link FinancialRecordsAct})
 */
public class FinancialRecordFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding binding;
    private FinancialRecordVM         recordVM;
    /**
     * 0 - 充值记录
     * 1 - 提现记录
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
        String[] tips;
        if (type == Constant.NUMBER_0) {
            tips = getResources().getStringArray(R.array.financialRecordsActTips1);
        } else {
            tips = getResources().getStringArray(R.array.financialRecordsActTips2);
        }
        recordVM = new FinancialRecordVM(type, tips);
        binding.setViewModel(recordVM);
        binding.executePendingBindings();
        binding.recycler.setNestedScrollingEnabled(false);
        ready();
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        // 如果加载页面可见，则表示初次进入界面，需要请求数据
        if (recordVM.emptyState.isLoading()) {
            recordVM.req_data(recordVM.getPtrFrame());
        }
    }
}