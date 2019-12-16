package com.bomwebportal.bean;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bomwebportal.dao.ConstantLkupDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.SystemTime;
import com.bomwebportal.util.SystemTime.TimeSource;

@Component
public class DBAdjustedTimeSource implements TimeSource {
	private final static Log logger = LogFactory.getLog(DBAdjustedTimeSource.class);

	private long timeDiff = 0;
	


	@Autowired
	private InitTestConnectionBean initTestConnectionBean;
	
	@Autowired
	private ConstantLkupDAO constantLkupDAO;
	

	public ConstantLkupDAO getConstantLkupDao() {
		return constantLkupDAO;
	}


	public void setConstantLkupDao(ConstantLkupDAO constantLkupDao) {
		this.constantLkupDAO = constantLkupDao;
	}
	
	


	
	public long millis() {
		return new Date().getTime() + timeDiff;
	}

	
	@PostConstruct
	public void adjustSystemTime() {
		timeDiff = 0;
		try {
			Date d = constantLkupDAO.getSysDate();
			timeDiff = d.getTime() - new Date().getTime();
			SystemTime.setTimeSource(this);
		} catch (DAOException e) {
			logger.error("Failed to get db sysdate, use local system time  ...");
			SystemTime.reset();
		}
	}
}
