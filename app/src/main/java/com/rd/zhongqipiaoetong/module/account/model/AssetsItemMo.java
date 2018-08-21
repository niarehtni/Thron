package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.module.account.viewmodel.MyAssetsVM;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/14 17:38
 * <p/>
 * Description: 账户总览({@link MyAssetsVM }) - 资产列表Mo
 */
public class AssetsItemMo {
    /**
     * HEADER = 0 - 饼状图view
     * GROUP  = 1 - list的一级菜单
     * CHILD  = 2 - list的二级菜单
     */
    private int                type;
    /**
     * 一级菜单色块色值
     */
    private int                color;
    /**
     * 菜单项标题
     */
    private String             title;
    /**
     * 菜单项金额
     */
    private String             money;
    /**
     * 二次子项
     */
    private List<AssetsItemMo> child;

    public AssetsItemMo() {
    }

    public AssetsItemMo(int type, int color, String title, String money, List<AssetsItemMo> child) {
        this.type = type;
        this.color = color;
        this.title = title;
        this.money = money;
        this.child = child;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<AssetsItemMo> getChild() {
        return child;
    }

    public void setChild(List<AssetsItemMo> child) {
        this.child = child;
    }
}