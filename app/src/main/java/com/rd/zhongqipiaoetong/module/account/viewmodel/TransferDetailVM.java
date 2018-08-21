package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseListItemMo;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.databinding.TransferDetailActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.TransferDetailAct;
import com.rd.zhongqipiaoetong.module.account.model.TransferMo;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.product.activity.BondDetailAct;
import com.rd.zhongqipiaoetong.module.product.activity.BondRecordListAct;
import com.rd.zhongqipiaoetong.module.product.activity.FinancingDetailAct;
import com.rd.zhongqipiaoetong.module.user.model.ProtocolMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/13 17:59
 * <p/>
 * Description: 转让详情页面VM({@link TransferDetailAct})
 */
public class TransferDetailVM extends BaseRecyclerViewVM<BaseListItemMo> {
    private TransferDetailActBinding binding;

    @Override
    protected void selectView(ItemView itemView, int position, BaseListItemMo item) {
        itemView.setBindingVariable(BR.item).setLayoutRes(R.layout.account_transfer_recycler_view_item);
    }

    public TransferDetailVM(TransferDetailActBinding binding, TransferMo transferMo, int type) {
        this.binding = binding;
        hidden.set(false);
        if(type  == Constant.NUMBER_1){
            items.addAll(initTransfering(transferMo));
        } else {
            items.addAll(initTransfered(transferMo));
        }
    }

    /**
     * 完成转让数据转换
     */
    private List<BaseListItemMo> initTransfered(final TransferMo mo) {
        List<BaseListItemMo> list     = new ArrayList<>();
        Context              mContext = binding.getRoot().getContext();
        final String         desc[]   = binding.getRoot().getResources().getStringArray(R.array.transferedBond);
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[0], mo.getBorrowName(), Constant.NUMBER_0, Constant.NUMBER_1, true, true));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[1], binding.getRoot().getContext().getResources().getStringArray(R.array.creditorListTitles)
                [Constant.NUMBER_2], Constant.NUMBER_0, Constant.NUMBER_0, false, false));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[3], DisplayFormat.doubleMoney(mo.getManageFee()), Constant.NUMBER_0, Constant.NUMBER_0,
                false, false));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[4], DisplayFormat.doubleMoney(mo.getBondCapital()), Constant.NUMBER_0, Constant.NUMBER_0,
                false, false));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[5], DisplayFormat.doubleMoney(mo.getSellingPrice()), Constant.NUMBER_0, Constant.NUMBER_0,
                false, false));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[6], mContext.getString(R.string.apr_percent, String.valueOf(mo.getDiscountRate())), Constant.NUMBER_0, Constant.NUMBER_0,
                false, false));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[7], DisplayFormat.coverTimeYYMMDD(mo.getAddTime()), Constant.NUMBER_0, Constant.NUMBER_0,
                false, false));

        list.add(new BaseListItemMo(Constant.NUMBER_1, desc[10], "", Constant.NUMBER_0, Constant.NUMBER_0, true, false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.ID, String.valueOf(mo.getBorrowId()));
                intent.putExtra(BundleKeys.NAME, mo.getBorrowName());
                ActivityUtils.push(FinancingDetailAct.class, intent);
            }
        }));
        //        list.add(new BaseListItemMo(Constant.NUMBER_1, desc[11], "", Constant.NUMBER_0, Constant.NUMBER_0,
        //                false, false, new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Intent intent = new Intent();
        //                intent.putExtra(BundleKeys.TITLE, desc[11]);
        //                intent.putExtra(BundleKeys.URL, mo.getProtrcolUrl());
        //                ActivityUtils.push(RDWebViewAct.class, intent);
        //            }
        //        }));
        list.add(new BaseListItemMo(Constant.NUMBER_1, desc[12], "", Constant.NUMBER_0, Constant.NUMBER_0,
                false, false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.ID, String.valueOf(mo.getId()));
                intent.putExtra(BundleKeys.IS_INVESTRECORD, false);
                ActivityUtils.push(BondRecordListAct.class, intent);
            }
        }));
        list.add(new BaseListItemMo(Constant.NUMBER_1, "查看协议", "", Constant.NUMBER_0, Constant.NUMBER_0,
                false, false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProtocol(mo.getId());
            }
        }));

        return list;
    }

    /**
     * 转让中数据转换（转让中）
     */
    private List<BaseListItemMo> initTransfering(final TransferMo mo) {
        List<BaseListItemMo> list     = new ArrayList<>();
        Context              mContext = binding.getRoot().getContext();
        String               desc[]   = mContext.getResources().getStringArray(R.array.cantransferBond);
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[0], mo.getBorrowName(), Constant.NUMBER_0, Constant.NUMBER_1, true, true));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[1], binding.getRoot().getContext().getResources().getStringArray(R.array.creditorListTitles)
                [Constant.NUMBER_1], Constant.NUMBER_0, Constant.NUMBER_0, false, false));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[2], DisplayFormat.doubleMoney(mo.getBondCapital()), Constant.NUMBER_0, Constant.NUMBER_0, false, false));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[3], DisplayFormat.doubleMoney(mo.getSellingPrice()), Constant.NUMBER_0, Constant.NUMBER_0, false,
                false));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[4], mContext.getString(R.string.apr_percent, String.valueOf(mo.getDiscountRate())), Constant.NUMBER_0, Constant.NUMBER_0,
                false, false));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[5], DisplayFormat.coverTimeYYMMDD(mo.getAddTime()), Constant.NUMBER_0, Constant.NUMBER_0, false,
                false));
        //                list.add(new BaseListItemMo(Constant.NUMBER_0, desc[6], DisplayFormat.coverTimeYYMMDD(mo.getEndTime()), Constant.NUMBER_0, Constant
        //         .NUMBER_0, false,
        //                        false));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[7], DisplayFormat.coverTimeYYMMDD(mo.getRepaymentTime()), Constant.NUMBER_0, Constant
                .NUMBER_0, false, false));
        list.add(new BaseListItemMo(Constant.NUMBER_1, desc[8], "", Constant.NUMBER_0, Constant.NUMBER_0, true, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.ID, String.valueOf(mo.getId()));
                intent.putExtra(BundleKeys.UUID, String.valueOf(mo.getBorrowId()));
                intent.putExtra(BundleKeys.NAME, mo.getBorrowName());
                ActivityUtils.push(BondDetailAct.class, intent);
            }
        }));
        emptyState.setLoading(false);
        return list;
    }

    private void getProtocol(int bondId){
        Call<ProtocolMo> call = RDClient.getService(ProductService.class).getBondSellProtocol(bondId + "",3);
        call.enqueue(new RequestCallBack<ProtocolMo>() {
            @Override
            public void onSuccess(Call<ProtocolMo> call, Response<ProtocolMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.DATA, response.body().getProtocolContext());
                intent.putExtra(BundleKeys.TITLE, "协议");
                ActivityUtils.push(RDWebViewAct.class, intent);
            }
        });
    }
}