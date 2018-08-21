package com.rd.zhongqipiaoetong.module.product.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRdwebviewBinding;
import com.rd.zhongqipiaoetong.module.product.activity.ProductInfoAct;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/9 15:34
 * <p/>
 * Description:借款资料列表({@link ProductInfoAct})
 */
public class FlowInfoListFrag extends BaseLazyFragment {
    private CommonRdwebviewBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_rdwebview, container, false);
        ready();
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        // 如果加载页面可见，则表示初次进入界面，需要请求数据

        String pUrl = CommonMethod.getUrl(BaseParams.WX_FLOW_INFO);
        pUrl = pUrl+"&"+BundleKeys.UUID+"="+getActivity().getIntent().getStringExtra(BundleKeys.UUID);
        binding.rdWebview.loadUrl(pUrl);
    }
}
