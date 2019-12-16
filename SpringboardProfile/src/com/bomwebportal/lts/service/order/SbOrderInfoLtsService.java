package com.bomwebportal.lts.service.order;

import java.util.List;

import com.bomwebportal.lts.dto.ImsPendingOrderDTO;
import com.bomwebportal.lts.dto.ImsSbOrderDetailDTO;
import com.bomwebportal.lts.dto.PcdOrderCheckAttrDTO;
import com.bomwebportal.lts.dto.order.LtsPendingSbOrderDTO;

public interface SbOrderInfoLtsService {

	public abstract String getSbLtsLatestPendingOrderBySrvNum(String pSrvNum,
			String pSrvType);

	public abstract List<ImsPendingOrderDTO> getSbImsLatestPendingOrderBySrvNum(String pSrvNum);

	public ImsSbOrderDetailDTO getPcdSbOrder(String orderId);
	
	public List<PcdOrderCheckAttrDTO> getPcdSbOrderDetails(String orderId);
	
	public String getPcdSbOrderHasDel(String orderId, String pcdBundleFreeType);
	
	public String checkAnyActiveServiceWithinXMonths(String srvbdry_num, String floor_num, String unit_num, String prevSerTermMth);
	
	public List<LtsPendingSbOrderDTO> getPendingSbOrderList(String pSrvNum, String pSrvType);
	
	public String getPcdActivationDate(String orderId, String preInstallCheck, String pcdBundleFreeType);
}