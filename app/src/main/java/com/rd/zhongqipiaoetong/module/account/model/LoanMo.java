package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.module.account.viewmodel.LoanManageVM;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/11 16:51
 * <p/>
 * Description: 借款管理Mo({@link LoanManageVM})
 */
public class LoanMo {
    /**
     * 历史借款
     */
    private String         oldAmount;
    /**
     * 在借金额
     */
    private String         nowAmount;
    /**
     * 本期应还
     */
    private String         repayment;
    /**
     * 借款列表
     */
    private List<BorrowMo> borrowList;

    public LoanMo() {
    }

    public LoanMo(String oldAmount, String nowAmount, String repayment, List<BorrowMo> borrowList) {
        this.oldAmount = oldAmount;
        this.nowAmount = nowAmount;
        this.repayment = repayment;
        this.borrowList = borrowList;
    }

    public String getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(String oldAmount) {
        this.oldAmount = oldAmount;
    }

    public String getNowAmount() {
        return nowAmount;
    }

    public void setNowAmount(String nowAmount) {
        this.nowAmount = nowAmount;
    }

    public String getRepayment() {
        return repayment;
    }

    public void setRepayment(String repayment) {
        this.repayment = repayment;
    }

    public List<BorrowMo> getBorrowList() {
        return borrowList;
    }

    public void setBorrowList(List<BorrowMo> borrowList) {
        this.borrowList = borrowList;
    }
}