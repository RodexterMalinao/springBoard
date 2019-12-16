package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ThirdPartyDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 3505037056471302484L;

	private String appntFirstName = null;
	private String appntLastName = null;
	private String appntDocType = null;
	private String appntDocId = null;
	private String relationshipCode = null;
	private String appntIdVerifiedInd = null;
	private String appntContactNum = null;
	private String title = null;
	private String remarks = null;
	private String dob = null;
	
	
	public String getAppntFirstName() {
		return appntFirstName;
	}

	public void setAppntFirstName(String appntFirstName) {
		this.appntFirstName = appntFirstName;
	}

	public String getAppntLastName() {
		return appntLastName;
	}

	public void setAppntLastName(String appntLastName) {
		this.appntLastName = appntLastName;
	}

	public String getAppntDocType() {
		return appntDocType;
	}

	public void setAppntDocType(String appntDocType) {
		this.appntDocType = appntDocType;
	}

	public String getAppntDocId() {
		return appntDocId;
	}

	public void setAppntDocId(String appntDocId) {
		this.appntDocId = appntDocId;
	}

	public String getRelationshipCode() {
		return relationshipCode;
	}

	public void setRelationshipCode(String relationshipCode) {
		this.relationshipCode = relationshipCode;
	}

	public String getAppntIdVerifiedInd() {
		return appntIdVerifiedInd;
	}

	public void setAppntIdVerifiedInd(String appntIdVerifiedInd) {
		this.appntIdVerifiedInd = appntIdVerifiedInd;
	}

	public String getAppntContactNum() {
		return appntContactNum;
	}

	public void setAppntContactNum(String appntContactNum) {
		this.appntContactNum = appntContactNum;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String pTitle) {
		this.title = pTitle;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
}
