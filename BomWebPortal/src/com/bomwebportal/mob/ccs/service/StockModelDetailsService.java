package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ccs.dto.StockModelDetailsDTO;
import com.bomwebportal.mob.ccs.dto.StockModelResultDTO;

public interface StockModelDetailsService {
	
	public List<StockModelDetailsDTO> getStockCatalogListByItemCode(String itemCode);
	
	public List<StockCatalogDTO> getStockCatalogList();
	
	public int insertModelDetails(StockModelDetailsDTO dto);
	
	public int updateModelDetails(StockModelDetailsDTO dto);
	
	public List<StockModelResultDTO> getStockModelResultDTO (String type, String model);
}
