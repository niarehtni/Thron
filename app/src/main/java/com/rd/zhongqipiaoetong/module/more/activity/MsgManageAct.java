package com.rd.zhongqipiaoetong.module.more.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.MoreMessageActBinding;
import com.rd.zhongqipiaoetong.module.more.model.MsgMo;
import com.rd.zhongqipiaoetong.module.more.model.MsgMoList;
import com.rd.zhongqipiaoetong.module.more.viewmodel.MsgManageVM;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ExtraService;
import com.rd.zhongqipiaoetong.view.entity.EmptyState;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/18 10:10
 * <p/>
 * Description: 我的消息列表
 */
public class MsgManageAct extends BaseActivity {
    private MoreMessageActBinding binding;
    private List<MsgMo>           list;
    private MsgManageVM           mAdapter;
    /**
     * 选中数量
     */
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.more_message_act);
        list = new ArrayList<>();
        mAdapter = new MsgManageVM(this, list);
        binding.msgList.setAdapter(mAdapter);
        binding.setListener(new PtrFrameListener() {
            @Override
            public void ptrFrameInit(final PtrClassicFrameLayout ptrFrame) {
                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.autoRefresh();
                    }
                }, 100);
            }

            @Override
            public void ptrFrameRefresh() {
                page = 0;
            }

            @Override
            public void ptrFrameRequest(PtrFrameLayout ptrFrame) {
                req_getProductInvestList(ptrFrame);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.msg_title);
    }

    /**
     * 获得消息记录
     */
    private void req_getProductInvestList(final PtrFrameLayout ptrFrame) {
        page++;
        Call<MsgMoList> call = RDClient.getService(ExtraService.class).messageList(page);
        call.enqueue(new RequestCallBack<MsgMoList>(ptrFrame) {
            @Override
            public void onSuccess(Call<MsgMoList> call, Response<MsgMoList> response) {
                if (response.body().getInfoList() == null) {
                    return;
                }
                // 刷新时重新加载
                if (page == 1) {
                    list.clear();
                    mAdapter.closeAllExcept(null);
                }

                list.addAll(response.body().getInfoList());
                if (list.size() < 1) {
                    EmptyState state = new EmptyState();
                    state.setPrompt(R.string.empty_message);
                    binding.msgPtr.setVisibility(View.GONE);
                    binding.msgLoading.setEmptyState(state);
                    binding.msgLoading.setVisibility(View.VISIBLE);
                }

                mAdapter.notifyDataSetChanged();
                // 分页处理，最后一页时，禁止加载更多
                response.body().isOver(ptrFrame);
            }
        });
    }

    @Override
    public void finish() {
        setResult(BundleKeys.RESULT_CODE_MSGMANAGE);
        super.finish();
    }

    /**
     * 消息操作设置
     *
     * @param id
     *         消息id(多选)
     * @param isReaded
     *         1-删除信息,2-标记未读,3-标记已读
     */
    public void req_getSettingMsg(String id, String isReaded) {
        Call<MsgMoList> call = RDClient.getService(ExtraService.class).messageSetting(id, isReaded);
        call.enqueue(new RequestCallBack<MsgMoList>() {
            @Override
            public void onSuccess(Call<MsgMoList> call, Response<MsgMoList> response) {

            }
        });
    }

    /**
     * 设置选中数量
     */
    public void setSelectText(int num) {
        if (num > 0) {
            binding.msgButton.setEnabled(true);
        } else {
            binding.msgButton.setEnabled(false);
        }
        binding.msgSelectText.setText(getString(R.string.msg_select_text, num));
    }
}