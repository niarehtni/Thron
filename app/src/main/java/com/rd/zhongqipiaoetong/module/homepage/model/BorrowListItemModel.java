package com.rd.zhongqipiaoetong.module.homepage.model;

import android.databinding.ObservableField;

public class BorrowListItemModel extends ObservableField<BorrowListItemModel> {

	/**
	 * 标的ID
	 */
	private String id;
	
	/**
	 * 标的UUID
	 */
	private String uuid;
	
	/**
	 * 标的名称
	 */
	private String name;
	
	/**
	 * 年化利率
	 */
	private double apr;
	
	/**
	 * 借款总额
	 */
	private double borrowAmount;
	
	/**
	 * 借款时间类型
	 */
	private String borrowTimeType;
	
	/**
	 * 借款期限
	 */
	private int borrowTimeLimit;
	
	/**
	 * 投标进度
	 */
	private double progress;
	
	/**
	 * 标的状态
	 */
	private String status;
	
	/**
	 * 最低起投
	 */
	private double lowestAccount;
	
	/**
	 * 剩余金额
	 */
	private double accountWait;
	
	/**
	 * 是否定向标
	 */
	private boolean isDirectional;
	
	/**
	 * 是否推荐标
	 */
	private boolean isRecommended;
	
	/**
	 * 借款标类型
	 */
	private int type;
	
	/**
	 * 创建时间
	 */
	private Long addTime;
	
	/**
	 * 是否新手标
	 */
	private boolean isNewbieOnly;
	
	/**
	 * 查看此标详情是否需要先登录
	 */
	private boolean isLogonRequired;

	//--------------------------华丽分割线----------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getApr() {
		return apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	public double getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public String getBorrowTimeType() {
		return borrowTimeType;
	}

	public void setBorrowTimeType(String borrowTimeType) {
		this.borrowTimeType = borrowTimeType;
	}

	public int getBorrowTimeLimit() {
		return borrowTimeLimit;
	}

	public void setBorrowTimeLimit(int borrowTimeLimit) {
		this.borrowTimeLimit = borrowTimeLimit;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getLowestAccount() {
		return lowestAccount;
	}

	public void setLowestAccount(double lowestAccount) {
		this.lowestAccount = lowestAccount;
	}

	public double getAccountWait() {
		return accountWait;
	}

	public void setAccountWait(double accountWait) {
		this.accountWait = accountWait;
	}

	public boolean isDirectional() {
		return isDirectional;
	}

	public void setDirectional(boolean isDirectional) {
		this.isDirectional = isDirectional;
	}

	public boolean isRecommended() {
		return isRecommended;
	}

	public void setRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public boolean isNewbieOnly() {
		return isNewbieOnly;
	}

	public void setNewbieOnly(boolean isNewbieOnly) {
		this.isNewbieOnly = isNewbieOnly;
	}

	public boolean isLogonRequired() {
		return isLogonRequired;
	}

	public void setLogonRequired(boolean isLogonRequired) {
		this.isLogonRequired = isLogonRequired;
	}
	
}
