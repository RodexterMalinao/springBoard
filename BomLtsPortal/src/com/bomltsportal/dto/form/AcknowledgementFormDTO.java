package com.bomltsportal.dto.form;

import java.io.Serializable;

public class AcknowledgementFormDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1448298118982067044L;
	
	private String installDate;
	private String installTime;
	private String installAddress;
	
	public String getInstallDate() {
		return installDate;
	}
	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}
	public String getInstallTime() {
		return installTime;
	}
	public void setInstallTime(String installTime) {
		this.installTime = installTime;
	}
	public String getInstallAddress() {
		return installAddress;
	}
	public void setInstallAddress(String installAddress) {
		this.installAddress = installAddress;
	}
	
	
}
