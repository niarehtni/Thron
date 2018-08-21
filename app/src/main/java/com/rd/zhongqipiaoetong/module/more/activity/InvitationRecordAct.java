package com.rd.zhongqipiaoetong.module.more.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.databinding.MoreInvitationRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.more.viewmodel.InvitationRecordVM;

public class InvitationRecordAct extends BaseActivity {
    private InvitationRecordVM invitationRecordVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoreInvitationRecyclerViewBinding binding = DataBindingUtil.setContentView(this, R.layout.more_invitation_recycler_view);
        invitationRecordVM = new InvitationRecordVM(getResources().getStringArray(R.array.userInviteTips));
        binding.setViewModel(invitationRecordVM);
        binding.executePendingBindings();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.invitation_record);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(invitationRecordVM.getPtrFrame() != null)
            invitationRecordVM.getPtrFrame().autoRefresh();
    }
}
