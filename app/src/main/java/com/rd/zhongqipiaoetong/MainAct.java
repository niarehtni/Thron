package com.rd.zhongqipiaoetong;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.MainTabActBinding;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.CommonService;
import com.rd.zhongqipiaoetong.network.entity.VersionMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DialogUtils;
import com.rd.zhongqipiaoetong.utils.PermissionCheck;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.statistics.DeviceInfoUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/23 上午9:53
 * <p/>
 * Description: 底部导航栏页面
 */
public class MainAct extends BaseActivity {
    private MainTabActBinding binding;
    private MainVM            viewModel;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_tab_act);
        viewModel = new MainVM(binding, getSupportFragmentManager());
        binding.setViewModel(viewModel);
        init();
        if (isFirst)
            getVersionInfo();
        isFirst = false;
    }

    private void getVersionInfo() {
        Call<VersionMo> call = RDClient.getService(CommonService.class).getVersionNum();
        call.enqueue(new RequestCallBack<VersionMo>() {
            @Override
            public void onSuccess(Call<VersionMo> call, final Response<VersionMo> response) {
                if (response.body().getAndroidVersion() != null && checkUpdate(response.body().getAndroidVersion())) {
                    DialogUtils.updateDialog(binding.getRoot().getContext(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (response.body().getDownloadUrl() != null) {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(response.body().getDownloadUrl());
                                intent.setData(content_url);
                                startActivity(intent);
                            }
                        }
                    }).show();
                }
            }
        });
    }

    /**
     * 检测是否需要提示更新
     *
     * @param newVersion
     *
     * @return
     */
    private boolean checkUpdate(String newVersion) {
        String[] oldVersions = {"0", "0", "0"};
        for (int i = 0; i < (DeviceInfoUtils.getVersionName(binding.getRoot
                ().getContext()).split("\\.").length > 3 ? 3 : DeviceInfoUtils.getVersionName(binding.getRoot
                ().getContext()).split("\\.").length);
             i++) {
            oldVersions[i] = DeviceInfoUtils.getVersionName(binding.getRoot
                    ().getContext()).split("\\.")[i];
        }
        String[] newVersions = {"0", "0", "0"};
        for (int i = 0; i < (newVersion.split("\\.").length > 3 ? 3 : newVersion.split("\\.").length); i++) {
            newVersions[i] = newVersion.split("\\.")[i];
        }
        for (int i = 0; i < 3; i++) {
            try {
                if (Integer.valueOf(newVersions[i]) > Integer.valueOf(oldVersions[i])) {
                    return true;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 是否要显示"我的账户"页面（由用户的登录、登出操作决定）
        if (getIntent().getBooleanExtra(BundleKeys.IS_SHOW, false)) {
            getIntent().putExtra(BundleKeys.IS_SHOW, false);
            viewModel.accountClick(binding.mainAccount);
            if (getIntent().getBooleanExtra(BundleKeys.IS_UPDATA, false)) {
                if (viewModel.mAccountFrag != null) {
                    viewModel.mAccountFrag.upData();
                }
            }
        } else if (getIntent().getBooleanExtra(BundleKeys.IS_PRODUCT, false)) {
            viewModel.listClick(binding.mainList);
        } else {
            viewModel.homeClick(binding.mainHome);
        }
    }

    /**
     * APP基础操作初始化
     */
    private void init() {
        MyApplication.getInstance().metrics = Utils.getMetrics(this);
        PermissionCheck.getInstance().askForPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionCheck.REQUEST_CODE_ALL);
    }

    @Override
    public void onBackPressed() {
        ActivityUtils.onExit();
    }
}