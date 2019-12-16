package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.dto.OrderDTO;

public interface MobCcsUrgentOrderSearchService {
	
	List<OrderDTO> getUrgentOrder(String deliveryDate, String orderId);
	
}
