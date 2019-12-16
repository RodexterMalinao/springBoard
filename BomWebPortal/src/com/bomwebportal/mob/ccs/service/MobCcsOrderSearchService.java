package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.dto.OrderDTO;

public interface MobCcsOrderSearchService {
	
	List<OrderDTO> findOrderEnquiry(String startDate, String endDate, String orderId, 
			String orderStatus, String variousDateType, String channel, String staffId, 
			String orderType, String category, String areaCd, String shopCd, String group, 
			String mrt);
	
	String getOcidStatus(String ocid);
	
}
