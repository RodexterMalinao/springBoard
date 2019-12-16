package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ui.StockManualAssgnUI;

public interface StockManualAssgnService {
	
	public List<String> getStockManualAssgn(String itemCode, String locCd, String statusCd, String stockPool);
	
	public List<String> getDSStockManualAssgn(String itemCode, String orderId);
	
	public boolean manualStockAssgn(StockManualAssgnUI dto);
	
	public int manualDSStockAssgn(StockManualAssgnUI dto, StockManualAssgnUI oDto, String orderId, String username, String action);
	
	public List<Object[]> getOrderInfo(String orderId);
	
	public List<CodeLkupDTO> getItemColorlist(String itemCode);

	public StockManualAssgnUI getAssignedItemSerialStatus(String orderId, String itemCode);
	
	public String getStatusChange(String orderId, String status, String itemCode, String action);
}