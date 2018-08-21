package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.common.ui.BaseViewPagerVM;
import com.rd.zhongqipiaoetong.databinding.CommonViewPagerBinding;
import com.rd.zhongqipiaoetong.module.account.fragment.RepaymentDetailFrag;
import com.rd.zhongqipiaoetong.module.account.fragment.RepaymentReceivedFrag;
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
 * 投资信息
 * <p/>
 * 回款信息
 */
public class RepaymentDetailAct extends BaseActivity {
    private RepaymentDetailFrag   repaymentDetailFrag;
    private RepaymentReceivedFrag repaymentReceivedFrag;
    private String investmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        investmentId = getIntent().getStringExtra(BundleKeys.INVESTMENT_ID);
        CommonViewPagerBinding binding = DataBindingUtil.setContentView(this, R.layout.common_view_pager);
        BaseViewPagerVM viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.investmentDetailTitles), getSupportFragmentManager());
        // 投资信息
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeys.INVESTMENT_ID,investmentId);
        repaymentDetailFrag = new RepaymentDetailFrag();
        repaymentDetailFrag.setArguments(bundle);
        viewModel.items.add(repaymentDetailFrag);
        // 回款信息
        repaymentReceivedFrag = new RepaymentReceivedFrag();
        viewModel.items.add(repaymentReceivedFrag);

        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(viewModel.items.size() - 1);
        if(getIntent().getIntExtra(BundleKeys.TRANSFERTYPE, Constant.NUMBER_0) == Constant.NUMBER_1){
            res_bonddata(investmentId);
        }else{
            res_data(investmentId);
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
                repaymentDetailFrag.setRecordData(response.body());
                repaymentReceivedFrag.setRecordData(response.body());
            }
        });
    }

    private void res_bonddata(String id) {
        Call<CashRecordDetailMo> call = RDClient.getService(LogService.class).boughtBondDetail(id);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<CashRecordDetailMo>() {
            @Override
            public void onSuccess(Call<CashRecordDetailMo> call, Response<CashRecordDetailMo> response) {
                repaymentDetailFrag.setRecordData(response.body());
                repaymentReceivedFrag.setRecordData(response.body());
            }
        });
    }
}