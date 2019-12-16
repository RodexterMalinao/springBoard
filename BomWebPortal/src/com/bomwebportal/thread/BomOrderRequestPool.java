package com.bomwebportal.thread;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BomOrderRequestPool {
	
	private List<String> bomRequestList;
	protected final Log logger = LogFactory.getLog(getClass());
	
	public BomOrderRequestPool(){
		bomRequestList = Collections.synchronizedList(new ArrayList<String>());
	}

	public synchronized String getBomOrderReq(){
		if(bomRequestList.size()!=0){
			String bomRequestLaunch = (String)bomRequestList.get(0);
			logger.info("Try to get the object from pool [" + bomRequestList.size()+"]");
			bomRequestList.remove(0);

			return bomRequestLaunch;
		}else{
			return null;
		}
	}
	
	public synchronized void addBomOrderRequest(String bomRequestLaunch){
		bomRequestList.add(bomRequestLaunch);
	}
	
	public synchronized void addBomOrderRequestList(List<String> bomRequestLaunchList){
		for(int i=0; i<bomRequestLaunchList.size(); i++){
			bomRequestList.add((String)bomRequestLaunchList.get(i));
		}
	}
	
	public synchronized boolean isRequestPoolEmpty(){
		return bomRequestList.isEmpty();
	}
	
	public synchronized int getRequestPoolSize(){
		return bomRequestList.size();
	}
}
