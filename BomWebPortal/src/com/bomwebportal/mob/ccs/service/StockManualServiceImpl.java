package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDTO;
import com.bomwebportal.mob.ds.dao.MobDsStockManualDAO;

@Transactional(readOnly=true)
public class StockManualServiceImpl implements StockManualService{
	
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
	
	public List<StockManualDTO> getStockManual(String startDate, String endDate, 
			String orderStatus, String orderId, String searchCriteria) {
		try {
			logger.info("getStockManual() is called in StockManualServiceImpl");
			return stockdao.getStockManual(startDate, endDate, 
					orderStatus, orderId, searchCriteria);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockManual()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<StockManualDTO> getDSStockManual(String startDate, String endDate, 
			String orderStatus, String orderId, String searchCriteria, String teamCode) {
		try {
			logger.info("getDSStockManual() is called in StockManualServiceImpl");
			return stockdao.getDSStockManual(startDate, endDate, 
					orderStatus, orderId, searchCriteria, teamCode);
		} catch (DAOException de) {
			logger.error("Exception caught in getDSStockManual()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<CodeLkupDTO> getDSStockStatusList() {
		try {
			logger.info("getDSStockStatusList() is called in StockManualServiceImpl");
			return stockdao.getStockMainStatusList("MDV");
		} catch (Exception e) {
			logger.error("Exception caught in getDSStockStatusList()", e);
			throw new AppRuntimeException(e);
		}
	}
}
