package com.rd.zhongqipiaoetong.module.more.model;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by xyy on 2016/10/31.
 * desc: 消息列表封装类修改
 */
public class MsgMoList {

    private int      pageTotal;
    private int      page;

    private List<MsgMo> infoList;

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MsgMo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<MsgMo> infoList) {
        this.infoList = infoList;
    }

    /**a
     * @return 是否已经显示完全
     * true - 已经是最后页，无需再次请求，loadMore 无需再显示
     * false - 还不是最后页，需要再次请求数据，loadMore 需要再显示
     */
    public boolean isOver() {
        return pageTotal <= page;
    }

    public void isOver(PtrFrameLayout ptrFrame) {
        if (isOver()) {
            ptrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        } else {
            ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        }
    }
}
