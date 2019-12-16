package com.bomwebportal.lts.service;

import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsRemarkService {

	void generateCustomerRemark(SbOrderDTO sbOrder, String pUser);
	void generateOrderRemark(SbOrderDTO sbOrder, String pUser);
	
}
