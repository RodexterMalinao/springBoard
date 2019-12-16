package com.bomwebportal.lts.dto.acq;

import java.io.Serializable;

public class LtsAcqFaxImageDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5613353183629926948L;
	
	private String faxSerial;
	private String fileImageUrl;
	private String fileRemoveUrl;
	private String errorMsg;
	public String getFaxSerial() {
		return faxSerial;
	}
	public void setFaxSerial(String faxSerial) {
		this.faxSerial = faxSerial;
	}
	public String getFileImageUrl() {
		return fileImageUrl;
	}
	public void setFileImageUrl(String fileImageUrl) {
		this.fileImageUrl = fileImageUrl;
	}
	public String getFileRemoveUrl() {
		return fileRemoveUrl;
	}
	public void setFileRemoveUrl(String fileRemoveUrl) {
		this.fileRemoveUrl = fileRemoveUrl;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
