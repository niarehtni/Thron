package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.module.account.activity.LoanDetailAct;
import com.rd.zhongqipiaoetong.module.account.activity.LoanManageAct;
import com.rd.zhongqipiaoetong.module.account.model.BorrowMo;
import com.rd.zhongqipiaoetong.module.account.model.LoanMo;
import com.rd.zhongqipiaoetong.view.entity.EmptyState;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/11 15:00
 * <p/>
 * Description: 借款管理VM({@link LoanManageAct})
 */
public class LoanManageVM {
    /**
     * 空态对象
     */
    public final EmptyState              emptyState = new EmptyState();
    public final ObservableField<LoanMo> item       = new ObservableField<>();
    public final ItemView                itemView   = ItemView.of(BR.item, R.layout.account_loan_content_item).setOnItemClickListener(new ItemView
            .OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            view.getContext().startActivity(new Intent(view.getContext(), LoanDetailAct.class));
        }
    });

    public LoanManageVM() {
        req_data();
    }

    /**
     * 网络请求
     */
    private void req_data() {
        BorrowMo mo1 = new BorrowMo();
        mo1.setName("金融产品增信项目第五期");
        mo1.setStatus("3");
        mo1.setAllAmount("15000");
        mo1.setCollectAmount("15000");
        mo1.setProgressPercentage("100");

        BorrowMo mo2 = new BorrowMo();
        mo2.setName("金融产品增信项目第四期");
        mo2.setStatus("1");
        mo2.setAllAmount("15000");
        mo2.setCollectAmount("0");
        mo2.setProgressPercentage("0");

        BorrowMo mo3 = new BorrowMo();
        mo3.setName("金融产品增信项目第三期");
        mo3.setStatus("0");
        mo3.setAllAmount("15000");
        mo3.setCollectAmount("550");
        mo3.setProgressPercentage("25");

        List<BorrowMo> list = new ArrayList<>();
        list.add(mo1);
        list.add(mo2);
        list.add(mo3);
        list.add(mo3);
        list.add(mo3);
        item.set(new LoanMo("100000", "98000", "5200", list));
    }

    /**
     * 还款
     */
    public void submit(View view) {

    }
}