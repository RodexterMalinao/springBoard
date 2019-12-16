package com.bomwebportal.service;

import org.openuri.www.SearchingKeyDTO;

import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LoggingService {

	public abstract void logSearchByCriteria(String pUser,
			SearchingKeyDTO pSearchingKey);

	public abstract void logSearchByCustomerNum(String pUser, String pCustNum);

	public abstract void logSearchByLoginId(String pUser, String pLoginId,
			String pDomain);

	public abstract void logSearchByService(String pUser, String pSrvType,
			String pSrvNum);

	public abstract void logSearchByDoc(String pUser, String pDocType,
			String pDocNum);

	public abstract void logRecallLtsOrder(String pUser, SbOrderDTO pSbOrder);
}