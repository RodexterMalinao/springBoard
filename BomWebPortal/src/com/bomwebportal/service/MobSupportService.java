package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.MobSupportDTO;

public interface MobSupportService {
	
	List<String[]> getAlertOrders();
	
	MobSupportDTO getSBOrderBasicInfo(String orderId);
	
	MobSupportDTO getSBMUPOrderBasicInfo(String orderId);
	
	int updateSBorderStatus(String orderId, String oldOrderStatus, String newOrderStatus);
	
	int updateSBorderAppDate(String orderId, Date appDate);
	
	//String getSBsimStatus(String sim);
	String getWSsimStatus(String sim);
	//int updateSBsimStatus(String sim, String newSimStatus, String oldSimStatus);
	
	//String getSBmrtStatus(String mrt);
	String getWSmrtStatus(String mrt, String shopCd);
	String getCentralNumPoolWSmrtStatus(String mrt, String shopCd);
	//int updateSBmrtStatus(String mrt, String newMrtStatus, String oldMrtStatus);
	
	String getBomSimStatus(String sim);
	
	String getBomMrtStatus(String mrt);
	
	String getOcid(String orderId, String checkPt, String reqStatus);
	/**
	 * Execute update statement
	 * @param inDto
	 * @return List of failure messages if any
	 */
	List<String> executeUpdate(MobSupportDTO inDto);
	
	List<String> executeSIMUpdate(MobSupportDTO inDto);
	
	List<String> executeMRTUpdate(MobSupportDTO inDto);
	
	List<String> executeCcsUpdate(MobSupportDTO inDto);
	
	String getSbOcid(String orderId);
	
	List<String[]> getConflictOrder(String orderId, String simNumber, String mrtNumber);
	
	List<String> updateCcsReasonCd(String orderId);
	
	List<String> executeMemUpdate(MobSupportDTO inDto);
}
