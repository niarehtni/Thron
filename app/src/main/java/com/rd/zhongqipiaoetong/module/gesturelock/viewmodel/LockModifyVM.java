package com.rd.zhongqipiaoetong.module.gesturelock.viewmodel;

import android.view.View;

import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * <p/>
 * Description: 手势密码修改管理类
 */
public class LockModifyVM {
    /** 结束当前页面 */
    public void getBackClick(View view) {
        ActivityUtils.pop(ActivityUtils.peek());
    }
}