package com.bomwebportal.lts.service.acq;

public interface LtsAcqAccountDetailService {
	
	public abstract void updateDummyAcctNum(String sbOrderId, String acctNum, String userId);
	public String getBomDummyAcctNum();

}
