package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class OrdStsHistDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 561514901986590055L;
	
	private String OrderId;
	private String OrderStatus;
	private String ReasonCd;
	private Date LastUpdDate;
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}
	public String getReasonCd() {
		return ReasonCd;
	}
	public void setReasonCd(String reasonCd) {
		ReasonCd = reasonCd;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}

	
	

}
