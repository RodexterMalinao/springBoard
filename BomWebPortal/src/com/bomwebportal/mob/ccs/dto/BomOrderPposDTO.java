package com.bomwebportal.mob.ccs.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class BomOrderPposDTO {
	
	private String orderId;
	private String ocid;
	private String bomStatus;
	private String smNum;
	private BigDecimal smTotalAmt;
	private String pposPaymentStatus;
	private String smType;
	private String bomStatusDesc;
	private String smTypeDesc;
	private Date bomServReqDate;
	
	/**
	 * @return the bomServReqDate
	 */
	public Date getBomServReqDate() {
		return bomServReqDate;
	}
	/**
	 * @param bomServReqDate the bomServReqDate to set
	 */
	public void setBomServReqDate(Date bomServReqDate) {
		this.bomServReqDate = bomServReqDate;
	}
	/**
	 * @return the smTypeDesc
	 */
	public String getSmTypeDesc() {
		return smTypeDesc;
	}
	/**
	 * @param smTypeDesc the smTypeDesc to set
	 */
	public void setSmTypeDesc(String smTypeDesc) {
		this.smTypeDesc = smTypeDesc;
	}
	/**
	 * @return the bomStatusDesc
	 */
	public String getBomStatusDesc() {
		return bomStatusDesc;
	}
	/**
	 * @param bomStatusDesc the bomStatusDesc to set
	 */
	public void setBomStatusDesc(String bomStatusDesc) {
		this.bomStatusDesc = bomStatusDesc;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the ocid
	 */
	public String getOcid() {
		return ocid;
	}
	/**
	 * @param ocid the ocid to set
	 */
	public void setOcid(String ocid) {
		this.ocid = ocid;
	}
	/**
	 * @return the smNum
	 */
	public String getSmNum() {
		return smNum;
	}
	/**
	 * @param smNum the smNum to set
	 */
	public void setSmNum(String smNum) {
		this.smNum = smNum;
	}
	/**
	 * @return the smTotalAmt
	 */
	public BigDecimal getSmTotalAmt() {
		return smTotalAmt;
	}
	/**
	 * @param smTotalAmt the smTotalAmt to set
	 */
	public void setSmTotalAmt(BigDecimal smTotalAmt) {
		this.smTotalAmt = smTotalAmt;
	}
	/**
	 * @return the pposPaymentStatus
	 */
	public String getPposPaymentStatus() {
		return pposPaymentStatus;
	}
	/**
	 * @param pposPaymentStatus the pposPaymentStatus to set
	 */
	public void setPposPaymentStatus(String pposPaymentStatus) {
		this.pposPaymentStatus = pposPaymentStatus;
	}
	/**
	 * @return the smType
	 */
	public String getSmType() {
		return smType;
	}
	/**
	 * @param smType the smType to set
	 */
	public void setSmType(String smType) {
		this.smType = smType;
	}
	/**
	 * @return the bomStatus
	 */
	public String getBomStatus() {
		return bomStatus;
	}
	/**
	 * @param bomStatus the bomStatus to set
	 */
	public void setBomStatus(String bomStatus) {
		this.bomStatus = bomStatus;
	}
	
	
}
