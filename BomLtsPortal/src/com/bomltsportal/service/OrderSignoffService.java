package com.bomltsportal.service;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface OrderSignoffService {

	SbOrderDTO signoffOrder(OrderCaptureDTO orderCapture, String sessionId);
	String regMyHktTheClub (SbOrderDTO sbOrder, String locale);
}
