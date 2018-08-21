package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.content.Intent;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.product.activity.FlowDetailAct;
import com.rd.zhongqipiaoetong.module.product.fragment.FlowListFrag;
import com.rd.zhongqipiaoetong.module.product.model.FlowDetailMo;
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
 * Date: 2016/2/24 11:16
 * <p/>
 * Description: 计息理财VM({@link FlowListFrag})
 */
public class FlowListVM extends BaseRecyclerViewVM<FlowDetailMo> {
    @Override
    protected void selectView(ItemView itemView, int position, FlowDetailMo item) {
        itemView.set(BR.info, R.layout.product_flow_list_item)
                .setOnItemClickListener(new ItemView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (!MyApplication.getInstance().isLand()) {
                            ActivityUtils.push(LoginAct.class);
                            return;
                        }
                        Intent intent = new Intent();
                        intent.putExtra(BundleKeys.UUID, items.get(position).getUuid());
                        intent.putExtra(BundleKeys.NAME, items.get(position).getName());
                        ActivityUtils.push(FlowDetailAct.class, intent);
                    }
                });
    }

    private int page = 0;

    public FlowListVM() {
        type = DividerLine.DEFAULT;

        listener.set(new PtrFrameListener() {
            @Override
            public void ptrFrameInit(PtrClassicFrameLayout ptrFrame) {
                setPtrFrame(ptrFrame);
            }

            @Override
            public void ptrFrameRefresh() {//刷新
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
        Call<ListMo<FlowDetailMo>> call = RDClient.getService(ProductService.class).flowList(page);
        call.enqueue(new RequestCallBack<ListMo<FlowDetailMo>>(ptrFrame) {
            @Override
            public void onSuccess(Call<ListMo<FlowDetailMo>> call, Response<ListMo<FlowDetailMo>> response) {
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
