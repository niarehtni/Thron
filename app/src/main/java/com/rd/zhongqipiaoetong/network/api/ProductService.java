package com.rd.zhongqipiaoetong.network.api;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.account.model.AppPaymentMo;
import com.rd.zhongqipiaoetong.module.account.model.DoAddBondMo;
import com.rd.zhongqipiaoetong.module.account.model.ExpMoList;
import com.rd.zhongqipiaoetong.module.account.model.RedPackeMoList;
import com.rd.zhongqipiaoetong.module.account.model.ToAddBondMo;
import com.rd.zhongqipiaoetong.module.account.model.TransferingMo;
import com.rd.zhongqipiaoetong.module.account.model.UpRateMo;
import com.rd.zhongqipiaoetong.module.product.model.BondDetailMo;
import com.rd.zhongqipiaoetong.module.product.model.BondRecordMo;
import com.rd.zhongqipiaoetong.module.product.model.BondTenderMo;
import com.rd.zhongqipiaoetong.module.product.model.CreditorMo;
import com.rd.zhongqipiaoetong.module.product.model.DoBondMo;
import com.rd.zhongqipiaoetong.module.product.model.FinancingDetailMo;
import com.rd.zhongqipiaoetong.module.product.model.FlowDetailMo;
import com.rd.zhongqipiaoetong.module.product.model.FlowInitMo;
import com.rd.zhongqipiaoetong.module.product.model.FlowTenderMo;
import com.rd.zhongqipiaoetong.module.product.model.InvestMoList;
import com.rd.zhongqipiaoetong.module.product.model.InvestmentMo;
import com.rd.zhongqipiaoetong.module.product.model.ProductInfoMo;
import com.rd.zhongqipiaoetong.module.product.model.ProductMoList;
import com.rd.zhongqipiaoetong.module.user.model.ProtocolMo;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.network.entity.ListMo;

import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 11:30
 * <p/>
 * Description: 产品服务
 * (@Url: 不要以 / 开头)
 */
public interface ProductService {
    /**
     * 获取定期理财产品列表
     */
    @FormUrlEncoded
    @POST("tender/tenderList")
    Call<ProductMoList> investList(@Field(RequestParams.PAGE) int page);

    /**
     * 获取即息理财产品列表
     */
    @FormUrlEncoded
    @POST("flow/flowList.html")
    Call<ListMo<FlowDetailMo>> flowList(@Field(RequestParams.PAGE) int page);

    /**
     * 获取债权转让产品列表
     */
    @FormUrlEncoded
    @POST("tender/creditorLog")
    Call<ListMo<CreditorMo>> bondList(@Field(RequestParams.PAGE) int page);

    /**
     * 投资记录列表
     */
    @FormUrlEncoded
    @POST("tender/assignmentRecode")
    Call<ListMo<BondRecordMo>> bondRecordList(@Field(RequestParams.BONDID) String id, @Field(RequestParams.UUID) String uuid, @Field(RequestParams.PAGE) int
            page);

    /**
     * 投资记录列表
     */
    @FormUrlEncoded
    @POST("tender/investLog")
    Call<InvestMoList> investRecordList(@Field(RequestParams.BORROWID) String borrowId, @Field(RequestParams.PAGE) int page);

    /**
     * 计息理财投资记录列表
     */
    @FormUrlEncoded
    @POST("invest/flowTenderList.html")
    Call<ListMo<FlowTenderMo>> flowInvestRecordList(@Field(RequestParams.ID) String id, @Field(RequestParams.PAGE) int page);

    /**
     * 获取标详情
     */
    @FormUrlEncoded
    @POST("tender/tenderDetail")
    Call<FinancingDetailMo> investDetail(@Field(RequestParams.BORROWID) String borrowId);

    /**
     * 获取计息理财详情
     */
    @FormUrlEncoded
    @POST("flow/detail.html")
    Call<FlowDetailMo> flowDetail(@Field(RequestParams.UUID) String uuid);

    /**
     * 投资初始化(普通标)
     *
     * @param borrowId
     *
     * @return
     */
    @FormUrlEncoded
    @POST("tender/initInvest")
    Call<InvestmentMo> investInitialize(@Field(RequestParams.BORROWID) String borrowId);

    /**
     * 投资初始化(即息理财)
     */
    @FormUrlEncoded
    @POST("tender/initInvest")
    Call<FlowInitMo> flowInvestInitialize(@Field(RequestParams.BORROWID) String borrowId);

    /**
     * 红包列表
     */
    @FormUrlEncoded
    @POST("account/redPacket")
    Call<RedPackeMoList> redEnvelopeList(@Field(RequestParams.PAGE) int page);

    /**
     * 红包兑换
     */
    @FormUrlEncoded
    @POST("invest/redEnvelopeExchange.html")
    Call<HttpResult> redEnvelopeExchange(@Field(RequestParams.ID) String id);

    /**
     * 体验券列表
     */
    @FormUrlEncoded
    @POST("account/myTicketExperience")
    Call<ExpMoList> experienceList(@Field(RequestParams.PAGE) int page);

    /**
     * 加息券列表
     */
    @FormUrlEncoded
    @POST("account/myTicketRate")
    Call<ListMo<UpRateMo>> UpRateList(@Field(RequestParams.PAGE) int page);

