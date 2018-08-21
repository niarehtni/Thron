package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.module.product.model.AuthenticationMo;
import com.rd.zhongqipiaoetong.module.product.model.BorrowerDetailMo;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/2 11:15
 * <p/>
 * Description:借款方详情VM
 */
public class BorrowerDetailVM {
    public final ObservableField<BorrowerDetailMo>     detail     = new ObservableField<>();
    public final ObservableArrayList<AuthenticationMo> items    = new ObservableArrayList<>();
    /**
     * ItemView of a single type
     */
    public final ItemView                              itemView = ItemView.of(BR.authentication_info, R.layout.product_authen_list_item);
}