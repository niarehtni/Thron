package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.content.Intent;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.product.activity.ImageDisplayAct;
import com.rd.zhongqipiaoetong.module.product.activity.ProductInfoAct;
import com.rd.zhongqipiaoetong.module.product.model.ProductInfoMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.view.DividerLine;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/2/25 17:46
 * <p/>
 * Description:借款资料VM({@link ProductInfoAct})
 */
public class BorrowerInfoListVM extends BaseRecyclerViewVM<ProductInfoMo.ImageList>{

    private List<ProductInfoMo.ImageList> list;

    public BorrowerInfoListVM() {
        type = DividerLine.DEFAULT;
    }

    @Override
    protected void selectView(ItemView itemView, final int position, ProductInfoMo.ImageList item) {
        itemView.set(BR.item, R.layout.product_borrower_info_list_item).setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent = new Intent();
                intent.putExtra("position",i);
                ArrayList<String> imagesList = new ArrayList<String>();
                for (ProductInfoMo.ImageList imageList:list){
                    imagesList.add(imageList.getImageUrl());
                }
                intent.putExtra("list",imagesList);
                ActivityUtils.push(ImageDisplayAct.class,intent);
            }
        });
    }

    public void setImages(List<ProductInfoMo.ImageList> list){
        emptyState.setLoading(false);
        if(list == null || list.size() == 0){
            emptyState.setPrompt(R.string.empty_record);
            return;
        }
        this.list = list;
        items.addAll(list);

    }
}