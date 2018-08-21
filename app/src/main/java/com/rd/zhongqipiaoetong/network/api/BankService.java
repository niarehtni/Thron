package com.rd.zhongqipiaoetong.network.api;

import com.rd.zhongqipiaoetong.module.account.model.AppPaymentMo;
import com.rd.zhongqipiaoetong.module.account.model.BankCardListMo;
import com.rd.zhongqipiaoetong.module.account.model.BindBnakMo;
import com.rd.zhongqipiaoetong.module.account.model.InitialAddBankMo;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;

import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/20/16.
 */
public interface BankService {
    /**
     * 绑卡初始化
     * @return
     */
    @POST("account/initAddBankCard")
    Call<InitialAddBankMo> toAddBank();

    /**
     * 绑卡
     * @param bindBnakMo
     * @return
     */
    @POST("account/addBankCard")
    Call<String> addBank(@Body BindBnakMo bindBnakMo);

    @FormUrlEncoded
    @POST("account/addBankCard")
    Call<HttpResult> addBank(@FieldMap TreeMap<String, Object> bindBnakMo);

    @POST("account/addBankCard")
    Call<AppPaymentMo> addBank();

    @POST("account/bankCardList")
    Call<BankCardListMo> bankList();
}

