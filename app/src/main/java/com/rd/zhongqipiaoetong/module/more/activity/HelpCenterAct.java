package com.rd.zhongqipiaoetong.module.more.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.more.viewmodel.HelpCenterVM;

/**
 * Created by Administrator on 2017/4/24.
 */
public class HelpCenterAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonRecyclerViewBinding binding = DataBindingUtil.setContentView(this, R.layout.common_recycler_view);
        binding.setViewModel(new HelpCenterVM());
        binding.executePendingBindings();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.helpcenter_title);
    }
}
