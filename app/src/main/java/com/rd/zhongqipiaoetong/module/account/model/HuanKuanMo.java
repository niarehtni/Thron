package com.rd.zhongqipiaoetong.module.account.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.product.activity.FinancingDetailAct;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.network.Html5Util;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.utils.UrlUtils;
import com.rd.zhongqipiaoetong.view.CustomDialog;

import java.io.Serializable;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by pzw on 2018/1/19.
 */
public class HuanKuanMo extends BaseObservable implements Serializable {
    //已投金额：截标时为截标金额
    private String  amountInvested;
    //标的编号
    private String  borrowId;
    //预计还款时间
    private String  expectedRepaymentTime;
    //还款编号
    private String  id;
    //借款标题
    private String  name;
    //期数
    private String  period;
    //还款金额
    private String  repaymentAmount;
    //还款状态 -1还款处理中0待还;1已还 ;2网站垫付 ;3垫付后还款;4还款待审核
    private String  status;
    //总期数
    private String  totalPeriod;
    private String  borrowName;
    private String  repay;
    private boolean repayColor;

    public boolean getRepayColor() {
        return "0".equals(status);
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getRepay() {
        return period + "/" + totalPeriod;
    }

    public void setRepay(String repay) {
        this.repay = repay;
    }

    public String getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(String amountInvested) {
        this.amountInvested = amountInvested;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getExpectedRepaymentTime() {
        return expectedRepaymentTime;
    }

    public void setExpectedRepaymentTime(String expectedRepaymentTime) {
        this.expectedRepaymentTime = expectedRepaymentTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(String totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public void detailClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.ID, borrowId);
        ActivityUtils.push(FinancingDetailAct.class, intent);
    }

    public void repayClick(final View view) {
        Call<PersonInfoMo> call = RDClient.getService(AccountService.class).securityInfo();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<PersonInfoMo>(true) {
            @Override
            public void onSuccess(Call<PersonInfoMo> call, Response<PersonInfoMo> response) {
                if(BaseParams.paymentType == 1 && response.body().getRealNameStatus() == 3){
                    UserLogic.accountToAuthor(null, "");
                }else{
                    if ("0".equals(status)) {
                        Call<ToRepayMo> callRepay = RDClient.getService(AccountService.class).reqToRepay(id);
                        callRepay.enqueue(new RequestCallBack<ToRepayMo>() {
                            @Override
                            public void onSuccess(Call<ToRepayMo> call, Response<ToRepayMo> response) {
                                CustomDialog.Builder builder = new CustomDialog.Builder(view.getContext());
                                builder.setMessage("本期还款金额：" + DisplayFormat.doubleFormat(repaymentAmount) + "元");
                                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TreeMap map = new TreeMap<String, Object>();
                                        map.put(RequestParams.ID, id);
                                        Intent intent = new Intent();
                                        intent.putExtra(BundleKeys.TITLE, "还款");
                                        intent.putExtra(BundleKeys.NEED_GOBACK, false);
                                        intent.putExtra(BundleKeys.URL, UrlUtils.getSignaWebUrlOther(Html5Util.REPAY));
                                        intent.putExtra(BundleKeys.PARAMS, UrlUtils.getUrlParams(map));
                                        ActivityUtils.push(RDWebViewAct.class, intent);
                                    }
                                });
                                builder.setTitle(null);
                                builder.setCanceledOnTouchOutside(false);
                                builder.setCancelable(false);
                                Dialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                }
            }
        });
    }
}
