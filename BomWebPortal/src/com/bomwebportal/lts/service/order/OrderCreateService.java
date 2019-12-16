package com.bomwebportal.lts.service.order;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;


public interface OrderCreateService {
	SbOrderDTO createSbOrder(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser);
}
