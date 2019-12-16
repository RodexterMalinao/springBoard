package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;


public interface StockUpdateService {
	
	public List<StockUpdateDTO> getStockUpdateDTObyImei(String serialNumber);
	
	public List<StockUpdateDTO> getStockUpdateDTObyImeiReal(String serialNumber);
	
	public List<StockUpdateDTO> getDSStockUpdateDTObyImei(String serialNumber);
	
	public String updateStockInventory(StockUpdateDTO dto, String username);
	
	public int updateDSStockInventory(StockUpdateDTO dto, String originalStatus, String username);
	
	public List<CodeLkupDTO> getStockUpdateStatusList();
	
	public boolean validateEffEndDate(String eventCode);
	
	public boolean validateUpdateStaff(String channelCode, String newStaffTeamCode, String userTeamCode, String newStaffID, String originalStaffID);
	
	public boolean validateStaff(String staffID, String storeCode, String teamCode);
	
}
