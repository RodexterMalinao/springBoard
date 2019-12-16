package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class ForceCancelDTO {
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOcid() {
		return ocid;
	}
	public void setOcid(String ocid) {
		this.ocid = ocid;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	public Date getMaxFalloutDate() {
		return maxFalloutDate;
	}
	public void setMaxFalloutDate(Date maxFalloutDate) {
		this.maxFalloutDate = maxFalloutDate;
	}
	public int getFalloutDateDiff() {
		return falloutDateDiff;
	}
	public void setFalloutDateDiff(int falloutDateDiff) {
		this.falloutDateDiff = falloutDateDiff;
	}
	public boolean isHottestModel() {
		return hottestModel;
	}
	public void setHottestModel(boolean hottestModel) {
		this.hottestModel = hottestModel;
	}
	public int getDeliveryFailCount() {
		return deliveryFailCount;
	}
	public void setDeliveryFailCount(int deliveryFailCount) {
		this.deliveryFailCount = deliveryFailCount;
	}
	public boolean isForceCancelled() {
		return forceCancelled;
	}
	public void setForceCancelled(boolean forceCancelled) {
		this.forceCancelled = forceCancelled;
	}
	public String getForceCancelCode() {
		return forceCancelCode;
	}
	public void setForceCancelCode(String forceCancelCode) {
		this.forceCancelCode = forceCancelCode;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public int getLastUpdateDateDiff() {
		return lastUpdateDateDiff;
	}
	public void setLastUpdateDateDiff(int lastUpdateDateDiff) {
		this.lastUpdateDateDiff = lastUpdateDateDiff;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public int getAppDateHrDiff() {
		return appDateHrDiff;
	}
	public void setAppDateHrDiff(int appDateHrDiff) {
		this.appDateHrDiff = appDateHrDiff;
	}
	private String orderId;
	private String ocid;
	private String orderStatus;
	private String checkPoint;
	private String reasonCd;
	private Date maxFalloutDate;
	private int falloutDateDiff;
	private boolean hottestModel;
	private int deliveryFailCount;
	private boolean forceCancelled;
	private String forceCancelCode;
	private String shopCd;
	private Date lastUpdateDate;
	private int lastUpdateDateDiff;
	private Date appDate;
	private int appDateHrDiff;
}
