package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.lts.dto.OrderSendRequestDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;

public interface LtsEmailService {
	
	String sendSignOffEmail(SbOrderDTO sbOrder, String emailAddr, String userId);
	
	String sendSignOffEmail(SbOrderDTO sbOrder,
			String userId, String filePathName1,
			String filePathName2, String filePathName3, boolean prePayment);
	
	String sendSignOffEmail(SbOrderDTO sbOrder, String emailAddr, String userId, 
			String filePathName1, String filePathName2, String filePathName3, boolean prePayment);
	
	String sendCareCashEmailToCustomer(SbOrderDTO sbOrder, String emailAddr, String userId);
	String sendCareCashEmailToAdmin(SbOrderDTO sbOrder, String userId);
	
	List<OrderSendRequestDTO> getCCOrderSendRequest(String orderId, String requestDate, 
			String staffId, String teamCd, String seqNo, String templateId, String[] channelId);
	
	String sendOrderEmail(OrdEmailReqDTO insertedDto);
	
	List<OrdEmailReqDTO> getOrdEmailReqDTOLTS(String orderId, String lob, String... templateIds);
	
	OrdEmailReqDTO createAmendmentNoticeReq(SbOrderDTO sbOrder, String templateId, String emailAddr, String smsNo, DisMode method, String username);
	int getNextSeqNo(String orderId);
	
	long insertFormPrintReq(final String pOrderId, final String pPrintReqType,
			final String pPostalAddrLine1, final String pPostalAddrLine2,
			final String pPostalAddrLine3, final String pPostalAddrLine4,
			final String pPostalAddrLine5, final String pPostalAddrLine6,
			final String pEmailContent, final String pEmailSubject,
			final String pEmailFrom, final String pSmsContent,
			final String pCreateBy, final String pLastUpdBy);
	
	OrdEmailReqDTO createOrderEmailReq(String orderId, String templateId, String emailAddr, String smsNo, String method, 
			String username, String lang, String filePathName1, String filePathName2, String filePathName3, String paramString, Long printReqId);
	
	OrdEmailReqDTO createOrderEmailReq(String orderId, String templateId, int seqNo, String emailAddr, String smsNo, 
			String method, String username, String lang, 
			String filePathName1, String filePathName2, String filePathName3, String paramString,
			Long printReqId);
	
	String createLetterPrintReq(SbOrderDTO sbOrder, String userId);
	
	String getBomBillAddress(ServiceDetailDTO serviceDetail);
}
