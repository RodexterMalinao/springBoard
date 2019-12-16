package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class MaintParmLkupDTO {	
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getFunctionCd() {
		return functionCd;
	}
	public void setFunctionCd(String functionCd) {
		this.functionCd = functionCd;
	}
	public String getParmType() {
		return parmType;
	}
	public void setParmType(String parmType) {
		this.parmType = parmType;
	}
	public String getParmValue() {
		return parmValue;
	}
	public void setParmValue(String parmValue) {
		this.parmValue = parmValue;
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
	private String channelCd;
	private String lob;
	private String functionCd;
	private String parmType;
	private String parmValue;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
}
