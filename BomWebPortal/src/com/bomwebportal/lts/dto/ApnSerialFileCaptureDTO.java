package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.apn.LtsApnDnDTO;
import com.bomwebportal.lts.dto.apn.LtsApnFileDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileEnquiryFormDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileUploadFormDTO;

public class ApnSerialFileCaptureDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4258838867065785009L;
	
	//UI FORM
	private LtsApnSerialFileUploadFormDTO ltsApnSerialFileUploadForm;
	private LtsApnSerialFileEnquiryFormDTO ltsApnSerialFileEnquiryForm;
	private LtsApnFileDTO fileUploadInfo;
	private List<LtsApnDnDTO> dnList;
	
	public LtsApnSerialFileUploadFormDTO getLtsApnSerialFileUploadForm() {
		return ltsApnSerialFileUploadForm;
	}

	public void setLtsApnSerialFileUploadForm(
			LtsApnSerialFileUploadFormDTO ltsApnSerialFileUploadForm) {
		this.ltsApnSerialFileUploadForm = ltsApnSerialFileUploadForm;
	}

	public LtsApnSerialFileEnquiryFormDTO getLtsApnSerialFileEnquiryForm() {
		return ltsApnSerialFileEnquiryForm;
	}

	public void setLtsApnSerialFileEnquiryForm(
			LtsApnSerialFileEnquiryFormDTO ltsApnSerialFileEnquiryForm) {
		this.ltsApnSerialFileEnquiryForm = ltsApnSerialFileEnquiryForm;
	}

	public LtsApnFileDTO getFileUploadInfo() {
		return fileUploadInfo;
	}

	public void setFileUploadInfo(LtsApnFileDTO fileUploadInfo) {
		this.fileUploadInfo = fileUploadInfo;
	}

	public List<LtsApnDnDTO> getDnList() {
		return dnList;
	}

	public void setDnList(List<LtsApnDnDTO> dnList) {
		this.dnList = dnList;
	}
		
}
