package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.view.View;
import android.widget.Toast;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.activity.MyCouponAct;
import com.rd.zhongqipiaoetong.module.account.model.CouponMo;
import com.rd.zhongqipiaoetong.module.account.model.ExpMoList;
import com.rd.zhongqipiaoetong.module.account.model.ExperienceMo;
import com.rd.zhongqipiaoetong.module.account.model.PersonInfoMo;
import com.rd.zhongqipiaoetong.module.account.model.RedPackeMoList;
import com.rd.zhongqipiaoetong.module.account.model.UpRateMo;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.network.entity.ListMo;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.payment.ToPaymentCheck;
import com.rd.zhongqipiaoetong.payment.ToPaymentMo;
import com.rd.zhongqipiaoetong.utils.UpDataListener;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/8 10:11
 * <p/>
 * Description: ({@link MyCouponAct})
 */
public class MyCouponVM extends BaseRecyclerViewVM<Object> {
    // 是否显示CheckBox
    private boolean isShow = false;
    //红包类型 : 0.红包。1.体验券。2.加息券
    private int    type;
    // 可选红包
    private String couponOptional;
    // 已选红包
    private String couponSelected;
    private int page = 0;

    // 布局效果
    @Override
    protected void selectView(ItemView itemView, int position, Object item) {
        if (type == 1) {
            itemView.set(BR.item, R.layout.account_my_experience_item).setVariable(BR.isShow, false).setOnItemClickListener(new ItemView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (isShow) {
                        //TODO 体验券选择业务逻辑
                        ExperienceMo mo = (ExperienceMo) items.get(position);
                        mo.setIsCheck(mo.getIsCheck());
                    }
                }
            });
        } else if (type == 2) {
            itemView.set(BR.item, R.layout.account_my_uprate_item).setVariable(BR.isShow, false).setOnItemClickListener(new ItemView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (isShow) {
                        //TODO 加息券选择业务逻辑
                        UpRateMo mo = (UpRateMo) items.get(position);
                        mo.setIsCheck(mo.getIsCheck());
                    }
                }
            });
        } else {
            itemView.set(BR.item, R.layout.account_my_coupon_item).setVariable(BR.isShow, false).setOnItemClickListener(new ItemView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (isShow) {
                        // TODO 红包选择业务逻辑
                        CouponMo mo = (CouponMo) items.get(position);
                        mo.setChoose(mo.getChoose());
                    }
                }
            });
        }
    }

    public MyCouponVM(int type, boolean isShow) {
        this.isShow = isShow;
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
    public void req_data(PtrFrameLayout ptrFrame) {
        page++;
        if (type == 1) {
            res_experience(ptrFrame);
        } else if (type == 2) {
            res_upRate(ptrFrame);
        } else {
            res_red(ptrFrame);
        }
    }

    /**
     * 确定选择红包
     */
    public void submit(View view) {
        Toast.makeText(view.getContext(), "submit", Toast.LENGTH_SHORT).show();
    }

    private void res_red(final PtrFrameLayout ptrFrame) {
        Call<RedPackeMoList> call = RDClient.getService(ProductService.class).redEnvelopeList(page);
        call.enqueue(new RequestCallBack<RedPackeMoList>(ptrFrame) {
            @Override
            public void onSuccess(Call<RedPackeMoList> call, Response<RedPackeMoList> response) {

                if (response.body().getRedPackeList() == null) {
                    return;
                }

                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }
                for (final CouponMo mo : response.body().getRedPackeList()) {
                    mo.setCouponClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mo.isExchang()) {
                                req_securityInfo(mo.getId());
                            }
                        }
                    });
                    items.add(mo);
                }
                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_redenvelope);
                    return;
                }

                if (response.body().getRedPackeList().size() == 0) {
                    ptrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
                } else {
                    ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
                }

                // 分页处理，最后一页时，禁止加载更多
                //                response.body().isOver(ptrFrame);
            }

            @Override
            public void onFailure(Call<RedPackeMoList> call, Throwable t) {
                ptrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
                super.onFailure(call, t);
            }
        });
    }

    /**
     * 网络请求
     */
    private void req_securityInfo(final String id) {
        Call<PersonInfoMo> call = RDClient.getService(AccountService.class).securityInfo();
        call.enqueue(new RequestCallBack<PersonInfoMo>() {
            @Override
            public void onSuccess(Call<PersonInfoMo> call, Response<PersonInfoMo> response) {
                SharedInfo.getInstance().setValue(PersonInfoMo.class, response.body());
                ToPaymentMo toPaymentMo = new ToPaymentMo();
                toPaymentMo.setRealNameStatus(response.body().getRealNameStatus());
                if (!RDPayment.getInstance().getPayController().toPayment(PayController.TPYE_REGISTER, toPaymentMo, new ToPaymentCheck(null), true)) {
                    return;
                }
                UserLogic.couponExchang(id, new UpDataListener<Boolean>() {
                    @Override
                    public void updata(int type, Boolean o) {
                        ptrFrame.autoRefresh();
                    }
                });
            }
        });
    }

    private void res_experience(final PtrFrameLayout ptrFrame) {
        Call<ExpMoList> call = RDClient.getService(ProductService.class).experienceList(page);
        call.enqueue(new RequestCallBack<ExpMoList>(ptrFrame) {
            @Override
            public void onSuccess(Call<ExpMoList> call, Response<ExpMoList> response) {

                if (response.body().getTicketExperienceList() == null) {
                    return;
                }
                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }

                // 刷新时重新加载
                if (page == 1) {
                    items.clear();
                }

                items.addAll(response.body().getTicketExperienceList());

                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_experience);
                    return;
                }
                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }

    private void res_upRate(final PtrFrameLayout ptrFrame) {
        Call<ListMo<UpRateMo>> call = RDClient.getService(ProductService.class).UpRateList(page);
        call.enqueue(new RequestCallBack<ListMo<UpRateMo>>(ptrFrame) {
            @Override
            public void onSuccess(Call<ListMo<UpRateMo>> call, Response<ListMo<UpRateMo>> response) {

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
                    emptyState.setPrompt(R.string.empty_uprate);
                    return;
                }
                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }
}