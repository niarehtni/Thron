package com.rd.zhongqipiaoetong.module.account.viewmodel;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.fragment.InvestmentFinancingFrag;
import com.rd.zhongqipiaoetong.module.product.model.FlowTenderMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.LogService;
import com.rd.zhongqipiaoetong.network.entity.ListMo;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/29 10:38
 * <p/>
 * Description: 投资记录-pager VM ({@link InvestmentFinancingFrag})
 */
public class FlowFinancingVM extends BaseRecyclerViewVM<Object> {
    private int page = 0;

    @Override
    protected void selectView(ItemView itemView, int position, Object item) {
        itemView.set(BR.item, R.layout.account_flow_record_item).setVariable(BR.item, item);
    }

    public FlowFinancingVM(String[] tips) {

        this.tips = tips;

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
        req_invest(ptrFrame);
    }

    /**
     * 流转理财
     *
     * @param ptrFrame
     */
    public void req_invest(final PtrFrameLayout ptrFrame) {
        Call<ListMo<FlowTenderMo>> call = RDClient.getService(LogService.class).flowInvestRecordList(page);
        call.enqueue(new RequestCallBack<ListMo<FlowTenderMo>>(ptrFrame) {
            @Override
            public void onSuccess(Call<ListMo<FlowTenderMo>> call, Response<ListMo<FlowTenderMo>> response) {
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
                    emptyState.setPrompt(R.string.empty_invest);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }
}
