package com.bomwebportal.lts.service;

import java.util.List;

public interface OfferChangeService {

	public List<String[]> getOfferChangeList(String ltsFromProd,
			String ltsToProd, String imsFromProd, String imsToProd, String mirror, String[] fromOffers, String rentalRouter);
	
}
