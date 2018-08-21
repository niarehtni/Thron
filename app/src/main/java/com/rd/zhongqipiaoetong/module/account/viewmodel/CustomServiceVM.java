package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableField;
import android.net.Uri;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.module.account.model.QQMo;
import com.rd.zhongqipiaoetong.module.more.model.InviteMo;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ExtraService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by pzw on 2017/6/21.
 */
public class CustomServiceVM extends BaseRecyclerViewVM<QQMo> {
    public  ObservableField<String> webPhone    = new ObservableField<>("");
    public  ObservableField<String> serviceTime = new ObservableField<>("");
    private List<QQMo>              qqs         = new ArrayList<QQMo>();

    @Override
    protected void selectView(ItemView itemView, int position, QQMo item) {
        itemView.set(BR.info, R.layout.qq_item);
    }

    public CustomServiceVM() {
        req_info();
    }

    private void req_info() {
        Call<InviteMo> call = RDClient.getService(ExtraService.class).userInvite();
        call.enqueue(new RequestCallBack<InviteMo>() {
            @Override
            public void onSuccess(Call<InviteMo> call, Response<InviteMo> response) {
                webPhone.set(response.body().getWebPhone());
                serviceTime.set(response.body().getServiceTime());
                String serviceQQ = response.body().getServiceQQ();
                if (serviceQQ.contains("\n")) {
                    String[] qq = serviceQQ.split("\n");
                    for (int i = 0; i < qq.length; i++) {
                        if (qq[i].contains("#")) {
                            String[] info = qq[i].split("#");
                            qqs.add(new QQMo("客服名称-" + info[1] + "：", info[0]));
                        } else {
                            qqs.add(new QQMo("客服：", qq[i]));
                        }
                    }
                } else {
                    if (serviceQQ.contains("#")) {
                        String[] info = serviceQQ.split("#");
                        qqs.add(new QQMo("客服名称-" + info[1] + "：", info[0]));
                    } else {
                        qqs.add(new QQMo("客服：", serviceQQ));
                    }
                }
                items.addAll(qqs);
            }
        });
    }

    public void phoneClick(View view) {
        UserLogic.createDialog(view.getRootView().getContext().getString(R.string.phone, webPhone.get()), new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + webPhone.get()));
                        ActivityUtils.peek().startActivity(intent);
                    }
                });
    }
}
