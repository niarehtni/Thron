package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.product.viewmodel.BondRecordListVM;

/**
 * 转让记录
 */
public class BondRecordListAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String                    id      = getIntent().getStringExtra(BundleKeys.ID);
        String                    uuid    = getIntent().getStringExtra(BundleKeys.UUID);
        CommonRecyclerViewBinding binding = DataBindingUtil.setContentView(this, R.layout.common_recycler_view);
        BondRecordListVM          vm      = new BondRecordListVM(id, uuid,getResources().getStringArray(R.array.investmentRecordTips));
        binding.setViewModel(vm);
        binding.executePendingBindings();
        vm.req_data(vm.getPtrFrame());
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.product_invest_title2);
    }
}
