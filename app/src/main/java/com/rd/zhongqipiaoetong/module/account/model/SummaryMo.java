package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Created by pzw on 2017/6/19.
 */
public class SummaryMo {
    private AccountMo accountVO;
    private boolean hasExpBorrow;

    public AccountMo getAccountVO() {
        return accountVO;
    }

    public void setAccountVO(AccountMo accountVO) {
        this.accountVO = accountVO;
    }

    public boolean isHasExpBorrow() {
        return hasExpBorrow;
    }

    public void setHasExpBorrow(boolean hasExpBorrow) {
        this.hasExpBorrow = hasExpBorrow;
    }
}
