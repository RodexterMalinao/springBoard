/*
 * Created on Nov 9, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.service;

import com.bomwebportal.dao.CodeLkupDAO;

public interface CodeLkupCacheService {
	
	public static final String WILD_CARDS = "*";
	
	public CodeLkupDAO getCodeLkupDAO();

	public void setCodeLkupDAO(CodeLkupDAO codeLkupDAO);
	
    public Object get(String pKey);
}
