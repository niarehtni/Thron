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
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.LockActBinding;
import com.rd.zhongqipiaoetong.module.gesturelock.logic.LockLogic;
import com.rd.zhongqipiaoetong.module.gesturelock.viewmodel.LockVM;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.UserService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.view.lock.LockPatternView;
import com.rd.zhongqipiaoetong.view.lock.LockPatternView.Cell;
import com.rd.zhongqipiaoetong.view.lock.LockPatternView.DisplayMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/18 下午3:02
 * <p/>
 * Description: 手势密码登录页面
 */
public class LockAct extends BaseActivity implements LockPatternView.OnPatternListener {
    private LockActBinding binding;
    private List<Cell>     lockPattern;
    private              ImageView[] pointers = new ImageView[9];
    private static final int         TIME     = 1000;
    private              Handler     mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.lock_act);

        binding.setViewModel(new LockVM());
        smallView();
        binding.lockPattern.setOnPatternListener(this);
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
            binding.lockSmall.addView(layout);
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
            binding.lockHint.setText(getResources().getString(R.string.lock_pattern_recording_incorrect_too_short));
            binding.lockHint.setTextColor(Color.RED);
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
            refreshToken();
        } else {
            LockLogic.getInstance().addErrInputCount();
            final int other = LockLogic.getInstance().getRemainErrInputCount();
            if (other > 0) {
                binding.lockHint.setText(getString(R.string.lock_pattern_error2, other));
                binding.lockHint.setTextColor(Color.RED);
                // 左右移动动画
                Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
                binding.lockHint.startAnimation(shakeAnimation);
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

    private void refreshToken() {
        Call<OauthTokenMo> call = RDClient.getService(UserService.class).refreshToken(SharedInfo.getInstance().getValue(OauthTokenMo.class).getRefreshToken());
        call.enqueue(new RequestCallBack<OauthTokenMo>(true) {
            @Override
            public void onSuccess(Call<OauthTokenMo> call, Response<OauthTokenMo> response) {
                binding.lockPattern.clearPattern();
                UserLogic.saveToken(response.body());
                LockLogic.getInstance().reset();
                finish();
            }

            @Override
            public void onFailure(Call<OauthTokenMo> call, Throwable t) {
                binding.lockPattern.clearPattern();
                super.onFailure(call, t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        ActivityUtils.onExit();
    }
}