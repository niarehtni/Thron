package com.rd.zhongqipiaoetong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/12 14:54
 * <p/>
 * Description: 进度的进度条，线程安全的View，可直接在线程中更新进度
 */
public class RoundProgressBar extends View {
    /**
     * 画笔对象的引用
     */
    private Paint   paint;
    /**
     * 圆环的颜色
     */
    private int     roundColor;
    /**
     * 圆环进度的颜色
     */
    private int     roundProgressColor;
    /**
     * 中间进度百分比的字符串的颜色
     */
    private int     textColor;
    /**
     * 中间进度百分比的字符串的字体
     */
    private float   textSize;
    /**
     * 圆环的宽度
     */
    private float   roundWidth;
    /**
     * 最大进度
     */
    private int     max;
    /**
     * 当前进度
     */
    private float   progress;
    /**
     * 是否显示中间的进度
     */
    private boolean textIsDisplayable;
    /**
     * 进度的风格，实心或者空心
     */
    private int     style;
    /**
     * 中间的是文字
     */
    private String textInPro;
    public static final int STROKE = 0;
    public static final int FILL   = 1;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);

        // 获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
        textColor = mTypedArray.getColor(R.styleable.StampView_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundTextSize, 30);
        roundWidth = mTypedArray.getDimension(R.styleable.AssetsPie_roundWidth, 5);
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // padding
        final int paddingLeft   = getPaddingLeft();
        final int paddingRight  = getPaddingRight();
        final int paddingTop    = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        final int diameter = getWidth() < getHeight() ? getWidth() : getHeight();

        // view的宽度
        final int width = diameter - paddingLeft - paddingRight;
        // view的高度
        final int height = diameter - paddingTop - paddingBottom;

        // 圆心 - x轴坐标
        final float x = paddingLeft + width / 2;
        // 圆心 - y轴坐标
        final float y = paddingTop + height / 2;
        // 外圆的半径
        final int radius = Math.min(width, height) / 2;

        /**
         * 画最外层的大圆环
         */
        paint.setColor(roundColor); // 设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); // 设置空心
        paint.setStrokeWidth(roundWidth); // 设置圆环的宽度
        paint.setAntiAlias(true);  // 消除锯齿
        canvas.drawCircle(x, y, radius-roundWidth, paint); // 画出圆环

        /**
         * 画进度百分比或文字
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体

        String drawInnerText = (textInPro==null? DisplayFormat.doubleFormat(Math.abs(progress)) + "%":textInPro);

        float textWidth = paint.measureText(drawInnerText);   // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间

        if (textIsDisplayable && style == STROKE) {
            canvas.drawText(drawInnerText, x - textWidth / 2, y + textSize / 2, paint); // 画出进度百分比
        }

        /**
         * 画圆弧 ，画圆环的进度
         */
        // 设置圆环的宽度
        paint.setStrokeWidth(roundWidth);
        // 设置进度的颜色
        paint.setColor(roundProgressColor);
        // 用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(x - radius+roundWidth, y - radius+roundWidth, x + radius-roundWidth, y + radius-roundWidth);

        // 设置进度是实心还是空心
        switch (style) {
            case STROKE: {
                if (progress != 0) {
                    paint.setStyle(Paint.Style.STROKE);
                    // 根据进度画圆弧
                    canvas.drawArc(oval, -90, -360 * progress / max, false, paint);
                }
                break;
            }
            case FILL: {
                if (progress != 0) {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                    // 根据进度画圆弧
                    canvas.drawArc(oval, -90, -360 * progress / max, true, paint);
                }
                break;
            }
        }
    }

    /**
     * 获取进度的最大值
     */
    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     */
    public synchronized float getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     *         百分比
     * @param flag
     *         是否显示动画
     */
    public synchronized void setProgress(float progress, boolean flag) {
        if (!flag) {
            if (Math.abs(progress) > max) {
                return;
            }
            this.progress = progress;
            postInvalidate();
        } else {
            if (progress > max || progress < 0) {
                return;
            }
            final float p = progress;
            // 启动线程执行任务
            new Thread() {
                public void run() {
                    while (RoundProgressBar.this.progress++ < p) {
                        // 发送消息
                        handler.sendEmptyMessage(0x11);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            postInvalidate();
        }
    };

    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    public String getTextInPro() {
        return textInPro;
    }

    public void setTextInPro(String textInPro) {
        this.textInPro = textInPro;
    }

    @BindingAdapter(value = {"progress","status","isBond"},requireAll = false)
    public static void progress(View view, String p, String status,boolean isBond) {
        float progress = TextUtils.isEmpty(p) ? 0 : Float.valueOf(p);

        if (progress == 100){
            if(!TextUtils.isEmpty(status)){
                switch (Integer.parseInt(status)){
                    case 1:
                        break;
                    case 3:
                        if(isBond){
                            ((RoundProgressBar) view).setTextInPro("已转让");
                        }else{
                            ((RoundProgressBar) view).setTextInPro("已满标");
                        }
                        break;
                    case 4:
                        if(isBond){
                            ((RoundProgressBar) view).setTextInPro("已结束");
                        }else{
                            ((RoundProgressBar) view).setTextInPro("还款中");
                        }
                        break;
                    case 5:
                        if(isBond){
                            ((RoundProgressBar) view).setTextInPro("已结束");
                            break;
                        }
                    case 6:
                        if(isBond){
                            ((RoundProgressBar) view).setTextInPro("已结束");
                            break;
                        }
                    case 8:
                        ((RoundProgressBar) view).setTextInPro("已还款");
                        break;
                    default:
                        ((RoundProgressBar) view).setTextInPro("已满标");
                        break;
                }
            } else {
                if(isBond){
                    ((RoundProgressBar) view).setTextInPro("已转让");
                }else{
                    ((RoundProgressBar) view).setTextInPro("已满标");
                }
            }

            ((RoundProgressBar) view).setTextColor(view.getContext().getResources().getColor(R.color.grey_bg));
            ((RoundProgressBar) view).setProgress(0, false);
        }else{
            ((RoundProgressBar) view).setTextInPro(null);
            ((RoundProgressBar) view).setTextColor(view.getContext().getResources().getColor(R.color.app_color_secondary));
            ((RoundProgressBar) view).setProgress(-progress, false);
        }
        view.invalidate();
    }

    @BindingAdapter(value = {"progressBorrow"})
    public static void progressBorrow(View view,String progressBorrow){
        float progress = TextUtils.isEmpty(progressBorrow) ? 0 : Float.valueOf(progressBorrow);
        ((RoundProgressBar) view).setTextInPro(null);
        ((RoundProgressBar) view).setTextColor(view.getContext().getResources().getColor(R.color.app_color_secondary));
        ((RoundProgressBar) view).setProgress(-progress, false);
        view.invalidate();
    }
}