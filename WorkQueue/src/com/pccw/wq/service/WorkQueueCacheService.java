/*
 * Created on Nov 9, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.service;

import com.pccw.wq.dao.ConstantLkupDAO;

public interface WorkQueueCacheService {
	public ConstantLkupDAO getConstantLkupDAO();

	public void setConstantLkupDAO(ConstantLkupDAO constantLkupDAO);
	
    public Object get(String pKey);
}
