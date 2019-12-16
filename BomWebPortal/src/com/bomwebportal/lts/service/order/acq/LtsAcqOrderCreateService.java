package com.bomwebportal.lts.service.order.acq;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;


public interface LtsAcqOrderCreateService {
	SbOrderDTO createSbOrder(AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser);
}
