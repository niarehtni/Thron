package com.rd.zhongqipiaoetong.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/20/16.
 */
public class CityMo extends AreaMo<AreaMo> {
    @SerializedName("county")
    private ArrayList<AreaMo> county;

    public ArrayList<AreaMo> getCounty() {
        return county;
    }

    public void setCounty(ArrayList<AreaMo> county) {
        this.county = county;
    }

    @Override
    public ArrayList<AreaMo> getArrayList() {
        return this.county;
    }
}
