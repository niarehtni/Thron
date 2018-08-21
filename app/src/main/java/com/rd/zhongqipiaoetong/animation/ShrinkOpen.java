package com.rd.zhongqipiaoetong.animation;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/17 下午6:57
 * <p/>
 * Description: 伸缩展开动画
 */
public class ShrinkOpen {
    /**
     * 获取上移动画
     */
    public static ObjectAnimator getUpMoveAnimator(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0F, -300F);
        // 设置动画持续时间
        animator.setDuration(1000);
        return animator;
    }

    /**
     * 获取下移动画
     */
    public static ObjectAnimator getDownMoveAnimator(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -300F, 0F);
        // 设置动画持续时间
        animator.setDuration(1000);
        return animator;
    }

    /**
     * 获取展开动画
     */
    public static ScaleAnimation getOpenAnimation() {
        ScaleAnimation animator = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
        // 设置动画持续时间
        animator.setDuration(1000);
        animator.setFillAfter(true);
        return animator;
    }

    /**
     * 获取收缩动画
     */
    public static ScaleAnimation getShrinkAnimation() {
        ScaleAnimation animator = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
        // 设置动画持续时间
        animator.setDuration(1000);
        animator.setFillAfter(true);
        return animator;
    }
}