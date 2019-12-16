package com.bomwebportal.lts.service.order.acq;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;

public interface LtsAcqOrderRecallService {
	
	AcqOrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser);
}
