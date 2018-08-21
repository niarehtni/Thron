package com.rd.zhongqipiaoetong.module.account.viewmodel;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.fragment.InvestmentFinancingFrag;
import com.rd.zhongqipiaoetong.module.account.model.CashRecordListMo;
import com.rd.zhongqipiaoetong.module.account.model.InvestmenMoList;
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
 * Date: 2016/2/29 10:38
 * <p/>
 * Description: 投资记录-pager VM ({@link InvestmentFinancingFrag})
 */
public class InvestmentFinancingVM extends BaseRecyclerViewVM<Object> {
    private int page = 0;
    private int type;

    @Override
    protected void selectView(ItemView itemView, int position, Object item) {
        if (type == Constant.NUMBER_0) {
            itemView.set(BR.item, R.layout.account_investment_record_item).setVariable(BR.item, item);
        } else {
            itemView.set(BR.item, R.layout.account_cash_record_item).setVariable(BR.item, item);
        }
    }

    public InvestmentFinancingVM(int type, String[] tips) {

        this.tips = tips;
        this.type = type;

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
        if (type == Constant.NUMBER_0) {
            req_invest(ptrFrame);
        } else {
            req_bond(ptrFrame);
        }
    }

    /**
     * 定期理财
     *
     * @param ptrFrame
     */
    public void req_invest(final PtrFrameLayout ptrFrame) {
        Call<InvestmenMoList> call = RDClient.getService(LogService.class).investRecordList(page);
        call.enqueue(new RequestCallBack<InvestmenMoList>(ptrFrame) {
            @Override
            public void onSuccess(Call<InvestmenMoList> call, Response<InvestmenMoList> response) {
                if (response.body().getInvestList() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }
                items.addAll(response.body().getInvestList());
                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_invest);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }

    /**
     * 债权转让
     *
     * @param ptrFrame
     */
    public void req_bond(final PtrFrameLayout ptrFrame) {
        Call<CashRecordListMo> call = RDClient.getService(LogService.class).boughtBondList(page);
        call.enqueue(new RequestCallBack<CashRecordListMo>(ptrFrame) {
            @Override
            public void onSuccess(Call<CashRecordListMo> call, Response<CashRecordListMo> response) {
                if (response.body().getCreditorList() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }
                items.addAll(response.body().getCreditorList());
                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_invest_bond);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }
}