package com.bomwebportal.lts.service;

import com.bomwebportal.lts.dto.ItemDetailDTO;

public interface OfferProfileService {

	public abstract ItemDetailDTO getTpVasItemDetail(String pItemId,
			String pLocale);

}