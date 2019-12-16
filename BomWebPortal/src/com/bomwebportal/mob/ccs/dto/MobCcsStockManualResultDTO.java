package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class MobCcsStockManualResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -912438097919404190L;

	private String itemCode;
	private String mrt;
	private String orderId;
	private Date appnDate;
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getMrt() {
		return mrt;
	}
	public void setMrt(String mrt) {
		this.mrt = mrt;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getAppnDate() {
		return appnDate;
	}
	public void setAppnDate(Date appnDate) {
		this.appnDate = appnDate;
	}

}
