package com.rd.zhongqipiaoetong.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rd.zhongqipiaoetong.R;

/**
 * Created by chenwei on 16/5/23.
 * 组合控件
 * description 自带删除按钮的EditText
 */
public class IntegratedEditText extends LinearLayout {
    private Context   context;
    private EditText  mEditText;
    private ImageView mDeletImg;
    private ImageView mShowPwdImg;
    private boolean isShowPwd = true;

    public IntegratedEditText(Context context) {
        this(context, null);
    }

    public IntegratedEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IntegratedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        //        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IntegratedEditText);
        //        String hint = a.getString(R.styleable.IntegratedEditText_hint);
        //        mEditText = new EditText(context);
        //        mEditText.setBackgroundDrawable(null);
        //        mEditText.setHint(hint);
        //        setEditText();
        //        addView(mEditText,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        //
        //        a.recycle();
    }

    /**
     * 设置EditText的监听事件等
     */
    private void setEditText() {

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mDeletImg.getVisibility() == GONE)
                        mDeletImg.setVisibility(VISIBLE);
                    startAnimation(mDeletImg, 0, 1, hasFocus);
                } else {
                    startAnimation(mDeletImg, 1, 0, hasFocus);
                }
            }
        });
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {

        if (index > 2)
            throw new IllegalArgumentException("Can only have three view");

        if (child instanceof EditText) {
            if (mEditText != null)
                throw new IllegalArgumentException("Can only have one EditText subview");
            mEditText = (EditText) child;
            setEditText();
        } else if (child instanceof ImageView && mDeletImg == null) {

            mDeletImg = (ImageView) child;
            mDeletImg.setVisibility(GONE);
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditText.setText("");
                }
            });
        } else if (child instanceof ImageView && mShowPwdImg == null) {
            mShowPwdImg = (ImageView) child;
            mShowPwdImg.setImageResource(R.drawable.signup_bxs_pressed);
            mShowPwdImg.setColorFilter(0xFF49A7F6, PorterDuff.Mode.MULTIPLY);
            mShowPwdImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isShowPwd){

                        isShowPwd = false;
                        mShowPwdImg.setImageResource(R.drawable.signup_bxs_normal);
                        mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }else{
                        isShowPwd = true;
                        mShowPwdImg.setImageResource(R.drawable.signup_bxs_pressed);
                        mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            });
        }
        super.addView(child, index, params);

    }

    private void startAnimation(View view, float statrNum, float endNum, final boolean isFocus) {
        AnimatorSet    set         = new AnimatorSet();
        ObjectAnimator alphaAniam  = ObjectAnimator.ofFloat(view, "alpha", statrNum, endNum);
        ObjectAnimator scaleXAniam = ObjectAnimator.ofFloat(view, "scaleX", statrNum, endNum);
        ObjectAnimator scaleYAniam = ObjectAnimator.ofFloat(view, "scaleY", statrNum, endNum);
        set.playTogether(alphaAniam, scaleXAniam, scaleYAniam);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //设置按钮是否可以点击
                if (isFocus) {
                    mDeletImg.setClickable(true);
                } else {
                    mDeletImg.setClickable(false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.setDuration(500);
        set.start();
    }

    //获取内部的控件
    public EditText getmEditText() {
        return mEditText;
    }

    public void setmEditText(EditText mEditText) {
        this.mEditText = mEditText;
    }

    public ImageView getmDeletImg() {
        return mDeletImg;
    }

    public void setmDeletImg(ImageView mDeletImg) {
        this.mDeletImg = mDeletImg;
    }

    public ImageView getmShowPwdImg() {
        return mShowPwdImg;
    }

    public void setmShowPwdImg(ImageView mShowPwdImg) {
        this.mShowPwdImg = mShowPwdImg;
    }
}
