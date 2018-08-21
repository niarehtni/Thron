package com.rd.zhongqipiaoetong.module.account.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentRecordAct;
import com.rd.zhongqipiaoetong.module.account.viewmodel.PendingPaymentVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/8 14:06
 * <p/>
 * Description: 回款记录{@link PaymentRecordAct} - 已回款
 */
public class AlreadyPaymentFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding binding;
    private PendingPaymentVM          paymentVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);
        paymentVM = new PendingPaymentVM(getResources().getStringArray(R.array.paymentRecordActTips), 1);
        binding.setViewModel(paymentVM);
        binding.executePendingBindings();
        binding.recycler.setNestedScrollingEnabled(false);
        ready();
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        // 如果加载页面可见，则表示初次进入界面，需要请求数据
        if (paymentVM.emptyState.isLoading()) {
            paymentVM.req_data(paymentVM.getPtrFrame());
        }
    }
}