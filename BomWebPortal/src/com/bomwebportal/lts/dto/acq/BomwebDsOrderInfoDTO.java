package com.bomwebportal.lts.dto.acq;

import java.io.Serializable;

public class BomwebDsOrderInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3464019916435283776L;
	private String orderId;
	private String shopCd;
	private String transactionCd;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getTransactionCd() {
		return transactionCd;
	}
	public void setTransactionCd(String transactionCd) {
		this.transactionCd = transactionCd;
	}	
}
