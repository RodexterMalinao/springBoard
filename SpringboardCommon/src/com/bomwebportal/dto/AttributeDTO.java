package com.bomwebportal.dto;


public class AttributeDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -1247876466639725482L;

	private String attbSeq = null;
	private String attbValue = null;
	private String attbType = null;

	public String getAttbSeq() {
		return attbSeq;
	}

	public void setAttbSeq(String attbSeq) {
		this.attbSeq = attbSeq;
	}

	public String getAttbValue() {
		return attbValue;
	}

	public void setAttbValue(String attbValue) {
		this.attbValue = attbValue;
	}

	public String getAttbType() {
		return attbType;
	}

	public void setAttbType(String attbType) {
		this.attbType = attbType;
	}
}
