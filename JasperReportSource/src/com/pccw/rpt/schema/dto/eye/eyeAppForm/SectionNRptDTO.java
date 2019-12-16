package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import com.pccw.rpt.util.ReportUtil;

public class SectionNRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2284453608402588526L;

	private String applicantName;
	
	private String idDocNum;
	
	private String idDocType;
	
	private String contactNum;
	
	private String relationship;
	
	private String idVerifiedInd;
	
	private String content;

	public String getApplicantName() {
		return ReportUtil.defaultString(this.applicantName);
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getIdDocNum() {
		return ReportUtil.defaultString(this.idDocNum);
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public String getIdDocType() {
		return ReportUtil.defaultString(this.idDocType);
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getContactNum() {
		return ReportUtil.defaultString(this.contactNum);
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getRelationship() {
		return ReportUtil.defaultString(this.relationship);
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getIdVerifiedInd() {
		return ReportUtil.defaultString(this.idVerifiedInd);
	}

	public void setIdVerifiedInd(String idVerifiedInd) {
		this.idVerifiedInd = idVerifiedInd;
	}
	
	public boolean isIdVerified() {
		return "Y".equals(this.idVerifiedInd);
	}

	public String getContent() {
		return ReportUtil.defaultString(this.content);
	}

	public void setContent(String content) {
		this.content = content;
	}
}
