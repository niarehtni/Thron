package com.rd.zhongqipiaoetong.module.more.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.more.viewmodel.ActivityVM;

/**
 * 精彩活动页面
 */
public class ActivityAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonRecyclerViewBinding binding = DataBindingUtil.setContentView(this, R.layout.common_recycler_view);
        binding.setViewModel(new ActivityVM());
        binding.executePendingBindings();
        binding.getRoot().setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.more_activity);
    }
}
