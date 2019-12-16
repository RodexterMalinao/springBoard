package com.bomwebportal.mob.ccs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockResultDTO;
import com.bomwebportal.mob.ccs.dto.ui.HottestModelManagementUI;
import com.bomwebportal.mob.ds.dao.MobDsMrtManagementDAO;
import com.bomwebportal.mob.ds.dao.MobDsStockDAO;
import com.bomwebportal.util.BomWebPortalConstant;

@Transactional(readOnly = true)
public class StockServiceImpl implements StockService {

	private static final Logger logger = Logger.getLogger(StockServiceImpl.class);

	private StockDAO stockdao;
	private MobDsStockDAO dsStockdao;
    private MobCcsMaintParmLkupService mobCcsMaintParmLkupService;
    private MobDsMrtManagementDAO mobDsMrtManagementDAO;

	public void setStockdao(StockDAO stockdao) {
		this.stockdao = stockdao;
	}

	public StockDAO getStockdao() {
		return stockdao;
	}
	
	public MobDsStockDAO getDsStockdao() {
		return dsStockdao;
	}

	public void setDsStockdao(MobDsStockDAO dsStockdao) {
		this.dsStockdao = dsStockdao;
	}

	public MobCcsMaintParmLkupService getMobCcsMaintParmLkupService() {
		return mobCcsMaintParmLkupService;
	}

	public void setMobCcsMaintParmLkupService(
			MobCcsMaintParmLkupService mobCcsMaintParmLkupService) {
		this.mobCcsMaintParmLkupService = mobCcsMaintParmLkupService;
	}

	public MobDsMrtManagementDAO getMobDsMrtManagementDAO() {
		return mobDsMrtManagementDAO;
	}

	public void setMobDsMrtManagementDAO(MobDsMrtManagementDAO mobDsMrtManagementDAO) {
		this.mobDsMrtManagementDAO = mobDsMrtManagementDAO;
	} 
	
