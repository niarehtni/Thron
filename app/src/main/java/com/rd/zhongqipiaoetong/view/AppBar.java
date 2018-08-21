package com.rd.zhongqipiaoetong.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rd.zhongqipiaoetong.databinding.AppbarBinding;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.info.ErrorInfo;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/9 15:39
 * <p/>
 * Description: 标题栏
 */
public class AppBar extends LinearLayout {
    private RelativeLayout mLayout;
    // 左侧
    private FrameLayout    mLeftParent;
    private TextView       mLeftText;
    private ImageView      mLeftImage;
    // title
    private TextView       mTitleText;
    // 右侧
    private FrameLayout    mRightParent;
    private TextView       mRightText;
    private ImageView      mRightImage;
    // error
    private TextView       mError;

    public AppBar(Context context) {
        this(context, null);
    }

    public AppBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        AppbarBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.appbar, this, true);
        binding.setError(ErrorInfo.getInstance().error);

        mLayout = (RelativeLayout) findViewById(R.id.appbar_bg);
        // 左侧
        mLeftParent = (FrameLayout) findViewById(R.id.left_parent);
        mLeftText = (TextView) findViewById(R.id.appbar_left_text);
        mLeftImage = (ImageView) findViewById(R.id.appbar_left);

        // title
        mTitleText = (TextView) findViewById(R.id.appbar_title);

        // 右侧
        mRightParent = (FrameLayout) findViewById(R.id.right_parent);
        mRightText = (TextView) findViewById(R.id.appbar_right_text);
        mRightImage = (ImageView) findViewById(R.id.appbar_right);

        // error
        mError = (TextView) findViewById(R.id.appbar_error);
        setBackOption(true);
    }

    ///////////////////////////////////////////////////////////////////////////
    // title
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 设置title
     */
    public void setTitle(CharSequence title) {
        mTitleText.setText(title);
    }

    /**
     * 设置title
     */
    public void setTitle(@StringRes int resId) {
        mTitleText.setText(getResources().getString(resId));
    }

    /**
     * 设置title颜色
     */
    public void setTitleColor(@ColorInt int color) {
        mTitleText.setTextColor(color);
    }

    ///////////////////////////////////////////////////////////////////////////
    // left
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 设置back键显示
     */
    public void setBackOption(boolean option) {
        if (option) {
            mLeftImage.setVisibility(View.VISIBLE);
            mLeftParent.setEnabled(true);
            mLeftParent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) getContext()).onBackPressed();
                }
            });
        } else {
            mLeftParent.setOnClickListener(null);
            mLeftParent.setEnabled(false);
            mLeftImage.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置左边icon的显示图片和点击事件
     */
    public void setLeftIconOption(@DrawableRes int imgResID, OnClickListener listener) {
        mLeftImage.setImageResource(imgResID);
        mLeftImage.setVisibility(View.VISIBLE);
        mLeftText.setVisibility(View.GONE);
        if (null != listener) {
            mLeftParent.setOnClickListener(listener);
        }
    }

    /**
     * 设置左边文字的显示文字和点击事件
     */
    public void setLeftTextOption(CharSequence text, OnClickListener listener) {
        mLeftText.setText(text);
        mLeftText.setVisibility(View.VISIBLE);
        mLeftImage.setVisibility(View.GONE);
        if (null != listener) {
            mLeftParent.setOnClickListener(listener);
        }
    }

    /**
     * 设置左边文字字体颜色
     */
    public void setLeftTextColor(@ColorInt int color) {
        mLeftText.setTextColor(color);
    }

    ///////////////////////////////////////////////////////////////////////////
    // right
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 设置右边icon的显示图片和点击事件
     */
    public void setRightIconOption(@DrawableRes int imgResID, OnClickListener listener) {
        mRightImage.setImageResource(imgResID);
        mRightImage.setVisibility(View.VISIBLE);
        mRightText.setVisibility(View.GONE);
        if (null != listener) {
            mRightParent.setOnClickListener(listener);
        }
    }

    /**
     * 设置右边文字的显示文字和点击事件
     */
    public void setRightTextOption(CharSequence text, OnClickListener listener) {
        mRightText.setText(text);
        mRightText.setVisibility(View.VISIBLE);
        mRightImage.setVisibility(View.GONE);
        if (null != listener) {
            mRightParent.setOnClickListener(listener);
        }
    }

    /**
     * 隐藏右边文字和点击事件
     */
    public void removeRightText(){
        mRightText.setVisibility(View.GONE);
        mRightParent.setClickable(false);
    }

    /**
     * 设置右边文字字体颜色
     */
    public void setRightTextColor(@ColorInt int color) {
        mRightText.setTextColor(color);
    }

    ///////////////////////////////////////////////////////////////////////////
    // appbar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 设置appbar背景色
     */
    public void setBackgroundColor(@ColorInt int color) {
        mLayout.setBackgroundColor(color);
    }

    /**
     * 设置error提示信息
     */
    public void setError(String error) {
        mError.setText(error);
        if (TextUtils.isEmpty(error)) {
            mError.setVisibility(View.GONE);
        } else {
            mError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 为back键设置selector
     *
     * @param color
     *         normal
     * @param colorBurn
     *         press
     */
    public void setLeftParentSelector(@ColorInt int color, @ColorInt int colorBurn) {
        if (color != 0 && colorBurn != 0) {
            // 初始化一个空对象
            StateListDrawable listDrawable = new StateListDrawable();

            ColorDrawable drawable     = new ColorDrawable(color);
            ColorDrawable drawableBurn = new ColorDrawable(colorBurn);

            // 获取对应的属性值 Android框架自带的属性 attr
            int pressed        = android.R.attr.state_pressed;
            int window_focused = android.R.attr.state_window_focused;
            int focused        = android.R.attr.state_focused;
            int selected       = android.R.attr.state_selected;

            listDrawable.addState(new int[]{pressed, window_focused}, drawableBurn);
            listDrawable.addState(new int[]{pressed, -focused}, drawableBurn);
            listDrawable.addState(new int[]{selected}, drawableBurn);
            listDrawable.addState(new int[]{focused}, drawableBurn);
            // 没有任何状态时显示的图片，我们给它设置为空集合
            listDrawable.addState(new int[]{}, drawable);
            mLeftParent.setBackgroundDrawable(listDrawable);
        }
    }
}