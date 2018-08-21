package com.rd.zhongqipiaoetong.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by pzw on 2017/9/28.
 */
public class ShareDialogReturn {
    private Context              context;
    private ShareDialog          shareDialog;
    private View.OnClickListener listener;

    public ShareDialogReturn(Context context) {
        this.context = context;
    }

    Handler mHandler = new Handler();

    @JavascriptInterface
    public void showShareDialog(final String shareLink, final String sharePicUrl, final String shareTitle, final String shareContent) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                initShare(shareLink,sharePicUrl,shareTitle,shareContent);
                //                shareDialog.show();
            }
        });
    }

    /**
     * 分享初始化
     */
    public void initShare(final String shareLink, final String sharePicUrl, final String shareTitle, final String shareContent) {
        Glide.with(context).load(sharePicUrl)
                .asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                UMImage       myUmImage = new UMImage(context, resource);
                final UMImage umImage   = (myUmImage == null ? new UMImage(context, R.drawable.app_icon) : myUmImage);
                listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        switch (v.getId()) {
                            case R.id.share_wechat:
                                new ShareAction(ActivityUtils.peek()).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                                        .withText(shareContent)
                                        .withTitle(shareTitle)
                                        .withMedia(umImage)
                                        .withTargetUrl(shareLink)
                                        .share();
                                break;
                            case R.id.share_wxcircle:
                                new ShareAction(ActivityUtils.peek()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                                        .withText(shareContent)
                                        .withTitle(shareTitle)
                                        .withMedia(umImage)
                                        .withTargetUrl(shareLink)
                                        .share();
                                break;
                            case R.id.share_sina:
                                new ShareAction(ActivityUtils.peek()).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                                        .withText(shareContent)
                                        .withTitle(shareTitle)
                                        .withExtra(umImage)
                                        .withTargetUrl(shareLink)
                                        .share();
                                break;
                        }
                        shareDialog.dismiss();
                    }
                };
                shareDialog = new ShareDialog(context, listener);
                shareDialog.show();
            }
        });
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
}
