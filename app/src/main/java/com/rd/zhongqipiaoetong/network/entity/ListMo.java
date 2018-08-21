package com.rd.zhongqipiaoetong.network.entity;

import java.util.List;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/19/16.
 */
public class ListMo<T> extends PageMo {
    private List<T> list;
    private List<T> assigmentList;
    private List<T> creditorList;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getAssigmentList() {
        return assigmentList;
    }

    public void setAssigmentList(List<T> assigmentList) {
        this.assigmentList = assigmentList;
    }

    public List<T> getCreditorList() {
        return creditorList;
    }

    public void setCreditorList(List<T> creditorList) {
        this.creditorList = creditorList;
    }
}
