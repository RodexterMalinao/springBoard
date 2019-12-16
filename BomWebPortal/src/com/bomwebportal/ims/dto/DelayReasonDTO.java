package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class DelayReasonDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5096531629663008744L;
	
	private String delayReason;
	private String delayReasonCode;
	public String getDelayReason() {
		return delayReason;
	}
	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}
	public String getDelayReasonCode() {
		return delayReasonCode;
	}
	public void setDelayReasonCode(String delayReasonCode) {
		this.delayReasonCode = delayReasonCode;
	}
	

}
