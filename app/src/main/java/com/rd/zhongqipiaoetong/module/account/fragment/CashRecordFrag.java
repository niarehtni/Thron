package com.rd.zhongqipiaoetong.module.account.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CashRecordFragBinding;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.CashRecordVM;

/**
 * Author: tudehua
 * E-mail:tdh@erongdu.com
 * Date: 2017/6/21 11:54
 * <p/>
 * Descripiton: 资金记录
 */
public class CashRecordFrag extends BaseLazyFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CashRecordFragBinding binding = DataBindingUtil.inflate(inflater, R.layout.cash_record_frag, null, false);
        binding.setViewModel(new CashRecordVM(binding));
        binding.recycler.setNestedScrollingEnabled(false);
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {

    }
}
