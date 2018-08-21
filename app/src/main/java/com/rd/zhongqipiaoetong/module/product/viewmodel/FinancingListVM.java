package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.content.Intent;
import android.view.View;

import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.product.activity.FinancingDetailAct;
import com.rd.zhongqipiaoetong.module.product.activity.NewProductAct;
import com.rd.zhongqipiaoetong.module.product.fragment.FinanceListFrag;
import com.rd.zhongqipiaoetong.module.product.model.ExpProductMo;
import com.rd.zhongqipiaoetong.module.product.model.FinancingMo;
import com.rd.zhongqipiaoetong.module.product.model.ProductMoList;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ExtraService;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.view.DividerLine;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/2/24 11:16
 * <p/>
 * Description: 理财产品VM({@link FinanceListFrag})
 */
public class FinancingListVM extends BaseRecyclerViewVM<FinancingMo> {
    @Override
    protected void selectView(ItemView itemView, int position, FinancingMo item) {
        itemView.set(BR.info, R.layout.product_financing_list_item)
                .setOnItemClickListener(new ItemView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (!MyApplication.getInstance().isLand()) {
                            ActivityUtils.push(LoginAct.class);
                            return;
                        }
                        if (items.get(position).getClassify() ==4) {
                            Intent intent = new Intent();
                            intent.putExtra(BundleKeys.ID, items.get(position).getId());
                            ActivityUtils.push(NewProductAct.class, intent);
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra(BundleKeys.ID, items.get(position).getId());
                            intent.putExtra(BundleKeys.NAME, items.get(position).getName());
                            ActivityUtils.push(FinancingDetailAct.class, intent);
                        }
                    }
                });

    }

    private int page = 0;
    private ExpProductMo.ExpProduct expProduct;

    public FinancingListVM(CommonRecyclerViewBinding binding) {
        type = DividerLine.DEFAULT;

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
                req_data(ptrFrame);
            }
        });
    }

    /**
     * 网络请求
     */
    public void req_data(final PtrFrameLayout ptrFrame) {
        page++;
        Call<ProductMoList> call = RDClient.getService(ProductService.class).investList(page);
        call.enqueue(new RequestCallBack<ProductMoList>(ptrFrame) {
            @Override
            public void onSuccess(Call<ProductMoList> call, Response<ProductMoList> response) {
                if (response.body().getTenderList() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }

                items.addAll(response.body().getTenderList());
                if (expProduct == null) {
                    expHidden.set(false);
                } else {
                    expHidden.set(true);
                    info.set(expProduct);
                }
                if (items.size() <= 0 && expProduct == null) {
                    emptyState.setPrompt(R.string.empty_product);
                    return;
                }

                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }

    public void req_exp_data() {
        Call<ExpProductMo> callExp = RDClient.getService(ExtraService.class).getExp();
        callExp.enqueue(new RequestCallBack<ExpProductMo>() {
            @Override
            public void onSuccess(Call<ExpProductMo> call, Response<ExpProductMo> response) {
                if (response.body().getExpBorrowDetail() != null)
                    expProduct = response.body().getExpBorrowDetail();
                req_data(ptrFrame);
            }
        });
    }
}