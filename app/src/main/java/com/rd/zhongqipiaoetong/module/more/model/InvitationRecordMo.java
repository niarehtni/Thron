package com.rd.zhongqipiaoetong.module.more.model;

/**
 * Author: JinF
 * E-mail: jf@erongdu.com
 * Date: 2016/10/21 14:32
 * <p/>
 * Description: 好友记录
 */
public class InvitationRecordMo {
    /**
     * invitedTime : 1472191209000
     * inviterName : 17312340001
     * name : 1***2
     */
    private long   invitedTime;
    private String inviterName;
    private String name;

    public long getInvitedTime() {
        return invitedTime;
    }

    public void setInvitedTime(long invitedTime) {
        this.invitedTime = invitedTime;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
