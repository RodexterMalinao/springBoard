/*
 * Created on Nov 8, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pccw.wq.dao.ConstantLkupDAO;
import com.pccw.wq.schema.dto.SelectionItemDTO;

public class WorkQueueStatusFlowServiceImpl implements WorkQueueStatusFlowService {
	
	private static Logger logger = Logger.getLogger(WorkQueueStatusFlowServiceImpl.class);
		
	private HashMap<String,String []> statusFlowMap = null; 
					
	private ConstantLkupDAO constantLkupDAO;
	
	private void buildStatusFlow() {
		statusFlowMap = new HashMap<String,String []>();
        String tmpStatus;
    	String [] validStatus;
		
		List <SelectionItemDTO> selections = constantLkupDAO.getCodeLkup();
 		
        for (SelectionItemDTO statusFlow : selections) {
        	tmpStatus = statusFlow.getItemValue();
        	validStatus = StringUtils.split(tmpStatus,",");
        	statusFlowMap.put(statusFlow.getItemKey(),validStatus);
 		}    		
	}
	
	public String [] getValidToStatusChange(String [] pFromStatus) {
		if (ArrayUtils.isEmpty(pFromStatus)) {
			return null;
		}
		
        if (statusFlowMap == null) {
        	this.buildStatusFlow();
        }		
        
        //Get unique values first
        HashSet<String> uniqueSet = new HashSet<String>();
        for (int i = 0; i < pFromStatus.length;i++) {
        	uniqueSet.add(pFromStatus[i]);
        }
        
        String[] sUniqueFromStatus = (String[]) uniqueSet.toArray(new String[uniqueSet.size()]);
        
        int statusListCnt = 0;        
        ArrayList<String> resultSet = null;
		if (!statusFlowMap.containsKey(sUniqueFromStatus[0])) {
			return null;
		}

		resultSet = new ArrayList<String>(Arrays.asList(statusFlowMap.get(sUniqueFromStatus[0])));
		if (resultSet.size() <= 0) {
			return null;
		}

        ArrayList <String> tmpSet = new ArrayList<String>();        
		
		for ( int i = statusListCnt; i < sUniqueFromStatus.length; i++)  {
			if (statusFlowMap.containsKey(sUniqueFromStatus[i])) {
			   tmpSet.addAll(Arrays.asList(statusFlowMap.get(sUniqueFromStatus[i])));
			} else {
				return null;
			}
			resultSet.retainAll(tmpSet);
		}
		
		return (String [])resultSet.toArray(new String[resultSet.size()]);
	}

	public boolean isValidStatusChange(String pStatusFrom, String pStatusTo) {
        if (statusFlowMap == null) {
        	this.buildStatusFlow();
        }
        
		if (StringUtils.isEmpty(pStatusFrom)
				|| StringUtils.isEmpty(pStatusTo)
				|| !statusFlowMap.containsKey(pStatusFrom)) {
			return false;
		}
		
		String[] status = (String[]) statusFlowMap.get(pStatusFrom);
		for (int i = 0; i < status.length; i++) {
			if (pStatusTo.equals(status[i])) {
				return true;
			}
		}
		return false;        
	}

	public ConstantLkupDAO getConstantLkupDAO() {
		return constantLkupDAO;
	}

	public void setConstantLkupDAO(ConstantLkupDAO constantLkupDAO) {
		this.constantLkupDAO = constantLkupDAO;
	}

}
