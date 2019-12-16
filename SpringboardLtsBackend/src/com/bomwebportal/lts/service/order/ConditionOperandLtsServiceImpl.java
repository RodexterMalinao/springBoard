package com.bomwebportal.lts.service.order;

import org.apache.commons.lang.StringUtils;

public class ConditionOperandLtsServiceImpl implements ConditionOperationService {

	private static final String OPERAND_LESS_THAN = "<";
	private static final String OPERAND_LESS_THAN_OR_EQUAL = "<=";
	private static final String OPERAND_GREATER_THAN = ">";
	private static final String OPERAND_GREATER_THAN_OR_EQUAL = ">=";
	private static final String OPERAND_EQUAL = "=";
	

	public boolean isSatisfyCondition(String pConditionType, String pCompareValue, String pInputValue) {
		
		double input = Double.parseDouble(pInputValue);
		double compare = Double.parseDouble(pCompareValue);
		
		if (StringUtils.equals(OPERAND_LESS_THAN, pConditionType)) {
			return input < compare;
		} else if (StringUtils.equals(OPERAND_LESS_THAN_OR_EQUAL, pConditionType)) {
			return input < compare || input == compare;
		} else if (StringUtils.equals(OPERAND_GREATER_THAN, pConditionType)) {
			return input > compare;
		} else if (StringUtils.equals(OPERAND_GREATER_THAN_OR_EQUAL, pConditionType)) {
			return input > compare || input == compare;
		} else if (StringUtils.equals(OPERAND_EQUAL, pConditionType)) {
			return input == compare;
		}
		return false;
	}
}
