package com.rd.zhongqipiaoetong.view.swipe.listener;

import com.rd.zhongqipiaoetong.view.swipe.ZSwipeItem;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/18 12:25
 * <p/>
 * Description: 滑动监听器
 */
public interface SwipeListener {
    public void onStartOpen(ZSwipeItem layout);

    public void onOpen(ZSwipeItem layout);

    public void onStartClose(ZSwipeItem layout);

    public void onClose(ZSwipeItem layout);

    public void onUpdate(ZSwipeItem layout, int leftOffset, int topOffset);

    public void onHandRelease(ZSwipeItem layout, float xvel, float yvel);
}