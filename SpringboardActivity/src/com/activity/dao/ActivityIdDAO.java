package com.activity.dao;

import com.bomwebportal.exception.DAOException;

public interface ActivityIdDAO {

	public abstract String generateActvId() throws DAOException;

	public abstract int getMaxTaskSeq(String pActvId) throws DAOException;
	
	public abstract int getMaxDocumentSeq(String pId, String pDocType) throws DAOException;

}