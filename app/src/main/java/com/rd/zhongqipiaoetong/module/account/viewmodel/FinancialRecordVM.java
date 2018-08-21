package com.rd.zhongqipiaoetong.module.account.viewmodel;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.fragment.FinancialRecordFrag;
import com.rd.zhongqipiaoetong.module.account.model.RechargeRecodMoList;
import com.rd.zhongqipiaoetong.module.account.model.WithdrawRecodMoList;
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
 * Date: 2016/3/21 15:37
 * <p/>
 * Description: 充值提现记录的VM({@link FinancialRecordFrag})
 */
public class FinancialRecordVM extends BaseRecyclerViewVM<Object> {
    /**
     * 0 - 充值记录
     * 1 - 提现记录
     */
    private int type;
    /**
     * 当前请求页码
     */
    private int page = 0;

    public FinancialRecordVM(int type, String[] tips) {
        this.type = type;
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

    @Override
    protected void selectView(ItemView itemView, int position, Object item) {
        if (type == Constant.NUMBER_0) {
            itemView.set(BR.item, R.layout.account_recharge_record_item).setVariable(BR.item, item);
        } else {
            itemView.set(BR.item, R.layout.account_withdraw_record_item).setVariable(BR.item, item);
        }
    }

    /**
     * 网络请求
     * <p/>
     * 0 - 充值记录
     * 1 - 提现记录
     */
    public void req_data(PtrFrameLayout ptrFrame) {
        if (type == Constant.NUMBER_0) {
            req_recharge(ptrFrame);
        } else {
            req_withdraw(ptrFrame);
        }
    }

    /**
     * 网络请求 - 充值记录
     */
    public void req_recharge(final PtrFrameLayout ptrFrame) {
        page++;
        Call<RechargeRecodMoList> call = RDClient.getService(LogService.class).rechargeLoglist(page);
        call.enqueue(new RequestCallBack<RechargeRecodMoList>(ptrFrame) {
            @Override
            public void onSuccess(Call<RechargeRecodMoList> call, Response<RechargeRecodMoList> response) {
                if (response.body().getRechargeList() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }
                items.addAll(response.body().getRechargeList());
                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_recharge);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }

    /**
     * 网络请求 - 提现记录
     */
    public void req_withdraw(final PtrFrameLayout ptrFrame) {
        page++;
        Call<WithdrawRecodMoList> call = RDClient.getService(LogService.class).cashLogList(page);
        call.enqueue(new RequestCallBack<WithdrawRecodMoList>(ptrFrame) {
            @Override
            public void onSuccess(Call<WithdrawRecodMoList> call, Response<WithdrawRecodMoList> response) {
                if (response.body().getWithdrawList() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }
                items.addAll(response.body().getWithdrawList());
                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_withdraw);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }
}