package com.bomwebportal.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.SalesDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class SalesServiceImpl implements SalesService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private SalesDAO salesDAO;
	
	public SalesDAO getSalesDAO() {
		return salesDAO;
	}

	public void setSalesDAO(SalesDAO salesDAO) {
		this.salesDAO = salesDAO;
	}

	public void updateShopCd(String shopcd, String username, String areacd) {
		try{
			salesDAO.updateShopCd(shopcd, username, areacd);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
		
	}
}
