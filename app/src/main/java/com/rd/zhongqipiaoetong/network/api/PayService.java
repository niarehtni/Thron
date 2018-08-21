package com.rd.zhongqipiaoetong.network.api;

import com.rd.zhongqipiaoetong.module.account.model.AppPaymentMo;
import com.rd.zhongqipiaoetong.module.account.model.RechargeMo;
import com.rd.zhongqipiaoetong.module.account.model.TipsMo;
import com.rd.zhongqipiaoetong.module.account.model.ToCashMo;
import com.rd.zhongqipiaoetong.module.account.model.WithdrawMo;
import com.rd.zhongqipiaoetong.network.RequestParams;

import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/18/16.
 */
public interface PayService {
    /**
     * 充值初始化
     *
     * @return
     */
    @POST("account/initCharge")
    Call<RechargeMo> toRecharge();

    /**
     * 充值
     *
     * @param sessionId
     *         会话ID
     * @param money
     *         充值金额
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/charge")
    Call<AppPaymentMo> doRecharge(@Field(RequestParams.SESSION_ID) String sessionId, @Field(RequestParams.MONEY) String money);

    @FormUrlEncoded
    @POST("account/charge")
    Call<AppPaymentMo> doRecharge(@FieldMap TreeMap<String, Object> paramMap);

    /**
     * 提现初始化
     *
     * @return
     */
    @POST("account/withdrawPage")
    Call<WithdrawMo> toCash();

    /**
     * 提现
     *
     * @param toCashMo
     *
     * @return
     */
    @POST("account/withdraw")
    Call<AppPaymentMo> doCash(@Body ToCashMo toCashMo);

    @FormUrlEncoded
    @POST("account/withdraw")
    Call<AppPaymentMo> doCash(@FieldMap TreeMap<String, Object> toCashMo);

    /**
     * 提现提示
     */
    @POST("account/withdrawTip")
    Call<TipsMo> getWithdrawTips();
}
