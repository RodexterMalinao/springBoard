package com.activity.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class DocumentDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -8210984781069092459L;

	private String docType = null;
	private String collectedInd = "N";
	private String waiveReason = null;
	private String waivedBy = null;

	private List<DocumentDetailDTO> docDetailList = null;

	
	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getCollectedInd() {
		return collectedInd;
	}

	public void setCollectedInd(String collectedInd) {
		this.collectedInd = collectedInd;
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

	public DocumentDetailDTO[] getDocDetails() {

		if (this.docDetailList == null || this.docDetailList.size() == 0) {
			return null;
		}
		return this.docDetailList.toArray(new DocumentDetailDTO[this.docDetailList.size()]);
	}

	public void setDocDetails(DocumentDetailDTO[] docDetails) {

		if (ArrayUtils.isEmpty(docDetails)) {
			return;
		}
		this.docDetailList = new ArrayList<DocumentDetailDTO>(Arrays.asList(docDetails));
		this.updateCollectedInd();
	}
	
	public void appendDocumentDetail(DocumentDetailDTO pDocDtl) {

		if (this.docDetailList == null) {
			this.docDetailList = new ArrayList<DocumentDetailDTO>();
		}
		this.docDetailList.add(pDocDtl);
		this.updateCollectedInd();
	}
	
	private void updateCollectedInd() {
		
		for (int i=0; this.docDetailList!=null && i<this.docDetailList.size(); ++i) {
			if (!StringUtils.equals("Y", this.docDetailList.get(i).getOutdatedInd())) {
				this.collectedInd = "Y";
			}
		}
	}
}
