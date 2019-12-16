package com.bomwebportal.dto;

public class CustomerInformationQuotaDTO implements java.io.Serializable{
	
	private String idDocNum;
	private String quotaId;
	private String quotaDesc;
	private int quotaCeiling;
	private int quotaInUse;
	

	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	public String getQuotaId() {
		return quotaId;
	}
	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}
	public String getQuotaDesc() {
		return quotaDesc;
	}
	public void setQuotaDesc(String quotaDesc) {
		this.quotaDesc = quotaDesc;
	}
	public int getQuotaCeiling() {
		return quotaCeiling;
	}
	public void setQuotaCeiling(int quotaCeiling) {
		this.quotaCeiling = quotaCeiling;
	}
	public int getQuotaInUse() {
		return quotaInUse;
	}
	public void setQuotaInUse(int quotaInUse) {
		this.quotaInUse = quotaInUse;
	}

	
	

}
