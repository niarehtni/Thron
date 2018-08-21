package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.module.account.viewmodel.BankCardListVM;

import java.util.List;

/**
 * Description: 银行卡列表({@link BankCardListVM})信息
 */
public class BankCardListMo {
    private String tppType;
    /**
     * 授权类型（联动优势独有）
     */
    private String authorizeType;

    /**
     * 银行卡集合
     */
    private List<BankCardMo> bankCardList;

    private TppConfineMo tppConfine;


    //--------------------------华丽分割线----------------------------------

    public TppConfineMo getTppConfine() {
        return tppConfine;
    }

    public void setTppConfine(TppConfineMo tppConfine) {
        this.tppConfine = tppConfine;
    }

    public String getTppType() {
        return tppType;
    }

    public void setTppType(String tppType) {
        this.tppType = tppType;
    }

    public String getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(String authorizeType) {
        this.authorizeType = authorizeType;
    }

    public List<BankCardMo> getBankCardList() {
        return bankCardList;
    }

    public void setBankCardList(List<BankCardMo> bankCardList) {
        this.bankCardList = bankCardList;
    }
}