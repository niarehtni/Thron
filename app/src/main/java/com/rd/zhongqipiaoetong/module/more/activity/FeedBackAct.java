package com.rd.zhongqipiaoetong.module.more.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.MoreFeedbackActBinding;
import com.rd.zhongqipiaoetong.module.more.viewmodel.FeedBackVM;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/30 上午11:17
 * <p/>
 * Description: 意见反馈
 */
public class FeedBackAct extends BaseActivity{
    private MoreFeedbackActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.more_feedback_act);
        FeedBackVM viewModel = new FeedBackVM();
        binding.setViewModel(viewModel);
        binding.feedbackLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.feedbackLl.setFocusable(true);
                binding.feedbackLl.setFocusableInTouchMode(true);
                binding.feedbackLl.requestFocus();
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.feedback_title);
        setRightText(R.string.commit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBackAct.this.finish();
            }
        });
    }
}