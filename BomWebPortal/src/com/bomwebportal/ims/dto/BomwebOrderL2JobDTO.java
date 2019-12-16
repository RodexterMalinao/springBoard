package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class BomwebOrderL2JobDTO implements Serializable {
	private String orderId;
	private String dtlId;
	private String dtlSeq;
	private String productId;
	private String l2Cd;
	private String ftInd;
	private String l2Qty;
	private String actInd;
	private String lastUpdDate;
	private String lastUpdBy;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDtlId() {
		return dtlId;
	}
	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}
	public String getDtlSeq() {
		return dtlSeq;
	}
	public void setDtlSeq(String dtlSeq) {
		this.dtlSeq = dtlSeq;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getL2Cd() {
		return l2Cd;
	}
	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}
	public String getFtInd() {
		return ftInd;
	}
	public void setFtInd(String ftInd) {
		this.ftInd = ftInd;
	}
	public String getL2Qty() {
		return l2Qty;
	}
	public void setL2Qty(String l2Qty) {
		this.l2Qty = l2Qty;
	}
	public String getActInd() {
		return actInd;
	}
	public void setActInd(String actInd) {
		this.actInd = actInd;
	}
	public String getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(String lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	
	
}
