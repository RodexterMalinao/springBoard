package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ccs.dto.StockModelDetailsDTO;
import com.bomwebportal.mob.ccs.dto.StockModelResultDTO;

@Transactional(readOnly=true)
public class StockModelDetailsServiceImpl implements StockModelDetailsService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockDAO stockdao;

	public void setStockdao(StockDAO stockdao) {
		this.stockdao = stockdao;
	}
	public StockDAO getStockdao() {
		return stockdao;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<StockCatalogDTO> getStockCatalogList() {
		try {
			logger.info("getStockCatalogList() is called in StockModelDetailsServiceImpl");
			return stockdao.getStockCatalogList();
		} catch (DAOException de) {
			logger.error("Exception caught in getStockCatalogList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<StockModelDetailsDTO> getStockCatalogListByItemCode(String itemCode) {
		try {
			logger.info("getStockCatalogListByItemCode() is called in StockModelDetailsServiceImpl");
			return stockdao.getStockCatalogListByItemCode(itemCode);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockCatalogListByItemCode()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertModelDetails(StockModelDetailsDTO dto) {
		try {
			logger.debug("enter insert model details@StockModelDetailsServImpl");
			int isSuccess = stockdao.insertModelDetails(dto);
			logger.debug("insert model details: isSuccess = " + isSuccess);
			return isSuccess;
		} catch (DAOException de) {
			logger.error("Exception caught in insertModelDetails()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateModelDetails(StockModelDetailsDTO dto) {
		try {
			logger.debug("enter update model details@StockModelDetailsServImpl");
			int isSuccess = stockdao.updateModelDetails(dto);
			logger.debug("update model details: isSuccess = " + isSuccess);
			return isSuccess;
		} catch (DAOException de) {
			logger.error("Exception caught in updateModelDetails()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<StockModelResultDTO> getStockModelResultDTO (String type, String model) {
		try {
			logger.info("getStockModelResultDTO() is called in StockModelDetailsServiceImpl");
			return stockdao.getStockModelResultDTO(type, model);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockModelResultDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
}
