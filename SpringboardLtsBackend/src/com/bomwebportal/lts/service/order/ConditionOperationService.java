package com.bomwebportal.lts.service.order;


public interface ConditionOperationService {

	public abstract boolean isSatisfyCondition(String pConditionType, String pCompareValue, String pInputValue);
}