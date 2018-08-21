package com.rd.zhongqipiaoetong.module.more.viewmodel;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.module.more.activity.InvitationAct;
import com.rd.zhongqipiaoetong.module.more.model.InviteMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ExtraService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.QRCodeUtil;
import com.rd.zhongqipiaoetong.utils.UrlUtils;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.encryption.MD5Util;
import com.rd.zhongqipiaoetong.view.ShareDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: JinF
 * E-mail: jf@erongdu.com
 * Date: 2016/10/12 16:17
 * <p/>
 * Description: 邀请好友VM {@link InvitationAct}
 */
public class InvitationVM {
    private ShareDialog     shareDialog;
    private OnClickListener listener;
    private Context         context;
    private String shareLink = UrlUtils.getAddress();
    /** 二维码存储地址 */
    private ImageView iv;
    private InviteMo mo;

    public InvitationVM(Context context, ImageView iv) {
        this.context = context;
        this.iv = iv;
        initShare();
        req_data();
    }

    public InviteMo getMo() {
        return mo;
    }

    /**
     * 邀请好友click事件
     */
    public void onShareLinkClick(View view) {
        if (shareLink == null) {
            req_data();
        } else {
            shareDialog.show();
        }
    }

    /**
     * 分享初始化
     */
    private void initShare() {
        final UMImage umImage = new UMImage(context, R.drawable.app_icon);
        listener = new OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.share_wechat:
                        new ShareAction(ActivityUtils.peek()).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                                .withText(v.getContext().getString(R.string.umeng_share_content))
                                .withTitle(v.getContext().getString(R.string.app_name))
                                .withMedia(umImage)
                                .withTargetUrl(shareLink)
                                .share();
                        break;
                    case R.id.share_wxcircle:
                        new ShareAction(ActivityUtils.peek()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                                .withTitle(v.getContext().getString(R.string.app_name))
                                .withText(v.getContext().getString(R.string.umeng_share_content))
                                .withMedia(umImage)
                                .withTargetUrl(shareLink)
                                .share();
                        break;
                    case R.id.share_sina:
                        new ShareAction(ActivityUtils.peek()).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                                .withText(v.getContext().getString(R.string.umeng_share_content))
                                .withTitle(v.getContext().getString(R.string.app_name))
                                .withExtra(umImage)
                                .withTargetUrl(shareLink)
                                .share();
                        break;
                }
                shareDialog.dismiss();
            }
        };
        shareDialog = new ShareDialog(context, listener);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Utils.toast(R.string.invitation_collect_success);
            } else {
                Utils.toast(R.string.invitation_share_success);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Utils.toast(R.string.invitation_share_fail);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Utils.toast(R.string.invitation_share_cancel);
        }
    };
    private Handler         handler         = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            iv.setImageBitmap(BitmapFactory.decodeFile(getFilePath(shareLink)));
        }
    };

    private void req_data() {
        Call<InviteMo> call = RDClient.getService(ExtraService.class).userInvite();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<InviteMo>() {
            @Override
            public void onSuccess(Call<InviteMo> call, Response<InviteMo> response) {
                shareLink = response.body().getUrl();
                mo = response.body();
                //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(getFilePath(shareLink));
                        if (file.exists()) {
                            handler.sendEmptyMessage(0);
                            return;
                        }
                        boolean success = QRCodeUtil.createQRImage(shareLink, 500, 500,
                                // BitmapFactory.decodeResource(ActivityUtils.peek().getResources(),R.drawable.logo),   //添加LOGO
                                null, getFilePath(shareLink));
                        if (success) {
                            handler.sendEmptyMessage(0);
                        }
                    }
                }).start();
            }
        });
    }

    private String getFilePath(String link) {
        return BaseParams.ROOT_PATH+ File.separator + "qr_" + MD5Util.getMD5Str(link) + ".jpg";
    }
}