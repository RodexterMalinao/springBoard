package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dto.StockModelResultDTO;

@Transactional(readOnly=true)
public class StockModelServiceImpl implements StockModelService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockDAO stockdao;

	public void setStockdao(StockDAO stockdao) {
		this.stockdao = stockdao;
	}
	public StockDAO getStockdao() {
		return stockdao;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<StockModelResultDTO> getStockModelResultDTO (String type, String model) {
		try {
			logger.info("getStockModelResultDTO() is called in StockModelServiceImpl");
			return stockdao.getStockModelResultDTO(type, model);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockModelResultDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

}
