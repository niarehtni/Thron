package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.model.UpRateMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

import java.util.ArrayList;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Description:选择加息券的vm
 */

public class ChoiceUpVM extends BaseRecyclerViewVM<UpRateMo> {
    private ObservableArrayList<UpRateMo> upMoArrayList = new ObservableArrayList<>();
    //可选加息券
    public String upNum;
    //选择加息券的数量
    public ObservableField<String> upChoice = new ObservableField<>();
    //选中的加息券id
    private String upId;
    private String upRate       = "0";
    private int    lastPosition = -1;

    public ChoiceUpVM(ArrayList<UpRateMo> upMoList, String upId) {
        this.upId = upId;
        upMoArrayList.addAll(upMoList);
        upNum = String.valueOf(upMoArrayList.size());
        upChoice.set("0");

        for (int i = 0; i < upMoArrayList.size(); i++) {
            UpRateMo mo = upMoArrayList.get(i);
            if (mo.getId().equals(upId)) {
                mo.setIsCheck(true);
                lastPosition = i;
                upRate = mo.getUpRate();
                upChoice.set("1");
            } else {
                mo.setIsCheck(false);
            }
        }

        items.addAll(upMoArrayList);
    }

    @Override
    protected void selectView(ItemView itemView, int position, final UpRateMo item) {
        itemView.set(BR.item, R.layout.account_my_uprate_item);
        itemView.setVariable(BR.isShow, true);

        //填充map数据
        itemView.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                UpRateMo mo = upMoArrayList.get(i);
                if (mo.getIsCheck()) {
                    mo.setIsCheck(false);
                    upId = "";
                    upRate = "0";
                    upChoice.set("0");
                    lastPosition = -1;
                } else {
                    mo.setIsCheck(true);
                    upId = mo.getId();
                    upChoice.set("1");
                    if (lastPosition != -1)
                        upMoArrayList.get(lastPosition).setIsCheck(false);
                    lastPosition = i;
                    upRate = upMoArrayList.get(lastPosition).getUpRate();
                }
            }
        });
    }

    //确定按钮
    public void sureChoice(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.UPID, upId);
        intent.putExtra(BundleKeys.UPRATE, upRate);
        ActivityUtils.pop(ActivityUtils.peek(), intent);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 数据填装和拆解
    ///////////////////////////////////////////////////////////////////////////
}