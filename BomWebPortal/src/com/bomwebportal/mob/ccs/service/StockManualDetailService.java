package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDetailDTO;
import com.bomwebportal.mob.ccs.dto.ui.StockManualAssgnUI;


public interface StockManualDetailService {
	
	public List<StockManualDetailDTO> getStockManualDetail(String orderId);
	
	public boolean deassignAllItemByOrder(String orderId);
	
	// add by Joyce 20120507
	public boolean deassignPerItem(String orderId, String itemCode, String itemSerial);
	
	public int dsDeassignPerItem(StockManualAssgnUI dto, String orderId, String username, String action);
	
	public boolean manualOrderStatusProcess(String orderId);
	
	public String doaDeassignItem(String orderId, String itemCode, String itemSerial, String oldItemCode);
	
	public List<StockManualDetailDTO> getDoaDetail(String orderId);
	
	public StockManualAssgnUI getAssignedItemSerialStatus(String orderId, String itemCode);
	
	public StockManualDetailDTO getPreviousItem(String orderId, String itemCode);
	
	public StockManualDetailDTO getPreviousItemSingleItemCode(String orderId, String itemCode);
	
	public List<CodeLkupDTO> getItemColorlist(String itemCode);
	
	public String getStockPool(String orderId);
	
	public String getCCSStockPool(String channelCd);
}