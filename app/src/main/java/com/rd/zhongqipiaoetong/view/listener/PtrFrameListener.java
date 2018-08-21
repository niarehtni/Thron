package com.rd.zhongqipiaoetong.view.listener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/2 14:05
 * <p/>
 * Description:
 */
public interface PtrFrameListener {
    /**
     * 初始化view，只会在第一次创建view的时候，被调用
     *
     * @param ptrFrame
     *         PtrClassicFrameLayout
     */
    void ptrFrameInit(PtrClassicFrameLayout ptrFrame);

    /**
     * 刷新时会被调用，用以还原特定状态，例如：page
     */
    void ptrFrameRefresh();

    /**
     * 刷新、加载时都会调用，用以请求数据
     *
     * @param ptrFrame
     *         PtrFrameLayout
     */
    void ptrFrameRequest(PtrFrameLayout ptrFrame);
}