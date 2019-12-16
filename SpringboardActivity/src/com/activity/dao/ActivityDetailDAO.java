package com.activity.dao;

import com.bomwebportal.exception.DAOException;

public interface ActivityDetailDAO {

	public abstract String[] getPendingActvByOrderId(String pOrderId, String pActvType) throws DAOException;
	
	public abstract String[] getAllActvByOrderId(String pOrderId, String pActvType) throws DAOException;
	
	public abstract String[] getAllCompletedActvByOrderId(String pOrderId, String pActvType) throws DAOException;

	public abstract String[] getActvByWqWpAssgnId(String pWqWpAssgnId) throws DAOException;
	
	public abstract String[] getRelatedAssgnIdFromSearchSourceByAssgnId(String pWqWpAssgnId) throws DAOException;

	public abstract int getNextRemarkSeq(String pActvId, String pTaskSeq) throws DAOException;

	public abstract void updateActvTaskWqWpAssgnId(String pActvId, String pTaskSeq, String pWqWpAssgnId) throws DAOException;
}