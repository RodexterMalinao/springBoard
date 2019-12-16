package com.bomwebportal.lts.service.order;

public interface ConditionLtsService {

	public static final String CONDITION_TYPE_DEFAULT = "DEFAULT";
	public static final String CONDITION_TYPE_OPERAND = "OPERAND";
	public static final String CONDITION_TYPE_COMPARE = "COMPARE";
	
	public abstract String getConditionResult(String pConditionKey, String... pValues);

}