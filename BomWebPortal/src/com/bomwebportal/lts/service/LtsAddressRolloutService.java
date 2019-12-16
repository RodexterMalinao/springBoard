package com.bomwebportal.lts.service;

import java.util.List;


public interface LtsAddressRolloutService {

	List<String> getTeamByPremierAddr(String premierAddr, String activationFee);
	boolean isDiffCustFsaExist(String serviceBoundary, String flat, String floor, String docType, String docNum);
	boolean isDiffCustFsaExist(String[] fsas, String docType, String docNum);
	
}
