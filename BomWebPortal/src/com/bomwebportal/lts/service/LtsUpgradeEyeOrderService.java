package com.bomwebportal.lts.service;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsUpgradeEyeOrderService {
	
	String saveOrder(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser, String reasonCd);
	SbOrderDTO retrieveOrder(String sbOrderId);
	boolean cancelOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser, String reasonCd);
	
	void approvalOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	String signOffOrder(SbOrderDTO sbOrder, String pReportType, String pExportType, String pEditButton, BomSalesUserDTO bomSalesUser);
	void suspendOrderWithPending(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	
	OrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser);
	
	SbOrderDTO createSbOrder(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser);
}
