package com.rd.zhongqipiaoetong.module.homepage.viewmodel;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.MainAct;
import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.databinding.HomeFragBinding;
import com.rd.zhongqipiaoetong.module.homepage.fragment.HomeFrag;
import com.rd.zhongqipiaoetong.module.homepage.model.BannerModel;
import com.rd.zhongqipiaoetong.module.homepage.model.FridayMo;
import com.rd.zhongqipiaoetong.module.homepage.model.HomeModel;
import com.rd.zhongqipiaoetong.module.more.activity.ActivityAct;
import com.rd.zhongqipiaoetong.module.more.activity.InvitationAct;
import com.rd.zhongqipiaoetong.module.more.activity.InvitationTypeBAct;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.more.model.ActivityMo;
import com.rd.zhongqipiaoetong.module.product.activity.FinancingDetailAct;
import com.rd.zhongqipiaoetong.module.product.activity.NewProductAct;
import com.rd.zhongqipiaoetong.module.product.model.FinancingMo;
import com.rd.zhongqipiaoetong.module.product.model.ProductMoList;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.network.FridayClient;
import com.rd.zhongqipiaoetong.network.Html5Util;
import com.rd.zhongqipiaoetong.network.InitRequestCallBack;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.api.CommonService;
import com.rd.zhongqipiaoetong.network.api.ExtraService;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.view.DividerLine;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;
import com.rd.banner.banner.CBViewHolderCreator;
import com.rd.banner.banner.ConvenientBanner;
import com.rd.banner.banner.NetworkImageHolderView;
import com.rd.banner.banner.RBannerBean;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/1 下午7:12
 * <p/>
 * Description: 首页viewmodel控制器({@link HomeFrag})
 */
