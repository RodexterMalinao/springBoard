package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class AllOrdDocAssgnLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 8191280951623068600L;
	
	private String docType = null;
	private String waiveReason = null;
	private String waivedBy = null;
	private String collectedInd = null;
	private String markDelInd = null;


	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getWaiveReason() {
		return waiveReason;
	}

	public void setWaiveReason(String waiveReason) {
		this.waiveReason = waiveReason;
	}

	public String getWaivedBy() {
		return waivedBy;
	}

	public void setWaivedBy(String waivedBy) {
		this.waivedBy = waivedBy;
	}

	public String getCollectedInd() {
		return collectedInd;
	}

	public void setCollectedInd(String collectedInd) {
		this.collectedInd = collectedInd;
	}

	public String getMarkDelInd() {
		return markDelInd;
	}

	public void setMarkDelInd(String markDelInd) {
		this.markDelInd = markDelInd;
	}
}
