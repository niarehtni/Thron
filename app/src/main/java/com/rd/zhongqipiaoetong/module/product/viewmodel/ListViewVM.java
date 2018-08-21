package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.rd.zhongqipiaoetong.BR;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/8 14:18
 * <p/>
 * Description:
 */
public abstract class ListViewVM<T> {
    public final ObservableList<T> items    = new ObservableArrayList<>();
    public final ItemView          itemView = ItemView.of(getBindingVariable(), getLayoutRes());

    //ListView item_view ID
    public abstract int getLayoutRes();

    public int getBindingVariable() {
        return BR.info;
    }
}