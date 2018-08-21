package com.rd.zhongqipiaoetong.module.account.viewmodel;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.databinding.AccountRepatmentListActBinding;
import com.rd.zhongqipiaoetong.module.account.model.HuanKuanListMo;
import com.rd.zhongqipiaoetong.module.account.model.HuanKuanMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.view.DividerLine;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by pzw on 2018/1/22.
 */
public class HuanKuanVM extends BaseRecyclerViewVM<HuanKuanMo> {
    public int lineType = DividerLine.DEFAULT;
    int page = 0;
    AccountRepatmentListActBinding binding;

    @Override
    protected void selectView(ItemView itemView, int position, HuanKuanMo item) {
        itemView.set(BR.item, R.layout.account_huankuan_item);
    }

    public HuanKuanVM(AccountRepatmentListActBinding binding) {
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
                reqData(ptrFrame);
            }
        });
        reqData(binding.ptrAll);
    }

    private void reqData(final PtrFrameLayout ptrFrame){
        page++;
        Call<HuanKuanListMo> call = RDClient.getService(AccountService.class).getRepayments(page);
        call.enqueue(new RequestCallBack<HuanKuanListMo>(ptrFrame) {
            @Override
            public void onSuccess(Call<HuanKuanListMo> call, Response<HuanKuanListMo> response) {
                if (response.body() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }

                items.addAll(response.body().getBorrowRepaymentList());

                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_repay);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }
}
