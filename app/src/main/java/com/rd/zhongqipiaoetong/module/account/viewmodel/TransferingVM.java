package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.activity.TransferDetailAct;
import com.rd.zhongqipiaoetong.module.account.model.TransferListMo;
import com.rd.zhongqipiaoetong.module.account.model.TransferMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.LogService;
import com.rd.zhongqipiaoetong.utils.UpDataListener;
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
 * <p/>
 * Description: 转让中列表控制器
 */
public class TransferingVM extends BaseRecyclerViewVM<TransferMo> {
    private int page = 0;
    private UpDataListener<Boolean> upDataListener;
    private Fragment                fragment;

    @Override
    protected void selectView(ItemView itemView, int position, TransferMo item) {
        itemView.set(BR.transferMo, R.layout.account_transfering_list_item).setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                TransferMo mo     = items.get(i);
                Intent        intent = new Intent(fragment.getActivity(), TransferDetailAct.class);
                intent.putExtra(BundleKeys.TRANSFERTYPE, Constant.NUMBER_1);
                intent.putExtra(BundleKeys.TRANSFERMO, mo);
                fragment.startActivityForResult(intent, BundleKeys.REQUEST_CODE_TRANSFERDETAIL);
            }
        });
    }

    public TransferingVM(Fragment fragment, UpDataListener<Boolean> upDataListener) {
        this.fragment = fragment;
        this.upDataListener = upDataListener;
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
        Call<TransferListMo> call = RDClient.getService(LogService.class).sellingBondList(page,1);
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
                for (TransferMo mo : response.body().getAssigmentList()) {
                    mo.setUpDataListener(upDataListener);
                    items.add(mo);
                }
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