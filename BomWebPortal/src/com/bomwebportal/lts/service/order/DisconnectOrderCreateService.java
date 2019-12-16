package com.bomwebportal.lts.service.order;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface DisconnectOrderCreateService {
	
	SbOrderDTO createTerminateSbOrder(TerminateOrderCaptureDTO orderCapture,
			BomSalesUserDTO bomSalesUser);
	
}
