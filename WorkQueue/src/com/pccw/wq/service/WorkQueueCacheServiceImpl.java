/*
 * Created on Nov 9, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.pccw.util.cache.CacheManager;
import com.pccw.wq.dao.ConstantLkupDAO;
import com.pccw.wq.schema.dto.SelectionItemDTO;

public class WorkQueueCacheServiceImpl extends CacheManager implements
		WorkQueueCacheService {
	
	private static Logger logger = Logger.getLogger(WorkQueueCacheServiceImpl.class);	
	
	private ConstantLkupDAO constantLkupDAO;
		
	public ConstantLkupDAO getConstantLkupDAO() {
		return constantLkupDAO;
	}

	public void setConstantLkupDAO(ConstantLkupDAO constantLkupDAO) {
		this.constantLkupDAO = constantLkupDAO;
	}
	
	public void buildCacheMap() {
		List <SelectionItemDTO> selections = constantLkupDAO.getCodeLkup();
 		
        for (SelectionItemDTO codeAndDesc : selections) {
        	this.put(codeAndDesc.getItemKey(),codeAndDesc.getItemValue());
 		}    				
	}
}
