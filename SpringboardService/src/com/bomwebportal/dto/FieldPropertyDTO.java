package com.bomwebportal.dto;

import java.io.Serializable;

public class FieldPropertyDTO implements Serializable {

	private static final long serialVersionUID = -542167353552776792L;

	private String fieldname = null;
	private String fieldType = null;
	private int fieldLength = Integer.MAX_VALUE;
	private String defaultVaule = null;
	private String description = null;
	private boolean mandatory = false;
	private String validationRule = null;

	
	public FieldPropertyDTO(String pFieldName, String pFieldType, int pFieldLength, String pDefaultVaule, String pDescription, 
			boolean pMandatory, String pValidationRule) {
		this.fieldname = pFieldName;
		this.fieldType = pFieldType;
		this.fieldLength = pFieldLength;
		this.defaultVaule = pDefaultVaule;
		this.description = pDescription;
		this.mandatory = pMandatory;
		this.validationRule = pValidationRule;
	}

	public String getFieldName() {
		return fieldname;
	}

	public String getFieldType() {
		return fieldType;
	}

	public int getFieldLength() {
		return fieldLength;
	}
	
	public String getDefaultVaule() {
		return defaultVaule;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public String getValidationRule() {
		return validationRule;
	}
}
