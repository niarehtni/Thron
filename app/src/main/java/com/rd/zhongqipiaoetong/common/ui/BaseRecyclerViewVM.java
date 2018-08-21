package com.rd.zhongqipiaoetong.common.ui;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.product.activity.NewProductAct;
import com.rd.zhongqipiaoetong.module.product.model.ExpProductMo;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.view.DividerLine;
import com.rd.zhongqipiaoetong.view.entity.EmptyState;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/3 10:45
 * <p/>
 * Description: 抽象的共用RecyclerView
 */
public abstract class BaseRecyclerViewVM<T> {
    /**
     * 设置布局
     */
    protected abstract void selectView(ItemView itemView, int position, T item);

    /**
     * 是否显示appbar
     */
    public final ObservableBoolean hidden = new ObservableBoolean(true);
    /**
     * 是否显示tiyanbiao
     */
    public final ObservableBoolean expHidden = new ObservableBoolean(false);
    /**
     * 空态对象
     */
    public final EmptyState emptyState = new EmptyState();
    /**
     * tips栏目
     */
    public String[] tips = null;
    /**
     * 分割线类型
     * -1 - 没有分割线
     * 0 - 水平分割线
     * 1 - 垂直分割线
     */
    public int type = DividerLine.HORIZONTAL;
    public ObservableField<ExpProductMo.ExpProduct> info = new ObservableField<>();
    public ObservableInt counts = new ObservableInt(1);
    /**
     * 数据源
     */
    public final ObservableList<T> items = new ObservableArrayList<>();
    public final ItemViewSelector<T> itemView = new ItemViewSelector<T>() {
        @Override
        public void select(ItemView itemView, int position, T item) {
            selectView(itemView, position, item);
        }

        @Override
        public int viewTypeCount() {
            return 0;
        }
    };

    /**
     * 刷新控件回调
     */
    public final ObservableField<PtrFrameListener> listener = new ObservableField<>();
    /**
     * 刷新控件对象
     */
    protected PtrClassicFrameLayout ptrFrame;

    public PtrClassicFrameLayout getPtrFrame() {
        return ptrFrame;
    }

    protected void setPtrFrame(PtrClassicFrameLayout ptrFrame) {
        this.ptrFrame = ptrFrame;
    }

    public void expClick(View view) {
        if (MyApplication.getInstance().isLand()) {
            Intent intent = new Intent();
            intent.putExtra(BundleKeys.ID, info.get().getId());
            ActivityUtils.push(NewProductAct.class, intent);
        } else {
            ActivityUtils.push(LoginAct.class);
        }
    }
}