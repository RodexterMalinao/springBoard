package com.bomwebportal.lts.service.order;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface DisconnectOrderModifyService {
	SbOrderDTO modifyTerminateSbOrder(TerminateOrderCaptureDTO orderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
}
