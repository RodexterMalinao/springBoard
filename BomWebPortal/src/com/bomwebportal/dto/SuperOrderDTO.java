package com.bomwebportal.dto;

import com.bomwebportal.mobquota.dto.QuotaConsumeRequest;


public class SuperOrderDTO {

	private String orderId;
	private String orderSalesCd;
	private String orderShopCd;
	private QuotaConsumeRequest quotaConsumeRequest;
	private boolean checkIsWhiteList;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderSalesCd() {
		return orderSalesCd;
	}
	public void setOrderSalesCd(String orderSalesCd) {
		this.orderSalesCd = orderSalesCd;
	}
	public String getOrderShopCd() {
		return orderShopCd;
	}
	public void setOrderShopCd(String orderShopCd) {
		this.orderShopCd = orderShopCd;
	}
	public QuotaConsumeRequest getQuotaConsumeRequest() {
		return quotaConsumeRequest;
	}
	public void setQuotaConsumeRequest(QuotaConsumeRequest quotaConsumeRequest) {
		this.quotaConsumeRequest = quotaConsumeRequest;
	}
	public boolean isCheckIsWhiteList() {
		return checkIsWhiteList;
	}
	public void setCheckIsWhiteList(boolean checkIsWhiteList) {
		this.checkIsWhiteList = checkIsWhiteList;
	}
	
}
