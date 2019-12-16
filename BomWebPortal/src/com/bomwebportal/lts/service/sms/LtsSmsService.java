package com.bomwebportal.lts.service.sms;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsSmsService {
	String sendSignOffSms(SbOrderDTO sbOrder, String userId);
	String sendSignOffSms(SbOrderDTO sbOrder, String smsNo, String userId);
	String formatMsgContent(SbOrderDTO sbOrder, String msgContent);
	String sendAmendmentNoticeSMS(OrdEmailReqDTO insertedDto);
	String sendSms(String smsNo, String msgContent, String ref);
	
	static final String STATUS_SUCCESS = "success";
	static final String STATUS_FAIL = "fail";
}