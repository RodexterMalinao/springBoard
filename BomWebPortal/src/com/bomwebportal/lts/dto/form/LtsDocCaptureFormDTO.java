package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.dto.DocImgUploadDTO;

public class LtsDocCaptureFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5925818228525920932L;
	
	private String orderId;
	private String[] allowedExtensions;
	private String submitDocType;
	private long maxUploadSize;
	private List<LtsDocImgUploadDTO> docImgUploadList;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String[] getAllowedExtensions() {
		return allowedExtensions;
	}
	public void setAllowedExtensions(String[] allowedExtensions) {
		this.allowedExtensions = allowedExtensions;
	}
	public String getSubmitDocType() {
		return submitDocType;
	}
	public void setSubmitDocType(String submitDocType) {
		this.submitDocType = submitDocType;
	}
	public long getMaxUploadSize() {
		return maxUploadSize;
	}
	public void setMaxUploadSize(long maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}
	public List<LtsDocImgUploadDTO> getDocImgUploadList() {
		return docImgUploadList;
	}
	public void setDocImgUploadList(List<LtsDocImgUploadDTO> docImgUploadList) {
		this.docImgUploadList = docImgUploadList;
	}
	
	public class LtsDocImgUploadDTO extends DocImgUploadDTO{
		private boolean collected;
		private boolean mandatory;

		public boolean isCollected() {
			return collected;
		}
		public void setCollected(boolean collected) {
			this.collected = collected;
		}
		public boolean isMandatory() {
			return mandatory;
		}
		public void setMandatory(boolean mandatory) {
			this.mandatory = mandatory;
		}
	}

}
