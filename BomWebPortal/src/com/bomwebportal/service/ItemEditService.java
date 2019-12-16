package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.ItemEditDTO;
import com.bomwebportal.dto.ItemOfferDTO;

public interface ItemEditService {
	
	public ItemEditDTO getItem(int itemId );
	public int insertItemAll(ItemEditDTO dto);
	public void updateItemAll(ItemEditDTO dto);
	public void deleteItemAll(ItemEditDTO dto);
	public void insertItemOffer(ItemOfferDTO dto) ;
	public List<ItemOfferDTO> getItemOffer(int itemId);
	public void deleteItemOffer(ItemEditDTO dto);
	
	public List<ItemOfferDTO> stringToListItemOfferDTO(int itemId,
			String inputString);
	

}
