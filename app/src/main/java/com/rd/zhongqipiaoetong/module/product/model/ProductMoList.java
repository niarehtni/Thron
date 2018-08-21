package com.rd.zhongqipiaoetong.module.product.model;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by xyy on 2016/11/1.
 */
public class ProductMoList {
    private int         totalPage;
    private int         page;

    private List<FinancingMo> tenderList;

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

    public List<FinancingMo> getTenderList() {
        return tenderList;
    }

    public void setTenderList(List<FinancingMo> tenderList) {
        this.tenderList = tenderList;
    }

    /**a
     * @return 是否已经显示完全
     * true - 已经是最后页，无需再次请求，loadMore 无需再显示
     * false - 还不是最后页，需要再次请求数据，loadMore 需要再显示
     */
    public boolean isOver() {
        return totalPage <= page;
    }

    public void isOver(PtrFrameLayout ptrFrame) {
        if (isOver()) {
            ptrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        } else {
            ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        }
    }
}
