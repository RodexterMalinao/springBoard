package com.bomltsportal.service;

import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface OrderPostSubmitService {

	String saveAndSendApplicationForm(SbOrderDTO pSbOrder);
	void sendSMSToCustomer(String pSmsNum, String pLang, SbOrderDTO pSbOrder, String pSrvType);	
}