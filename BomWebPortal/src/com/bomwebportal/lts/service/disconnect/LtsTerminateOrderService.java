package com.bomwebportal.lts.service.disconnect;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsTerminateOrderService {
	public String saveOrder(TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser, String reasonCd);
	SbOrderDTO retrieveOrder(String sbOrderId);
	boolean cancelOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser, String reasonCd);
	
	void approvalOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	String signOffOrder(SbOrderDTO sbOrder, String pReportType, String pExportType, String pEditButton, BomSalesUserDTO bomSalesUser);
	void suspendOrderWithPending(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	
	TerminateOrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser);
	
	public SbOrderDTO createSbOrder(TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser);
}
