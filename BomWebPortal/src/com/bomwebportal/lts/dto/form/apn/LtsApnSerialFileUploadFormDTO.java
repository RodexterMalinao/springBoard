package com.bomwebportal.lts.dto.form.apn;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class LtsApnSerialFileUploadFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5369155684025747615L;

	
	private MultipartFile batchFile;
	
	private Action formAction;

	public enum Action {
		UPLOAD, RETURN;
	}
	
	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}
	public MultipartFile getBatchFile() {
		return batchFile;
	}
	public void setBatchFile(MultipartFile batchFile) {
		this.batchFile = batchFile;
	}	
}
