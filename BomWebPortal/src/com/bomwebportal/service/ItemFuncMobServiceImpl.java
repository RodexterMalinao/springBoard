package com.bomwebportal.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.ItemFuncMobDAO;
import com.bomwebportal.dto.ItemFuncAssgnMobDTO;
import com.bomwebportal.exception.AppRuntimeException;

@Transactional(readOnly=true)
public class ItemFuncMobServiceImpl implements ItemFuncMobService {

	private ItemFuncMobDAO itemFuncMobDAO;
	
	public ItemFuncMobDAO getItemFuncMobDAO() {
		return itemFuncMobDAO;
	}

	public void setItemFuncMobDAO(ItemFuncMobDAO itemFuncMobDAO) {
		this.itemFuncMobDAO = itemFuncMobDAO;
	}

	public ItemFuncAssgnMobDTO getItemFuncAssgnMobDTO(String itemId,
			String funcId) {
		try {
			return itemFuncMobDAO.getItemFuncAssgnMobDTO(itemId, funcId);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}

	public List<ItemFuncAssgnMobDTO> findItemFuncAssgnMobDTO(String itemId, String appDate) {
		try {
			return itemFuncMobDAO.findItemFuncAssgnMobDTO(itemId, appDate);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
	
	

}
