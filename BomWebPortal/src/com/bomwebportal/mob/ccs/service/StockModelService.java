package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.StockModelResultDTO;

public interface StockModelService {
	
	public List<StockModelResultDTO> getStockModelResultDTO (String type, String model);
	
}
