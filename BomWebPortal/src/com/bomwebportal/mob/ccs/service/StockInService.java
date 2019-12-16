package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ccs.dto.StockInDTO;

public interface StockInService {
	
	public List<CodeLkupDTO> getStatusList();

	public List<StockInDTO> getStockInDTO(String itemCode);
	
	public List<StockCatalogDTO> getTypeNModel(String itemCode);
	
	public String[] insertStockInventory(StockInDTO dto, String username);
	
	public int insertDsStockInventory(StockInDTO dto, String username);
	
	public int updateDsRWHStockInventory(StockInDTO originalDto, StockInDTO dto, String username);
	
	public List<StockInDTO> checkDuplicateStockIn(String itemCode, String imei);
	
	public String[] insertFreeGiftItem(StockInDTO dto);
	
	public int getItemSerialLengthByTypeId(String typeId);

	public List<StockInDTO> checkValidStaff(String staffId, String storeCd, String teamCd);
	
	public boolean allowRWHStock(StockInDTO newStock, List<StockInDTO> duplicatedStockList);
	
	public int insertDsRWHStockInventoryHistory(StockInDTO originalDto, StockInDTO dto, String username);
}
