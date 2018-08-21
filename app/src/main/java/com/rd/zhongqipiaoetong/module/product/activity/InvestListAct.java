package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.product.viewmodel.InvestRecordListVM;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/2/25 10:20
 * <p/>
 * Description: 投资记录、转让记录
 */
public class InvestListAct extends BaseActivity {
    private CommonRecyclerViewBinding binding;
    private InvestRecordListVM        listVM;
    private boolean                   isInvestRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id   = getIntent().getStringExtra(BundleKeys.ID);
        String uuid = getIntent().getStringExtra(BundleKeys.UUID);
        isInvestRecord = getIntent().getBooleanExtra(BundleKeys.IS_INVESTRECORD, true);

        binding = DataBindingUtil.setContentView(this, R.layout.common_recycler_view);

        listVM = new InvestRecordListVM(id, uuid, isInvestRecord, FeatureConfig.enableInvestRecordTimeFeature == 1 ? getResources().getStringArray(R.array
                .investmentRecordTips) : getResources().getStringArray(R.array.investmentRecordTips_without_time));

        binding.setViewModel(listVM);

        //立即绑定
        binding.executePendingBindings();
        listVM.req_data(listVM.getPtrFrame());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isInvestRecord) {
            setTitle(R.string.product_invest_title1);
        } else {
            setTitle(R.string.product_invest_title2);
        }
    }
}