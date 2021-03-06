package com.rd.zhongqipiaoetong.module.gesturelock.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.LockModifyActBinding;
import com.rd.zhongqipiaoetong.module.gesturelock.logic.LockLogic;
import com.rd.zhongqipiaoetong.module.gesturelock.viewmodel.LockModifyVM;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.view.lock.LockPatternView;
import com.rd.zhongqipiaoetong.view.lock.LockPatternView.Cell;
import com.rd.zhongqipiaoetong.view.lock.LockPatternView.DisplayMode;

import java.util.List;

/**
 * <p/>
 * Description: 手势密码修改页面
 */
public class LockModifyPwdAct extends BaseActivity implements LockPatternView.OnPatternListener {
    private LockModifyActBinding binding;
    private List<Cell>           lockPattern;
    private static final int         TIME     = 1000;
    private              Handler     mHandler = new Handler();
    private              ImageView[] pointers = new ImageView[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.lock_modify_act);
        binding.setLockVM(new LockModifyVM());
        binding.lockPattern.setOnPatternListener(this);
        smallView();
        final String password = LockLogic.getInstance().getPassword();
        if (!TextUtils.isEmpty(password)) {
            lockPattern = LockPatternView.stringToPattern(password);
        } else {
            LockLogic.getInstance().checkLock(this);
            this.finish();
        }
    }

    private void smallView() {
        for (int i = 0; i < 3; i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 3; j++) {
                ImageView pointer = new ImageView(this);
                pointer.setScaleType(ImageView.ScaleType.CENTER);
                pointer.setPadding(4, 4, 4, 4);
                pointer.setImageDrawable(getResources().getDrawable(R.drawable.lock_small_default));
                layout.addView(pointer);
                pointers[i * 3 + j] = pointer;
            }
            binding.lockModifySmall.addView(layout);
        }
    }

    private void changeSmall(List<Cell> pattern) {
        for (ImageView pointer : pointers) {
            pointer.setImageDrawable(getResources().getDrawable(R.drawable.lock_small_default));
        }
        for (LockPatternView.Cell cell : pattern) {
            pointers[cell.getRow() * 3 + cell.getColumn()].setImageDrawable(getResources().getDrawable(R.drawable.lock_small_blue));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getString(R.string.lock_input_original_title));
//        setBackOption(false);
    }

    @Override
    public void onPatternStart() {

    }

    @Override
    public void onPatternCleared() {

    }

    @Override
    public void onPatternCellAdded(List<Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<Cell> pattern) {
        if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
            binding.lockModifyHint.setText(getResources().getString(R.string.lock_pattern_recording_incorrect_too_short));
            binding.lockModifyHint.setTextColor(Color.RED);
            binding.lockPattern.setDisplayMode(DisplayMode.Wrong);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.lockPattern.clearPattern();
                    binding.lockPattern.enableInput();
                }
            }, TIME);
            return;
        }
        changeSmall(pattern);
        if (pattern.equals(lockPattern)) {
            LockLogic.getInstance().reset();
            ActivityUtils.push(LockStepAct.class);
            this.finish();
        } else {
            LockLogic.getInstance().addErrInputCount();
            final int other = LockLogic.getInstance().getRemainErrInputCount();
            if (other > 0) {
                binding.lockModifyHint.setText(getString(R.string.lock_pattern_error2, other));
                binding.lockModifyHint.setTextColor(Color.RED);
                // 左右移动动画
                Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
                binding.lockModifyHint.startAnimation(shakeAnimation);
                binding.lockPattern.setDisplayMode(DisplayMode.Wrong);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.lockPattern.clearPattern();
                        binding.lockPattern.enableInput();
                    }
                }, TIME);
            } else {
                binding.lockPattern.clearPattern();
                UserLogic.signOutPwdTime(getString(R.string.lock_pattern_error3), true);
            }
        }
    }
}