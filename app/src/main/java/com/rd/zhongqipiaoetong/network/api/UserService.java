package com.rd.zhongqipiaoetong.network.api;

import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.module.user.model.ProtocolMo;
import com.rd.zhongqipiaoetong.module.user.model.dto.LoginDTO;
import com.rd.zhongqipiaoetong.module.user.model.dto.RegisterDTO;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 11:30
 * <p/>
 * Description: 用户服务
 * (@Url:
 * 以"/"开头，则表示"接口地址"会拼接在 BASE_URL 中的第一个"/"之后
 * 不以"/"开头，则表示"接口地址"会拼接在 BASE_URL 中的最后一个"/"之后)
 */
public interface UserService {
    /**
     * 登录请求
     *
     * @return {@link OauthTokenMo}
     */
    @POST("user/doLogin")
    Call<OauthTokenMo> doLogin(@Body LoginDTO dto);

    /**
     * 校验手机号是否已经注册
     *
     * @param phone
     *         手机号
     */
    @FormUrlEncoded
    @POST("user/checkPhoneAvailable")
    Call<String> checkPhone(@Field(RequestParams.PHONE) String phone);

    /**
     * 获取注册是的验证码
     *
     * @param phone
     *         手机号
     */
    @FormUrlEncoded
    @POST("user/phoneCodeNew")
    Call<String> getRegisterCode(@Field(RequestParams.PHONE) String phone, @Field(RequestParams.SB) String sb);

    @FormUrlEncoded
    @POST("user/checkRegisterCode")
    Call<String> checkRegisterCode(@Field(RequestParams.PHONE) String phone, @Field(RequestParams.VALIDCODE) String VALIDCODE);

    /**
     * @param dto
     *         注册入参
     *
     * @return 是否成功
     */
    @POST("user/register")
    Call<OauthTokenMo> doRegister(@Body RegisterDTO dto);

    /**
     * 忘记密码时的获取验证码
     *
     * @param phone
     *         手机号
     */
    @FormUrlEncoded
    @POST("user/getResetPwdCodeNew")
    Call<String> getFindPwdCode(@Field(RequestParams.PHONE) String phone, @Field(RequestParams.SB) String sb);

    /**
     * 验证忘记密码的验证码
     * @param phone
     * @param validCode
     * @return
     */
    @FormUrlEncoded
    @POST("user/check")
    Call<HttpResult> checkFindPwdCode(@Field(RequestParams.PHONE) String phone, @Field(RequestParams.VALIDCODE) String validCode);

    /**
     * 找回登录密码
     *
     * @param phone
     * @param newPassword
     * @param validCode
     * @return
     */
    @FormUrlEncoded
    @POST("user/resetPwd")
    Call<String> checkFindPwd(@Field(RequestParams.PHONE) String phone, @Field(RequestParams.NEWPASSWORD) String newPassword ,@Field(RequestParams.VALIDCODE) String validCode);

    /**
     * 刷新OauthTokenMo
     *
     * @param refresh_token
     *
     * @return
     */
    @FormUrlEncoded
    @POST("oauth/refreshToken")
    Call<OauthTokenMo> refreshToken(@Field(RequestParams.REFRESH_TOKEN) String refresh_token);

    /**
     * 获取注册协议内容
     * @return
     */
    @POST("getRegisterProtocolContext")
    Call<ProtocolMo> getRegisterProtocolContext();
}