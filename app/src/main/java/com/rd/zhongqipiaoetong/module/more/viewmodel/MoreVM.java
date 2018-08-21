package com.rd.zhongqipiaoetong.module.more.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;
import android.view.View.OnClickListener;

import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.databinding.MoreFragBinding;
import com.rd.zhongqipiaoetong.module.account.activity.CustomServiceAct;
import com.rd.zhongqipiaoetong.module.more.activity.ActivityAct;
import com.rd.zhongqipiaoetong.module.more.activity.FeedBackAct;
import com.rd.zhongqipiaoetong.module.more.activity.HelpCenterAct;
import com.rd.zhongqipiaoetong.module.more.activity.InvitationAct;
import com.rd.zhongqipiaoetong.module.more.activity.InvitationTypeBAct;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.more.fragment.MoreFrag;
import com.rd.zhongqipiaoetong.module.more.model.InviteMo;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.network.Html5Util;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ExtraService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.UrlUtils;
import com.rd.zhongqipiaoetong.view.ShareDialog;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/30 下午1:52
 * <p/>
 * Description: 更多内容控制器({@link MoreFrag})
 */
public class MoreVM {
    private ShareDialog     shareDialog;
    private OnClickListener listener;
    private String shareLink = UrlUtils.getAddress();
    private MoreFragBinding binding;
    public ObservableField<String> versionName = new ObservableField<>();

    public MoreVM(MoreFragBinding binding) {
        this.binding = binding;
        versionName.set(BaseParams.getVersionName());
        //        initShare();
    }

    /**
     * 意见反馈
     */
    public void feedBackClick(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), FeedBackAct.class));
    }

    /**
     * 公告
     */
    public void noticeClick(final View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, binding.getRoot().getContext().getString(R.string.more_msg));
        intent.putExtra(BundleKeys.NEED_GOBACK, true);
        intent.putExtra(BundleKeys.ISCLOSE, true);
        String getUrl = CommonMethod.getUrl(Html5Util.NOTICE_H5);
        intent.putExtra(BundleKeys.URL, getUrl + "&type=1");
        ActivityUtils.push(RDWebViewAct.class, intent);
    }

    /**
     * 网站资讯
     */
    public void messageClick(final View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, binding.getRoot().getContext().getString(R.string.more_web));
        intent.putExtra(BundleKeys.NEED_GOBACK, true);
        intent.putExtra(BundleKeys.ISCLOSE, true);
        String getUrl = CommonMethod.getUrl(Html5Util.NOTICE_H5);
        intent.putExtra(BundleKeys.URL, getUrl + "&type=2");
        ActivityUtils.push(RDWebViewAct.class, intent);
    }

    public void onIntroduceClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, "关于我们");
        intent.putExtra(BundleKeys.NEED_GOBACK, true);
        intent.putExtra(BundleKeys.ISCLOSE, true);
        String getUrl = CommonMethod.getUrl(Html5Util.NOTICE_H5);
        intent.putExtra(BundleKeys.URL, getUrl);
        ActivityUtils.push(RDWebViewAct.class, intent);
    }

    /**
     * 活动中心
     */
    public void activityListClick(View view) {
        ActivityUtils.push(ActivityAct.class);
    }

    /**
     * 邀请好友
     */
    public void inviteClick(View view) {
        if (MyApplication.getInstance().isLand()) {
            if(BaseParams.inviteType == 1){
                ActivityUtils.push(InvitationAct.class);
            }else if(BaseParams.inviteType == 2){
                ActivityUtils.push(InvitationTypeBAct.class);
            }
        } else {
            ActivityUtils.push(LoginAct.class);
        }
    }

    /**
     * 帮助中心
     */
    public void helpCenterClick(View view) {
        ActivityUtils.push(HelpCenterAct.class);
    }

    /**
     * 网络请求
     */
    private void getShareLink(final View view) {
        Call<InviteMo> call = RDClient.getService(ExtraService.class).userInvite();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<InviteMo>() {
            @Override
            public void onSuccess(Call<InviteMo> call, Response<InviteMo> response) {
                shareLink = response.body().getUrl();
                shareDialog.show();
            }
        });
    }

    /**
     * 拨打客服电话
     */
    public void servicePhoneClick(View view) {
        ActivityUtils.push(CustomServiceAct.class);
    }

    /**
     * 版本信息
     */
    public void verisonClick(View view) {
        //        getVersionInfo();
    }
}