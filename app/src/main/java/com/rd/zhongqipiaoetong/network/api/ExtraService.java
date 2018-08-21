package com.rd.zhongqipiaoetong.network.api;

import com.rd.zhongqipiaoetong.module.account.model.AreaVersionMo;
import com.rd.zhongqipiaoetong.module.account.model.ProvinceListMo;
import com.rd.zhongqipiaoetong.module.homepage.model.RepayMo;
import com.rd.zhongqipiaoetong.module.homepage.model.ServerModel;
import com.rd.zhongqipiaoetong.module.more.model.ActivityMo;
import com.rd.zhongqipiaoetong.module.more.model.HelpMo;
import com.rd.zhongqipiaoetong.module.more.model.InvitationRecordMo;
import com.rd.zhongqipiaoetong.module.more.model.InviteMo;
import com.rd.zhongqipiaoetong.module.more.model.MsgMoList;
import com.rd.zhongqipiaoetong.module.product.model.ExpProductMo;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.entity.ListMo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/19/16.
 */
public interface ExtraService {
    /**
     * 获取服务器上area版本
     *
     * @return
     */
    @POST("extra/arealistVersion.html")
    Call<AreaVersionMo> arealistVersion();

    /**
     * 获取服务器上area 信息
     *
     * @return
     */
    @POST
    Call<ProvinceListMo> area(@Url String url);

    @POST("/themes/theme_default/app/area.json")
    Call<ProvinceListMo> area();

    /**
     * 消息管理
     *
     * @param page
     *
     * @return
     */
    @FormUrlEncoded
    @POST("more/myinfo")
    Call<MsgMoList> messageList(@Field(RequestParams.PAGE) int page);

    /**
     * 消息设置（已读未读删除）
     *
     * @param id
     *         消息ID
     * @param flag
     *         1删除信息2标记未读3标记已读
     *
     * @return
     */
    @FormUrlEncoded
    @POST("more/messageCenter/update")
    Call<MsgMoList> messageSetting(@Field(RequestParams.IDS) String id, @Field(RequestParams.FLAG) String flag);

    /**
     * 获取分享链接
     */
    @POST("more/invite")
    Call<InviteMo> userInvite();

    /**
     * 获取分享链接
     */
    @FormUrlEncoded
    @POST("more/invite")
    Call<InviteMo> userInvite(@Field(RequestParams.PAGE) int page);

    /**
     * 获取邀请好友的记录列表
     */
    @POST("extra/userInviteList.html")
    Call<ListMo<InvitationRecordMo>> userInviteList();

    /**
     * 获取 服务器地址，图片地址
     *
     * @return
     */
    @POST("extra/servers.html")
    Call<ServerModel> servers();

    @POST("extra/repayTypes.html")
    Call<AreaVersionMo> repayTypes();

    /**
     * 获取服务器上repay 信息
     *
     * @return
     */
    @POST
    Call<RepayMo> repay(@Url String url);

    /**
     * 帮助中心
     */
    @FormUrlEncoded
    @POST("more/helpCenter")
    Call<HelpMo> helpCenter(@Field(RequestParams.PAGE) int page);

    /**
     * 体验标
     */
    @POST("expBorrow/home")
    Call<ExpProductMo> getExp();

    @FormUrlEncoded
    @POST("column/activityList")
    Call<ActivityMo> getActivity(@Field(RequestParams.PAGE) int page);
}
