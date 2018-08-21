package com.rd.zhongqipiaoetong.view.entity;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/11 16:16
 * <p/>
 * Description: 圆弧
 */
public class PieArc {
    /**
     * 颜色
     */
    private int   color;
    /**
     * 百分比
     */
    private float percent;

    public PieArc() {
    }

    public PieArc(int color, float percent) {
        this.color = color;
        this.percent = percent;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}