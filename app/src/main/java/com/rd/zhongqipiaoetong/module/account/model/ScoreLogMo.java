package com.rd.zhongqipiaoetong.module.account.model;

import android.view.View;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ScoreLogMo {
    /**
     * 时间
     */
    private long   addTime;
    /**
     * 积分记录详情
     */
    private String remark;
    /**
     * 积分数量
     */
    private String score;
    /**
     * 积分类型名称
     */
    private String scoreTypeName;
    /**
     * 第一条线是否可见
     */
    private int firstLineVisible  = View.VISIBLE;
    /**
     * 第二条线是否可见
     */
    private int secondLineVisible = View.VISIBLE;

    //--------------------------华丽分割线----------------------------------

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreTypeName() {
        return scoreTypeName;
    }

    public void setScoreTypeName(String scoreTypeName) {
        this.scoreTypeName = scoreTypeName;
    }

    public int isFirstLineVisible() {
        return firstLineVisible;
    }

    public void setFirstLineVisible(int firstLineVisible) {
        this.firstLineVisible = firstLineVisible;
    }

    public int isSecondLineVisible() {
        return secondLineVisible;
    }

    public void setSecondLineVisible(int secondLineVisible) {
        this.secondLineVisible = secondLineVisible;
    }
}
