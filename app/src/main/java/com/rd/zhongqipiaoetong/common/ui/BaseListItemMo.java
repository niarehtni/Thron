package com.rd.zhongqipiaoetong.common.ui;

import android.view.View;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/7 12:03
 * <p/>
 * Description: {@link BaseRecyclerViewVM}
 */
public class BaseListItemMo {
    /**
     * 0 - 常规item
     * 1 - 带指向箭头的item
     */
    private int                  type;
    /**
     * item描述
     */
    private String               desc;
    /**
     * item内容
     */
    private String               content;
    /**
     * item点击事件
     */
    private View.OnClickListener listener;
    /**
     * 上划线长度
     * 0长（不添加marginleft）
     * 1短（添加marginleft）
     */
    private int                  upDriveType;
    /**
     * 下划线是否显示
     * 0不显示
     * 1显示
     */
    private int                  downDriveType;
    /**
     * 是否上间隔
     */
    private boolean              isUpSpace;
    /**
     * 是否下间隔
     */
    private boolean              isDownSpace;

    public BaseListItemMo() {
    }

    public BaseListItemMo(int type, String desc, String content) {
        this.type = type;
        this.desc = desc;
        this.content = content;
    }

    public BaseListItemMo(int type, String desc, String content, View.OnClickListener listener) {
        this.type = type;
        this.desc = desc;
        this.content = content;
        this.listener = listener;
    }

    public BaseListItemMo(int type, String desc, String content, int upDriveType, int downDriveType, boolean isUpSpace, boolean isDownSpace, View
            .OnClickListener listener) {
        this.type = type;
        this.desc = desc;
        this.content = content;
        this.listener = listener;
        this.downDriveType = downDriveType;
        this.upDriveType = upDriveType;
        this.isUpSpace = isUpSpace;
        this.isDownSpace = isDownSpace;
    }

    public BaseListItemMo(int type, String desc, String content, int upDriveType, int downDriveType, boolean isUpSpace, boolean isDownSpace) {
        this.type = type;
        this.desc = desc;
        this.content = content;
        this.downDriveType = downDriveType;
        this.upDriveType = upDriveType;
        this.isUpSpace = isUpSpace;
        this.isDownSpace = isDownSpace;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public int getUpDriveType() {
        return upDriveType;
    }

    public void setUpDriveType(int upDriveType) {
        this.upDriveType = upDriveType;
    }

    public int getDownDriveType() {
        return downDriveType;
    }

    public void setDownDriveType(int downDriveType) {
        this.downDriveType = downDriveType;
    }

    public boolean isUpSpace() {
        return isUpSpace;
    }

    public void setIsUpSpace(boolean isUpSpace) {
        this.isUpSpace = isUpSpace;
    }

    public boolean isDownSpace() {
        return isDownSpace;
    }

    public void setIsDownSpace(boolean isDownSpace) {
        this.isDownSpace = isDownSpace;
    }
}