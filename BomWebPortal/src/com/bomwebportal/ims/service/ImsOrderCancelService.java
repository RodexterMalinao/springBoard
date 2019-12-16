package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.ims.dto.CancelOrderDTO;

public interface ImsOrderCancelService {
	
	public List<CancelOrderDTO> getPendingToCancelOrder();
	public void cancelOrder(CancelOrderDTO order);

}
