package com.rd.zhongqipiaoetong.module.account.model;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by xyy on 2016/11/1.
 */
public class RechargeRecodMoList {
    private int                    totalPage;
    private int                    page;
    private int                    page_total;

    private List<RechargeRecordMo> rechargeList;

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<RechargeRecordMo> getRechargeList() {
        return rechargeList;
    }

    public void setRechargeList(List<RechargeRecordMo> rechargeList) {
        this.rechargeList = rechargeList;
    }

    /**
     * a
     *
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
