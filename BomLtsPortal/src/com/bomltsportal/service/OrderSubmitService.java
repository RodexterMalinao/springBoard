package com.bomltsportal.service;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface OrderSubmitService {
	
	
	SbOrderDTO submitOrder(OrderCaptureDTO orderCapture);
	void submitOrderPayment(OrderCaptureDTO orderCapture);
}
