package com.activity.dto;

import org.apache.commons.lang.ArrayUtils;

import com.activity.util.ActivityConstants;
import com.bomwebportal.dto.RemarkDTO;
import com.bomwebportal.dto.StatusDTO;

public class ActivityStatusDTO extends StatusDTO {

	private static final long serialVersionUID = 4936189797136427394L;

	private String statusSeq = null;
	
	private RemarkDTO[] remarks = null;
	
	private ActivityAttributeDTO[] attributes = null;

	
	public String getStatusSeq() {
		return statusSeq;
	}

	public void setStatusSeq(String statusSeq) {
		this.statusSeq = statusSeq;
	}

	public RemarkDTO[] getRemarks() {
		return remarks;
	}

	public void setRemarks(RemarkDTO[] remarks) {
		this.remarks = remarks;
	}

	public ActivityAttributeDTO[] getAttributes() {
		return attributes;
	}

	public void setAttributes(ActivityAttributeDTO[] attributes) {
		this.attributes = attributes;
	}
	
	public boolean isPendingStatus() {
		return !ArrayUtils.contains(ActivityConstants.ACTV_END_STATUS, this.getStatus());
	}
}
