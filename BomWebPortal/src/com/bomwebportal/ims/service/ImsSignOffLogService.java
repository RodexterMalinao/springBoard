package com.bomwebportal.ims.service;

import com.bomwebportal.ims.dto.ui.OrderImsUI;


public interface ImsSignOffLogService {
	public void signOffOrderLog(OrderImsUI order, String action, String errMsg);
	public void signOffOrderLogException(OrderImsUI order, String action, Exception e);
	public void signOffOrderLog(OrderImsUI order, String action, String errMsg, String lastUpdBy);
}
