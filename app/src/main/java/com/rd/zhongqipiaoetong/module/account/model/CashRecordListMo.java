package com.rd.zhongqipiaoetong.module.account.model;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by pzw on 2017/8/21.
 */
public class CashRecordListMo {
    private List<CashRecordMo> creditorList;
    private int page_total;
    private int page;

    public List<CashRecordMo> getCreditorList() {
        return creditorList;
    }

    public void setCreditorList(List<CashRecordMo> creditorList) {
        this.creditorList = creditorList;
    }

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return 是否已经显示完全
     * true - 已经是最后页，无需再次请求，loadMore 无需再显示
     * false - 还不是最后页，需要再次请求数据，loadMore 需要再显示
     */
    public boolean isOver() {
        return page_total <= page;
    }

    public void isOver(PtrFrameLayout ptrFrame) {
        if (isOver()) {
            ptrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        } else {
            ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        }
    }
}
