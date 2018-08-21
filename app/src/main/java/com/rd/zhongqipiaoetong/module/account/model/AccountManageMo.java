package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Created by pzw on 2018/1/22.
 */
public class AccountManageMo {
    //正在借款金额
    private String amountBorrowing = "0";
    //待还总金额
    private String amountToBeBack = "0";
    //正在借款项目
    private String projectBorrowing = "0";
    //已还金额
    private String repaidTotal = "0";

    public String getAmountBorrowing() {
        return amountBorrowing;
    }

    public void setAmountBorrowing(String amountBorrowing) {
        this.amountBorrowing = amountBorrowing;
    }

    public String getAmountToBeBack() {
        return amountToBeBack;
    }

    public void setAmountToBeBack(String amountToBeBack) {
        this.amountToBeBack = amountToBeBack;
    }

    public String getProjectBorrowing() {
        return projectBorrowing;
    }

    public void setProjectBorrowing(String projectBorrowing) {
        this.projectBorrowing = projectBorrowing;
    }

    public String getRepaidTotal() {
        return repaidTotal;
    }

    public void setRepaidTotal(String repaidTotal) {
        this.repaidTotal = repaidTotal;
    }
}
