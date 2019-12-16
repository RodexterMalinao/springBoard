package com.bomwebportal.service;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.MobPreActReqDAO;
import com.bomwebportal.dto.ShopDTO;
import com.bomwebportal.dto.report.MobPreActReqDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class MobPreActReqServiceImpl implements MobPreActReqService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobPreActReqDAO mobPreActReqDAO;
	
	public MobPreActReqDAO getMobPreActReqDAO() {
		return mobPreActReqDAO;
	}

	public void setMobPreActReqDAO(MobPreActReqDAO mobPreActReqDAO) {
		this.mobPreActReqDAO = mobPreActReqDAO;
	}

	public String generatePreActivationCode() {
		if (logger.isDebugEnabled()) {
			logger.debug("generatePreActivationCode() is called in CustomerProfileServiceImpl");
		}
		Random rand = new Random(System.currentTimeMillis());		
		int num = rand.nextInt(999999) + 1000001;
		return (String.valueOf(num)).substring(1, 7);
	}
	
	public MobPreActReqDTO getProcessActivationLetterDetail(String msisdn) {
		try {
			return mobPreActReqDAO.getProcessActivationLetterDetail(msisdn);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomAddrDtl()", de);
			throw new AppRuntimeException(de);
		}
	}

	public ShopDTO getShopDetail(String shopCd) {
		try {
			return mobPreActReqDAO.getShopDetail(shopCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getShopDetail()", de);
			throw new AppRuntimeException(de);
		}
	}
}
