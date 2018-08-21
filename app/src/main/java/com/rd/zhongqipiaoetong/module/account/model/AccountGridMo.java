package com.rd.zhongqipiaoetong.module.account.model;

import android.graphics.drawable.Drawable;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 16/8/11 上午11:31
 * <p/>
 * Description:
 */
public class AccountGridMo {
    Drawable drawbleId;
    int      titleId;

    public AccountGridMo(Drawable drawbleId, int titleId) {
        this.drawbleId = drawbleId;
        this.titleId = titleId;
    }

    public Drawable getDrawbleId() {
        return drawbleId;
    }

    public void setDrawbleId(Drawable drawbleId) {
        this.drawbleId = drawbleId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }
}
