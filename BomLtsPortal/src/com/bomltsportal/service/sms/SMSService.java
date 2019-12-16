package com.bomltsportal.service.sms;

import java.util.Locale;

import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface SMSService {
	void sendSMSMessage(String mobileno, Locale locale, SbOrderDTO pSbOrder, String pSrvType);
}
