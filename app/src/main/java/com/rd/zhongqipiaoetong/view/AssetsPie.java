package com.rd.zhongqipiaoetong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.view.entity.PieArc;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/9 16:53
 * <p/>
 * Description: 我的资产 - 饼状图
 */
public class AssetsPie extends View {
    private static final String TAG      = "AssetsPie";
    /**
     * 动画持续时间
     */
    private static final long   DURATION = 2000;
    /**
     * 圈之间的间隔
     */
    private static final int    SPACE    = 2;
    /**
     * 顺时针 - 1
     * 逆时针 - -1
     */
    private static final int    SYMBOL   = -1;
    /**
     * 资产 - 15,250.00
     */
    private              String assets   = "0.00";
    /**
     * "资产"的字体颜色
     */
    private int           assetsColor;
    /**
     * "资产"的字体大小
     */
    private float         assetsTextSize;
    /**
     * 圆环宽度
     */
    private float         roundWidth;
    /**
     * "资产" 和 "描述"的间距
     */
    private float         textMargin;
    /**
     * 描述 - 总资产(元)
     */
    private String        tips;
    /**
     * "描述"的字体颜色
     */
    private int           tipsColor;
    /**
     * "描述"的字体大小
     */
    private float         tipsTextSize;
    /**
     * 圆弧参数（颜色、百分比）
     */
    private List<PieArc>  arcs;
    /**
     * 画笔
     */
    private Paint         paint;
    /**
     * view动画
     */
    private PieAnimation  anim;
    /**
     * 绘制点
     */
    private float         sweepAngle;
    /**
     * 当前绘制使用的圆弧所在list中的下标
     */
    private int           index;
    /**
     * [0] - 起始点
     * [1] - 弧长
     * [2] - 弧终点
     */
    private List<float[]> point;
    /**
     * 最大角度
     */
    private float         maxAngle;

    public AssetsPie(Context context) {
        this(context, null, 0);
    }

    public AssetsPie(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AssetsPie(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.AssetsPie);
        assets = attributes.getNonResourceString(R.styleable.AssetsPie_assets);
        assetsColor = attributes.getColor(R.styleable.AssetsPie_assetsColor, Color.BLACK);
        assetsTextSize = attributes.getDimensionPixelSize(R.styleable.AssetsPie_assetsTextSize, 50);
        roundWidth = attributes.getDimension(R.styleable.AssetsPie_roundWidth, 20);
        textMargin = attributes.getDimension(R.styleable.AssetsPie_textMargin, 50);
        tips = attributes.getString(R.styleable.AssetsPie_tips);
        tipsColor = attributes.getColor(R.styleable.AssetsPie_tipsColor, Color.GRAY);
        tipsTextSize = attributes.getDimensionPixelSize(R.styleable.AssetsPie_tipsTextSize, 25);
        attributes.recycle();

        initView();
    }

    /**
     * view初始化
     */
    private void initView() {
        // 画笔初始化
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // animation 初始化
        anim = new PieAnimation();
        // 设置动画持续时间
        anim.setDuration(DURATION);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // padding
        final int paddingLeft   = getPaddingLeft();
        final int paddingRight  = getPaddingRight();
        final int paddingTop    = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        // view的宽度
        final int width = getWidth() - paddingLeft - paddingRight;
        // view的高度
        final int height = getHeight() - paddingTop - paddingBottom;

        // 圆心 - x轴坐标
        final float x = paddingLeft + width / 2;
        // 圆心 - y轴坐标
        final float y = paddingTop + height / 2;
        // 外圆的半径
        final int radius = Math.min(width, height) / 2;
        // 内圆的半径
        final int roundRadius = (int) (radius - roundWidth);

        drawAssetsText(canvas, x, y);
        drawTipsText(canvas, x, y);
        drawCircle(canvas, x, y, roundRadius);
        final float temp = sweepAngle;
        //        Logger.i(TAG, ".");
        //        Logger.i(TAG, "temp = " + temp);
        for (int i = 0; i <= index; i++) {
            if (Math.abs(sweepAngle) > Math.abs(point.get(index)[2]) && index < arcs.size() - 1) {
                index++;
            }
            if (i < index || temp == maxAngle || temp == point.get(index)[1]) {
                sweepAngle = point.get(i)[1];
            } else {
                if (index != 0) {
                    sweepAngle = temp - point.get(index - 1)[2];
                    // 移除可能多画的space部分
                    if (Math.abs(sweepAngle) > Math.abs(point.get(index)[1])) {
                        sweepAngle = point.get(index)[1];
                    }
                }
            }
            //            Logger.i(TAG, "i = " + i + " ,index = " + index);
            //            Logger.i(TAG, "startAngle = " + point.get(i)[0]);
            //            Logger.i(TAG, "sweepAngle = " + sweepAngle);
            drawArc(canvas, x, y, roundRadius, arcs.get(i), point.get(i)[0], sweepAngle);
        }
    }

