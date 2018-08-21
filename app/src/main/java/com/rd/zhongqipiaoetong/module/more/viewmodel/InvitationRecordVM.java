package com.rd.zhongqipiaoetong.module.more.viewmodel;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.more.model.InviteMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ExtraService;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: JinF
 * E-mail: jf@erongdu.com
 * Date: 2016/10/12 16:40
 * <p/>
 * Description:
 */
public class InvitationRecordVM extends BaseRecyclerViewVM<InviteMo.InviteList> {
    private int page = 0;

    @Override
    protected void selectView(ItemView itemView, int position, InviteMo.InviteList item) {
        itemView.set(BR.item, R.layout.more_invitation_record_item);
    }

    public InvitationRecordVM(String[] tips) {
        this.tips = tips;
        hidden.set(false);
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


//        if (emptyState.isLoading()) {
//            emptyState.setLoading(false);
//        }
//        if (mo.getInviteList() == null || mo.getInviteList().size() <= 0) {
//            emptyState.setPrompt(R.string.empty_record);
//        } else {
//            items.addAll(mo.getInviteList());
//        }
    }

        private void req_data(final PtrFrameLayout ptrFrame) {
            page++;
            Call<InviteMo> call = RDClient.getService(ExtraService.class).userInvite(page);
            call.enqueue(new RequestCallBack<InviteMo>(ptrFrame) {
                @Override
                public void onSuccess(Call<InviteMo> call, Response<InviteMo> response) {
                    if (response.body().getInviteList() == null)
                        return;
                    if (emptyState.isLoading()) {
                        emptyState.setLoading(false);
                    }
                    if (page == 1) {
                        items.clear();
                    }
                    items.addAll(response.body().getInviteList());
                    if (items.size() <= 0) {
                        emptyState.setPrompt(R.string.empty_record);
                    }
                    response.body().isOver(ptrFrame);
                }
            });
        }
}
