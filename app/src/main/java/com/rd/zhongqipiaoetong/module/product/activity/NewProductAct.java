package com.rd.zhongqipiaoetong.module.product.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.ProductNewProductDetailBinding;
import com.rd.zhongqipiaoetong.module.product.viewmodel.NewProductVM;

/**
 * Created by Administrator on 2017/4/5.
 */
public class NewProductAct extends BaseActivity {

    private NewProductVM newProductVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductNewProductDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.product_new_product_detail);
        String                         id      = getIntent().getStringExtra(BundleKeys.ID);
        newProductVM = new NewProductVM(binding, id);
        binding.setVm(newProductVM);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.newproduct_title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        newProductVM.onActivityResult(requestCode, resultCode, data);
    }
}
