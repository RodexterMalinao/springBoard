package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.ItemDisplayDTO;

public interface ItemDisplayService {
	public ItemDisplayDTO getItemDisplay(int itemId, String locale,  String displayType );
	public int insertItemDisplay(ItemDisplayDTO dto);
	public int updateItemDisplay(ItemDisplayDTO dto);
	public int deleteItemDisplay(ItemDisplayDTO dto);
	public List<ItemDisplayDTO> getItemDisplayAll(int itemId, String locale, String displayType);
	public String getTravelInsuranceDays(List<String> selectedVasItemList);
	public String getTravelInsuranceDays(String[] selectedVasItemList);
	public String getTravelInsuranceDays(String travelInsuranceItemId);
}
