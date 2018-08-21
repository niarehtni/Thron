package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableField;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.databinding.AccountJiekuanguanliActBinding;
import com.rd.zhongqipiaoetong.module.account.model.JieKuanManageMo;
import com.rd.zhongqipiaoetong.module.account.model.JieKuanMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
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
 * Created by pzw on 2018/1/19.
 */
public class JieKuanManageVM extends BaseRecyclerViewVM<JieKuanMo> {
    public int lineType = DividerLine.DEFAULT;
    int page = 0;
    public ObservableField<JieKuanManageMo> model = new ObservableField<JieKuanManageMo>();

    @Override
    protected void selectView(ItemView itemView, final int position, final JieKuanMo item) {
        itemView.set(BR.item, R.layout.account_jiekuanjihua_item);
    }

    public JieKuanManageVM(AccountJiekuanguanliActBinding binding) {
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
                reqData(ptrFrame);
            }
        });
        reqData(binding.ptrAll);
    }

    private void reqData(final PtrFrameLayout ptrFrame) {
        page++;
        Call<JieKuanManageMo> call = RDClient.getService(AccountService.class).getBorrows(page);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<JieKuanManageMo>(ptrFrame) {
            @Override
            public void onSuccess(Call<JieKuanManageMo> call, Response<JieKuanManageMo> response) {
                if (response.body() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }
                model.set(response.body());

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }

                items.addAll(response.body().getBorrowList());

                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_loan);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }
}
