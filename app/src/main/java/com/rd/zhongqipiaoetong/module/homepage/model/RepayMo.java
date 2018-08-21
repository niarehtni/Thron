package com.rd.zhongqipiaoetong.module.homepage.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 16/8/18 上午11:44
 * <p/>
 * Description:
 */
public class RepayMo {
    public HashMap<String, String> repayTypes;

    public HashMap<String, String> getRepayTypes() {
        return repayTypes;
    }

    public void setRepayTypes(HashMap<String, String> repayTypes) {
        this.repayTypes = repayTypes;
    }

    public boolean isCheckKey(String key) {
        if (repayTypes == null) {
            return false;
        }
        for (Map.Entry<String, String> entry : repayTypes.entrySet()) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
}
