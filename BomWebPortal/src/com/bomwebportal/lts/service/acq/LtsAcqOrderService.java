package com.bomwebportal.lts.service.acq;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsAcqOrderService {
	
	String saveOrder(AcqOrderCaptureDTO AcqOrderCapture, BomSalesUserDTO bomSalesUser, String reasonCd);
	SbOrderDTO retrieveOrder(String sbOrderId);
	boolean cancelOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser, String reasonCd);
	
	void approvalOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	String signOffOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	
	void suspendOrderWithPending(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	
	AcqOrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser);
	
	SbOrderDTO createSbOrder(AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser);
	
	String signOffOrderQC(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	
	public void suspendOrderForPrepayment(SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser);
	public void suspendOrderForQc(SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser);	
	public void updateAppointmentForQc(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser);
}
