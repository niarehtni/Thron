package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.model.ScoreLogListMo;
import com.rd.zhongqipiaoetong.module.account.model.ScoreLogMo;
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
 * Description:积分记录
 */
public class ScoreLogVM extends BaseRecyclerViewVM<ScoreLogMo> {
    private int                             page = 0;
    public  ObservableField<ScoreLogListMo> mo   = new ObservableField<ScoreLogListMo>();

    @Override
    protected void selectView(ItemView itemView, int position, ScoreLogMo item) {

        if (position == 0) {
            item.setFirstLineVisible(View.INVISIBLE);
            item.setSecondLineVisible(View.VISIBLE);
        } else if (page == mo.get().getScoreLogList().getPageCount() && position == items.size() - 1) {
            item.setFirstLineVisible(View.VISIBLE);
            item.setSecondLineVisible(View.INVISIBLE);
        } else {
            item.setFirstLineVisible(View.VISIBLE);
            item.setSecondLineVisible(View.VISIBLE);
        }
        itemView.set(BR.mo, R.layout.account_score_log_item).setVariable(BR.mo, item);
    }

    public ScoreLogVM() {
        type = -1;
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
        Call<ScoreLogListMo> call = RDClient.getService(LogService.class).scoreLogList(page);
        call.enqueue(new RequestCallBack<ScoreLogListMo>(ptrFrame) {
            @Override
            public void onSuccess(Call<ScoreLogListMo> call, Response<ScoreLogListMo> response) {
                mo.set(response.body());
                if (response.body().getScoreLogList().getList() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }
                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }
                items.addAll(response.body().getScoreLogList().getList());

                // 分页处理，最后一页时，禁止加载更多
                response.body().getScoreLogList().isOver(ptrFrame);
            }
        });
    }
}
