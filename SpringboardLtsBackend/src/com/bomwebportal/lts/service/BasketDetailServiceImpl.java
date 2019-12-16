package com.bomwebportal.lts.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.BasketDetailDAO;

public class BasketDetailServiceImpl implements BasketDetailService {

	private BasketDetailDAO basketDetailDao = null;
	
	private final Log logger = LogFactory.getLog(getClass());


	public String getBasketContractPeriod(String pBasketId) throws DAOException {
		
		try{
			return this.basketDetailDao.getBasketContractPeriod(pBasketId);
		} catch (DAOException de) {
			logger.error("Fail in BasketDetailService.getBasketContractPeriod()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public BasketDetailDAO getBasketDetailDao() {
		return basketDetailDao;
	}

	public void setBasketDetailDao(BasketDetailDAO basketDetailDao) {
		this.basketDetailDao = basketDetailDao;
	}
}
