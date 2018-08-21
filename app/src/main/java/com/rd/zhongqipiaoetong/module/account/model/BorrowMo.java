package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.module.product.model.ProjectInfo;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/11 16:56
 * <p/>
 * Description: 借款标信息
 */
public class BorrowMo extends ProjectInfo {
    /**
     * 借款总额
     */
    private String allAmount;
    /**
     * 以募集的金额
     */
    private String collectAmount;

    @Override
    protected void definitionStatus(String status) {
        // 状态 0-招标中 1-初审未通过 2-满标待审 3-还款中 4-复审未通过 5-已撤回 6-流标 7-已还款
        switch (status) {
            case Constant.STATUS_0:
                statusStr = Constant.STATUS_LOAN_ZBZ;
                break;

            case Constant.STATUS_1:
                statusStr = Constant.STATUS_LOAN_CSWTG;
                break;

            case Constant.STATUS_2:
                statusStr = Constant.STATUS_LOAN_MBDS;
                break;

            case Constant.STATUS_3:
                statusStr = Constant.STATUS_LOAN_HZK;
                break;

            case Constant.STATUS_4:
                statusStr = Constant.STATUS_LOAN_FSWTG;
                break;

            case Constant.STATUS_5:
                statusStr = Constant.STATUS_LOAN_YCH;
                break;

            case Constant.STATUS_6:
                statusStr = Constant.STATUS_LOAN_LB;
                break;

            case Constant.STATUS_7:
                statusStr = Constant.STATUS_LOAN_YHK;
                break;
        }
    }

    @Override
    protected void definitionType(String type) {
    }

    public BorrowMo() {
    }

    public String getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(String allAmount) {
        this.allAmount = allAmount;
    }

    public String getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(String collectAmount) {
        this.collectAmount = collectAmount;
    }
}