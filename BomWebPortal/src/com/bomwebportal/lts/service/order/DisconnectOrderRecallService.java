package com.bomwebportal.lts.service.order;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;

public interface DisconnectOrderRecallService {
	TerminateOrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser);
}
