package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Created by pzw on 2018/1/23.
 */
public class BorrowRepaymentMo {
    private String id;
    private String borrowId;
    private String repaymntAmount;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getRepaymntAmount() {
        return repaymntAmount;
    }

    public void setRepaymntAmount(String repaymntAmount) {
        this.repaymntAmount = repaymntAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
