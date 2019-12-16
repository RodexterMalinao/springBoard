package com.bomwebportal.mobquota.dto;

import java.util.Date;

public class MobQuotaInfoDTO {

	private String quotaId;
	private String engDesc;
	private String chiDesc;
	private int ceilCnt;
	
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
	private String effStartDate;
	
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
	private String effEndDate;
	


	public String getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
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

	public int getCeilCnt() {
		return ceilCnt;
	}

	public void setCeilCnt(int ceilCnt) {
		this.ceilCnt = ceilCnt;
	}

	public String getEffStartDate() {
		return effStartDate;
	}

	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}

	public String getEffEndDate() {
		return effEndDate;
	}

	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}

}
