package com.bomltsportal.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomltsportal.dao.AvailableServiceDAO;


@Transactional(readOnly = true)
public class AvailablePeriodServiceImpl implements AvailablePeriodService{

	protected final Log logger = LogFactory.getLog(getClass());
	private AvailableServiceDAO availableServiceDAO;
	
	public boolean IsServiceInMaintenance() {
		logger.info("IsServiceInMaintenance");
		
		try {
			return this.availableServiceDAO.IsServiceInMaintenance();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in IsServiceInMaintenance()", e);
		}
		
		return true;
	}

	public String getMaintenanceDetail(String lang){
		logger.info("getMaintenanceDetail");
		try {
			return this.availableServiceDAO.getMaintenanceDetail(lang);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getMaintenanceDetail()", e);
			return null;
		}
	}
	
	public AvailableServiceDAO getAvailableServiceDAO() {
		return availableServiceDAO;
	}

	public void setAvailableServiceDAO(AvailableServiceDAO availableServiceDAO) {
		this.availableServiceDAO = availableServiceDAO;
	}
	
}
