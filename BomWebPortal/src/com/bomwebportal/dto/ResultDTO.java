package com.bomwebportal.dto;

public class ResultDTO  {

	private Boolean returnBool=false;

/*	private String returnValue;

	private String returnCode;*/
	private String returnMessage;

	public Boolean getReturnBool() {
		return returnBool;
	}

	public void setReturnBool(Boolean returnBool) {
		this.returnBool = returnBool;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

}
