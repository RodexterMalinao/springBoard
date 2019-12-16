package com.bomwebportal.lts.service.order;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface OrderModifyService {

	SbOrderDTO modifySbOrder(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	int updateEdfRef(String orderId, String dtlId, String edfRef, String userId);	
}
