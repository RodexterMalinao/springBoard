package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.ims.dto.OrderImsDTO;

public interface ImsAutoSyncBackService {
	
	public List<OrderImsDTO> getBomOcDetail(String orderId);
	public List<OrderImsDTO> getOcPendingOrder();
	public void updateOrderOcDetail(OrderImsDTO order);
	public void createVimBundleChannelRequest(String orderId);
	//public void test() throws Exception;
}