    /**
     * 画"资产额度"
     */
    private void drawAssetsText(Canvas canvas, float x, float y) {
        paint.setStrokeWidth(0);
        paint.setColor(assetsColor);
        paint.setTextSize(assetsTextSize);
        //设置字体
        paint.setTypeface(Typeface.DEFAULT);
        //测量字体宽度(根据字体的宽度设置在圆环中间)
        if(assets == null){
            assets = "0.00";
        }
        float assetsTextWidth = paint.measureText(assets);
        canvas.drawText(assets, x - assetsTextWidth / 2, y + assetsTextSize / 2, paint);
    }

    /**
     * 画"资产描述"
     */
    private void drawTipsText(Canvas canvas, float x, float y) {
        paint.setStrokeWidth(0);
        paint.setColor(tipsColor);
        paint.setTextSize(tipsTextSize);
        //设置字体
        paint.setTypeface(Typeface.DEFAULT);
        //测量字体宽度(根据字体的宽度设置在圆环中间)
        float tipsTextWidth = paint.measureText(tips);
        canvas.drawText(tips, x - tipsTextWidth / 2, y + tipsTextSize / 2 + textMargin, paint);
    }

    /**
     * 画圆环
     */
    private void drawCircle(Canvas canvas, float x, float y, int roundRadius) {
        // 设置空心
        paint.setStyle(Paint.Style.STROKE);
        // 设置圆环的颜色
        paint.setColor(0xFFDDDDDD);
        // 设置圆环的宽度
        paint.setStrokeWidth(roundWidth);
        // 消除锯齿
        paint.setAntiAlias(true);
        // 画出圆环
        canvas.drawCircle(x, y, roundRadius, paint);
    }

    /**
     * 画圆弧
     */
    private void drawArc(Canvas canvas, float x, float y, int roundRadius, PieArc arc, float startAngle, float sweepAngle) {
        // 设置空心
        paint.setStyle(Paint.Style.STROKE);
        // 设置实心
        // paint.setStyle(Paint.Style.FILL_AND_STROKE);
        // 设置圆环的宽度
        paint.setStrokeWidth(roundWidth);
        // 设置进度的颜色
        paint.setColor(arc.getColor());
        // 用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(x - roundRadius, y - roundRadius, x + roundRadius, y + roundRadius);
        // 根据进度画圆弧
        canvas.drawArc(oval, startAngle, sweepAngle, false, paint);
    }

    /**
     * 设置需要绘制的圆弧
     */
    public void setArcs(List<PieArc> list) {
        if (list != null && list.size() > 0) {
            this.arcs = list;
            // 索引
            index = 0;
            // 排序,兼获取百分比不为0的数量
            int size = bubbleSort();
            // 计算间隔
            float space = 0;
            // 不止一条数据，且最大的不为100，则设置间隔
            if (size > 1 && arcs.get(0).getPercent() != 100) {
                space = SYMBOL * SPACE;
            }
            // 系数
            float coefficient = (float) ((360.0 - size * Math.abs(space)) / 100.0);
            // 计算起始点（最大圆弧，基于圆最底点的左右平分）
            float startAngle = 90 - SYMBOL * arcs.get(index).getPercent() * coefficient / 2;
            // 计算点阵
            point = new ArrayList<>();
            point.add(new float[]{startAngle + space / 2, SYMBOL * arcs.get(index).getPercent() * coefficient, SYMBOL * arcs.get(index).getPercent() *
                    coefficient + space / 2});
            float[] temp;
            for (int i = 1; i < size; i++) {
                temp = point.get(i - 1);
                point.add(new float[]{temp[0] + temp[1] + space, SYMBOL * arcs.get(i).getPercent() * coefficient, temp[2] + SYMBOL * arcs.get(i)
                        .getPercent() * coefficient + space});
            }
            maxAngle = point.get(size - 1)[2];
            // 开启动画，画圆
            this.startAnimation(anim);
        }
    }

    /**
     * 按照百分比 - 冒泡排序
     */
    private int bubbleSort() {
        PieArc temp;
        for (int i = 0; i < arcs.size() - 1; i++) {
            for (int j = 0; j < arcs.size() - 1 - i; j++) {
                if (arcs.get(j).getPercent() < arcs.get(j + 1).getPercent()) {
                    temp = arcs.get(j);
                    arcs.set(j, arcs.get(j + 1));
                    arcs.set(j + 1, temp);
                }
            }
        }
        int size = 0;
        for (PieArc arc : arcs) {
            if (arc.getPercent() != 0) {
                size++;
            }
        }
        return size;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * view动画
     */
    class PieAnimation extends Animation {
        public PieAnimation() {
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                sweepAngle = interpolatedTime * maxAngle;
            } else {
                sweepAngle = maxAngle;
            }
            postInvalidate();
        }
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public void setAssetsColor(int assetsColor) {
        this.assetsColor = assetsColor;
    }

    public void setAssetsTextSize(float assetsTextSize) {
        this.assetsTextSize = assetsTextSize;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setTipsColor(int tipsColor) {
        this.tipsColor = tipsColor;
    }

    public void setTipsTextSize(float tipsTextSize) {
        this.tipsTextSize = tipsTextSize;
    }

    public void setTextMargin(float textMargin) {
        this.textMargin = textMargin;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }
}