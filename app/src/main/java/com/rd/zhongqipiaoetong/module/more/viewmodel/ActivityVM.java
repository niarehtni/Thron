package com.rd.zhongqipiaoetong.module.more.viewmodel;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.more.model.ActivityMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ExtraService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.view.DividerLine;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/4/18.
 */
public class ActivityVM extends BaseRecyclerViewVM<ActivityMo.Activity> {
    private int page = 0;

    public ActivityVM() {
        type = DividerLine.DEFAULT;
        hidden.set(false);
        listener.set(new PtrFrameListener() {
            @Override
            public void ptrFrameInit(PtrClassicFrameLayout ptrFrame) {
                setPtrFrame(ptrFrame);
                reqActivity();
            }

            @Override
            public void ptrFrameRefresh() {
                page = 0;
            }

            @Override
            public void ptrFrameRequest(PtrFrameLayout ptrFrame) {
                reqActivity();
            }
        });
    }

    @Override
    protected void selectView(ItemView itemView, int position, ActivityMo.Activity item) {
        itemView.set(BR.item, R.layout.activity_list_item).setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if (TextUtils.isEmpty(items.get(i).getUrl())) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.TITLE, items.get(i).getTitle());
                intent.putExtra(BundleKeys.URL, CommonMethod.getUrlWithoutBase(items.get(i).getUrl()));
                ActivityUtils.push(RDWebViewAct.class, intent);
            }
        });
    }

    private void reqActivity() {
        page++;
        Call<ActivityMo> call = RDClient.getService(ExtraService.class).getActivity(page);
        call.enqueue(new RequestCallBack<ActivityMo>(ptrFrame) {
            @Override
            public void onSuccess(Call<ActivityMo> call, Response<ActivityMo> response) {
                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }
                if (page == 1) {
                    items.clear();
                }
                for (ActivityMo.Activity activity : response.body().getActivityList()) {
                    activity.setImageHost(response.body().getQiniuDomain());
                }
                items.addAll(response.body().getActivityList());
                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.activity_no);
                    return;
                }
                response.body().isOver(ptrFrame);
            }
        });
    }
}
