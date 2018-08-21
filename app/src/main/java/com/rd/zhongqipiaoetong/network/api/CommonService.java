package com.rd.zhongqipiaoetong.network.api;

import com.rd.zhongqipiaoetong.module.homepage.model.BannerModel;
import com.rd.zhongqipiaoetong.module.homepage.model.HomeModel;
import com.rd.zhongqipiaoetong.module.homepage.model.PromotionMo;
import com.rd.zhongqipiaoetong.module.product.model.ExpInfoMo;
import com.rd.zhongqipiaoetong.module.product.model.ExpProductMo;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.network.entity.VersionMo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/7 12:00
 * <p/>
 * Description: 基础服务
 * (@Url: 不要以 / 开头)
 */
public interface CommonService {
    /**
     * 获取首页banner
     */
    @POST("bannerList")
//    Call<List<BannerModel>> getBanner();
    Call<BannerModel> getBanner();
    /**
     * 获取首页标
     */
    @POST("homeList")
    Call<HomeModel> getIndexProduct();
    /**
     * 体验标详情
     */
    @FormUrlEncoded
    @POST("expBorrow/expBorrowDetail")
    Call<ExpProductMo> expBorrowDetail(@Field(RequestParams.ID) String id);

    /**
     * 投资体验标
     */
    @POST("expBorrow/invest")
    Call<String> expBorrowInvest(@Body ExpInfoMo mo);

    /**
     * 渠道
     */
    @POST("promotion/add")
    Call<HttpResult> promotionAdd(@Body PromotionMo mo);

    /**
     * 获取版本号
     */
    @GET("manageApp/version")
    Call<VersionMo> getVersionNum();
}