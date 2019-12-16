package com.bomwebportal.lts.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dao.OfferChangeDAO;

public class OfferChangeServiceImpl implements OfferChangeService {

	private OfferChangeDAO offerChangeDao;

	public OfferChangeDAO getOfferChangeDao() {
		return offerChangeDao;
	}

	public void setOfferChangeDao(OfferChangeDAO offerChangeDao) {
		this.offerChangeDao = offerChangeDao;
	}
	
	public List<String[]> getOfferChangeList(String ltsFromProd,
			String ltsToProd, String imsFromProd, String imsToProd, String mirror, String[] fromOffers, String rentalRouter) {
		try {
			return offerChangeDao.getOfferChangeList(ltsFromProd, ltsToProd, 
					StringUtils.replace(imsFromProd, "TV", ""), StringUtils.replace(imsToProd, "TV", ""), mirror, fromOffers, rentalRouter);
		}
		catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
	
}
