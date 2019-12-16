package com.bomltsportal.service.email;

import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsEmailService {
	
	String sendSignOffEmail(SbOrderDTO sbOrder,
			String userId, String filePathName1,
			String filePathName2, String filePathName3, boolean isAmend);
	
}
