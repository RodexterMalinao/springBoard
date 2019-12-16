package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsPrePaymentDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupCriteriaDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupResultDTO;

public interface LtsPaymentService{

	public String getTenureCode(double tenure);
	public List<String> getNewPayMethod(String tenureCode, String existPayMethod);
	public List<String[]> getPrePayItem(String tenureCode, String houseType, String deviceType);
	public List<LookupItemDTO> getBankCode();
	public List<LookupItemDTO> getBranchCode(String bankCode);
	public ItemDetailDTO getItemDetail(String itemId, String displayType, String locale, String type);
	public List<ItemDetailDTO> getItemDetailByType(String type);
	public List<ItemDetailDTO> getItemDetailByType(String type, String displayType, String locale);
	public ItemDetailDTO getItemDetail(double tenure, String newPayMethod, String houseType, String displayType, String locale, String type, String deviceType);
	public List<String> getCancelRemarkItemIdList();
	public LtsPrePaymentDTO getOrderLtsPrepayment(String orderId);
	public String getBranchNameByCode(String bankCd, String branchCd);
	public String getBankNameByCode(String bankCd);
	public List<String> getCcExpireYearList();
	public List<PrepaymentLkupResultDTO> getPrepaymentItemIdByLkup(PrepaymentLkupCriteriaDTO criteria);
	public List<String> getPrepayExcludePsefCodeList();
}
