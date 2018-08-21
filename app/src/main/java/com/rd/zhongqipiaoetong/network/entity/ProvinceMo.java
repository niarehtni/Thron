package com.rd.zhongqipiaoetong.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/20/16.
 */
public class ProvinceMo extends AreaMo<CityMo> {
    @SerializedName("city")
    private ArrayList<CityMo> city;

    public ArrayList<CityMo> getCity() {
        return city;
    }

    public void setCity(ArrayList<CityMo> city) {
        this.city = city;
    }

    @Override
    public ArrayList<CityMo> getArrayList() {
        return city;
    }
}
