package com.pccw.rpt.schema.dto.wq;

public class ImsWorkQueueRptDTO extends WorkQueueRptDTO {

	//this file created by ims steven 20130208
	private static final long serialVersionUID = -2695118625959352009L;
	
	private String applicationDate;
	private String applicationMethod; 
	private String sourceCode;
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getApplicationMethod() {
		return applicationMethod;
	}
	public void setApplicationMethod(String applicationMethod) {
		this.applicationMethod = applicationMethod;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
}