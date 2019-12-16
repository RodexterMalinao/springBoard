package com.bomwebportal.ims.service;

import java.util.List;

public interface IsImsBlacklistCustService {
	
	public boolean isImsBlacklistCust(String idDocType, String idDocNum);
	public List<String> getImsOsBalanceAcct(String idDocType, String idDocNum);
	public String getImsOsBalance(String acctNum);
}