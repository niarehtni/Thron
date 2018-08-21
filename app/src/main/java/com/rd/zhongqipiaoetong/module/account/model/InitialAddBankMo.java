package com.rd.zhongqipiaoetong.module.account.model;

import java.util.ArrayList;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/20/16.
 */
public class InitialAddBankMo {
    ArrayList<OpeningBankMo> bankList;
    String                   realName;
    String                   sessionId;

    public ArrayList<OpeningBankMo> getBankList() {
        return bankList;
    }

    public void setAvailableBankList(ArrayList<OpeningBankMo> bankList) {
        this.bankList = bankList;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
