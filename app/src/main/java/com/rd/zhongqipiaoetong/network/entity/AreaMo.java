package com.rd.zhongqipiaoetong.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/20/16.
 */
public class AreaMo<T> {
    @SerializedName("name")
    private String name;
    @SerializedName("areaCode")
    private String areaCode;
    @SerializedName("nid")
    private String nid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    private ArrayList<T> arrayList;

    public ArrayList<T> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<T> arrayList) {
        this.arrayList = arrayList;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return name;
    }
}
