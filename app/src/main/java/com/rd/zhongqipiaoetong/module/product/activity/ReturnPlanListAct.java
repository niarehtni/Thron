package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.product.viewmodel.ReturnPlanListVM;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/1 13:25
 * <p/>
 * Description:还款计划列表
 */
public class ReturnPlanListAct extends BaseActivity {
    private CommonRecyclerViewBinding binding;
    private ReturnPlanListVM          listVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.common_recycler_view);
        listVM = new ReturnPlanListVM();
        binding.setViewModel(listVM);
        binding.getRoot().postDelayed(new Runnable() {
            @Override
            public void run() {
                listVM.emptyState.setLoading(false);
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.product_return_title);
    }
}