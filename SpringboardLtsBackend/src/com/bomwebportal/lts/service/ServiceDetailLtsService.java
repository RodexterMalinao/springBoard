package com.bomwebportal.lts.service;

public interface ServiceDetailLtsService {
	
	public abstract void updateDnStatus(String serviceInventSts, String orderId, String dtlId, String user);
	
	public abstract void updateGatewayNum(String gatewayNum, String orderId, String dtlId, String user);

}
