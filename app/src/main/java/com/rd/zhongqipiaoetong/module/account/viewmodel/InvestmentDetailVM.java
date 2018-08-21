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
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.fragment.InvestmentDetailFrag;
import com.rd.zhongqipiaoetong.module.account.model.CashRecordDetailMo;
import com.rd.zhongqipiaoetong.module.account.model.InvestRecordDetailMo;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.product.activity.BondDetailAct;
import com.rd.zhongqipiaoetong.module.product.activity.FinancingDetailAct;
import com.rd.zhongqipiaoetong.module.product.activity.NewProductAct;
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
 * Date: 2016/4/13 18:05
 * <p/>
 * Description: 投资详情({@link InvestmentDetailFrag})
 */
public class InvestmentDetailVM extends BaseRecyclerViewVM<BaseListItemMo> {
    private Context mContext;

    @Override
    protected void selectView(ItemView itemView, int position, BaseListItemMo item) {
        itemView.setBindingVariable(BR.item).setLayoutRes(R.layout.common_recycler_item);
    }

    public InvestmentDetailVM(CommonRecyclerViewBinding binding) {
        this.mContext = binding.getRoot().getContext();
    }

    /**
     * 填写数据
     */
    public void setRecordData(InvestRecordDetailMo mo) {
        items.addAll(init(mo));
    }

    /**
     * 数据转换
     */
    private List<BaseListItemMo> init(final InvestRecordDetailMo mo) {

        List<BaseListItemMo> list = new ArrayList<>();
        list.add(new BaseListItemMo(Constant.NUMBER_1, mo.getBorrowName(), "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 跳转页面
                if (mo.getClassify() != 4) {
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.ID, mo.getBorrowId());
                    intent.putExtra(BundleKeys.NAME, mo.getBorrowName());
                    ActivityUtils.push(FinancingDetailAct.class, intent);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.ID, mo.getBorrowId());
                    ActivityUtils.push(NewProductAct.class, intent);
                }
            }
        }));
        if (mo.getStatus() == 1) {//待回款
            String[] desc = mContext.getResources().getStringArray(R.array.investRecordDetail1);
            //            final String extra = mContext.getString(R.string.ir_xy);
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[0], DisplayFormat.getInvestmenStr(mo.getStatus())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[1], mo.showRate()));
            list.add(new BaseListItemMo(Constant.NUMBER_0, mo.showCapital(), DisplayFormat.doubleMoney(mo.getCapital())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[3], DisplayFormat.coverTimeYYMMDD(mo.getInterestStartTime())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[4], DisplayFormat.coverTimeYYMMDD(mo.getPaymentTime())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[5], DisplayFormat.doubleMoney(mo.getRepaidCapital())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[6], DisplayFormat.doubleMoney(mo.getRepaidInterest())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[7], mo.showExpectInterest()));
        } else if (mo.getStatus() == 2) {//已结算
            String[] desc = mContext.getResources().getStringArray(R.array.investRecordDetail2);
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[0], DisplayFormat.getInvestmenStr(mo.getStatus())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[1], mo.showRate()));
            list.add(new BaseListItemMo(Constant.NUMBER_0, mo.showCapital(), DisplayFormat.doubleMoney(mo.getCapital())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[3], DisplayFormat.coverTimeYYMMDD(mo.getInterestStartTime())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[4], DisplayFormat.coverTimeYYMMDD(mo.getPaymentTime())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[5], DisplayFormat.doubleMoney(mo.getRepaidInterest())));
        } else if (mo.getStatus() == 0) {//投资中
            String[] desc = mContext.getResources().getStringArray(R.array.investRecordDetail0);
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[0], DisplayFormat.getInvestmenStr(mo.getStatus())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[1], mo.showRate()));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[2], mo.showTimeLimit()));
            list.add(new BaseListItemMo(Constant.NUMBER_0, mo.showCapital(), DisplayFormat.doubleMoney(mo.getCapital())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[4], mContext.getString(R.string.apr_percent, mo.getProgressPercentage())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[5], mo.showExpectInterest()));
        } else {
            String[] desc = mContext.getResources().getStringArray(R.array.investRecordDetail3);
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[0], DisplayFormat.getInvestmenStr(mo.getStatus())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[1], mo.showRate()));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[2], mo.showTimeLimit()));
            list.add(new BaseListItemMo(Constant.NUMBER_0, mo.showCapital(), DisplayFormat.doubleMoney(mo.getCapital())));
            list.add(new BaseListItemMo(Constant.NUMBER_0, desc[4], mo.showFailMark()));
        }
        return list;
    }

    public void setRecordData(CashRecordDetailMo mo) {
        items.addAll(init(mo));
    }

    private List<BaseListItemMo> init(final CashRecordDetailMo mo) {

        List<BaseListItemMo> list = new ArrayList<>();
        list.add(new BaseListItemMo(Constant.NUMBER_1, mo.getBorrowName(), "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 跳转页面
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.ID, mo.getBondId());
                intent.putExtra(BundleKeys.NAME, mo.getBorrowName());
                ActivityUtils.push(BondDetailAct.class, intent);
            }
        }));

        String[] desc = mContext.getResources().getStringArray(R.array.investmentBondDetailSuccessItemDesc);
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[0], mo.getStatusStr()));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[1], DisplayFormat.doubleMoney(mo.getInvestMoney())));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[2], mContext.getString(R.string.apr_percent, DisplayFormat.doubleFormat(mo.getDiscountRate()))));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[3], DisplayFormat.doubleMoney(mo.getBoughtPrice())));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[4], DisplayFormat.aprFormat(String.valueOf(mo.getRateYear())).toString()));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[5], DisplayFormat.doubleMoney(mo.getReceivedInterest())));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[6], DisplayFormat.doubleMoney(mo.getWaitInterest())));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[7], DisplayFormat.getRepayMentStr(mo.getRepayWay())));
        list.add(new BaseListItemMo(Constant.NUMBER_0, desc[8], DisplayFormat.coverTimeYYMMDD(mo.getAddTime())));
        list.add(new BaseListItemMo(Constant.NUMBER_1, "查看协议", "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBondBuyProtocol(mo.getBondInvestmentId());
            }
        }));
        return list;
    }

    private void getBorrowProtocol(String borrowId) {
        Call<ProtocolMo> call = RDClient.getService(ProductService.class).getBorrowProtocol(borrowId);
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

    private void getBondBuyProtocol(String id) {
        Call<ProtocolMo> call = RDClient.getService(ProductService.class).getBondBuyProtocol(id);
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