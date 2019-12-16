package com.bomwebportal.lts.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.OfferDetailProfileDAO;
import com.bomwebportal.lts.dto.ItemDetailDTO;

public class OfferProfileServiceImpl implements OfferProfileService {

	protected OfferDetailProfileDAO offerDetailProfileDao = null;
	
	protected final Log logger = LogFactory.getLog(getClass());
	

	public ItemDetailDTO getTpVasItemDetail(String pItemId, String pLocale) {
		try {
			return this.offerDetailProfileDao.getTpVasItemDetail(pItemId, pLocale);
		} catch (DAOException de) {
			logger.error("Fail in OfferService.getTpVasItemDetail()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public OfferDetailProfileDAO getOfferDetailProfileDao() {
		return offerDetailProfileDao;
	}

	public void setOfferDetailProfileDao(OfferDetailProfileDAO offerDetailProfileDao) {
		this.offerDetailProfileDao = offerDetailProfileDao;
	}
}