    /**
     * 投资接口
     *
     * @param uuid
     *         标id
     * @param id
     *         用户id
     * @param money
     *         投资金额
     * @param paypwd
     *         支付密码
     * @param pwd
     *         定向标密码
     * @param red_ids
     *         红包id
     * @param exp_ids
     *         体验券id
     * @param up_ids
     *         加息券id
     * @param session_id
     *
     * @return
     */
    @FormUrlEncoded
    @POST("tender/invest")
    Call<HttpResult> doInvest(@Field(RequestParams.UUID) String uuid, @Field(RequestParams.ID) String id, @Field(RequestParams.MONEY) String money, @Field
            (RequestParams.PAYPASSWORD) String paypwd, @Field(RequestParams.DIRPWD) String pwd, @Field(RequestParams.REDPACKETIDS) String red_ids, @Field
                                      (RequestParams.EXPIDS) String exp_ids, @Field(RequestParams.UPIDS) String up_ids, @Field(RequestParams.SESSION_ID)
                              String session_id, @Field(RequestParams.ACKTOKEN) String imei, @Field(RequestParams.ACKAPPKEY) String ackkey);

    @FormUrlEncoded
    @POST("tender/invest")
    Call<HttpResult> doInvest(@FieldMap TreeMap<String, Object> map);

    @FormUrlEncoded
    @POST("flow/doFlowInvset.html")
    Call<AppPaymentMo> doFlowInvest(@FieldMap TreeMap<String, Object> map);

    /**
     * 获取债券详情
     */
    @FormUrlEncoded
    @POST("tender/creditorDetail")
    Call<BondDetailMo> bondDetail(@Field(RequestParams.UUID) String uuid, @Field(BundleKeys.ID) String id);


    /**
     * 停止债权转让
     */
    @FormUrlEncoded
    @POST("account/stopBond")
    Call<TransferingMo> stopBond(@Field(RequestParams.ID) String id);

    /**
     * 债券投资初始化
     * @param id
     *         标的id
     * @return
     */
    @FormUrlEncoded
    @POST("tender/bondInvestInit")
    Call<BondTenderMo> bondInitialize(@Field(RequestParams.ID) String id);

    /**
     * 债券转让投标
     *
     * @param id
     * @param money
     * @param paypwd
     * @param session_id
     *
     * @return
     */
    @FormUrlEncoded
    @POST("bond/doBond.html")
    Call<AppPaymentMo> doBondTender(@Field(BundleKeys.UUID) String uuid, @Field(RequestParams.ID) String id, @Field(RequestParams.MONEY) String money, @Field
            (RequestParams.PAYPASSWORD) String
            paypwd, @Field(RequestParams.SESSION_ID) String session_id);

    @FormUrlEncoded
    @POST("bond/doBond.html")
    Call<AppPaymentMo> doBondTender(@FieldMap TreeMap<String, Object> paramMap);

    /**
     * 债转初始化
     *
     * @return
     */
    @POST("bond/toAddBond.html")
    Call<ToAddBondMo> toAddBond();

    @POST("account/sureAssigment")
    Call<DoAddBondMo> doAddBond(@Body DoAddBondMo mo);

    /**
     * 了解项目-借款描述-普通标
     */
    @FormUrlEncoded
    @POST("tender/tenderInfomation")
    Call<ProductInfoMo> tenderInfomation(@Field(RequestParams.BORROWID) String borrowId);

    /**
     * 获取债权投资链接
     */
    @FormUrlEncoded
    @POST("bond/bondInvest")
    Call<DoBondMo> bondInvest(@Field(RequestParams.ID) String id, @Field(RequestParams.INVESTCAPITAL) String investCapital);

    /**
     * 借款协议
     */
    @FormUrlEncoded
    @POST("protocol/getBorrowProtocolContext")
    Call<ProtocolMo> getBorrowProtocol(@Field(RequestParams.BORROWID) String borrowId);

    /**
     * 借款协议2
     */
    @FormUrlEncoded
    @POST("protocol/getBorrowProtocolContext")
    Call<ProtocolMo> getBorrowProtocolTwo(@Field(RequestParams.BORROWID) String borrowId, @Field(RequestParams.INVESTMENTID) String investmentId);

    /**
     * 债权买方协议
     */
    @FormUrlEncoded
    @POST("protocol/getBondBuyProtocolContext")
    Call<ProtocolMo> getBondBuyProtocol(@Field(RequestParams.BONDINVESTMENTID) String bondInvestmentId);

    /**
     * 债权卖方协议
     * type 1:债权详情  2:债权转让 3:已转出
     */
    @FormUrlEncoded
    @POST("protocol/getBondSellProtocolContext")
    Call<ProtocolMo> getBondSellProtocol(@Field(RequestParams.BONDID) String bondId, @Field(RequestParams.TYPE) int type);

    /**
     * 债权卖方协议
     * type 1:债权详情  2:债权转让 3:已转出
     */
    @FormUrlEncoded
    @POST("protocol/getBondSellProtocolContext")
    Call<ProtocolMo> getBondSellProtocolTwo(@Field(RequestParams.BORROWINVESTMENTID) String borrowInvestmentId, @Field(RequestParams.TYPE) int type);

    /** 获取了解项目分项 */
    @FormUrlEncoded
    @POST("tender/tenderInfomation")
    Call<ProductInfoMo> getProductDetail(@Field(RequestParams.BORROWID) String borrowId);
}