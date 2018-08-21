package com.rd.zhongqipiaoetong.module.user.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.UserFindpwdFirstActBinding;
import com.rd.zhongqipiaoetong.module.user.viewmodel.FindPwdFirstVM;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 6/14/16.
 */
public class ForgetAct extends BaseActivity {
    private UserFindpwdFirstActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_findpwd_first_act);
        binding.setViewModel(new FindPwdFirstVM(binding));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.findpwd_title);

        setLeftImage(R.drawable.appbar_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.getViewModel().backEvent();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.findpwdTimebtn.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            binding.getViewModel().backEvent();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
