package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.StockManualAssgnUI;
import com.bomwebportal.mob.ds.dao.MobDsStockManualDAO;

@Transactional(readOnly=true)
public class StockManualAssgnServiceImpl implements StockManualAssgnService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockDAO stockdao;
	private MobDsStockManualDAO mobDsStockManualDAO;

	public void setStockdao(StockDAO stockdao) {
		this.stockdao = stockdao;
	}
	public StockDAO getStockdao() {
		return stockdao;
	}
	public MobDsStockManualDAO getMobDsStockManualDAO() {
		return mobDsStockManualDAO;
	}
	public void setMobDsStockManualDAO(MobDsStockManualDAO mobDsStockManualDAO) {
		this.mobDsStockManualDAO = mobDsStockManualDAO;
	}
	
	// get possible item serials
	public List<String> getStockManualAssgn(String itemCode, String locCd, String statusCd, String stockPool) {
		try {
			logger.info("getStockManualAssgn() is called in StockManualAssgnServiceImpl");
			return stockdao.getStockManualAssgn(itemCode, locCd, statusCd, stockPool);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockManualAssgn()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<String> getDSStockManualAssgn(String itemCode, String orderId) {
		try {
			logger.info("getStockManualAssgn() is called in StockManualAssgnServiceImpl");
			return stockdao.getDSStockManualAssgn(itemCode, orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockManualAssgn()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean manualStockAssgn(StockManualAssgnUI dto) {
		try {
			logger.info("manualStockAssgn() is called in StockManualAssgnServiceImpl");
			return stockdao.manualStockAssgn(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockManualAssgn()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int manualDSStockAssgn(StockManualAssgnUI dto, StockManualAssgnUI oDto, String orderId, String username, String action) {
		try {
			logger.info("manualStockAssgn() is called in StockManualAssgnServiceImpl");
			return mobDsStockManualDAO.manualDSStockAssgn(dto, oDto, orderId, username, action);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockManualAssgn()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<Object[]> getOrderInfo(String orderId) {
		try {
			logger.info("getOrderInfo() is called in StockManualAssgnServiceImpl");
			return stockdao.getOrderInfo(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderInfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<CodeLkupDTO> getItemColorlist(String itemCode) {
		try {
			logger.info("getItemColorlist() is called in StockManualAssgnServiceImpl");
			return stockdao.getItemColorlist(itemCode);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderInfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public StockManualAssgnUI getAssignedItemSerialStatus(String orderId, String itemCode) {
		try {
			logger.info("getAssignedItemSerialStatus() is called in StockManualAssgnServiceImpl");
			return mobDsStockManualDAO.getAssignedItemSerialStatus(orderId, itemCode);
		} catch (DAOException de) {
			logger.error("Exception caught in getAssignedItemSerialStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getStatusChange(String orderId, String status, String itemCode, String action) {
		try {
			logger.info("getAssignedItemSerialStatus() is called in StockManualAssgnServiceImpl");
			return mobDsStockManualDAO.getStatusChange(orderId, status, itemCode, action);
		} catch (DAOException de) {
			logger.error("Exception caught in getAssignedItemSerialStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
}
