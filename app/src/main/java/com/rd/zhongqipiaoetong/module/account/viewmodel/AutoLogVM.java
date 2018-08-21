package com.rd.zhongqipiaoetong.module.account.viewmodel;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.model.AutoLogMo;
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
 * Date: 16/7/27 下午8:43
 * <p/>
 * Description:
 */
public class AutoLogVM extends BaseRecyclerViewVM<Object> {
    private int page = 0;

    @Override
    protected void selectView(ItemView itemView, int position, Object item) {
        itemView.set(BR.item, R.layout.auto_log_item).setVariable(BR.item, item);
    }

    public AutoLogVM() {
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
     * 自动投标记录
     *
     * @param ptrFrame
     */
    public void req_invest(final PtrFrameLayout ptrFrame) {
        Call<ListMo<AutoLogMo>> call = RDClient.getService(LogService.class).autoTenderList(page);
        call.enqueue(new RequestCallBack<ListMo<AutoLogMo>>(ptrFrame) {
            @Override
            public void onSuccess(Call<ListMo<AutoLogMo>> call, Response<ListMo<AutoLogMo>> response) {
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


