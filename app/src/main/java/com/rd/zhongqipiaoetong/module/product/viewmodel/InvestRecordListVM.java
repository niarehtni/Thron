package com.rd.zhongqipiaoetong.module.product.viewmodel;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.product.activity.InvestListAct;
import com.rd.zhongqipiaoetong.module.product.model.InvestMoList;
import com.rd.zhongqipiaoetong.module.product.model.InvestRecordMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/2/25 11:11
 * <p/>
 * Description:投资记录/转让记录VM({@link InvestListAct})
 */
public class InvestRecordListVM extends BaseRecyclerViewVM<InvestRecordMo> {
    private int page = 0;
    private String  id;//标id
    private String  uuid;
    private boolean is_investrecord;

    @Override
    protected void selectView(ItemView itemView, int position, InvestRecordMo item) {
       if(FeatureConfig.enableInvestRecordTimeFeature == 1){
           itemView.set(BR.item, R.layout.product_invest_record_list_item);
       }else{
           itemView.set(BR.item, R.layout.product_invest_record_list_item_without_time);
       }
    }

    public InvestRecordListVM(String id, String uuid, boolean is_investrecord, String[] tips) {
        this.id = id;
        this.uuid = uuid;
        this.is_investrecord = is_investrecord;
        this.tips = tips;
        hidden.set(false);
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
     * 普通标的投标记录
     */
    public void req_data(final PtrFrameLayout ptrFrame) {
        page++;
        Call<InvestMoList> call = RDClient.getService(ProductService.class).investRecordList(id, page);
        call.enqueue(new RequestCallBack<InvestMoList>(ptrFrame) {
            @Override
            public void onSuccess(Call<InvestMoList> call, Response<InvestMoList> response) {
                if (response.body().getInvestLog() == null)
                    return;

                if (emptyState.isLoading())
                    emptyState.setLoading(false);

                if (page == 1)
                    items.clear();
                items.addAll(response.body().getInvestLog());

                //判断是否是普通标，是普通标显示布局，债券标隐藏
                for (int i = 0;i < items.size();i++){
                    if (is_investrecord) {
                        items.get(i).setIsshow(true);
                    } else {
                        items.get(i).setIsshow(false);
                    }
                }

                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_invest);
                    return;
                }
                response.body().isOver(ptrFrame);
            }
        });
    }
}