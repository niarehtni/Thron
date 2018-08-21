package com.rd.zhongqipiaoetong.module.product.viewmodel;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.product.activity.InvestListAct;
import com.rd.zhongqipiaoetong.module.product.model.FlowTenderMo;
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
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/2/25 11:11
 * <p/>
 * Description:投资记录/转让记录VM({@link InvestListAct})
 */
public class FlowInvestRecordListVM extends BaseRecyclerViewVM<FlowTenderMo> {
    private int page = 0;
    private long  id;//标id

    @Override
    protected void selectView(ItemView itemView, int position, FlowTenderMo item) {
        itemView.set(BR.item, R.layout.product_flow_invest_record_list_item);
    }

    public FlowInvestRecordListVM(long id, String[] tips) {
        this.id = id;
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
     * 流转标的投标记录
     */
    public void req_data(final PtrFrameLayout ptrFrame) {
        page++;
        Call<ListMo<FlowTenderMo>> call;
        call = RDClient.getService(ProductService.class).flowInvestRecordList(String.valueOf(id), page);

        call.enqueue(new RequestCallBack<ListMo<FlowTenderMo>>(ptrFrame) {
            @Override
            public void onSuccess(Call<ListMo<FlowTenderMo>> call, Response<ListMo<FlowTenderMo>> response) {
                if (response.body().getList() == null)
                    return;

                if (emptyState.isLoading())
                    emptyState.setLoading(false);

                if (page == 1)
                    items.clear();

                items.addAll(response.body().getList());

                response.body().isOver(ptrFrame);
            }
        });
    }
}
