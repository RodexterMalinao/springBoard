package com.bomltsportal.dto.form;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.SrdDTO;

public class AmendmentFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4288510178922450473L;
	private boolean allowAmend;
	private String notAllowMsg;
	
	private String title;
	private String familyName;
	private String addr;
	private SrdDTO earliestSrd;
	private String installDate;
	private String installTimeAndType;
	private String installTime;
	private String installTimeType;
	private String prebookSerialNum;

	private String originalInstallDate;
	private String originalInstallTime;
	
	private String userId;
	
	
	public boolean isAllowAmend() {
		return allowAmend;
	}
	public void setAllowAmend(boolean allowAmend) {
		this.allowAmend = allowAmend;
	}
	public String getNotAllowMsg() {
		return notAllowMsg;
	}
	public void setNotAllowMsg(String notAllowMsg) {
		this.notAllowMsg = notAllowMsg;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public SrdDTO getEarliestSrd() {
		return earliestSrd;
	}
	public void setEarliestSrd(SrdDTO earliestSrd) {
		this.earliestSrd = earliestSrd;
	}
	public String getInstallDate() {
		return installDate;
	}
	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}
	public String getInstallTimeAndType() {
		if(this.installTimeAndType != null){
			return this.installTimeAndType;
		}
		return installTime + "||" + installTimeType;
	}

	public void setInstallTimeAndType(String installTimeAndType) {
		this.installTimeAndType = installTimeAndType;
		if(StringUtils.isNotEmpty(installTimeAndType)){
			String[] timeAndType = StringUtils.split(installTimeAndType, "||");
			this.installTime = timeAndType[0];
			this.installTimeType = timeAndType[1];
		}
	}
	public String getInstallTime() {
		return installTime;
	}
	public void setInstallTime(String installTime) {
		this.installTime = installTime;
	}
	public String getInstallTimeType() {
		return installTimeType;
	}
	public void setInstallTimeType(String installTimeType) {
		this.installTimeType = installTimeType;
	}
	public String getPrebookSerialNum() {
		return prebookSerialNum;
	}
	public void setPrebookSerialNum(String prebookSerialNum) {
		this.prebookSerialNum = prebookSerialNum;
	}
	public String getOriginalInstallDate() {
		return originalInstallDate;
	}
	public void setOriginalInstallDate(String originalInstallDate) {
		this.originalInstallDate = originalInstallDate;
	}
	public String getOriginalInstallTime() {
		return originalInstallTime;
	}
	public void setOriginalInstallTime(String originalInstallTime) {
		this.originalInstallTime = originalInstallTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
