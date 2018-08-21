package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Window;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.ProductFlowDetailActBinding;
import com.rd.zhongqipiaoetong.module.product.viewmodel.FlowDetailVM;
import com.rd.zhongqipiaoetong.utils.Utils;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/2/24 17:26
 * <p/>
 * Description: 理财产品详情
 */
public class FlowDetailAct extends BaseActivity {
    private ProductFlowDetailActBinding binding;
    private FlowDetailVM           vm;
    private String                      name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String uuid = getIntent().getStringExtra(BundleKeys.UUID);
        binding = DataBindingUtil.setContentView(this, R.layout.product_flow_detail_act);
        name = getIntent().getStringExtra(BundleKeys.NAME);
        vm = new FlowDetailVM(uuid,binding);
        binding.setViewModel(vm);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(name);

        int colorBurn = Utils.colorBurn(getResources().getColor(R.color.app_color_principal));
        mAppBar.setBackgroundColor(getResources().getColor(R.color.app_color_principal));
        mAppBar.setLeftParentSelector(getResources().getColor(R.color.app_color_principal),colorBurn);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setStatusBarColor(colorBurn);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.req_data(binding.ptrAll);
    }
}
