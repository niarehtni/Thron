package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.activity.DoTransferAct;
import com.rd.zhongqipiaoetong.module.account.fragment.CanTransferListFrag;
import com.rd.zhongqipiaoetong.module.account.model.TransferListMo;
import com.rd.zhongqipiaoetong.module.account.model.TransferMo;
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
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/21 下午3:20
 * <p/>
 * Description: 可转让列表控制器({@link CanTransferListFrag})
 */
public class CanTransferVM extends BaseRecyclerViewVM<TransferMo> {
    private int page = 0;
    private Fragment       fragment;
    private TransferListMo mo;

    @Override
    protected void selectView(ItemView itemView, int position, TransferMo item) {
        itemView.set(BR.transferMo, R.layout.account_can_transfer_list_item).setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent = new Intent(fragment.getActivity(),DoTransferAct.class);
                intent.putExtra(BundleKeys.FEETYPE,mo.getFeeType());
                intent.putExtra(BundleKeys.TRANSFERMO, items.get(i));
                intent.putExtra(BundleKeys.DISCOUNTRATEMIN,mo.getDiscountRateMin());
                intent.putExtra(BundleKeys.DISCOUNTRATEMAX,mo.getDiscountRateMax());
                intent.putExtra(BundleKeys.SELLFEE,mo.getSellFee());
                fragment.startActivityForResult(intent,BundleKeys.REQUEST_CODE_DOTRANSFER);
            }
        });
    }

    public CanTransferVM(Fragment fragment) {
        this.fragment = fragment;
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
        Call<TransferListMo> call = RDClient.getService(LogService.class).saleableBondList(page);
        call.enqueue(new RequestCallBack<TransferListMo>(ptrFrame) {
            @Override
            public void onSuccess(Call<TransferListMo> call, Response<TransferListMo> response) {
                if (response.body().getAssigmentList() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }
                mo = response.body();
                items.addAll(response.body().getAssigmentList());
                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_record);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }
}