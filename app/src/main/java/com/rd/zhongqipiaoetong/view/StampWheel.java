package com.rd.zhongqipiaoetong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DensityUtils;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/2/24 15:23
 * <p/>
 * Description:
 */
public class StampWheel extends View {
    private int      layoutHeight  = 0;
    private int      layoutWidth   = 0;
    //圆圈线宽度
    private int      circleWidth   = 2;
    //字体大小
    private int      textSize      = DensityUtils.sp2px(ActivityUtils.peek(), 10);
    //圆圈之间的间距
    private int      dividerWidth  = 4;
    //字体上下间距
    private int      textSpace     = 10;
    // Padding (with defaults)
    private int      paddingTop    = 5;
    private int      paddingBottom = 5;
    private int      paddingLeft   = 5;
    private int      paddingRight  = 5;
    //圆圈的颜色
    private int      circleColor   = Color.parseColor("#FF7F7F");
    //字体的颜色
    private int      textColor     = Color.parseColor("#FF7F7F");
    // Paints
    private Paint    circlePaint   = new Paint();
    private Paint    middlePaint   = new Paint();
    private Paint    textPaint     = new Paint();
    // Rectangles
    private RectF    innerCircle   = new RectF();
    private RectF    outerCircle   = new RectF();
    private RectF    middleCircle  = new RectF();
    // 字体
    private String   text          = "";
    private String[] splitText     = {};
    //字体的显示的路径
    private Path     path          = new Path();
    //字体路径与水平直线的夹角度数为45度
    private float    degrees       = 0;
    //绘图出去到界面外的距离
    private int      translate     = 20;

