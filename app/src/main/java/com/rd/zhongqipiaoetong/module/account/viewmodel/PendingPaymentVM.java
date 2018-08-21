package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.util.Log;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.fragment.PendingPaymentFrag;
import com.rd.zhongqipiaoetong.module.account.model.PendingPaymentMo;
import com.rd.zhongqipiaoetong.module.account.model.PendingPaymentMoList;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.LogService;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/8 14:16
 * <p/>
 * Description: 回款记录({@link PendingPaymentFrag})VM 待回款
 */
public class PendingPaymentVM extends BaseRecyclerViewVM<PendingPaymentMo> {
    @Override
    protected void selectView(ItemView itemView, int position, PendingPaymentMo item) {
        Log.e("status", status + "");
        if (status == 0) {
            itemView.set(BR.item, R.layout.account_pending_payment_item);
        } else {
            itemView.set(BR.item, R.layout.account_pending_payment_repaid_item);
        }
    }

    private int page = 0;
    //还款状态  0未还款 1已还款
    private int status = 0;

    public PendingPaymentVM(String[] tip, int status) {
        this.tips = tip;
        this.status = status;
        Log.e("run here", status + "");
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

    public void req_data(final PtrFrameLayout ptrFrame) {
        page++;
        Call<PendingPaymentMoList> call = RDClient.getService(LogService.class).investRepayPlan(page, status);
        call.enqueue(new RequestCallBack<PendingPaymentMoList>(ptrFrame) {
            @Override
            public void onSuccess(Call<PendingPaymentMoList> call, Response<PendingPaymentMoList> response) {
                if (response.body().getReturnList() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }
                items.addAll(response.body().getReturnList());
                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_repayment);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }
}