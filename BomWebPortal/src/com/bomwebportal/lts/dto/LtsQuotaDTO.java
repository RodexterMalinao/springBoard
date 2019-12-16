package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class LtsQuotaDTO implements Serializable {

	private static final long serialVersionUID = 2162540730227531296L;

	private String programCd;
	private String programDesc;
	private String psefCd;
	private int quota;
	private int quotaUsed;
	private int currentQuotaUsed;
	
	public String getProgramCd() {
		return programCd;
	}
	public void setProgramCd(String programCd) {
		this.programCd = programCd;
	}
	public String getProgramDesc() {
		return programDesc;
	}
	public void setProgramDesc(String programDesc) {
		this.programDesc = programDesc;
	}
	public String getPsefCd() {
		return psefCd;
	}
	public void setPsefCd(String psefCd) {
		this.psefCd = psefCd;
	}
	public List<String> getPsefCdList(){
		if(psefCd != null){
			return Arrays.asList(psefCd.split("|"));
		}
		return null;
	}
	public int getQuota() {
		return quota;
	}
	public void setQuota(int quota) {
		this.quota = quota;
	}
	public int getQuotaUsed() {
		return quotaUsed;
	}
	public void setQuotaUsed(int quotaUsed) {
		this.quotaUsed = quotaUsed;
	}
	public int getCurrentQuotaUsed() {
		return currentQuotaUsed;
	}
	public void setCurrentQuotaUsed(int currentQuotaUsed) {
		this.currentQuotaUsed = currentQuotaUsed;
	}
	
	
}
