package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;


public class Ims1AMSFSAInfoListDTO implements Serializable{
		
	private static final long serialVersionUID = -332480489380790252L;


	private List<Ims1AMSFSAInfoDTO> ims1AMSFSAInfoList;
	private int returnValue;
	private int errorCode;
	private String errorMsg;	
	
    
	public List<Ims1AMSFSAInfoDTO> getIms1AMSFSAInfoList() {
		return ims1AMSFSAInfoList;
	}
	public void setIms1AMSFSAInfoList(List<Ims1AMSFSAInfoDTO> ims1amsfsaInfoList) {
		ims1AMSFSAInfoList = ims1amsfsaInfoList;
	}
	
	
	public int getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
