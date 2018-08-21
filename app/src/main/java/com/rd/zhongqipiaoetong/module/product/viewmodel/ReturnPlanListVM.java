package com.rd.zhongqipiaoetong.module.product.viewmodel;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.product.activity.ReturnPlanListAct;
import com.rd.zhongqipiaoetong.module.product.model.ReturnPlanMo;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/1 11:15
 * <p/>
 * Description:还款列表VM({@link ReturnPlanListAct})
 */
public class ReturnPlanListVM extends BaseRecyclerViewVM<ReturnPlanMo> {
    @Override
    protected void selectView(ItemView itemView, int position, ReturnPlanMo item) {
        itemView.set(BR.item, R.layout.product_return_plan_list_item);
    }

    public ReturnPlanListVM() {
        hidden.set(false);
        req_data();
    }

    /**
     * 网络请求
     */
    private void req_data() {
        items.add(new ReturnPlanMo("5", "1", "1"));
        items.add(new ReturnPlanMo("5", "1", "2"));
        items.add(new ReturnPlanMo("5", "1", "3"));
        items.add(new ReturnPlanMo("5", "0", "4"));
        items.add(new ReturnPlanMo("5", "0", "5"));
    }
}