package com.rd.zhongqipiaoetong.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.databinding.PageLoadingLayoutBinding;
import com.rd.zhongqipiaoetong.view.entity.EmptyState;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/1 16:02
 * <p/>
 * Description: 页面前置加载
 */
public class PageLoadingView extends LinearLayout {
    private PageLoadingLayoutBinding binding;

    public PageLoadingView(Context context) {
        this(context, null, 0);
    }

    public PageLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.page_loading_layout, this, true);
    }

    /**
     * 设置空态值
     */
    public void setEmptyState(EmptyState state) {
        binding.setState(state);
    }
}