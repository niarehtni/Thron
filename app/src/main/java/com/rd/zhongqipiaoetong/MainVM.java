package com.rd.zhongqipiaoetong;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.rd.zhongqipiaoetong.databinding.MainTabActBinding;
import com.rd.zhongqipiaoetong.module.account.fragment.MyAccountFrag;
import com.rd.zhongqipiaoetong.module.homepage.fragment.HomeFrag;
import com.rd.zhongqipiaoetong.module.more.fragment.MoreFrag;
import com.rd.zhongqipiaoetong.module.product.fragment.ProductListFrag;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/23 下午2:12
 * <p/>
 * Description: tab页面控制器({@link MainAct})
 */
public class MainVM {
    private MainTabActBinding   binding;
    /**
     * fragment 管理器
     */
    private FragmentManager     fragmentM;
    /**
     * fragment 控制器
     */
    private FragmentTransaction fragmentTs;
    /**
     * 首页
     */
    private HomeFrag            mHomeFrag;
    /**
     * 产品列表
     */
    public ProductListFrag     mProductListFrag;
    /**
     * 我的账户
     */
    public  MyAccountFrag       mAccountFrag;
    /**
     * 更多
     */
    private MoreFrag            mMoreFrag;
    /**
     * fragmentTransaction 中tag名称，以变从中获取fragment
     */
    private static final String[] TAGS = {"HOME", "LIST", "ACCOUNT", "MORE"};

    public MainVM(MainTabActBinding binding, FragmentManager fragmentM) {
        this.binding = binding;
        this.fragmentM = fragmentM;
        fragmentTs = fragmentM.beginTransaction();
        homeClick(binding.mainHome);
    }

    /**
     * 首页点击事件
     */
    public void homeClick(View view) {
        if (null != mHomeFrag && !mHomeFrag.isHidden()) {
            return;
        }
        hideAllFragment();
        if (mHomeFrag == null) {
            mHomeFrag = (HomeFrag) fragmentM.findFragmentByTag(TAGS[0]);
        }
        if (mHomeFrag == null) {
            mHomeFrag = new HomeFrag();
            fragmentTs.add(R.id.main_content, mHomeFrag, TAGS[0]);
        } else {
            fragmentTs.show(mHomeFrag);
        }
        adjustTabButton(view, R.drawable.bottom_home_style);
        fragmentTs.commit();
    }

    /**
     * 项目列表点击事件
     */
    public void listClick(View view) {
        if (null != mProductListFrag && !mProductListFrag.isHidden()) {
            return;
        }
        hideAllFragment();
        if (mProductListFrag == null) {
            mProductListFrag = (ProductListFrag) fragmentM.findFragmentByTag(TAGS[1]);
        }
        if (mProductListFrag == null) {
            mProductListFrag = new ProductListFrag();
            fragmentTs.add(R.id.main_content, mProductListFrag, TAGS[1]);
        } else {
            fragmentTs.show(mProductListFrag);
        }
        adjustTabButton(view, R.drawable.bottom_list_style);
        fragmentTs.commit();
    }

    /**
     * 账户中心点击事件
     */
    public void accountClick(View view) {
        if (MyApplication.getInstance().isLand()) {
            hideAllFragment();
            if (mAccountFrag == null) {
                mAccountFrag = (MyAccountFrag) fragmentM.findFragmentByTag(TAGS[2]);
            }
            if (mAccountFrag == null) {
                mAccountFrag = new MyAccountFrag();
                fragmentTs.add(R.id.main_content, mAccountFrag, TAGS[2]);
            } else {
                fragmentTs.show(mAccountFrag);
            }
            if (mAccountFrag != null) {
                mAccountFrag.upData();
            }
            adjustTabButton(view, R.drawable.bottom_account_style);
            fragmentTs.commit();
        } else {
            homeClick(binding.mainHome);
            ActivityUtils.push(LoginAct.class);
        }
    }

    /**
     * 更多点击事件
     */
    public void moreClick(View view) {
        if (null != mMoreFrag && !mMoreFrag.isHidden()) {
            return;
        }
        hideAllFragment();
        if (mMoreFrag == null) {
            mMoreFrag = (MoreFrag) fragmentM.findFragmentByTag(TAGS[3]);
        }
        if (mMoreFrag == null) {
            mMoreFrag = new MoreFrag();
            fragmentTs.add(R.id.main_content, mMoreFrag, TAGS[3]);
        } else {
            fragmentTs.show(mMoreFrag);
        }
        adjustTabButton(view, R.drawable.bottom_more_style);
        fragmentTs.commit();
    }

    // 上一项的TextView
    private TextView previous;
    // 上一项的drawable
    private Drawable drawable;

    /**
     * 替换底部选中图片
     *
     * @param view
     *         本次选中的 TextView
     * @param resId
     *         本次选中的 TextView 的drawable
     */
    private void adjustTabButton(View view, @DrawableRes int resId) {
        Context context = view.getContext();
        // 还原上一项选中效果
        if (previous != null && drawable != null) {
            previous.setSelected(false);
            drawable.clearColorFilter();
            previous.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
        previous = (TextView) view;
        previous.setSelected(true);
        drawable = context.getResources().getDrawable(resId);
        drawable.setColorFilter(context.getResources().getColor(R.color.app_color_principal), PorterDuff.Mode.MULTIPLY);
        previous.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideAllFragment() {
        fragmentTs = fragmentM.beginTransaction();
        // 为空，则尝试用TAG去获取fragment对象
        if (mHomeFrag == null) {
            mHomeFrag = (HomeFrag) fragmentM.findFragmentByTag(TAGS[0]);
        }
        if (mProductListFrag == null) {
            mProductListFrag = (ProductListFrag) fragmentM.findFragmentByTag(TAGS[1]);
        }
        if (mAccountFrag == null) {
            mAccountFrag = (MyAccountFrag) fragmentM.findFragmentByTag(TAGS[2]);
        }
        if (mMoreFrag == null) {
            mMoreFrag = (MoreFrag) fragmentM.findFragmentByTag(TAGS[3]);
        }

        // 如果不为空，则隐藏
        if (mHomeFrag != null) {
            fragmentTs.hide(mHomeFrag);
        }
        if (mProductListFrag != null) {
            fragmentTs.hide(mProductListFrag);
        }
        if (mAccountFrag != null) {
            fragmentTs.hide(mAccountFrag);
        }
        if (mMoreFrag != null) {
            fragmentTs.hide(mMoreFrag);
        }


    }
}