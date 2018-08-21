package com.rd.zhongqipiaoetong.common.ui;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/4 17:18
 * <p/>
 * Description: 启用懒加载模式的Fragment
 */
public abstract class BaseLazyFragment extends BaseFragment {
    // 标志位，用以判断"初始化"动作是否完成
    protected boolean isPrepared;
    // 标志位，用以判断当面是否可见
    protected boolean isVisible;

    /**
     * to judge if is visible to user
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            this.isVisible = true;
            onVisible();
        } else {
            this.isVisible = false;
            onInvisible();
        }
    }

    /**
     * when the  fragment is not visible to user
     */
    protected void onInvisible() {
    }

    /**
     * when the fragment is visible to user lazyload
     */
    protected void onVisible() {
        // 如果没初始化好或者不可见，则return
        if (isPrepared && isVisible) {
            lazyLoad();
        }
    }

    /**
     * when the fragment init is complete
     */
    protected void ready() {
        isPrepared = true;
        if (isVisible) {
            lazyLoad();
        }
    }

    /**
     * 初始化完成 且 页面可见，则执行本方法
     */
    protected abstract void lazyLoad();
}