	public List<StockDTO> getUSerMultiStoreTeamCode(String username, int channelId) {
		try {
			return dsStockdao.getUserMultiStoreTeamCode(username, channelId);
		} catch (Exception e) {
			logger.error("Exception caught in getSupervisorStoreTeamCode()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<StockDTO> getUserMultiStoreCode(String username, int channelId) {
		try {
			return dsStockdao.getUserMultiStoreCode(username, channelId);
		} catch (Exception e) {
			logger.error("Exception caught in getSupervisorStoreCode()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<StockCatalogDTO> getTempMiddleList(String type, String model) {
		try {
			return stockdao.getTempMiddleList(type, model);
		} catch (Exception e) {
			logger.error("Exception caught in getTempMiddleList()", e);
			throw new AppRuntimeException(e);
		}
	}

	public List<StockCatalogDTO> getTempMiddleListByItemCode(String itemCode) {
		try {
			return stockdao.getTempMiddleListByItemCode(itemCode);
		} catch (Exception e) {
			logger.error("Exception caught in getTempMiddleListByItemCode()", e);
			throw new AppRuntimeException(e);
		}
	}

	public List<StockCatalogDTO> getStockCatalogList() {
		try {
			return stockdao.getStockCatalogList();
		} catch (Exception e) {
			logger.error("Exception caught in getStockCatalogList()", e);
			throw new AppRuntimeException(e);
		}
	}

	public List<CodeLkupDTO> getStockMainStatusList(String channel_cd) {
		try {
			return stockdao.getStockMainStatusList(channel_cd);
		} catch (Exception e) {
			logger.error("Exception caught in getStockMainStatusList()", e);
			throw new AppRuntimeException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<StockCatalogDTO> getModelList() {
		try {
			return stockdao.getModelList();
		} catch (DAOException e) {
			logger.error("Exception caught in getModelList()", e);
			throw new AppRuntimeException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<StockResultDTO> getStockResultDTO(StockDTO dto) {
		try {
			return stockdao.getStockResultDTO(dto);
		} catch (DAOException e) {
			logger.error("Exception caught in getStockResultDTO()", e);
			throw new AppRuntimeException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<StockResultDTO> getDSStockResultDTO(StockDTO dto) {
		try {
			return dsStockdao.getDSStockResultDTO(dto);
		} catch (DAOException e) {
			logger.error("Exception caught in getDSStockResultDTO()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<StockResultDTO> getStockResultDTOByItemSerial(String itemSerial) {
		try {
			return stockdao.getStockResultDTOByItemSerial(itemSerial);
		} catch (DAOException e) {
			logger.error("Exception caught in getStockResultDTOByItemSerial()",
					e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<StockResultDTO> getStockResultDTOByOrderId(String orderId) {
		try {
			return stockdao.getStockResultDTOByOrderId(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getStockResultDTOByOrderId()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	// add by eliot for hottest model management 20120314
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertHottestModel(String itemCode, Date startDate,
			Date endDate, String user) {
		try {
			return stockdao.insertHottestModel(itemCode, startDate, endDate,
					user);
		} catch (DAOException e) {
			logger.error("Exception caught in insertHottestModel()", e);
			throw new AppRuntimeException(e);
		}
	}

	// add by eliot for hottest model management 20120314
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateHottestModelStartDate(Date endDate, String user,
			String itemCode, Date startDate) {
		try {
			return stockdao.updateHottestModelStartDate(endDate, user,
					itemCode, startDate);
		} catch (DAOException e) {
			logger.error("Exception caught in updateHottestModelStartDate()", e);
			throw new AppRuntimeException(e);
		}
	}

	// add by eliot for hottest model management 20120314
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<HottestModelManagementUI> getHottestModelAndPeriod(
			String itemCode) {
		try {
			return stockdao.getHottestModelAndPeriod(itemCode);
		} catch (DAOException e) {
			logger.error("Exception caught in getHottestModelAndPeriod()", e);
			throw new AppRuntimeException(e);
		}
	}

	// add by eliot for hottest model management 20120314
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<String[]> getModelSearch(String itemCode, String itemDesc,
			String codeId) {
		try {
			return stockdao.getModelSearch(itemCode, itemDesc, codeId);
		} catch (DAOException e) {
			logger.error("Exception caught in getModelSearch()", e);
			throw new AppRuntimeException(e);
		}
	}

	// add by eliot for hottest model management 20120316
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public boolean validHottestModelManagementOverlap(String actionType,
			String itemCode, Date startDate, Date endDate) {
		try {
			return stockdao.validHottestModelManagementOverlap(actionType,
					itemCode, startDate, endDate);
		} catch (DAOException e) {
			logger.error("Exception caught in validHottestModelManagementOverlap()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	/**
	 * For CCS use only<br>
	 * Showing the stock quantity of each item related to a particular order
	 */
	public List<String> checkStockQty(String orderId) {
		try {
			return stockdao.checkStockQty(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in checkStockQty()", e);
			throw new AppRuntimeException(e);
		}
	}

	public List<MaintParmLkupDTO> getMaintParmValue(String channelCd, FunctionCd functionCd, ParmType parmType) {
    	logger.info("getMaintParmValue() is called in MrtInventoryServiceImpl");
    	return this.mobCcsMaintParmLkupService.getMaintParmLkupDTO(channelCd, "MOB", functionCd.getValue(), parmType.toString());
	}
	
	public List<StockDTO> getEventList() {
		try {
			return dsStockdao.getEventList();
		} catch (Exception e) {
			logger.error("Exception caught in getEventList()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<StockDTO> getValidEventList() {
		try {
			return dsStockdao.getValidEventList();
		} catch (Exception e) {
			logger.error("Exception caught in getValidEventList()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<StockDTO> getStoreList(String channelCd) {
		try {
			return dsStockdao.getStoreList(channelCd);
		} catch (Exception e) {
			logger.error("Exception caught in getStoreList()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<StockDTO> getTeamList(String channelCd) {
		try {
			return dsStockdao.getTeamList(channelCd);
		} catch (Exception e) {
			logger.error("Exception caught in getTeamList()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<StockDTO> getTeamListByStore(String storeCode) {
		try {
			return dsStockdao.getTeamListByStore(storeCode);
		} catch (Exception e) {
			logger.error("Exception caught in getTeamListByStore()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<StockAssgnDTO> getStockAssgnList(String basketID, String[] vasItemList) {
		try {
			return stockdao.getStockAssgnList(basketID, vasItemList);
		} catch (DAOException e) {
			logger.error("Exception caught in getStockAssgnList()", e);
			throw new AppRuntimeException(e);
		}
	}

	public List<String> getDsSimNum(String staffID,	Date checkDate, int simType, String channelCd, String storeCd, String teamCd){
		try {
			List<String> simList = new ArrayList<String>();
			if ("MDV".equals(channelCd)) {
				simList = stockdao.getMDVSimNum(checkDate, staffID, simType);
			} else if ("SIS".equals(channelCd)) {
				simList = stockdao.getSISSimNum(checkDate, staffID, simType, storeCd, teamCd);
			} else {
				simList = null;
			}
			return simList;
		} catch (Exception e) {
			logger.error("Exception caught in getDsSimNum()", e);
			return null;
		}
	}
	
	public List<String> getDsSerialNum(String itemCd, String staffID, Date checkDate, String channelCd, String storeCd, String teamCd) {
		try {
			//String channelCd = mobDsMrtManagementDAO.getSalesChannelCd(staffID, checkDate);
			List<String> serialList = new ArrayList<String>();
			if ("MDV".equals(channelCd)) {
				serialList = stockdao.getMDVSerialNum(checkDate, staffID, itemCd);
			} else if ("SIS".equals(channelCd)) {
				serialList = stockdao.getSISSerialNum(itemCd, storeCd, teamCd);
			} else {
				serialList = null;
			}
			return serialList;
		} catch (Exception e) {
			logger.error("Exception caught in getDsSerialNum()", e);
			return null;
		}
	}
	
	public String[] getShopFromOrder(String orderId) {
		try {
			return stockdao.getShopFromOrder(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in getShopFromOrder()", e);
			return null;
		}
	}
	
	public int isValidSerial(String itemCode, String serialNum, String staffID,
			String channelCd, String storeCd, String teamCd, Date checkDate) {
		try {
			int result = 0;
			if ("MDV".equals(channelCd)) {
				result = stockdao.isValidSerial_MDV(checkDate, staffID, itemCode, serialNum);
			} else if ("SIS".equals(channelCd)) {
				result = stockdao.isValidSerial_SIS(checkDate, staffID, itemCode, serialNum, storeCd, teamCd);
			} else {
				result = -1;
			}
			if (result > 0) {
				if (result == 27 || result == 28) {
					result = 1;
				} else {
					result = -2;
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in getInventoryStatus()", e);
			return -1;
		}
	}

	public int updateStockInventory(String item_code, String item_serial,
			String status_id) {
		try {
			return stockdao.updateStockInventory(item_code, item_serial, status_id);
		} catch (DAOException e) {
			logger.error("Exception caught in updateStockInventory()", e);
			return -1;
		}
		
	}

	public List<StockAssgnDTO> getStockAssgnListByOrderId(String orderId) {
		try {
			return stockdao.getStockAssgnListByOrderId(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getStockAssgnListByOrderId()", e);
			return null;
		}
	}

	public int updateBomWebStockAssgnStatus(String status_id, String orderId,
			String itemCode, String itemSerial) {
		try {
			return stockdao.updateBomWebStockAssgnStatus(status_id, orderId, itemCode, itemSerial);
		} catch (DAOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public String getSimType(String itemCode) {
		try {
			return stockdao.getSimType(itemCode);
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int updateSalesShopCode(String staffID, String storeCode, String teamCode) {
		try {
			return dsStockdao.updateSalesShopCode(staffID, storeCode, teamCode);
		} catch (DAOException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
