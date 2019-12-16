package com.bomwebportal.lts.service.order;

import java.util.Collection;
import java.util.List;

import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;

public interface ItemOfferMappingService {

	public abstract Collection<ItemDetailProfileLtsDTO> getServiceItems(
			List<OfferDetailProfileLtsDTO> pOfferList);

}