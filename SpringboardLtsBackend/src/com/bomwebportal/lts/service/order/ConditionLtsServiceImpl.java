package com.bomwebportal.lts.service.order;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.order.ConditionLtsDTO;
import com.bomwebportal.service.CodeLkupCacheService;

public class ConditionLtsServiceImpl implements ConditionLtsService {

	private static final char SEPERATOR = '|';
	
	private ConditionOperationService conditionOperandLtsService = null;
	
	private ConditionOperationService conditionCompareLtsService = null;
	
	private CodeLkupCacheService conditionLkupCache = null;
	
	
	@SuppressWarnings("unchecked")
	public String getConditionResult(String pConditionKey, String... pValues) {
		
		List<ConditionLtsDTO> conditionList = (List<ConditionLtsDTO>)this.conditionLkupCache.get(pConditionKey);
		String[] conditionTypes = null;
		String[] conditions = null;
		String[] compareValues = null;
		boolean isSatisfy = true;
		
		for (int i=0; conditionList!=null && i<conditionList.size(); ++i) {
			conditionTypes = StringUtils.split(conditionList.get(i).getConditionType(), SEPERATOR);
			conditions = StringUtils.split(conditionList.get(i).getCondition(), SEPERATOR);
			compareValues = StringUtils.split(conditionList.get(i).getValue(), SEPERATOR);
			isSatisfy = true;
			
			for (int j=0; j<conditions.length; ++j) {
				if (!(isSatisfy &= this.isSatisfyCondition(conditionTypes[j], conditions[j], compareValues[j], pValues[j]))) {
					break;
				}
			}
			if (isSatisfy) {
				return conditionList.get(i).getResult();
			}
		}
		return null;
	}
	
	public boolean isSatisfyCondition(String pConditionType, String pCondition, String pCompareValue, String pInputValue) {
		
		if (StringUtils.equals(CONDITION_TYPE_OPERAND, pConditionType)) {
			return this.conditionOperandLtsService.isSatisfyCondition(pCondition, pCompareValue, pInputValue);
		} else if (StringUtils.equals(CONDITION_TYPE_COMPARE, pConditionType)) {
			return this.conditionCompareLtsService.isSatisfyCondition(pCondition, pCompareValue, pInputValue);
		} else if (StringUtils.equals(CONDITION_TYPE_DEFAULT, pCondition)) {
			return true;
		}
		return false;
	}

	public CodeLkupCacheService getConditionLkupCache() {
		return conditionLkupCache;
	}

	public void setConditionLkupCache(CodeLkupCacheService conditionLkupCache) {
		this.conditionLkupCache = conditionLkupCache;
	}

	public ConditionOperationService getConditionOperandLtsService() {
		return conditionOperandLtsService;
	}

	public void setConditionOperandLtsService(ConditionOperationService conditionOperandLtsService) {
		this.conditionOperandLtsService = conditionOperandLtsService;
	}

	public ConditionOperationService getConditionCompareLtsService() {
		return conditionCompareLtsService;
	}

	public void setConditionCompareLtsService(
			ConditionOperationService conditionCompareLtsService) {
		this.conditionCompareLtsService = conditionCompareLtsService;
	}
}
