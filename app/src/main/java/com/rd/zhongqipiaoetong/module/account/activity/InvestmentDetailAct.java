package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.common.ui.BaseViewPagerVM;
import com.rd.zhongqipiaoetong.databinding.CommonViewPagerBinding;
import com.rd.zhongqipiaoetong.module.account.fragment.InvestmentDetailFrag;
import com.rd.zhongqipiaoetong.module.account.fragment.InvestmentReceivedFrag;
import com.rd.zhongqipiaoetong.module.account.model.CashRecordDetailMo;
import com.rd.zhongqipiaoetong.module.account.model.InvestRecordDetailMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.LogService;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/2 17:11
 * <p/>
 * Description: 投资详情
 * <p/>
 * 投资信息{@link InvestmentDetailFrag}
 * <p/>
 * 回款信息{@link InvestmentReceivedFrag}
 */
public class InvestmentDetailAct extends BaseActivity {
    private InvestmentDetailFrag   investmentDetailFrag;
    private InvestmentReceivedFrag investmentReceivedFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonViewPagerBinding binding = DataBindingUtil.setContentView(this, R.layout.common_view_pager);
        BaseViewPagerVM viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.investmentDetailTitles), getSupportFragmentManager());
        // 投资信息
        investmentDetailFrag = new InvestmentDetailFrag();
        viewModel.items.add(investmentDetailFrag);
        // 回款信息
        investmentReceivedFrag = new InvestmentReceivedFrag();
        viewModel.items.add(investmentReceivedFrag);

        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(viewModel.items.size() - 1);
        if(getIntent().getIntExtra(BundleKeys.TRANSFERTYPE, Constant.NUMBER_0) == Constant.NUMBER_1){
            res_bonddata(getIntent().getStringExtra(BundleKeys.INVESTMENT_ID));
        }else{
            res_data(getIntent().getStringExtra(BundleKeys.INVESTMENT_ID));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.ir_detail);
    }

    private void res_data(String id) {
        Call<InvestRecordDetailMo> call = RDClient.getService(LogService.class).investRecordDetail(id);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<InvestRecordDetailMo>() {
            @Override
            public void onSuccess(Call<InvestRecordDetailMo> call, Response<InvestRecordDetailMo> response) {
                investmentDetailFrag.setRecordData(response.body());
                investmentReceivedFrag.setRecordData(response.body());
            }
        });
    }

    private void res_bonddata(String id) {
        Call<CashRecordDetailMo> call = RDClient.getService(LogService.class).boughtBondDetail(id);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<CashRecordDetailMo>() {
            @Override
            public void onSuccess(Call<CashRecordDetailMo> call, Response<CashRecordDetailMo> response) {
                investmentDetailFrag.setRecordData(response.body());
                investmentReceivedFrag.setRecordData(response.body());
            }
        });
    }
}