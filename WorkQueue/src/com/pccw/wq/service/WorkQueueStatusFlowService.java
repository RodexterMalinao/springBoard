/*
 * Created on Nov 8, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.service;

import com.pccw.wq.dao.ConstantLkupDAO;

public interface WorkQueueStatusFlowService {
	public boolean isValidStatusChange(String pFromStatus, String pToStatus);

	public String [] getValidToStatusChange(String [] pFromStatus);
	
	public ConstantLkupDAO getConstantLkupDAO();

	public void setConstantLkupDAO(ConstantLkupDAO constantLkupDAO);
	
}
