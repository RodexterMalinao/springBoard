package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;

public interface CallPlanLtsService {

	public abstract IddCallPlanProfileLtsDTO[] mapIddCallPlan(String[] pIddCallPlanCds);
	public abstract String[] getCallPlanCd (String itemId);
	public abstract String getCallPlanType (String callPlanCd);
	public abstract String getCallPlanContractPeriod(String callPlanCd); 
}
