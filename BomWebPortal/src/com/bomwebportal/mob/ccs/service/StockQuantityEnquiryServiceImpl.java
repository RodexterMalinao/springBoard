package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockQuantityEnquiryDTO;
import com.bomwebportal.mob.ds.dao.MobDsStockDAO;

@Transactional(readOnly=true)
public class StockQuantityEnquiryServiceImpl implements StockQuantityEnquiryService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockDAO stockdao;
	private MobDsStockDAO dsStockdao;
	
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
	
	public List<StockQuantityEnquiryDTO> getStockQuantityEnquiry(String stockPool, String staffId, String type, String itemCode, String model) {
		try {
			logger.info("getStockQuantityEnquiry() is called in StockQuantityEnquiryServiceImpl");
			return stockdao.getStockQuantityEnquiry(stockPool, staffId, type, itemCode, model);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockQuantityEnquiry()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<StockQuantityEnquiryDTO> getDsStockQuantityEnquiry(StockDTO dto) {
		try {
			logger.info("getDsStockQuantityEnquiry() is called in StockQuantityEnquiryServiceImpl");
			return dsStockdao.getDsStockQuantityEnquiry(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in getDsStockQuantityEnquiry()", de);
			throw new AppRuntimeException(de);
		}
	}
}
