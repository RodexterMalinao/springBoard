package com.bomwebportal.lts.dto.acq;

import java.io.Serializable;

public class LtsAcqPdpoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4940046852538549035L;

	private String lob;
	private String optInd;
	
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getOptInd() {
		return optInd;
	}
	public void setOptInd(String optInd) {
		this.optInd = optInd;
	}
}
