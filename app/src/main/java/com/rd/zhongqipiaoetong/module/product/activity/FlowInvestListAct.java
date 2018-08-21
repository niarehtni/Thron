package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.product.viewmodel.FlowInvestRecordListVM;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/2/25 10:20
 * <p/>
 * Description: 流转投资记录
 */
public class FlowInvestListAct extends BaseActivity {
    private CommonRecyclerViewBinding binding;
    private FlowInvestRecordListVM        listVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id   = getIntent().getLongExtra(BundleKeys.ID,0);


        binding = DataBindingUtil.setContentView(this, R.layout.common_recycler_view);

        listVM = new FlowInvestRecordListVM(id, getResources().getStringArray(R.array.flowInvestmentRecordTips));

        binding.setViewModel(listVM);

        //立即绑定
        binding.executePendingBindings();
        listVM.req_data(listVM.getPtrFrame());
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.product_invest_title1);
    }
}