    /**
     * The constructor for the StampView
     *
     * @param context
     * @param attrs
     */
    public StampWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.StampView));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // The first thing that happen is that we call the superclass
        // implementation of onMeasure. The reason for that is that measuring
        // can be quite a complex process and calling the super method is a
        // convenient way to get most of this complexity handled.
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // We can’t use getWidth() or getHight() here. During the measuring
        // pass the view has not gotten its final size yet (this happens first
        // at the start of the layout pass) so we have to use getMeasuredWidth()
        // and getMeasuredHeight().
        int size                 = 0;
        int width                = getMeasuredWidth();
        int height               = getMeasuredHeight();
        int widthWithoutPadding  = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        // Finally we have some simple logic that calculates the size of the
        // view
        // and calls setMeasuredDimension() to set that size.
        // Before we compare the width and height of the view, we remove the
        // padding,
        // and when we set the dimension we add it back again. Now the actual
        // content
        // of the view will be square, but, depending on the padding, the total
        // dimensions
        // of the view might not be.
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode  = MeasureSpec.getMode(widthMeasureSpec);
        if (heightMode != MeasureSpec.UNSPECIFIED && widthMode != MeasureSpec.UNSPECIFIED) {
            if (widthWithoutPadding > heightWithoutPadding) {
                size = heightWithoutPadding;
            } else {
                size = widthWithoutPadding;
            }
        } else {
            size = Math.max(heightWithoutPadding, widthWithoutPadding);
        }

        // If you override onMeasure() you have to call setMeasuredDimension().
        // This is how you report back the measured size. If you don’t call
        // setMeasuredDimension() the parent will throw an exception and your
        // application will crash.
        // We are calling the onMeasure() method of the superclass so we don’t
        // actually need to call setMeasuredDimension() since that takes care
        // of that. However, the purpose with overriding onMeasure() was to
        // change the default behaviour and to do that we need to call
        // setMeasuredDimension() with our own values.
        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    /**
     * Use onSizeChanged instead of onAttachedToWindow to get the dimensions of
     * the view,
     * because this method is called after measuring the dimensions of
     * MATCH_PARENT & WRAP_CONTENT.
     * Use this dimensions to setup the bounds and paints.
     */
    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        layoutWidth = newWidth;
        layoutHeight = newHeight;
        setupBounds();
        setupPaints();
        invalidate();
    }

    /**
     * Set the properties of the paints we're using to
     * draw the progress wheel
     */
    private void setupPaints() {
        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circleWidth);

        middlePaint.setColor(circleColor);
        middlePaint.setAntiAlias(true);
        middlePaint.setStyle(Paint.Style.STROKE);
        middlePaint.setStrokeWidth(circleWidth);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        middlePaint.setPathEffect(effects);

        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
    }

    /**
     * Set the bounds of the component
     */
    private void setupBounds() {
        // Width should equal to Height, find the min value to setup the circle
        int minValue = Math.min(layoutWidth, layoutHeight);

        // Calc the Offset if needed
        int xOffset = layoutWidth - minValue;
        int yOffset = layoutHeight - minValue;

        // Add the offset
        paddingTop = this.getPaddingTop() + (yOffset / 2);
        paddingBottom = this.getPaddingBottom() + (yOffset / 2);
        paddingLeft = this.getPaddingLeft() + (xOffset / 2);
        paddingRight = this.getPaddingRight() + (xOffset / 2);

        int width  = getWidth();
        int height = getHeight();

        outerCircle = new RectF(paddingLeft + circleWidth / 2, paddingTop + circleWidth / 2,
                width - paddingRight - circleWidth / 2, height - paddingBottom - circleWidth / 2);

        middleCircle = new RectF(outerCircle.left + (circleWidth / 2.0f) + dividerWidth, outerCircle.top + (circleWidth / 2.0f) + dividerWidth,
                outerCircle.right - (circleWidth / 2.0f) - dividerWidth, outerCircle.bottom - (circleWidth / 2.0f) - dividerWidth);

        innerCircle = new RectF(middleCircle.left + (circleWidth / 2.0f) + dividerWidth, middleCircle.top + (circleWidth / 2.0f) + dividerWidth,
                middleCircle.right - (circleWidth / 2.0f) - dividerWidth, middleCircle.bottom - (circleWidth / 2.0f) - dividerWidth);

        //初始化文字方向的路径
        float mx = innerCircle.width() / 2;
        float my = innerCircle.height() / 2;
        //45为倾斜的度数，需要再优化
        float startX = (float) (getWidth() / 2 - mx * Math.cos(Math.toRadians(degrees)));
        float startY = (float) (getHeight() / 2 + my * Math.sin(Math.toRadians(degrees)));

        float endX = (float) (getWidth() / 2 + mx * Math.cos(Math.toRadians(degrees)));
        float endY = (float) (getHeight() / 2 - my * Math.sin(Math.toRadians(degrees)));

        path.moveTo(startX, startY);
        path.lineTo(endX, endY);
        path.lineTo(startX, startY);
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param a
     *         the attributes to parse
     */
    private void parseAttributes(TypedArray a) {
        circleWidth = (int) a.getDimension(R.styleable.StampView_circleWidth, circleWidth);
        dividerWidth = (int) a.getDimension(R.styleable.StampView_dividerWidth, dividerWidth);

        // Only set the text if it is explicitly defined
        if (a.hasValue(R.styleable.StampView_text)) {
            setText(a.getString(R.styleable.StampView_text));
        }

        textColor = a.getColor(R.styleable.StampView_textColor, textColor);
        circleColor = a.getColor(R.styleable.StampView_circleColor, circleColor);
        textSize = (int) a.getDimension(R.styleable.StampView_stampViewtextSize, textSize);
        degrees = a.getFloat(R.styleable.StampView_degrees, degrees);
        textSpace = (int) a.getDimension(R.styleable.StampView_textSpace, textSpace);
        translate = (int) a.getDimension(R.styleable.StampView_translate, translate);

        this.setPaddingTop(super.getPaddingTop());
        this.setPaddingLeft(super.getPaddingLeft());
        this.setPaddingRight(super.getPaddingRight());
        this.setPaddingBottom(super.getPaddingBottom());
        a.recycle();
    }

    //文字的适配还有问题，需要再调
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(translate, translate);

        canvas.drawArc(innerCircle, 360, 360, true, circlePaint);
        canvas.drawArc(outerCircle, 360, 360, true, circlePaint);
        canvas.drawArc(middleCircle, 360, 360, true, middlePaint);

        // Draw the text (attempts to center it horizontally and vertically)
        float textHeight         = textPaint.descent() - textPaint.ascent();
        float verticalTextOffset = (textHeight / 2) - textPaint.descent();
        float start              = verticalTextOffset * 3 - (((textHeight * splitText.length + (splitText.length - 1) * textSpace * 1f))) / 2;

        for (int i = 0; i < splitText.length; i++) {
            float horizontalTextOffset = textPaint.measureText(splitText[i]) / 2;
            canvas.drawTextOnPath(splitText[i], path, innerCircle.width() / 2 - horizontalTextOffset, start + ((textHeight + textSpace) * i), textPaint);
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    public void setText(String text) {
        this.text = text;
        splitText = this.text.split("\n");
        postInvalidate();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;

        if (this.textPaint != null) {
            this.textPaint.setTextSize(this.textSize);
        }
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;

        if (this.circlePaint != null) {
            this.circlePaint.setColor(this.circleColor);
        }
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;

        if (this.textPaint != null) {
            this.textPaint.setColor(this.textColor);
        }
    }
}