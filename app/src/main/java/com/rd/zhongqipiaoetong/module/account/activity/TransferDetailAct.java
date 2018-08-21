package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.TransferDetailActBinding;
import com.rd.zhongqipiaoetong.module.account.model.TransferMo;
import com.rd.zhongqipiaoetong.module.account.viewmodel.TransferDetailVM;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/22 上午10:52
 * <p/>
 * Description: 转让详情页面
 */
public class TransferDetailAct extends BaseActivity {
    private TransferDetailActBinding binding;
    private TransferDetailVM         detailVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.transfer_detail_act);
        int type = getIntent().getIntExtra(BundleKeys.TRANSFERTYPE, Constant.NUMBER_0);
        if (type == Constant.NUMBER_1) {
            final TransferMo mo = (TransferMo) getIntent().getParcelableExtra(BundleKeys.TRANSFERMO);
            detailVM = new TransferDetailVM(binding, mo,type);
            binding.transferStop.setVisibility(View.VISIBLE);
            binding.transferStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserLogic.transferRecall(String.valueOf(mo.getId()), null);
                }
            });
            //            if (mo.isCancelSellingBondFlag()) {
            //                binding.transferStop.setVisibility(View.VISIBLE);
            //            } else {
            //                binding.transferStop.setVisibility(View.GONE);
            //            }
        } else {
            TransferMo mo = (TransferMo) getIntent().getParcelableExtra(BundleKeys.TRANSFERMO);
            detailVM = new TransferDetailVM(binding, mo,type);
            binding.transferStop.setVisibility(View.GONE);
        }
        binding.setViewModel(detailVM);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.ir_detail);
    }
}