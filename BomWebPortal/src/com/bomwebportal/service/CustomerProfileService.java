package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.mob.ccs.dto.BomAddressDtlDTO;

public interface CustomerProfileService {

	public void insertBomCustomerProfile(CustomerProfileDTO dto);

	public void deleteBomCustomerProfile(String orderId);

	public CustomerProfileDTO getCustomerProfile(String orderId);// add by
																	// wilson
																	// 20110223

	public CustomerProfileDTO getCustomerProfileAll(String orderId);

	public CustomerProfileDTO getCurrentCustomerProfile(String orderId);

	boolean isPublicHousing(String serviceBoundaryNum); // add by eliot 20120112

	boolean isHktPremierCustomer(CustomerProfileDTO customerProfileDto);

	boolean isHktPremierAddr(String serviceBoundaryNum);

	public CustomerProfileDTO getCosCustomerProfile(String orderId);

	public BomAddressDtlDTO getBomAddrDtl(String idDocType, String idDocNum);

	public boolean isStudentPlan(String orderId);

	public String getCareCashStartDate(String codeType);

	public AccountDTO getAccountInfo(String srvNum, String idDocType, String idDocNum, String brand);

	public List<AccountDTO> getBarredFraud(String idDocType, String idDocNum);

}
