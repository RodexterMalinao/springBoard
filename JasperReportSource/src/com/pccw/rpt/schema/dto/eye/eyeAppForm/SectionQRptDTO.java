package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import com.pccw.rpt.util.ReportUtil;

public class SectionQRptDTO extends SectionRptDTO {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2426820237226956341L;

	private String contentWaive;
	private String contentNotWaive;
	private boolean coolingOffRequired;
	private boolean waiveCoolingOff;
	

	public String getContentWaive() {
		return ReportUtil.defaultString(this.contentWaive);
	}

	public void setContentWaive(String pContentWaive) {
		this.contentWaive = pContentWaive;
	}
	
	public String getContentNotWaive() {
		return ReportUtil.defaultString(this.contentNotWaive);
	}

	public void setContentNotWaive(String pContentNotWaive) {
		this.contentNotWaive = pContentNotWaive;
	}

	public boolean isCoolingOffRequired() {
		return coolingOffRequired;
	}

	public void setCoolingOffRequired(boolean coolingOffRequired) {
		this.coolingOffRequired = coolingOffRequired;
	}

	public boolean isWaiveCoolingOff() {
		return waiveCoolingOff;
	}

	public void setWaiveCoolingOff(boolean waiveCoolingOff) {
		this.waiveCoolingOff = waiveCoolingOff;
	}

	



}
