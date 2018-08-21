package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/27 下午9:02
 * <p/>
 * Description:
 */
public class AutoLogMo {
    /**
     * addtime : 1469606826000
     * borrowName : 测试自动投标12312
     * id : 111
     * remark : 自动投标成功
     * status : 1
     * tenderMoney : 1000
     * uuid : 1607271739271830
     */
    private long addtime;
    private String borrowName;
    private int    id;
    private String remark;
    private int    status;
    private int    tenderMoney;
    private String uuid;

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTenderMoney() {
        return tenderMoney;
    }

    public void setTenderMoney(int tenderMoney) {
        this.tenderMoney = tenderMoney;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
