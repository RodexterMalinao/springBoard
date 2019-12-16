package com.bomwebportal.dto;//Paper bill Athena 20130925 (Whole DTO)

import java.util.Date;

public class MobBillMediaDTO {
//Athena 20130925
	private String orderId;
	private String billMediaCode;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	
	private String codeType;
	private String codeId;
	private String codeDesc;
	private String authorizeInd;
	private String engDesc;
	private String chiDesc;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBillMediaCode() {
		return billMediaCode;
	}
	public void setBillMediaCode(String billMediaCode) {
		this.billMediaCode = billMediaCode;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeDesc() {
		return codeDesc;
	}
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
	public String getAuthorizeInd() {
		return authorizeInd;
	}
	public void setAuthorizeInd(String authorizeInd) {
		this.authorizeInd = authorizeInd;
	}
	public String getEngDesc() {
		return engDesc;
	}
	public void setEngDesc(String engDesc) {
		this.engDesc = engDesc;
	}
	public String getChiDesc() {
		return chiDesc;
	}
	public void setChiDesc(String chiDesc) {
		this.chiDesc = chiDesc;
	}

}
