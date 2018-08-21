package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.content.Intent;
import android.view.View;

import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.product.activity.BondDetailAct;
import com.rd.zhongqipiaoetong.module.product.model.CreditorMo;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.network.entity.ListMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.view.DividerLine;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/8 14:14
 * <p/>
 * Description:债券转让列表VM
 */
public class CreditorListVM extends BaseRecyclerViewVM<CreditorMo> {
    @Override
    protected void selectView(ItemView itemView,  int position, final CreditorMo item) {
        itemView.set(BR.info, R.layout.product_creditor_list_item)
                .setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if (!MyApplication.getInstance().isLand()) {
                    ActivityUtils.push(LoginAct.class);
                    return;
                }
                Intent     intent = new Intent();
                CreditorMo mo     = items.get(i);
                intent.putExtra(BundleKeys.ID, mo.getId());
                intent.putExtra(BundleKeys.UUID, mo.getUuid());
                intent.putExtra(BundleKeys.NAME, mo.getBorrowName());
                ActivityUtils.push(BondDetailAct.class, intent);
            }
        });
    }

    private int page = 0;

    public CreditorListVM() {
        type = DividerLine.CROSS;
        listener.set(new PtrFrameListener() {
            @Override
            public void ptrFrameInit(PtrClassicFrameLayout ptrFrame) {
                setPtrFrame(ptrFrame);
            }

            @Override
            public void ptrFrameRefresh() {
                page = 0;
            }

            @Override
            public void ptrFrameRequest(PtrFrameLayout ptrFrame) {
                req_data(ptrFrame);
            }
        });
    }

    /**
     * 网络请求
     */
    public void req_data(final PtrFrameLayout ptrFrame) {
        page++;
        Call<ListMo<CreditorMo>> call = RDClient.getService(ProductService.class).bondList(page);
        call.enqueue(new RequestCallBack<ListMo<CreditorMo>>(ptrFrame) {
            @Override
            public void onSuccess(Call<ListMo<CreditorMo>> call, Response<ListMo<CreditorMo>> response) {
                if (response.body().getList() == null) {
                    return;
                }
                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }
                items.addAll(response.body().getList());
                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_product);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }
}