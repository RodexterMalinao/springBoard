package com.activity.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.activity.dao.ActivityIdDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class ActivityIdFactory {

	private final Log logger = LogFactory.getLog(getClass());
	private ActivityIdDAO activityIdDao = null;
	
	
	public String generateActvId() {
		
		try {
			return this.activityIdDao.generateActvId();
		} catch (DAOException ex) {
			logger.error("Error in generateActvId()");
			throw new AppRuntimeException(ex.getMessage(), ex);
		}
	}
	
	
	public int getMaxTaskSeq(String pActvId) {
		
		try {
			return this.activityIdDao.getMaxTaskSeq(pActvId);
		} catch (DAOException ex) {
			logger.error("Error in getMaxTaskSeq()");
			throw new AppRuntimeException(ex.getMessage(), ex);
		}
	}
	
	public int getMaxDocumentSeq(String pId, String pDocType) {
		
		try {
			return this.activityIdDao.getMaxDocumentSeq(pId, pDocType);
		} catch (DAOException ex) {
			logger.error("Error in getMaxDocumentSeq()");
			throw new AppRuntimeException(ex.getMessage(), ex);
		}
	}

	public ActivityIdDAO getActivityIdDao() {
		return activityIdDao;
	}


	public void setActivityIdDao(ActivityIdDAO activityIdDao) {
		this.activityIdDao = activityIdDao;
	}
}
