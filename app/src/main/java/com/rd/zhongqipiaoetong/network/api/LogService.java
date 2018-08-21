package com.rd.zhongqipiaoetong.network.api;

import com.rd.zhongqipiaoetong.module.account.model.AutoLogMo;
import com.rd.zhongqipiaoetong.module.account.model.CashRecordDetailMo;
import com.rd.zhongqipiaoetong.module.account.model.CashRecordListMo;
import com.rd.zhongqipiaoetong.module.account.model.InvestRecordDetailMo;
import com.rd.zhongqipiaoetong.module.account.model.InvestmenMoList;
import com.rd.zhongqipiaoetong.module.account.model.PendingPaymentMoList;
import com.rd.zhongqipiaoetong.module.account.model.RechargeRecodMoList;
import com.rd.zhongqipiaoetong.module.account.model.ScoreLogListMo;
import com.rd.zhongqipiaoetong.module.account.model.TransferListMo;
import com.rd.zhongqipiaoetong.module.account.model.WithdrawRecodMoList;
import com.rd.zhongqipiaoetong.module.product.model.FlowTenderMo;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.entity.ListMo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/18/16.
 */
public interface LogService {
    /**
     * 投资记录-我的投资
     *
     * @param page
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/investRecords")
    Call<InvestmenMoList> investRecordList(@Field(RequestParams.PAGE) int page);

    /**
     * 投资记录详情-我的投资
     *
     * @param id
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/investDetail")
    Call<InvestRecordDetailMo> investRecordDetail(@Field(RequestParams.ID) String id);

    /**
     * 流转投资记录-我的投资
     *
     * @param page
     *
     * @return
     */
    @FormUrlEncoded
    @POST("flow/myTenderFlow.html")
    Call<ListMo<FlowTenderMo>> flowInvestRecordList(@Field(RequestParams.PAGE) int page);

    /**
     * 投资记录-债权转让
     *
     * @param page
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/creditorRecords")
    Call<CashRecordListMo> boughtBondList(@Field(RequestParams.PAGE) int page);

    /**
     * 已转入债权详情
     *
     * @param id
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/creditorAssignmentDetail")
    Call<CashRecordDetailMo> boughtBondDetail(@Field(RequestParams.ID) String id);

    /**
     * 回款记录
     *
     * @param page
     * @param status
     *         0未还款 1已还款
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/toReturnRecords")
    Call<PendingPaymentMoList> investRepayPlan(@Field(RequestParams.PAGE) int page, @Field(RequestParams.STATUS) int status);

    /**
     * 充值记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/rechargeRecords")
    Call<RechargeRecodMoList> rechargeLoglist(@Field(RequestParams.PAGE) int page);

    /**
     * 提现记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/withDrawRecords")
    Call<WithdrawRecodMoList> cashLogList(@Field(RequestParams.PAGE) int page);

    /**
     * 可转让列表
     *
     * @param page
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/canAssigment")
    Call<TransferListMo> saleableBondList(@Field(RequestParams.PAGE) int page);

    /**
     * 转让中列表
     *
     * @param page
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/Assigment")
    Call<TransferListMo> sellingBondList(@Field(RequestParams.PAGE) int page,@Field(RequestParams.STATUS) int status);

    /**
     * 已转让列表
     *
     * @param page
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/Assigment")
    Call<TransferListMo> soldBondList(@Field(RequestParams.PAGE) int page,@Field(RequestParams.STATUS) int status);

    /**
     * 自动投标记录
     *
     * @param page
     *
     * @return
     */
    @FormUrlEncoded
    @POST("invest/autoTenderList.html")
    Call<ListMo<AutoLogMo>> autoTenderList(@Field(RequestParams.PAGE) int page);

    /**
     * 积分记录
     */
    @FormUrlEncoded
    @POST("account/scoreLogList.html")
    Call<ScoreLogListMo> scoreLogList(@Field(RequestParams.PAGE) int page);
}
