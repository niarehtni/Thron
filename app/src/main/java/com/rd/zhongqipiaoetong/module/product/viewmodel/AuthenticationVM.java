package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.module.product.model.AuthenticationMo;
import com.rd.zhongqipiaoetong.module.product.model.BorrowerDetailMo;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/3 17:28
 * <p/>
 * Description:
 */
public class AuthenticationVM {
    public final ObservableList<AuthenticationMo> items    = new ObservableArrayList<>();
    /**
     * ItemView of a single type
     */
    public final ItemView                         itemView = ItemView.of(BR.authentication_info, R.layout.product_authen_list_item);

    public AuthenticationVM() {
        req_data();
    }

    private void req_data() {
        List<AuthenticationMo> list = new ArrayList<>();
        list.add(new AuthenticationMo("还款能力证明", "0", String.valueOf(System.currentTimeMillis())));
        list.add(new AuthenticationMo("信用报告", "1", String.valueOf(System.currentTimeMillis())));
        list.add(new AuthenticationMo("信用报告", "0", String.valueOf(System.currentTimeMillis())));
        BorrowerDetailMo mo = new BorrowerDetailMo("王大锤", "32", "本科狗", "0", "杭州", "3", "100000", "0", "0", list);
        items.addAll(mo.getList());
    }
}