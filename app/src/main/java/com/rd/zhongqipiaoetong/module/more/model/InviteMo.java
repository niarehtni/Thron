package com.rd.zhongqipiaoetong.module.more.model;

import java.io.Serializable;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 分享实体类
 * Created by Administrator on 2016/5/27.
 */
public class InviteMo implements Serializable {
    private String           url;
    private List<InviteList> inviteList;
    private String           webPhone;
    private String           serviceQQ;
    private String           serviceTime;
    private int              page_total;
    private int              page;
    private int              totalPage;

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

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public String getWebPhone() {
        return webPhone;
    }

    public void setWebPhone(String webPhone) {
        this.webPhone = webPhone;
    }

    public String getServiceQQ() {
        return serviceQQ;
    }

    public void setServiceQQ(String serviceQQ) {
        this.serviceQQ = serviceQQ;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<InviteList> getInviteList() {
        return inviteList;
    }

    public void setInviteList(List<InviteList> inviteList) {
        this.inviteList = inviteList;
    }

    public class InviteList implements Serializable {
        private long   inviteTime;
        private String inviteUsername;
        private String invitedUsername;

        public long getInviteTime() {
            return inviteTime;
        }

        public void setInviteTime(long inviteTime) {
            this.inviteTime = inviteTime;
        }

        public String getInviteUsername() {
            return inviteUsername;
        }

        public void setInviteUsername(String inviteUsername) {
            this.inviteUsername = inviteUsername;
        }

        public String getInvitedUsername() {
            return invitedUsername;
        }

        public void setInvitedUsername(String invitedUsername) {
            this.invitedUsername = invitedUsername;
        }
    }

    private boolean isOver() {
        return totalPage < page;
    }

    public void isOver(PtrFrameLayout ptrFrame) {
        if (isOver()) {
            ptrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        } else {
            ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        }
    }
}
