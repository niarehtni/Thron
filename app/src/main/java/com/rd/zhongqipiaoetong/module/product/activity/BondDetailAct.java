package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.ProductBondDetailActBinding;
import com.rd.zhongqipiaoetong.module.product.viewmodel.BondDetailVM;

/**
 * <p/>
 * Description: 债券详情
 */
public class BondDetailAct extends BaseActivity {
    private ProductBondDetailActBinding binding;
    private BondDetailVM                vm;
    private String                      name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id   = getIntent().getStringExtra(BundleKeys.ID);
        String uuid = getIntent().getStringExtra(BundleKeys.UUID);
        binding = DataBindingUtil.setContentView(this, R.layout.product_bond_detail_act);
        name = getIntent().getStringExtra(BundleKeys.NAME);
        vm = new BondDetailVM(uuid, id,binding);
        binding.setViewModel(vm);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.req_data(binding.ptrAll);
    }
}