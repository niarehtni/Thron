package com.rd.zhongqipiaoetong.module.homepage.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rd.zhongqipiaoetong.MainAct;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.module.homepage.viewmodel.ViewPagerAdapter;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.SPUtil;

import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/30 15:06
 * <p/>
 * Description: 引导页
 */
public class GuideAct extends BaseActivity implements OnClickListener, OnPageChangeListener {
    // image容器
    private ViewPager        mViewPager;
    // point容器
    private LinearLayout     mSpace;
    // TextView关闭
    private TextView         mClose;
    // ViewPager适配器
    private ViewPagerAdapter mAdapter;
    // image存放View
    private ArrayList<View>  views;
    // 引导图片资源
    private TypedArray       pics;
    // 底部小点的图片
    private ArrayList<View>  points;
    // 记录当前选中位置
    private int              currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);
        pics = getResources().obtainTypedArray(R.array.guideImages);
        mViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        mSpace = (LinearLayout) findViewById(R.id.guide_bottom_point);
        mClose = (TextView) findViewById(R.id.guide_tv_close);
        mClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtil.setValue(BaseParams.SP_IS_FIRST_INE + "-" + BaseParams.getVersion(), false);
                ActivityUtils.push(MainAct.class);
                finish();
            }
        });
        initView();
        initData();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化ArrayList对象
        views = new ArrayList<>();
        points = new ArrayList<>();
        // 实例化ViewPager适配器
        mAdapter = new ViewPagerAdapter(views);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 定义一个布局并设置参数
        LayoutParams mParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // 初始化引导图片列表
        for (int i = 0; i < pics.length(); i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            // 防止图片不能填满屏幕
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            // 加载图片资源
            iv.setImageDrawable(pics.getDrawable(i));
            views.add(iv);
        }
        // 设置数据
        mViewPager.setAdapter(mAdapter);
        // 设置监听
        mViewPager.setOnPageChangeListener(this);
        // 初始化底部小点
        initPoint();
    }

    /**
     * 初始化底部小点
     */
    private void initPoint() {
        View view;
        for (int i = 0; i < pics.length(); i++) {

            view = new View(this);
            LayoutParams layoutParams = new LayoutParams(36, 36);
            view.setPadding(5, 5, 5, 5);
            view.setLayoutParams(layoutParams);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.guide_point_cur);
            } else {
                view.setBackgroundResource(R.drawable.guide_point);
            }
            mSpace.addView(view);
            points.add(view);
        }
    }

    /**
     * 滑动状态改变时调用
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    /**
     * 当前页面滑动时调用
     */
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    /**
     * 新的页面被选中时调用
     */
    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        setCurDot(position);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    /**
     * 设置当前页面的位置
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length()) {
            return;
        }
        mViewPager.setCurrentItem(position);
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length() - 1 || currentIndex == position) {
            return;
        }
        points.get(currentIndex).setBackgroundResource(R.drawable.guide_point);
        points.get(position).setBackgroundResource(R.drawable.guide_point_cur);
        currentIndex = position;
    }
}