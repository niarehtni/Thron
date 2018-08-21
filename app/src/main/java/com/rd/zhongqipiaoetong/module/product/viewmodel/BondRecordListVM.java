package com.rd.zhongqipiaoetong.module.product.viewmodel;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.product.model.BondRecordMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.network.entity.ListMo;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Description:转让记录VM({@link BondRecordListVM})
 */
public class BondRecordListVM extends BaseRecyclerViewVM<BondRecordMo> {
    private int page = 0;
    private String  id;//标id
    private String  uuid;

    @Override
    protected void selectView(ItemView itemView, int position, BondRecordMo item) {
        itemView.set(BR.item, R.layout.product_bond_record_list_item);
    }

    public BondRecordListVM(String id, String uuid, String[] tips) {
        this.id = id;
        this.uuid = uuid;
        this.tips = tips;
        hidden.set(false);
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
     * 普通标的投标记录
     */
    public void req_data(final PtrFrameLayout ptrFrame) {
        page++;
        Call<ListMo<BondRecordMo>> call;
        call = RDClient.getService(ProductService.class).bondRecordList(id, uuid, page);
        call.enqueue(new RequestCallBack<ListMo<BondRecordMo>>(ptrFrame) {
            @Override
            public void onSuccess(Call<ListMo<BondRecordMo>> call, Response<ListMo<BondRecordMo>> response) {
                if (response.body().getList() == null)
                    return;

                if (emptyState.isLoading())
                    emptyState.setLoading(false);

                if (page == 1)
                    items.clear();
                items.addAll(response.body().getList());

                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_invest_bond);
                    return;
                }
                response.body().isOver(ptrFrame);
            }
        });
    }
}