package com.bomwebportal.lts.service;

import com.bomwebportal.dto.AlertOrderDTO;


public interface LtsAlertMessageService {

	AlertOrderDTO[] getOrderListWithOutstandingWq(String pSrvType, String pUser)throws Exception;
	String getAlertCount(String pSrvType, String pUser)throws Exception;
}
