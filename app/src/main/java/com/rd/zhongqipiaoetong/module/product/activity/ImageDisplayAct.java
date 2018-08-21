package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.ProductImagedisplayActBinding;
import com.rd.zhongqipiaoetong.module.product.viewmodel.ImageDisplayVM;

import java.util.List;

public class ImageDisplayAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductImagedisplayActBinding binding = DataBindingUtil.setContentView(this, R.layout.product_imagedisplay_act);
        int position = getIntent().getIntExtra("position",0);
        List<String> list = getIntent().getStringArrayListExtra("list");
        binding.setViewModel(new ImageDisplayVM(binding,position,list));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("查看图片");
    }
}
