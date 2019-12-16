package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockQuantityEnquiryDTO;

public interface StockQuantityEnquiryService {
	
	public List<StockQuantityEnquiryDTO> getStockQuantityEnquiry(String stockPool, String staffId, String type, String itemCode, String model);

	public List<StockQuantityEnquiryDTO> getDsStockQuantityEnquiry(StockDTO dto);
}