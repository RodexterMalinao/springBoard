package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.ItemsRelationshipDTO;

public interface ItemsRelationshipService {
	
	List<ItemsRelationshipDTO> getItemsRelations(String[] items, String channelId, Date appDate);
	
}
