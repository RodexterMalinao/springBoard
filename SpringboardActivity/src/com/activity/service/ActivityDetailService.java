package com.activity.service;

public interface ActivityDetailService {

	public abstract String[] getPendingActvByOrderId(String pOrderId,
			String pActvType);
	
	public abstract String[] getAllActvByOrderId(String pOrderId,
			String pActvType); 
	
	public abstract String[] getAllActvByOrderId(String pOrderId,
			String pActvType, Boolean pIsOnlyCompleted); 

	public abstract String[] getActvByWqWpAssgnId(String pWqWpAssgnId);
	
	public abstract String[] getRelatedAssgnIdFromSearchSourceByAssgnId(String pWqWpAssgnId);
}