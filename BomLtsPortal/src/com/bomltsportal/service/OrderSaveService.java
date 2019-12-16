package com.bomltsportal.service;

import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface OrderSaveService {
	void saveSbOrder(SbOrderDTO sbOrder);
	SbOrderDTO saveNewOrder(SbOrderDTO sbOrder);
	SbOrderDTO saveExistingOrder(SbOrderDTO existingOrder, SbOrderDTO newOrder);
}
