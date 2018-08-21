package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountScoreLogActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.ScoreLogVM;

/**
 * Description: 积分记录
 */
public class ScoreLogAct extends BaseActivity {
    private ScoreLogVM scoreLogVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountScoreLogActBinding binding = DataBindingUtil.setContentView(this, R.layout.account_score_log_act);
        scoreLogVM = new ScoreLogVM();
        binding.setViewModel(scoreLogVM);
        binding.executePendingBindings();
        scoreLogVM.req_data(scoreLogVM.getPtrFrame());
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.score_log_title);
    }
}
