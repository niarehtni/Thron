package com.rd.zhongqipiaoetong.module.more.viewmodel;

import android.content.Intent;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.more.model.HelpMo;
import com.rd.zhongqipiaoetong.network.Html5Util;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ExtraService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.UrlUtils;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/4/24.
 */
public class HelpCenterVM extends BaseRecyclerViewVM<HelpMo.HelpItem> {

    private int page;

    public HelpCenterVM() {
        hidden.set(false);
        listener.set(new PtrFrameListener() {
            @Override
            public void ptrFrameInit(PtrClassicFrameLayout ptrFrame) {
                ptrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
                setPtrFrame(ptrFrame);
                reqData();
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
    }

    @Override
    protected void selectView(ItemView itemView, int position, HelpMo.HelpItem item) {
        itemView.set(BR.item, R.layout.more_helpcenter_item).setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.TITLE, items.get(i).getTitle());
                intent.putExtra(BundleKeys.NEED_GOBACK, true);
                String getUrl = CommonMethod.getUrl(Html5Util.MORE_HELPCONTENT) + "&id=" + items.get(i).getId();
//                intent.putExtra(BundleKeys.URL,getUrl);
                intent.putExtra(BundleKeys.URL, UrlUtils.getAddress()+ Html5Util.MORE_HELPCONTENT);
                intent.putExtra(BundleKeys.POSTDATA, getUrl.substring(getUrl.indexOf("?")));
                ActivityUtils.push(RDWebViewAct.class, intent);
            }
        });
    }

    private void reqData(){
        page ++;
        Call<HelpMo> call = RDClient.getService(ExtraService.class).helpCenter(page);
        call.enqueue(new RequestCallBack<HelpMo>(ptrFrame) {
            @Override
            public void onSuccess(Call<HelpMo> call, Response<HelpMo> response) {
                if (emptyState.isLoading()) {
                    emptyState.setLoading(false);
                }
                if (page == 1) {
                    items.clear();
                }
                items.addAll(response.body().getHelpCenterList());
                if (items.size() <= 0) {
                    emptyState.setPrompt(R.string.empty_record);
                    return;
                }
                response.body().isOver(ptrFrame);
            }
        });
    }
}
