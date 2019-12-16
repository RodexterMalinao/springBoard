package com.bomltsportal.service;

import java.util.List;

import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;

public interface ItemDetailService {

	List<ItemDetailDTO> getBasketItemList(String channelId, String basketId,
			String itemType, String displayType, String locale,
			boolean selectDefault, boolean includeItemAttb);

	List<ItemDetailDTO> getItemList(String channelId, String itemType,
			String displayType, String locale, boolean selectDefault,
			boolean includeItemAttb);

	List<ItemDetailDTO> getItems(String[] itemIds, String displayType,
			String locale, boolean selectDefault, boolean includeItemAttb);

	ItemAttbDTO[] getItemAttb(String pItemId);

	List<ItemDetailDTO> getDummyBasketItemList(String channelId,
			String itemType, String displayType, String locale,
			boolean selectDefault, boolean includeItemAttb);
	
	
	List<ItemSetDetailDTO> getBasketItemSetList(ItemSetCriteriaDTO itemSetCriteria);
	
	
	ValidationResultDTO validateExclusiveItem(
			List<ItemDetailDTO> itemDetailListA,
			List<ItemDetailDTO> itemDetailListB, String locale);

	List<ExclusiveItemDetailDTO> getExclusiveItemDetailList(
			List<ItemDetailDTO> itemDetailListA,
			List<ItemDetailDTO> itemDetailListB, String locale,
			boolean isSelectedItemOnly) ;
	
	boolean isItemSelected(List<ItemDetailDTO> itemDetailList, String itemType);
	
	String getItemEligibleCardPrefix(String itemId);
	
}
