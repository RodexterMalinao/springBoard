package com.bomwebportal.lts.service.order;

import org.apache.commons.lang.StringUtils;

public class ConditionCompareLtsServiceImpl implements ConditionOperationService {

	private static final String COMPARE_EQUAL = "=";
	private static final String COMPARE_NOT_EQUAL = "!=";

	
	public boolean isSatisfyCondition(String pConditionType, String pCompareValue, String pInputValue) {
		
		if (StringUtils.equals(COMPARE_EQUAL, pConditionType)) {
			return StringUtils.equals(pCompareValue, pInputValue);
		} else if (StringUtils.equals(COMPARE_NOT_EQUAL, pConditionType)) {
			return !StringUtils.equals(pCompareValue, pInputValue);
		}
		return false;
	}
}
