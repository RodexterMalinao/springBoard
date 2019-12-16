package com.bomwebportal.service;

import java.util.List;

import org.openuri.www.CustomerBasicInfoDTO;

import com.bomwebportal.dto.CustomerInformationQuotaDTO;
import com.bomwebportal.dto.CustomerInformationQuotaUI;
import com.bomwebportal.dto.EligibilityDTO;

public interface CustomerInformationQuotaService {
	public List<CustomerInformationQuotaDTO> getCustomerInformationQuotaDTOList(
			String idDocType, String idDocNum);

	public List<EligibilityDTO> getEligibilityDTOList(String idDocType,
			String idDocNum);

	public List<EligibilityDTO> getEligibilityDTOWithDefaultValuesList(
			String idDocType, String idDocNum);

	public CustomerInformationQuotaUI getCustomerInformationQuotaUI(
			CustomerBasicInfoDTO customer);
	
	public List<CustomerInformationQuotaDTO> getCustomerInformationQuotaOverLimitDTOList(
			String idDocType, String idDocNum, String orderId);
}
