package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDTO;

public interface StockManualService {
	
	public List<StockManualDTO> getStockManual(String startDate, String endDate, 
			String orderStatus, String orderId, String searchCriteria);
	
	public List<StockManualDTO> getDSStockManual(String startDate, String endDate, 
			String orderStatus, String orderId, String searchCriteria, String teamCode);
	
	public List<CodeLkupDTO> getDSStockStatusList();
}