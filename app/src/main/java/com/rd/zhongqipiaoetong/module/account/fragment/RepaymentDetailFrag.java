package com.rd.zhongqipiaoetong.module.account.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.activity.InvestmentDetailAct;
import com.rd.zhongqipiaoetong.module.account.model.CashRecordDetailMo;
import com.rd.zhongqipiaoetong.module.account.model.InvestRecordDetailMo;
import com.rd.zhongqipiaoetong.module.account.viewmodel.RepaymentDetailVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/4 18:20
 * <p/>
 * Description: 投资详情({@link InvestmentDetailAct}) - 投资信息界面
 */
public class RepaymentDetailFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding binding;
    private RepaymentDetailVM        detailVM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);
        String investmentId = getArguments().getString(BundleKeys.INVESTMENT_ID);
        detailVM = new RepaymentDetailVM(binding,investmentId);
        binding.setViewModel(detailVM);
        ready();
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        // 如果加载页面可见，则表示初次进入界面，需要请求数据
        if (detailVM.emptyState.isLoading()) {
            detailVM.emptyState.setLoading(false);
        }
    }

    public void setRecordData(InvestRecordDetailMo mo) {
        detailVM.setRecordData(mo);
    }

    public void setRecordData(CashRecordDetailMo mo) {
        detailVM.setRecordData(mo);
    }
}