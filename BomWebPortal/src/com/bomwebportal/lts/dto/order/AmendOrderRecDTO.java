package com.bomwebportal.lts.dto.order;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class AmendOrderRecDTO extends ObjectActionBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5966562427424483211L;
	
	private String wqWpAssgnId;
	private String serviceNum;
	private String date;
	private String remarks;
	private String workingParty;
	private String wqSerial;
	private String wqStatus;
	private String wqStatusCd;
	private String nature;
	private String natureId;
	
	private boolean enableStatusChg = false;
	private boolean enableAddRemarks = false;
	private boolean enableShowMore = false;
	
	public String getWqWpAssgnId() {
		return this.wqWpAssgnId;
	}
	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = pWqWpAssgnId;
	}
	public String getServiceNum() {
		return this.serviceNum;
	}
	public void setServiceNum(String pServiceNum) {
		this.serviceNum = pServiceNum;
	}
	public String getDate() {
		return this.date;
	}
	public void setDate(String pDate) {
		this.date = pDate;
	}
	public String getRemarks() {
		return this.remarks;
	}
	public String getRemarksJavaScript(){
		return StringUtils.isEmpty(this.remarks)? this.remarks: StringUtils.replace(this.remarks, "'", "\\'");
	}
	public void setRemarks(String pRemarks) {
		this.remarks = pRemarks;
	}
	public String getWorkingParty() {
		return this.workingParty;
	}
	public void setWorkingParty(String pWorkingParty) {
		this.workingParty = pWorkingParty;
	}
	public String getWqSerial() {
		return this.wqSerial;
	}
	public void setWqSerial(String pWqSerial) {
		this.wqSerial = pWqSerial;
	}
	public String getWqStatus() {
		return this.wqStatus;
	}
	
	public String getWqStatusCd() {
		return this.wqStatusCd;
	}
	public void setWqStatusCd(String pWqStatusCd) {
		this.wqStatusCd = pWqStatusCd;
	}
	public void setWqStatus(String pWqStatus) {
		this.wqStatus = pWqStatus;
	}
	public String getNature() {
		return this.nature;
	}
	public void setNature(String pNature) {
		this.nature = pNature;
	}
	public boolean isEnableStatusChg() {
		return this.enableStatusChg;
	}
	public void setEnableStatusChg(boolean pEnableStatusChg) {
		this.enableStatusChg = pEnableStatusChg;
	}
	public boolean isEnableAddRemarks() {
		return this.enableAddRemarks;
	}
	public void setEnableAddRemarks(boolean pEnableAddRemarks) {
		this.enableAddRemarks = pEnableAddRemarks;
	}
	public boolean isEnableShowMore() {
		return this.enableShowMore;
	}
	public void setEnableShowMore(boolean pEnableShowMore) {
		this.enableShowMore = pEnableShowMore;
	}
	public String getNatureId() {
		return natureId;
	}
	public void setNatureId(String natureId) {
		this.natureId = natureId;
	}

}
