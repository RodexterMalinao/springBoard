package com.bomwebportal.lts.dto.order;

import java.io.Serializable;

public class OrderIdDTO implements Serializable {

	private static final long serialVersionUID = 4606839537687812780L;
	
	private String orderId = null;
	private String boc = null;
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBoc() {
		return boc;
	}
	public void setBoc(String boc) {
		this.boc = boc;
	}
}
