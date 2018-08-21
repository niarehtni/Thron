package com.rd.zhongqipiaoetong.module.account.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.activity.InvestmentDetailAct;
import com.rd.zhongqipiaoetong.module.account.model.CashRecordDetailMo;
import com.rd.zhongqipiaoetong.module.account.model.InvestRecordDetailCollectionItemMo;
import com.rd.zhongqipiaoetong.module.account.model.InvestRecordDetailMo;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.view.DividerLine;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/4 18:20
 * <p/>
 * Description: 投投资详情({@link InvestmentDetailAct}) - 回款信息界面
 */
public class InvestmentReceivedFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding    binding;
    private BaseRecyclerViewVM<String[]> detailVM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);
        detailVM = new BaseRecyclerViewVM<String[]>() {
            @Override
            protected void selectView(ItemView itemView, int position, String[] item) {
                if (item.length == 2) {
                    itemView.set(BR.item, R.layout.account_investment_received_item1);
                } else if (item.length == 1) {
                    itemView.set(BR.item, R.layout.account_investment_received_item2);
                } else {
                    itemView.set(BR.item, R.layout.account_investment_received_item3);
                }
            }
        };
        detailVM.type = DividerLine.DEFAULT;
        detailVM.emptyState.setLoading(false);
        binding.setViewModel(detailVM);
        binding.recycler.setNestedScrollingEnabled(false);
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        // 如果加载页面可见，则表示初次进入界面，需要请求数据
        if (detailVM.emptyState.isLoading()) {
            detailVM.emptyState.setLoading(false);
        }
    }

    /**
     * 填写数据
     *
     * @param mo
     */
    public void setRecordData(InvestRecordDetailMo mo) {
        detailVM.items.addAll(init(getResources().getStringArray(R.array.investmentReceivedItemDesc), mo));
        if (mo.getStatus() != 1) {
            detailVM.emptyState.setPrompt(R.string.empty_repaymentlog);
        }
    }

    /**
     * 数据转换
     */
    private List<String[]> init(String[] desc, InvestRecordDetailMo mo) {
        List<String[]> list         = new ArrayList<>();
        String         totalCollect = "";
        if (mo.getClassify() == 4) {
            totalCollect = DisplayFormat.doubleMoney(DisplayFormat.stringToDouble(mo.getInterest()) + DisplayFormat.stringToDouble(mo.getPlatformInterest()));
        } else {
            totalCollect = DisplayFormat.doubleMoney(DisplayFormat.stringToDouble(mo.getInterest()) + DisplayFormat.stringToDouble(mo.getCapital
                    ()) + DisplayFormat.stringToDouble(mo.getPlatformInterest()));
        }
        list.add(new String[]{desc[0], totalCollect});
        if (mo.getNoPayPeriod() > 0) {
            list.add(new String[]{desc[1], DisplayFormat.coverTimeYYMMDD(mo.getNextPayTime())});
        }
        list.add(new String[]{desc[2], DisplayFormat.periodFormat(mo.getReturnList().size())});
        list.add(new String[]{desc[3], DisplayFormat.periodFormat(mo.getReturnList().size() - mo.getNoPayPeriod())});
        list.add(new String[]{getString(R.string.ir_hkjl)});
        for (InvestRecordDetailCollectionItemMo item : mo.getReturnList()) {
            list.add(new String[]{DisplayFormat.coverTimeYYMMDD(item.getRepaymentTime()) + "\n" + DisplayFormat.coverTimeHHmmss(item.getRepaymentTime()),
                    DisplayFormat
                            .doubleFormat(item.getRepaymentAmount()), getString(R.string
                    .ir_period, String.valueOf(item.getPeriod())), item.getStatusStr(), item.getPeriod() + ""});
        }
        return list;
    }

    /**
     * 填写数据 债转
     *
     * @param mo
     */
    public void setRecordData(CashRecordDetailMo mo) {
        detailVM.items.addAll(init(getResources().getStringArray(R.array.investmentReceivedItemDesc), mo));
        //        if (mo.getStatus() != 0) {
        //            detailVM.emptyState.setPrompt(R.string.empty_repaymentlog);
        //        }
    }

    /**
     * 数据转换 债转
     */
    private List<String[]> init(String[] desc, CashRecordDetailMo mo) {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{desc[0], DisplayFormat.doubleMoney(mo.getInvestMoney() + mo.getWaitInterest() + mo.getReceivedInterest())});
        if (mo.getNoPayPeriod() > 0) {
            list.add(new String[]{desc[1], DisplayFormat.coverTimeYYMMDD(mo.getNextPayTime())});
        }
        list.add(new String[]{desc[2], DisplayFormat.periodFormat(mo.getTotalPeriod())});
        list.add(new String[]{desc[3], DisplayFormat.periodFormat(mo.getTotalPeriod() - mo.getNoPayPeriod())});
        list.add(new String[]{getString(R.string.ir_hkjl)});
        for (InvestRecordDetailCollectionItemMo item : mo.getReturnList()) {
            list.add(new String[]{DisplayFormat.coverTimeYYMMDD(item.getRepaymentTime()) + "\n" + DisplayFormat.coverTimeHHmmss(item.getRepaymentTime()),
                    DisplayFormat
                            .doubleFormat(item.getRepaymentAmount()), getString(R.string
                    .ir_period, String.valueOf(item.getPeriod())), item.getStatusStr(), item.getPeriod() + ""});
        }
        return list;
    }
}