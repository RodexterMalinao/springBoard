package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.lts.dto.LtsOrderSearchDTO;
import com.bomwebportal.lts.dto.OrderSrvStatusDetailDTO;

public interface LtsOrderSearchService {

	
	public List<LtsOrderSearchDTO> searchLtsOnlineOrder(String orderId,
			String idDocType, String idDocNum, String srvNum, String email, String userId);
	
	public List<LtsOrderSearchDTO> searchLtsCcOrder(String orderId,
			String idDocType, String idDocNum, String srvNum, String email, String userId, String[] channelId, 
			String staffNum, String salesTeam, String[] salesCenters, String salesChannel, String orgStaffId, String startDate, String endDate, String status, 
			String srdStartDate, String srdEndDate);
	
	public List<OrderSrvStatusDetailDTO> getPendingOrderSrvStatusList(String pOcid);
	
	public List<String> getDsSalesCenter(String staffId, String channelId);
}
