package com.bomwebportal.lts.service.acq;

public interface LtsAcqCustomerDetailService {
	
	public abstract void updateDummyCustNum(String sbOrderId, String custNum, String userId);
	
	public String getBomDummyCustNum();

}
