package com.bomwebportal.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.DepositLkupDTO;
import com.bomwebportal.exception.DAOException;

public interface DepositService {
	DepositDTO getDepositDTO(String orderId, String depositId);	
	List<DepositDTO> findDepositDTOByOrderId(String orderId);
	List<DepositDTO> findDepositDTOByOrderId(String orderId, String lang);

	int insertBomwebDeposit(DepositDTO dto);
	DepositLkupDTO getDepositLkup(String depositId);	
	List<DepositLkupDTO> findDepositLkupByDepositCd(String depositCd, Date appDate);
	List<DepositLkupDTO> findDepositLkupByProductId(String productId[], Date appDate);
	List<DepositLkupDTO> findAllDepositLkup();
	List<DepositLkupDTO> findAllDepositLkup(Date appDate);
	List<DepositLkupDTO> findDepositLkupByItemIds(String basketId, Date appInDate, String[] vasItemIds, String mipBrand, String mipSimType);
	BigDecimal getDepositAmountForOrder(String orderId);
}
