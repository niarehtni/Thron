package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.databinding.AccountMyAssetsActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.MyAssetsAct;
import com.rd.zhongqipiaoetong.module.account.activity.RechargeAct;
import com.rd.zhongqipiaoetong.module.account.activity.WithdrawAct;
import com.rd.zhongqipiaoetong.module.account.model.AccountMo;
import com.rd.zhongqipiaoetong.module.account.model.AssetsItemMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.payment.ToPaymentCheck;
import com.rd.zhongqipiaoetong.payment.ToPaymentMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.view.entity.PieArc;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/14 15:47
 * <p/>
 * Description: ({@link MyAssetsAct})
 */
public class MyAssetsVM {
    public  AccountMo                 accountMo;
    private AccountMyAssetsActBinding binding;
    private Context                   mContext;
    private MyAssetsAdapter           adapter;
    /**
     * 圈圈
     */
    private List<PieArc>              list;
    /**
     * 定期理财 二级数据
     */
    private List<AssetsItemMo>        child1;
    /**
     * 债转 二级数据
     */
    private List<AssetsItemMo>        child2;
    /**
     * 所有数据
     */
    private List<AssetsItemMo>        data;
    /**
     * 圈内title
     */
    private String                    title;

    public MyAssetsVM(AccountMyAssetsActBinding binding) {
        this.binding = binding;
        init();
    }

    /**
     * 充值 - 点击事件
     */
    public void rechargeClick(View view) {
        ToPaymentMo toPaymentMo = new ToPaymentMo();
        toPaymentMo.setRealNameStatus(accountMo.getRealNameStatus());
        toPaymentMo.setAuthorize(accountMo.isAuthorize());
        toPaymentMo.setAuthorizeType(accountMo.getAuthorizeType());
        toPaymentMo.setBankNum(accountMo.getBankNum());
        if (!RDPayment.getInstance().getPayController().toPayment(PayController.TPYE_RECHARGE, toPaymentMo, new ToPaymentCheck(null), true)) {
            return;
        }

        ActivityUtils.push(RechargeAct.class);
    }

    /**
     * 提现 - 点击事件
     */
    public void withdrawClick(View view) {

        ToPaymentMo toPaymentMo = new ToPaymentMo();
        toPaymentMo.setRealNameStatus(accountMo.getRealNameStatus());
        toPaymentMo.setAuthorize(accountMo.isAuthorize());
        toPaymentMo.setAuthorizeType(accountMo.getAuthorizeType());
        toPaymentMo.setBankNum(accountMo.getBankNum());
        toPaymentMo.setHas_set_paypwd(accountMo.isHasSetPayPwd());
        if (!RDPayment.getInstance().getPayController().toPayment(PayController.TPYE_WITHDRAW, toPaymentMo, new ToPaymentCheck(null), true)) {
            return;
        }

        ActivityUtils.push(WithdrawAct.class);
    }

    /**
     * 请求用户信息
     */
    public void req_data() {
        Call<AccountMo> call = RDClient.getService(AccountService.class).basic();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<AccountMo>() {
            @Override
            public void onSuccess(Call<AccountMo> call, Response<AccountMo> response) {
                accountMo = response.body();
                setData();
            }
        });
    }

    /**
     * 初始化页面
     */
    private void init() {
        mContext = binding.getRoot().getContext();
        list = new ArrayList<>();
        //        child1 = new ArrayList<>();
        //        child2 = new ArrayList<>();
        data = new ArrayList<>();
        title = DisplayFormat.doubleFormat("0");
        adapter = new MyAssetsAdapter(list, title, data);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    /**
     * 添加数据（更新）
     */
    private void setData() {

        float total = Float.valueOf(accountMo.getAccountAmountTotal());

        list.clear();
        list.add(new PieArc(mContext.getResources().getColor(R.color.assets_blue), Float.valueOf(accountMo.getBalanceAvailable()) / total * 100));
        list.add(new PieArc(mContext.getResources().getColor(R.color.assets_green), Float.valueOf(accountMo.getAmountFrozen()) / total * 100));
        list.add(new PieArc(mContext.getResources().getColor(R.color.assets_orange), Float.valueOf(accountMo.getIncomeCapital()) / total * 100));
        list.add(new PieArc(mContext.getResources().getColor(R.color.assets_red), Float.valueOf(accountMo.getIncomeCollecting()) / total * 100));

        title = DisplayFormat.doubleFormat(accountMo.getAccountAmountTotal());

        //        child1.clear();
        //
        //        child1.add(new AssetsItemMo(MyAssetsAdapter.CHILD, 0, mContext.getString(R.string.assets_ztje), accountMo.getInvestingAmount(), null));
        //        child1.add(new AssetsItemMo(MyAssetsAdapter.CHILD, 0, mContext.getString(R.string.assets_dzzsj), accountMo.getToCollectEarnMoney(), null));
        //
        //        child2.clear();
        //        child2.add(new AssetsItemMo(MyAssetsAdapter.CHILD, 0, mContext.getString(R.string.assets_ztje), accountMo.getBondInvestingAmount(), null));
        //        child2.add(new AssetsItemMo(MyAssetsAdapter.CHILD, 0, mContext.getString(R.string.assets_dzzsj), accountMo.getToCollectEarnMoney(), null));

        data.clear();
        // 圆圈
        data.add(new AssetsItemMo(MyAssetsAdapter.HEADER, 0, null, null, null));
        // list
        data.add(new AssetsItemMo(MyAssetsAdapter.GROUP, mContext.getResources().getColor(R.color.assets_blue), mContext.getString(R.string
                .assets_kyye),
                DisplayFormat.doubleFormat(accountMo.getBalanceAvailable()), null));
        data.add(new AssetsItemMo(MyAssetsAdapter.GROUP, mContext.getResources().getColor(R.color.assets_green), mContext.getString(R.string.assets_djje),
                DisplayFormat.doubleFormat(accountMo.getAmountFrozen()), null));
        data.add(new AssetsItemMo(MyAssetsAdapter.GROUP, mContext.getResources().getColor(R.color.assets_orange), mContext.getString(R.string
                .assets_dsjr),
                DisplayFormat.doubleFormat(accountMo.getIncomeCapital()), null));
        data.add(new AssetsItemMo(MyAssetsAdapter.GROUP, mContext.getResources().getColor(R.color.assets_red), mContext.getString(R.string
                .assets_dssy),
                DisplayFormat.doubleFormat(accountMo.getIncomeCollecting()), null));
        MyAssetsAdapter adapter = (MyAssetsAdapter) binding.recyclerView.getAdapter();
        adapter.setTitle(title);
        adapter.notifyDataSetChanged();
    }
}