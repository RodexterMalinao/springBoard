package com.bomwebportal.mob.validate.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5329631851359334562L;
	private List<ErrorDTO> errorList = new ArrayList<ErrorDTO>();;
	private String startPackNumber;
	private String onePssInd;

	public boolean hasError() {
		return  !errorList.isEmpty() ? true: false;
		//return this.hasError = !errorList.isEmpty() ? true: false;
	}

	public List<ErrorDTO> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorDTO> errorList) {
		this.errorList = errorList;
	}

	public String getStartPackNumber() {
		return startPackNumber;
	}

	public void setStartPackNumber(String startPackNumber) {
		this.startPackNumber = startPackNumber;
	}

	public String getOnePssInd() {
		return onePssInd;
	}

	public void setOnePssInd(String onePssInd) {
		this.onePssInd = onePssInd;
	}
	
	
	
}
