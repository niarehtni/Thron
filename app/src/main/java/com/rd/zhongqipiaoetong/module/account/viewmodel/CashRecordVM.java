package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.databinding.CashRecordFragBinding;
import com.rd.zhongqipiaoetong.module.account.CashRecordAdapter;
import com.rd.zhongqipiaoetong.module.account.model.CashRecordItem;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.entity.ListMo;
import com.rd.zhongqipiaoetong.view.DividerLine;
import com.rd.zhongqipiaoetong.view.entity.EmptyState;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/11/15 下午3:35
 * <p/>
 * Description:资金明细控制器
 */
public class CashRecordVM {
    private int page = 0;
    private CashRecordAdapter cashAdapter;
    public final ObservableField<PtrFrameListener> listener = new ObservableField<>();
    /** 刷新控件对象 */
    protected PtrClassicFrameLayout ptrFrame;
    /**
     * 空态对象
     */
    public final EmptyState emptyState = new EmptyState();

    public CashRecordVM(CashRecordFragBinding binding) {
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
                reqData();
            }
        });

        cashAdapter = new CashRecordAdapter();
        binding.recycler.setAdapter(cashAdapter);
        // 设置布局管理器
        final LinearLayoutManager layoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recycler.setLayoutManager(layoutManager);
        // 设置头部展示类
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(cashAdapter);
        binding.recycler.addItemDecoration(headersDecor);
        // 添加item横线
        binding.recycler.addItemDecoration(new DividerLine(DividerLine.HORIZONTAL));
        reqData();
    }

    protected void setPtrFrame(PtrClassicFrameLayout ptrFrame) {
        this.ptrFrame = ptrFrame;
    }

    /**
     * 数据请求
     */
    private void reqData() {
        page++;
        Call<ListMo<CashRecordItem>> call = RDClient.getService(AccountService.class).getAccountLog(page);
        call.enqueue(new RequestCallBack<ListMo<CashRecordItem>>(ptrFrame) {
            @Override
            public void onSuccess(Call<ListMo<CashRecordItem>> call, Response<ListMo<CashRecordItem>> response) {

                if (response.body().getList() == null) {
                    return;
                }
                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }
                if (page == 1) {
                    cashAdapter.clear();
                }
                if(response.body().getList().isEmpty()){
                    emptyState.setPrompt(R.string.empty_account);
                    return;
                }
                cashAdapter.addAll(response.body().getList());
                response.body().isOver(ptrFrame);
                //                if (response.body() != null && response.body().getData() != null) {
                //                    convert(response.body().getData().getList());
                //                    getSwipeLayout().setLoadMoreEnabled(!response.body().getData().isOver());
                //                } else {
                //                    getSwipeLayout().setLoadMoreEnabled(false);
                //                }
            }
        });
    }

    //    /**
    //     * 数据转换为viewModel
    //     *
    //     * @param list
    //     *         dataModel
    //     */
    //    private void convert(List<CashRecordRec> list) {
    //        if (pageMo.isRefresh() && null != list) {
    //            cashAdapter.clear();
    //        }
    //        if (list == null)
    //            return;
    //
    //        if (list.isEmpty()) {
    //            placeholderState.set(PlaceholderHelper.EMPTY_RECORD);
    //        }
    //
    //        List<CashRecordItemVM> dataList = new ArrayList<>();
    //        for (CashRecordRec rec : list) {
    //            CashRecordItemVM item = new CashRecordItemVM();
    //            item.setCreateTime(rec.getCreateTime());
    //            item.setAccountType(rec.getAccountType());
    //            item.setAccountTypeStr(rec.getAccountTypeStr());
    //            item.setFunName(rec.getFunName());
    //            item.setMoney(rec.getMoney());
    //            item.setMoneyStr(rec.getMoneyStr());
    //            item.setRemark(rec.getRemark());
    //            dataList.add(item);
    //        }
    //        cashAdapter.addAll(dataList);
    //    }
}