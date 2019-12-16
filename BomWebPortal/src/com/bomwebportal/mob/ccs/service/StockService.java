package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockResultDTO;
import com.bomwebportal.mob.ccs.dto.ui.HottestModelManagementUI;

public interface StockService {
	enum FunctionCd {
		STOCK_QUANTITY_ENQUIRY("STOCK QUANTITY ENQUIRY")
		;
		FunctionCd(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		private String value;
	}
	
	enum ParmType {
		STOCK_POOL,
		STOCK_STS
		;
	}

	public List<StockDTO> getUSerMultiStoreTeamCode(String username, int channelId);
	public List<StockDTO> getUserMultiStoreCode(String username, int channelId);
	
    List<StockCatalogDTO> getStockCatalogList();
    List<StockCatalogDTO> getModelList();
    List<CodeLkupDTO> getStockMainStatusList(String channelCd);
    List<StockResultDTO> getStockResultDTO(StockDTO dto);
    List<StockResultDTO> getDSStockResultDTO(StockDTO dto);
    List<StockCatalogDTO> getTempMiddleList(String type, String model);
    List<StockCatalogDTO> getTempMiddleListByItemCode(String itemCode);
    List<StockResultDTO> getStockResultDTOByItemSerial(String itemSerial);
    List<StockResultDTO> getStockResultDTOByOrderId(String orderId);

    // add by eliot for hottest model management 20120314
    int insertHottestModel(String itemCode, Date startDate, Date endDate, String user);
    int updateHottestModelStartDate(Date endDate, String user, String itemCode, Date startDate);
    List<HottestModelManagementUI> getHottestModelAndPeriod(String itemCode);
    List<String[]> getModelSearch(String itemCode, String itemDesc, String codeId);
    boolean validHottestModelManagementOverlap(String actionType, String itemCode, Date startDate, Date endDate);
    List<String> checkStockQty(String orderId);
    
    List<MaintParmLkupDTO> getMaintParmValue(String channelCd, FunctionCd functionCd, ParmType parmType);
    
    List<StockAssgnDTO> getStockAssgnList(String basketID, String[] vasItemList);
    public List<String> getDsSimNum(String staffID, Date checkDate, int simType, String channelCd, String storeCd, String teamCd);
    public List<String> getDsSerialNum(String itemCd, String staffID, Date checkDate, String channelCd, String storeCd, String teamCd);
    int isValidSerial(String itemCode, String serialNum, String staffID, String channelCd, String storeCd, String teamCd, Date checkDate);
    String getSimType(String itemCode);
    public String[] getShopFromOrder(String orderId);
    
    List<StockDTO> getEventList();
    List<StockDTO> getValidEventList();
    List<StockDTO> getStoreList(String channelCd);
    List<StockDTO> getTeamList(String channelCd);
    List<StockDTO> getTeamListByStore(String storeCode);
    
    int updateStockInventory(String item_code, String item_serial, String status_id);
	List<StockAssgnDTO> getStockAssgnListByOrderId(String orderId);
	int updateBomWebStockAssgnStatus(String status_id, String orderId, String itemCode, String itemSerial);
	public int updateSalesShopCode(String staffID, String storeCode, String teamCode);
}
