package com.pccw.wq.schema.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class WorkQueueAttributeDTO implements Serializable, Comparable<WorkQueueAttributeDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4442151842115816074L;

	private String attbName;
	
	private String attbValue;

	public String getAttbName() {
		return this.attbName;
	}

	public void setAttbName(String pAttbName) {
		this.attbName = pAttbName;
	}

	public String getAttbValue() {
		return this.attbValue;
	}

	public void setAttbValue(String pAttbValue) {
		this.attbValue = pAttbValue;
	}
	
	private String getCompareKey() {
		return StringUtils.defaultString(this.attbName) + "^" + StringUtils.defaultString(this.attbValue); 
	}
	
	@Override
	public int compareTo(WorkQueueAttributeDTO pO) {
		return this.getCompareKey().compareTo(pO.getCompareKey());
	}
}