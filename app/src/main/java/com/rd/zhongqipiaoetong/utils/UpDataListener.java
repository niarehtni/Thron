package com.rd.zhongqipiaoetong.utils;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/31/16.
 * 数据更新通知
 */
public interface UpDataListener<T> {
    void updata(int type, T o);
}
