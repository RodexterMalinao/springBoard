package com.activity.service;

import com.activity.dao.ActivityDetailDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class ActivityDetailServiceImpl implements ActivityDetailService {

	private ActivityDetailDAO activityDetailDao = null;
	

	public String[] getPendingActvByOrderId(String pOrderId, String pActvType) {
	
		try {
			return this.activityDetailDao.getPendingActvByOrderId(pOrderId, pActvType);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to getPendingActvByOrderId", ex);
		}
	}
	
	public String[] getAllActvByOrderId(String pOrderId, String pActvType) {
		
		try {
			return this.activityDetailDao.getAllActvByOrderId(pOrderId, pActvType);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to getPendingActvByOrderId", ex);
		}
	}
	
	public String[] getActvByWqWpAssgnId(String pWqWpAssgnId) {
		
		try {
			return this.activityDetailDao.getActvByWqWpAssgnId(pWqWpAssgnId);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to getActvByWqWpAssgnId", ex);
		}
	}
	
	public String[] getRelatedAssgnIdFromSearchSourceByAssgnId(String pWqWpAssgnId) {
		
		try {
			return this.activityDetailDao.getRelatedAssgnIdFromSearchSourceByAssgnId(pWqWpAssgnId);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to getRelatedAssgnIdFromSearchSourceByAssgnId", ex);
		}
	}

	public ActivityDetailDAO getActivityDetailDao() {
		return activityDetailDao;
	}

	public void setActivityDetailDao(ActivityDetailDAO activityDetailDao) {
		this.activityDetailDao = activityDetailDao;
	}

	@Override
	public String[] getAllActvByOrderId(String pOrderId, String pActvType, Boolean pIsOnlyCompleted) {
		if (pIsOnlyCompleted == false) {
			return this.getAllActvByOrderId(pOrderId, pActvType);
		} else {
			try {
				return this.activityDetailDao.getAllCompletedActvByOrderId(pOrderId, pActvType);
			} catch (DAOException ex) {
				throw new AppRuntimeException("Fail to getPendingActvByOrderId", ex);
			}
		}
	}
}
