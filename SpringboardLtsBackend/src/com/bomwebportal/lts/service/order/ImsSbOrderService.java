package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface ImsSbOrderService {

	public abstract void createImsEyeOrder(SbOrderDTO pSbOrder, String pUser);
	
	public abstract ImsSbOrderDTO getPcdSbOrder(String pIOrderId);
	
	public abstract String getPcdSbOrderHasDel(String pIOrderId, String pcdBundleFreeType);
	
	public abstract String getFsaNumOnImsSbOrder(String pImsSbOrderId);
	
	public abstract String getLangPreferenceImsSbOrder(String pOrderId);
	
	public abstract String checkAnyActiveServiceWithinXMonths(String srvbdry_num, String floor_num, String unit_num, String prevSerTermMth);
}
