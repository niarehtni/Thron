package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.databinding.AccountJiekuanguanliDetailActBinding;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.product.model.FinancingDetailMo;
import com.rd.zhongqipiaoetong.module.user.model.ProtocolMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by pzw on 2018/1/22.
 */
public class JieKuanDetailVM {
    public ObservableField<FinancingDetailMo> detail = new ObservableField<>();
    AccountJiekuanguanliDetailActBinding binding;
    boolean isGetProtocol = false;
    String protocolUrl;

    public JieKuanDetailVM(AccountJiekuanguanliDetailActBinding binding, String id) {
        this.binding = binding;
        reqData(id);
    }

    private void reqData(String id) {
        Call<FinancingDetailMo> call = RDClient.getService(ProductService.class).investDetail(id);
        call.enqueue(new RequestCallBack<FinancingDetailMo>() {
            @Override
            public void onSuccess(Call<FinancingDetailMo> call, Response<FinancingDetailMo> response) {
                detail.set(response.body());
                binding.titleBar.appbar.setTitle(detail.get().getBorrowVO().getName());
            }
        });
    }

    public void protocolClick(View view){
        if(isGetProtocol){
            openProtocol();
            return;
        }
        Call<ProtocolMo> call = RDClient.getService(ProductService.class).getBorrowProtocol(detail.get().getBorrowVO().getId() + "");
        call.enqueue(new RequestCallBack<ProtocolMo>() {
            @Override
            public void onSuccess(Call<ProtocolMo> call, Response<ProtocolMo> response) {
                protocolUrl = response.body().getProtocolContext();
                isGetProtocol = true;
                openProtocol();
            }
        });
    }

    private void openProtocol(){
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.DATA, protocolUrl);
        intent.putExtra(BundleKeys.TITLE, "协议");
        ActivityUtils.push(RDWebViewAct.class, intent);
    }
}
