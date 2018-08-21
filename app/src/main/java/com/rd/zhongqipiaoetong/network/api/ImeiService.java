package com.rd.zhongqipiaoetong.network.api;

import com.rd.zhongqipiaoetong.module.account.model.LoginingMo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/19/16.
 */
public interface ImeiService {
    /**
     * 爱财客启动接口
     */
    @POST("user/appStart.html")
    Call<LoginingMo> appStart(@Body LoginingMo loginingmo);
}
