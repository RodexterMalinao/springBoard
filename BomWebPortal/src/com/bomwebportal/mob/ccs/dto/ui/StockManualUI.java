package com.bomwebportal.mob.ccs.dto.ui;

import java.io.Serializable;

public class StockManualUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8365360540922674541L;
	
	private String startDate;
	private String endDate;
	private String orderStatus;
	private String orderId;
	private String searchCriteria;
	
	// add by Joyce 20120507
	private String reasonCode;
	private String checkPoint;
	
	private String storeCode;
	private String teamCode;
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartDate() {
		return startDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public String getSearchCriteria() {
		return searchCriteria;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

}
