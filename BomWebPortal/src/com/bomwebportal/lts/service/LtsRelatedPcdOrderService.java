package com.bomwebportal.lts.service;

import com.bomwebportal.lts.dto.ImsPendingOrderDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;

public interface LtsRelatedPcdOrderService {

	ImsSbOrderDTO retrievePcdSbOrder (String orderId, OrderCaptureDTO orderCapture, boolean isCustInfoConfirmed);
	ImsPendingOrderDTO getImsLatestPendingOrder(String fsa);
	
}
