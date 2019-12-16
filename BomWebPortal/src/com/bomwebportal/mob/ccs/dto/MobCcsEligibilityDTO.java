package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class MobCcsEligibilityDTO {
	public enum IdDocType {
		HKID // HKID
		, PASS // Passport
		, BS // BR
		//, CERT // Certicate No
		;
	}
	public IdDocType getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(IdDocType idDocType) {
		this.idDocType = idDocType;
	}
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	public String getValuePropId() {
		return valuePropId;
	}
	public void setValuePropId(String valuePropId) {
		this.valuePropId = valuePropId;
	}
	public String getValuePropDesc() {
		return valuePropDesc;
	}
	public void setValuePropDesc(String valuePropDesc) {
		this.valuePropDesc = valuePropDesc;
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
	private IdDocType idDocType;
	private String idDocNum;
	private String valuePropId;
	private String valuePropDesc;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
}
