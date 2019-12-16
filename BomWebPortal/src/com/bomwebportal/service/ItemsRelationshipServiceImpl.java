package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.dao.ItemsRelationshipDAO;
import com.bomwebportal.dto.ItemsRelationshipDTO;

public class ItemsRelationshipServiceImpl implements ItemsRelationshipService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	ItemsRelationshipDAO itemsRelationshipDAO;
	
	public ItemsRelationshipDAO getItemsRelationshipDAO() {
		return itemsRelationshipDAO;
	}

	public void setItemsRelationshipDAO(ItemsRelationshipDAO itemsRelationshipDAO) {
		this.itemsRelationshipDAO = itemsRelationshipDAO;
	}

	public List<ItemsRelationshipDTO> getItemsRelations(String[] items,
			String channelId, Date appDate) {
		
		try {
			return itemsRelationshipDAO.getItemsRelations(items, channelId, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in getItemsRelations()", e);
		}
		
		return null;
	}

}
