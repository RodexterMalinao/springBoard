package com.bomltsportal.service;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.dao.ShopDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class ShopServiceImpl implements ShopService {
	
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected ShopDAO shopDAO;
	
	public ShopDAO getShopDAO() {
		return shopDAO;
	}

	public void setShopDAO(ShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}

	public String getSalesChannel(String shopCd) {
		
		try {
			return shopDAO.getSalesChannel(shopCd);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new AppRuntimeException(de.getCustomMessage());
		}
	}

}
