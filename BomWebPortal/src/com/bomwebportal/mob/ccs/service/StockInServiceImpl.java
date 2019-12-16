package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dao.StockInDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ccs.dto.StockInDTO;

@Transactional(readOnly=true)
public class StockInServiceImpl implements StockInService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockDAO stockdao;
	private StockInDAO stockIndao;
	
	public void setStockdao(StockDAO stockdao) {
		this.stockdao = stockdao;
	}
	public StockDAO getStockdao() {
		return stockdao;
	}

	public void setStockIndao(StockInDAO stockIndao) {
		this.stockIndao = stockIndao;
	}
	public StockInDAO getStockIndao() {
		return stockIndao;
	}
	
	public List<StockInDTO> getStockInDTO(String itemCode) {
		try {
			logger.info("getStockInDTO() is called in StockInServiceImpl");
			return stockIndao.getStockInDTO(itemCode);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockInDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<StockCatalogDTO> getTypeNModel(String itemCode) {
		try {
			logger.info("getTypeNModel() is called in StockInServiceImpl");
			return stockIndao.getTypeNModel(itemCode);
		} catch (DAOException de) {
			logger.error("Exception caught in getTypeNModel()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<CodeLkupDTO> getStatusList() {
		try {
			logger.info("getStatusList() is called in StockInServiceImpl");
			return stockdao.getStatusList();
		} catch (DAOException de) {
			logger.error("Exception caught in getStatusList()", de);
			throw new AppRuntimeException(de);
		}
	}

	public String[] insertStockInventory(StockInDTO dto, String username) {
		try {
			logger.info("insertStockInventory() is called in StockInServiceImpl");
			return stockIndao.insertStockInventory(dto, username);
		} catch (DAOException de) {
			logger.error("Exception caught in insertStockInventory()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int insertDsStockInventory(StockInDTO dto, String username) {
		try {
			logger.info("insertDsStockInventory() is called in StockInServiceImpl");
			return stockIndao.insertDsStockInventory(dto, username);
		} catch (DAOException de) {
			logger.error("Exception caught in insertDsStockInventory()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int updateDsRWHStockInventory(StockInDTO originalDto, StockInDTO dto, String username) {
		try {
			logger.info("insertDsStockInventory() is called in StockInServiceImpl");
			return stockIndao.updateDsRWHStockInventory(originalDto, dto, username);
		} catch (DAOException de) {
			logger.error("Exception caught in insertDsStockInventory()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<StockInDTO> checkDuplicateStockIn(String itemCode, String imei) {
		try {
			logger.info("checkDuplicateStockIn() is called in StockInServiceImpl");
			return stockIndao.checkDuplicateStockIn(itemCode, imei);
		} catch (DAOException de) {
			logger.error("Exception caught in checkDuplicateStockIn()", de);
			throw new AppRuntimeException(de);
		}
	}

	public String[] insertFreeGiftItem(StockInDTO dto) {
		try {
			logger.info("insertFreeGiftItem() is called in StockInServiceImpl");
			return stockIndao.insertFreeGiftItem(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertFreeGiftItem()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int getItemSerialLengthByTypeId(String typeId) {
		try {
			logger.info("getItemSerialLengthByTypeId() is called in StockInServiceImpl");
			return stockIndao.getItemSerialLengthByTypeId(typeId);
		} catch (DAOException de) {
			logger.error("Exception caught in getItemSerialLengthByTypeId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<StockInDTO> checkValidStaff(String staffId, String storeCd, String teamCd) {
		try {
			logger.info("checkValidStaff() is called in StockInServiceImpl");
			return stockIndao.checkValidStaff(staffId, storeCd, teamCd);
		} catch (DAOException de) {
			logger.error("Exception caught in checkValidStaff()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean allowRWHStock(StockInDTO newStock, List<StockInDTO> duplicatedStockList) {
		boolean allowRWH = false;
		
		if (CollectionUtils.isEmpty(duplicatedStockList)) {
			return false;
		}
		
		if ("29".equalsIgnoreCase(newStock.getStatus())) {
			for (StockInDTO stock : duplicatedStockList) {
				if ("27".equalsIgnoreCase(stock.getStatus()) || "28".equalsIgnoreCase(stock.getStatus())) {
					allowRWH = true;
				}
			}
		} else {
			for (StockInDTO stock : duplicatedStockList) {
				if ("22".equalsIgnoreCase(stock.getStatus()) || "29".equalsIgnoreCase(stock.getStatus())) {
					allowRWH = true;
				}
			}
		}
		return allowRWH;
	}
	
	public int insertDsRWHStockInventoryHistory(StockInDTO originalDto, StockInDTO dto, String username) {
		try {
			logger.info("checkValidStaff() is called in StockInServiceImpl");
			return stockIndao.insertDsRWHStockInventoryHistory(originalDto, dto, username);
		} catch (DAOException de) {
			logger.error("Exception caught in checkValidStaff()", de);
			throw new AppRuntimeException(de);
		}
	}
}
