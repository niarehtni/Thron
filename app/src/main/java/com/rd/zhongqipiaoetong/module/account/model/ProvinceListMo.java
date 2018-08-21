package com.rd.zhongqipiaoetong.module.account.model;

import com.google.gson.annotations.SerializedName;
import com.rd.zhongqipiaoetong.network.entity.AreaMo;
import com.rd.zhongqipiaoetong.network.entity.ProvinceMo;

import java.util.ArrayList;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/20/16.
 */
public class ProvinceListMo extends AreaMo<ProvinceMo> {
    @SerializedName("province")
    private ArrayList<ProvinceMo> province;

    public ArrayList<ProvinceMo> getProvince() {
        return province;
    }

    public void setProvince(ArrayList<ProvinceMo> province) {
        this.province = province;
    }

    @Override
    public ArrayList<ProvinceMo> getArrayList() {
        return province;
    }

    /**
     * 获取第二层ArrayList
     * 市
     *
     * @return
     */
    public ArrayList<ArrayList<AreaMo>> getCityList() {
        ArrayList<ArrayList<AreaMo>> provinceMoArrayList = new ArrayList<>();
        for (ProvinceMo provinceMo : province) {
            provinceMoArrayList.add(setAreaMoArrayList(provinceMo));
        }
        return provinceMoArrayList;
    }

    /**
     * 获取第三层ArrayList
     * 县、区
     *
     * @return
     */
    public ArrayList<ArrayList<ArrayList<AreaMo>>> getCountyList() {
        ArrayList<ArrayList<ArrayList<AreaMo>>> provinceMoArrayList = new ArrayList<>();
        for (ProvinceMo provinceMo : province) {
            provinceMoArrayList.add(setCountyArrayList(provinceMo));
        }
        return provinceMoArrayList;
    }

    /**
     * 市  set to  ArrayList
     * 包含两级
     *
     * @param cityList
     *
     * @return
     */
    private ArrayList<ArrayList<AreaMo>> setCountyArrayList(AreaMo<? extends AreaMo> cityList) {
        ArrayList<ArrayList<AreaMo>> countyArrayList = new ArrayList<>();
        if (cityList.getArrayList() != null) {
            for (AreaMo areaMo : cityList.getArrayList()) {
                countyArrayList.add(setAreaMoArrayList(areaMo));
            }
        } else {
            ArrayList<AreaMo> aa = new ArrayList<>();
            aa.add(cityList);
            countyArrayList.add(aa);
        }
        return countyArrayList;
    }

    /**
     * 县、区  set to  ArrayList
     * 只包含一级
     *
     * @param countyList
     *
     * @return
     */
    private ArrayList<AreaMo> setAreaMoArrayList(AreaMo<? extends AreaMo> countyList) {
        ArrayList<AreaMo> areaMoArrayList = new ArrayList<>();
        if (countyList.getArrayList() != null) {
            areaMoArrayList.addAll(countyList.getArrayList());
        } else {
            areaMoArrayList.add(countyList);
        }
        return areaMoArrayList;
    }
}
