package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableField;

import com.rd.zhongqipiaoetong.module.account.activity.LoanDetailAct;
import com.rd.zhongqipiaoetong.module.account.model.BorrowMo;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/14 15:29
 * <p/>
 * Description: 借款详情VM({@link LoanDetailAct})
 */
public class LoanDetailVM {
    public final ObservableField<BorrowMo> borrow = new ObservableField<>();

    public LoanDetailVM() {
        req_data();
    }

    private void req_data() {
        BorrowMo mo = new BorrowMo();
        mo.setName("借款理财产品001号");
        mo.setAllAmount("100000");
        mo.setCollectAmount("50000");
        mo.setStatus("3");
        mo.setTypeStr("担保标");
        mo.setRateYear("10");
        mo.setTimeLimit("90");
//        mo.setRecordCount("3");
        borrow.set(mo);
    }
}