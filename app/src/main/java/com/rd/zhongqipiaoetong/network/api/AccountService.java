package com.rd.zhongqipiaoetong.network.api;

import com.rd.zhongqipiaoetong.module.account.model.AccountMo;
import com.rd.zhongqipiaoetong.module.account.model.AppPaymentMo;
import com.rd.zhongqipiaoetong.module.account.model.AutoTenderDetailMo;
import com.rd.zhongqipiaoetong.module.account.model.CashRecordItem;
import com.rd.zhongqipiaoetong.module.account.model.HuanKuanListMo;
import com.rd.zhongqipiaoetong.module.account.model.JieKuanManageMo;
import com.rd.zhongqipiaoetong.module.account.model.PersonInfoMo;
import com.rd.zhongqipiaoetong.module.account.model.SummaryMo;
import com.rd.zhongqipiaoetong.module.account.model.ToRepayMo;
import com.rd.zhongqipiaoetong.module.account.model.TokenMo;
import com.rd.zhongqipiaoetong.module.homepage.model.FridayMo;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.network.entity.ListMo;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 11:30
 * <p/>
 * Description: 账户服务
 * (@Url: 不要以 / 开头)
 */
public interface AccountService {
    /**
     * 账户中心
     */
    @POST("account/summary")
    Call<AccountMo> basic();

    /**
     * 认证信息
     */
    @POST("account/secure")
    Call<PersonInfoMo> securityInfo();

    /**
     * 实名认证
     *
     * @param realname
     *         真实姓名
     * @param cardId
     *         身份证card
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/openAccount")
    Call<AppPaymentMo> realnameIdentify(@Field(RequestParams.REALNAME) String realname, @Field(RequestParams.IDNO) String cardId, @Field(RequestParams
            .ACKTOKEN) String ackToken, @Field(RequestParams.ACKAPPKEY) String ackAppkey);

    @FormUrlEncoded
    @POST("account/openAccount")
    Call<AppPaymentMo> realnameIdentify(@FieldMap TreeMap<String, Object> paramMap);

    /**
     * 授权
     *
     * @param type
     * @param on_off
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/authorization.html")
    Call<AppPaymentMo> doAuthorization(@Field(RequestParams.AUTHORIZE_TYPE) String type, @Field(RequestParams.AUTHORIZE_ONOFF) boolean on_off);

    @FormUrlEncoded
    @POST("account/authSign")
    Call<AppPaymentMo> doAuthorization(@FieldMap TreeMap<String, Object> paramMap);

    /**
     * 修改登录密码
     *
     * @param password
     *         原始登录密码
     * @param newPassword
     *         新登录密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/loginPwd/modify")
    Call<HttpResult> modifyLoginPwd(@Field(RequestParams.PASSWORD) String password, @Field(RequestParams.NEWPASSWORD) String newPassword);

    /**
     * 修改支付密码
     *
     * @param oldPayPwd
     *         原支付密码
     * @param newPayPwd
     *         新支付密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/modifyPaypwd.html")
    Call<HttpResult> modifyPayPwd(@Field(RequestParams.OLD_PAYPWD) String oldPayPwd, @Field(RequestParams.NEW_PAYPWD) String newPayPwd);

    /**
     * 重置支付密码
     *
     * @param id_card
     *         省份证后6位
     * @param new_pwd
     *         新支付密码
     * @param code
     *         验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/resetPaypwd.html")
    Call<HttpResult> resetPayPwd(@Field(RequestParams.RESETPAYPWD_IDCARD) String id_card, @Field(RequestParams.RESETPAYPWD_NEWPWD) String new_pwd, @Field
            (RequestParams.CODE) String code);

    @POST("account/getResetPaypwdCode.html")
    Call<String> resetPayPwdGetCode();

    @FormUrlEncoded
    @POST("account/setPaypwd.html")
    Call<HttpResult> setPayPwd(@Field(RequestParams.SETPAYPWD_PWD) String paypwd);

    /**
     * 上传头像
     *
     * @param
     *
     * @return
     */
    @Multipart
    @POST
    Call<HttpResult> toImage(@Url String url, @PartMap Map<String, Object> params);

    @POST("app/daily/floatButton.html")
    Call<FridayMo> getAd();


    @POST("qiniu/uptoken")
    Call<TokenMo> qiuniuToken();

    @POST("user/certFileUpload")
    @FormUrlEncoded
    Call<AppPaymentMo> OCRImage(@Field(RequestParams.REALNAME) String realName, @Field(RequestParams.IDNO) String idNo, @Field(RequestParams.certFileFront)
            String certFileFront, @Field(RequestParams.certFileBack) String certFileBack);

    /**
     * 更新头像地址
     *
     * @param imgurl
     *
     * @return
     */
    @FormUrlEncoded
    @POST("account/headUploading")
    Call<HttpResult> upload(@Field(RequestParams.IMGURL) String imgurl);

    /**
     * 自动投标内容
     *
     * @return
     */
    @POST("invest/autoTenderDetail.html")
    Call<AutoTenderDetailMo> autoTenderDetail();

    /**
     * 修改自动投标状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST("invest/autoStatus.html")
    Call<HttpResult> autoStatus(@Field(RequestParams.enable) boolean enable);

    @FormUrlEncoded
    @POST("invest/autoStatus.html")
    Call<HttpResult> autoStatus(@FieldMap TreeMap<String, Object> toCashMo);

    /**
     * 修改自动投标状态
     *
     * @return
     */
    @POST("invest/autoTender.html")
    Call<HttpResult> autoTender(@Body AutoTenderDetailMo mo);

    @FormUrlEncoded
    @POST("invest/autoTender.html")
    Call<HttpResult> autoTender(@FieldMap TreeMap<String, Object> toCashMo);

    /**
     * 获取邮箱验证码
     */
    @FormUrlEncoded
    @POST("sendBindCode")
    Call<HttpResult> getEmailCode(@FieldMap TreeMap<String, Object> toBindMo);

    /**
     * 绑定邮箱
     */
    @FormUrlEncoded
    @POST("emailAuth")
    Call<HttpResult> bindEmail(@FieldMap TreeMap<String, Object> toBindMo);

    /**
     * 获取体验标开关
     */
    @POST("expBorrow/hasExpBorrow")
    Call<SummaryMo> isExpOpen();

    /**
     * 资金记录
     */
    @FormUrlEncoded
    @POST("account/getAccountLogPage")
    Call<ListMo<CashRecordItem>> getAccountLog(@Field(RequestParams.PAGE) int page);

    /**
     * 借款列表
     */
    @FormUrlEncoded
    @POST("account/myBorrows")
    Call<JieKuanManageMo> getBorrows(@Field(RequestParams.PAGE) int page);

    /**
     * 还款列表
     */
    @FormUrlEncoded
    @POST("account/myRepayments")
    Call<HuanKuanListMo> getRepayments(@Field(RequestParams.PAGE) int page);

    /**
     * 还款请求
     */
    @POST("account/repayment")
    @FormUrlEncoded
    Call<ToRepayMo> reqToRepay(@Field(RequestParams.ID) String id);

    /**
     * 还款提交
     */
    @POST("account/repaymentForm")
    @FormUrlEncoded
    Call<HttpResult> reqFormRepay(@Field(RequestParams.ID) String id);
}