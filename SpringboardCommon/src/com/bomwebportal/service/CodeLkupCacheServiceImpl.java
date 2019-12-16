/*
 * Created on Nov 9, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.service;


import java.util.logging.Logger;

import com.bomwebportal.dao.CodeLkupDAO;
import com.bomwebportal.dto.LookupItemDTO;
import com.pccw.util.cache.CacheManager;

public class CodeLkupCacheServiceImpl extends CacheManager implements CodeLkupCacheService {
	
//	private static Logger logger = Logger.getLogger(CodeLkupCacheServiceImpl.class);	
	
	private CodeLkupDAO codeLkupDAO;
		
	public void buildCacheMap() {
		LookupItemDTO [] lookupItemDTO = null;
		try {
		   lookupItemDTO = codeLkupDAO.getCodeLkup();
		} catch (Exception e) {
//		   logger.error(e.getMessage());
		}
		
        for (int i = 0;lookupItemDTO != null && i < lookupItemDTO.length; i++) {
        	this.put(lookupItemDTO[i].getItemKey(),lookupItemDTO[i].getItemValue());
 		}    				
        
        if (!this.containsKey(WILD_CARDS)) {
        	this.put("*",lookupItemDTO);        	
        }                
	}

	public CodeLkupDAO getCodeLkupDAO() {
		return codeLkupDAO;
	}

	public void setCodeLkupDAO(CodeLkupDAO codeLkupDAO) {
		this.codeLkupDAO = codeLkupDAO;
	}

}
