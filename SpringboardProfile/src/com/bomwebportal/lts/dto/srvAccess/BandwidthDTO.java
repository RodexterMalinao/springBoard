package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class BandwidthDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5816889184194678229L;

	private String[] bandwidth = null;
	private String gnRetVal = null;               
	private String gnErrCode = null;              
	private String gsErrText = null;

	
	public String getGnRetVal() {
		return this.gnRetVal;
	}

	public void setGnRetVal(String pGnRetVal) {
		this.gnRetVal = pGnRetVal;
	}

	public String getGnErrCode() {
		return this.gnErrCode;
	}

	public void setGnErrCode(String pGnErrCode) {
		this.gnErrCode = pGnErrCode;
	}

	public String getGsErrText() {
		return this.gsErrText;
	}

	public void setGsErrText(String pGsErrText) {
		this.gsErrText = pGsErrText;
	}

	public String[] getBandwidth() {
		return this.bandwidth;
	}

	public void setBandwidth(String[] pBandwidth) {
		this.bandwidth = pBandwidth;
	}
	

	
	
	
}