public class HomeVM {
    private HomeFragBinding binding;
    public       int                                   type             = DividerLine.HORIZONTAL;
    public       int                                   typeAcitivity    = DividerLine.DEFAULT;
    public final ObservableField<HomeModel>            item             = new ObservableField<>();
    public final ObservableList<FinancingMo>           itemsInvest      = new ObservableArrayList<>();
    public final ObservableList<ActivityMo.Activity>   itemsActvity     = new ObservableArrayList<>();
    public final ItemViewSelector<FinancingMo>         itemViewInvest   = new ItemViewSelector<FinancingMo>() {
        @Override
        public void select(ItemView itemView, int i, FinancingMo financingMo) {
            itemView.set(BR.info, R.layout.home_financing_list_item)
                    .setOnItemClickListener(new ItemView.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (!MyApplication.getInstance().isLand()) {
                                ActivityUtils.push(LoginAct.class);
                                return;
                            }
                            if (itemsInvest.get(position).getClassify() == 4) {
                                Intent intent = new Intent();
                                intent.putExtra(BundleKeys.ID, itemsInvest.get(position).getId());
                                ActivityUtils.push(NewProductAct.class, intent);
                            } else {
                                Intent intent = new Intent();
                                intent.putExtra(BundleKeys.ID, itemsInvest.get(position).getId());
                                intent.putExtra(BundleKeys.NAME, itemsInvest.get(position).getName());
                                ActivityUtils.push(FinancingDetailAct.class, intent);
                            }
                        }
                    });
        }

        @Override
        public int viewTypeCount() {
            return 0;
        }
    };
    public final ItemViewSelector<ActivityMo.Activity> itemViewActivity = new ItemViewSelector<ActivityMo.Activity>() {
        @Override
        public void select(ItemView itemView, int i, ActivityMo.Activity activityMo) {
            itemView.set(BR.item, R.layout.home_list_item).setOnItemClickListener(new ItemView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    if (TextUtils.isEmpty(itemsActvity.get(i).getUrl())) {
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.TITLE, itemsActvity.get(i).getTitle());
                    intent.putExtra(BundleKeys.URL, CommonMethod.getUrlWithoutBase(itemsActvity.get(i).getUrl()));
                    ActivityUtils.push(RDWebViewAct.class, intent);
                }
            });
        }

        @Override
        public int viewTypeCount() {
            return 0;
        }
    };
    /**
     * 刷新控件回调
     */
    public final ObservableField<PtrFrameListener>     listener         = new ObservableField<>();
    /**
     * banner控件
     */
    private ConvenientBanner convenientBanner;

    public HomeVM(HomeFragBinding binding, ConvenientBanner convenientBanner) {
        this.binding = binding;
        this.convenientBanner = convenientBanner;
        listener.set(new PtrFrameListener() {
            @Override
            public void ptrFrameInit(PtrClassicFrameLayout ptrFrame) {
                request(ptrFrame);
            }

            @Override
            public void ptrFrameRefresh() {
            }

            @Override
            public void ptrFrameRequest(PtrFrameLayout ptrFrame) {
                request(ptrFrame);
            }
        });
    }

    public void dismiss(View view) {
        binding.homeShowAd.setVisibility(View.GONE);
        MyApplication.getInstance().isShowFriday = false;
    }

    private void request(PtrFrameLayout ptrFrame) {
        if (FeatureConfig.enableFridayFunctionFeature == 1 && MyApplication.getInstance().isShowFriday) {
            Call<FridayMo> callAd = FridayClient.getService(AccountService.class).getAd();
            callAd.enqueue(new InitRequestCallBack<FridayMo>() {
                @Override
                public void onSuccess(Call<FridayMo> call, final Response<FridayMo> response) {
                    if (response.body().getFloatButton() == null || response.body().getFloatButton().getFloatButtonUrl() == null || "".equals(response.body()
                            .getFloatButton().getFloatButtonUrl())) {
                        return;
                    }
                    binding.homeIvAd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra(BundleKeys.TITLE, response.body().getFloatButton().getFloatButtonName());
                            intent.putExtra(BundleKeys.URL, CommonMethod.getUrlWithoutBase(response.body().getFloatButton().getJumpUrl()));
                            ActivityUtils.push(RDWebViewAct.class, intent);
                        }
                    });
                    Glide.with(ActivityUtils.peek()).load(response.body().getFloatButton().getFloatButtonUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            binding.homeIvAd.setImageBitmap(resource);
                            binding.homeShowAd.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
        }

        // 请求banner
        Call<BannerModel> callBanner = RDClient.getService(CommonService.class).getBanner();
        callBanner.enqueue(new RequestCallBack<BannerModel>() {
            @Override
            public void onSuccess(Call<BannerModel> call, Response<BannerModel> response) {
                bindBanner(response.body());
            }
        });
//        // 请求推荐产品
//        Call<HomeModel> callProduct = RDClient.getService(CommonService.class).getIndexProduct();
//        callProduct.enqueue(new RequestCallBack<HomeModel>(ptrFrame) {
//            @Override
//            public void onSuccess(Call<HomeModel> call, Response<HomeModel> response) {
//                //                if (response.body().size() > 0)
//                if (response.body() != null && response.body().getTenderItem() != null) {
//                    item.set(response.body());
//                }
//            }
//        });
        //请求精选理财
        Call<ProductMoList> call = RDClient.getService(ProductService.class).investList(1);
        call.enqueue(new RequestCallBack<ProductMoList>(ptrFrame) {
            @Override
            public void onSuccess(Call<ProductMoList> call, Response<ProductMoList> response) {
                if (response.body().getTenderList() == null) {
                    return;
                }
                itemsInvest.clear();
                if (response.body().getTenderList().size() >= 2) {
                    itemsInvest.add(response.body().getTenderList().get(0));
                    itemsInvest.add(response.body().getTenderList().get(1));
                } else {
                    itemsInvest.addAll(response.body().getTenderList());
                }
                if (itemsInvest.size() == 0) {
                    binding.homeInvestEmpty.setVisibility(View.VISIBLE);
                } else {
                    binding.homeInvestEmpty.setVisibility(View.GONE);
                }
            }
        });
        //        //获取活动列表
        Call<ActivityMo> callActivity = RDClient.getService(ExtraService.class).getActivity(1);
        callActivity.enqueue(new RequestCallBack<ActivityMo>(ptrFrame) {
            @Override
            public void onSuccess(Call<ActivityMo> call, Response<ActivityMo> response) {
                itemsActvity.clear();
                for (ActivityMo.Activity activity : response.body().getActivityList()) {
                    activity.setImageHost(response.body().getQiniuDomain());
                }
                if (response.body().getActivityList().size() >= 2) {
                    itemsActvity.add(response.body().getActivityList().get(0));
                    itemsActvity.add(response.body().getActivityList().get(1));
                } else {
                    itemsActvity.addAll(response.body().getActivityList());
                }
                if (itemsActvity.size() == 0) {
                    binding.homeActivityEmpty.setVisibility(View.VISIBLE);
                } else {
                    binding.homeActivityEmpty.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 绑定banner图
     */
    private void bindBanner(final BannerModel bannerModel) {
        List<RBannerBean> bannerImages = new ArrayList<>();
        for (BannerModel.Banner banner : bannerModel.getBannerList()) {
            RBannerBean bean = new RBannerBean();
            bean.setPicUrl(bannerModel.getQiniuDomain() + banner.getImageUrl());
            bean.setIntroduction(banner.getTitle());
            bannerImages.add(bean);
        }
        convenientBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView() {
                            @Override
                            protected void itemOnClick(int position) {
                                if (!bannerModel.getBannerList().get(position).getUrl().isEmpty()) {
                                    //link 不为空时跳转
                                    Intent intent = new Intent();
                                    intent.putExtra(BundleKeys.TITLE, bannerModel.getBannerList().get(position).getTitle());
                                    intent.putExtra(BundleKeys.URL, CommonMethod.getUrlWithoutBase(bannerModel.getBannerList().get(position).getUrl()));
                                    Log.e("home banner link====>", CommonMethod.getUrlWithoutBase(bannerModel.getBannerList().get(position).getUrl()));
                                    ActivityUtils.push(RDWebViewAct.class, intent);
                                } else if (null != bannerModel.getBannerList().get(position).getContent() && !"".equals(bannerModel.getBannerList().get
                                        (position).getContent())) {
                                    Intent intent = new Intent();
                                    intent.putExtra(BundleKeys.TITLE, bannerModel.getBannerList().get(position).getTitle());
                                    intent.putExtra(BundleKeys.DATA, bannerModel.getBannerList().get(position).getContent());
                                    Log.e("home banner content==>", bannerModel.getBannerList().get(position).getContent());
                                    ActivityUtils.push(RDWebViewAct.class, intent);
                                }
                            }
                        };
                    }
                }, bannerImages)
                // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});

    }

    //    /**
    //     * 立即抢购
    //     */
    //    public void onDetailClick(View v) {
    //        if (!MyApplication.getInstance().isLand()) {
    //            ActivityUtils.push(LoginAct.class);
    //            return;
    //        }
    //        Intent intent = new Intent();
    //        intent.putExtra(BundleKeys.ID, item.get().getTenderItem().getId());
    //        intent.putExtra(BundleKeys.NAME, item.get().getTenderItem().getName());
    //        ActivityUtils.push(FinancingDetailAct.class, intent);
    //    }

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

    /**
     * 活动中心
     */
    public void activityListClick(View view) {
        ActivityUtils.push(ActivityAct.class);
    }

    /**
     * 平台简介
     */
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
     * 邀请好友
     */
    public void inviteClick(View view) {
        if (MyApplication.getInstance().isLand()) {
            if (BaseParams.inviteType == 1) {
                ActivityUtils.push(InvitationAct.class);
            } else if (BaseParams.inviteType == 2) {
                ActivityUtils.push(InvitationTypeBAct.class);
            }
        } else {
            ActivityUtils.push(LoginAct.class);
        }
    }

    /**
     * 理财
     */
    public void projectClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.IS_PRODUCT, true);
        ActivityUtils.push(MainAct.class, intent);
    }

    /**
     * extraBtn点击事件
     */
    public void extraClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, binding.getRoot().getContext().getString(R.string.app_name));
        intent.putExtra(BundleKeys.NEED_GOBACK, true);
        intent.putExtra(BundleKeys.ISCLOSE, true);
        String getUrl = BaseParams.EXTRA_BUTTON_URL;
        intent.putExtra(BundleKeys.URL, getUrl);
        ActivityUtils.push(RDWebViewAct.class, intent);
    }
}