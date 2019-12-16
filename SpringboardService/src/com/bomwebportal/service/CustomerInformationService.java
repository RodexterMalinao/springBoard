package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.BomCustomerVerificationDTO;
import com.bomwebportal.dto.CustomerOrderHistoryDTO;

public interface CustomerInformationService {
	
	public List<CustomerOrderHistoryDTO> getCustomerOrderHistoryDTOALL(String idDocNum, String serviceNum, String idDocType, String loginId, String serviceType);
	
	public String getStreetCatgDescFromStCatgCd(String stCatgCd);
	
	public String getSectDescFromSectCd(String sectCd);
	
	public String getDistDscFromDistrNum(String distrNum);
	
	public String getAreaDescFromDistrNum(String distrNum);
	
	public String getAreaCdFromDistrNum(String distrNum);
	
	public BomCustomerVerificationDTO getBomCustomerVerificationResult(String pDocType, String pDocNum, String pFirstName, String pLastName);
}
