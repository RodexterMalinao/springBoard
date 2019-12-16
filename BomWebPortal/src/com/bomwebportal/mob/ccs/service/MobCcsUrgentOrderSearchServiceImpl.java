package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;

public class MobCcsUrgentOrderSearchServiceImpl implements MobCcsUrgentOrderSearchService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	MobCcsOrderDAO mobCcsOrderDao;
	
	
	/**
	 * @return the mobCcsOrderDao
	 */
	public MobCcsOrderDAO getMobCcsOrderDao() {
		return mobCcsOrderDao;
	}


	/**
	 * @param mobCcsOrderDao the mobCcsOrderDao to set
	 */
	public void setMobCcsOrderDao(MobCcsOrderDAO mobCcsOrderDao) {
		this.mobCcsOrderDao = mobCcsOrderDao;
	}


	public List<OrderDTO> getUrgentOrder(String deliveryDate, String orderId) {
		try {
			return mobCcsOrderDao.getUrgentOrder (deliveryDate, orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in findUrgentOrder()", e);
		}
		return null;
	}

}
