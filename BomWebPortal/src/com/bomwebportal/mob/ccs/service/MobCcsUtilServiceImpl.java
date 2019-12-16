package com.bomwebportal.mob.ccs.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsUtilDAO;

@Transactional(readOnly=true)
public class MobCcsUtilServiceImpl implements MobCcsUtilService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobCcsUtilDAO mobCcsUtilDAO;
	
	public MobCcsUtilDAO getMobCcsUtilDAO() {
		return mobCcsUtilDAO;
	}
	public void setMobCcsUtilDAO(MobCcsUtilDAO mobCcsUtilDAO) {
		this.mobCcsUtilDAO = mobCcsUtilDAO;
	}

	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public Date getNextNDeliveryDay(Date deliveryDate) {
		try {
			logger.info("getNextNDeliveryDay() is called in MobCcsUtilServiceImpl");
			return this.mobCcsUtilDAO.getNextNDeliveryDay(deliveryDate, 1);
		} catch (DAOException de) {
			logger.error("Exception caught in getNextNDeliveryDay()", de);
			throw new AppRuntimeException(de);
		}
	}

}
