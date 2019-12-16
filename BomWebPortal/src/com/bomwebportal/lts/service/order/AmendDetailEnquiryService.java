package com.bomwebportal.lts.service.order;

public interface AmendDetailEnquiryService {
	
	public abstract boolean isAmendmentAllow(String pSbOrderId, String pSrvType, String pCcSrvId, String pSrvNum, String pSrvMemNum, StringBuilder pMsgSb);

}