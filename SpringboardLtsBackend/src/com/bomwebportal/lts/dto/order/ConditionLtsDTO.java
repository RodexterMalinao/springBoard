package com.bomwebportal.lts.dto.order;

import java.io.Serializable;

public class ConditionLtsDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = 2290412804282504164L;
	
	private String conditionKey = null;
	private String conditionSeq = null;
	private String conditionType = null;
	private String condition = null;
	private String value = null;
	private String result = null;

	public String getConditionKey() {
		return conditionKey;
	}

	public void setConditionKey(String conditionKey) {
		this.conditionKey = conditionKey;
	}

	public String getConditionSeq() {
		return conditionSeq;
	}

	public void setConditionSeq(String conditionSeq) {
		this.conditionSeq = conditionSeq;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
