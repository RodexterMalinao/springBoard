package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsClubMembershipService {
	public void earnClubPointsAndUpdateItems(SbOrderDTO order, String userId);
	public boolean earnClubPoints(List<ItemDetailLtsDTO> itemList, String sbId, String custNum, String memberId);
	public boolean earnClubPoints(ItemDetailLtsDTO item, String sbId, String custNum, String memberId);
	public String getTheClubCatalogueUrl(String locale);
}
