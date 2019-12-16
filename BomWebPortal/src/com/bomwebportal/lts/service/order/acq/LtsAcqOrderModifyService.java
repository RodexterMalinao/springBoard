package com.bomwebportal.lts.service.order.acq;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsAcqOrderModifyService {

	SbOrderDTO modifySbOrder(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	void modifyAppointment(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder);
	int updateEdfRef(String orderId, String dtlId, String edfRef, String userId);	
}
