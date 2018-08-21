package com.rd.zhongqipiaoetong.module.more.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.MoreInvitationActBinding;
import com.rd.zhongqipiaoetong.databinding.MoreInvitationActTypebBinding;
import com.rd.zhongqipiaoetong.module.more.viewmodel.InvitationTypeBVM;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Author: JinF
 * E-mail: jf@erongdu.com
 * Date: 2016/10/13 9:57
 * <p/>
 * Description: 邀请好友
 */
public class InvitationTypeBAct extends BaseActivity {

    private InvitationTypeBVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoreInvitationActTypebBinding binding = DataBindingUtil.setContentView(this, R.layout.more_invitation_act_typeb);
        vm = new InvitationTypeBVM(this, binding.codeIv);
        binding.setViewModel(vm);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.invitation_title);
        setRightText(R.string.invitation_record, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                intent.putExtra(BundleKeys.INVITEMO,vm.getMo());
                ActivityUtils.push(InvitationRecordAct.class,intent);
            }
        });
    }
}
