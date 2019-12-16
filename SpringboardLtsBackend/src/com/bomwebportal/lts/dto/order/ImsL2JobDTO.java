package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ImsL2JobDTO extends ObjectActionBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1356316348682736725L;
	private String orderId; // BOMWEB_ORDER_L2_JOB.ORDER_ID
	private String dtlId; // BOMWEB_ORDER_L2_JOB.DTL_ID
	private String dtlSeq; // BOMWEB_ORDER_L2_JOB.DTL_SEQ
	private String productId; // BOMWEB_ORDER_L2_JOB.PRODUCT_ID
	private String l2Cd; // BOMWEB_ORDER_L2_JOB.L2_CD
	private String ftInd; // BOMWEB_ORDER_L2_JOB.FT_IND
	private String l2Oty; // BOMWEB_ORDER_L2_JOB.L2_OTY
	private String actInd; // BOMWEB_ORDER_L2_JOB.ACT_IND
	
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
	public String getL2Oty() {
		return l2Oty;
	}
	public void setL2Oty(String l2Oty) {
		this.l2Oty = l2Oty;
	}
	public String getActInd() {
		return actInd;
	}
	public void setActInd(String actInd) {
		this.actInd = actInd;
	}
	
}
