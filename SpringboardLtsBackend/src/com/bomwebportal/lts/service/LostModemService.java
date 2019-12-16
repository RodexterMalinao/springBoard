package com.bomwebportal.lts.service;

import java.util.List;

public interface LostModemService {

	public List<String> getL2Cd(String pOrderId);
	public void insertL2Cd(String pOrderId, String pDtlId, String pL2Cd);
	public void insertL2Cd(String pOrderId, String pDtlId, String pL2Cd, String pFTInd, String pL2Qty, String pActInd);
	public List<String[]> getLostModemApproverName(String pOrderId);
}
