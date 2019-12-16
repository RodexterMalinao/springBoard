package com.bomwebportal.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.ItemDisplayDAO;
import com.bomwebportal.dto.ItemDisplayDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.web.util.GenericReportHelper;

@Transactional(readOnly=true)
public class ItemDisplayServiceImpl implements ItemDisplayService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ItemDisplayDAO itemDisplayDao;
	private VasDetailService vasDetailService;

	public void setItemDisplayDao(ItemDisplayDAO itemDisplayDao) {
		this.itemDisplayDao = itemDisplayDao;
	}

	public ItemDisplayDAO getItemDisplayDao() {
		return itemDisplayDao;
	}
	
	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public ItemDisplayDTO getItemDisplay(int itemId,   String locale,String displayType){
		
		try{
			logger.info("getItemDisplay() is called in ItemDisplayServiceImpl");
			return itemDisplayDao.getItemDisplay(itemId,locale, displayType); 
		}catch (DAOException de) {
			logger.error("Exception caught in getItemDisplay()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<ItemDisplayDTO> getItemDisplayAll(int itemId, String locale,
			String displayType){
		try{
			logger.info("getItemDisplay() is called in ItemDisplayServiceImpl");
			return itemDisplayDao.getItemDisplayAll(itemId,locale, displayType); 
		}catch (DAOException de) {
			logger.error("Exception caught in getItemDisplay()", de);
			throw new AppRuntimeException(de);
		}
	
	}
	
	
	//@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertItemDisplay(ItemDisplayDTO dto) {

		try {
			
			logger.info("insertItemDisplay() is called in ItemDisplayServiceImpl");
			return itemDisplayDao.insertItemDisplay(dto);
			
		
		} catch (DAOException de) {
			logger.error("Exception caught in insertItemDisplay()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	
	
	//@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateItemDisplay(ItemDisplayDTO dto){

		try {
			logger.info("insertItemDisplay() is called in ItemDisplayServiceImpl");
			return itemDisplayDao.updateItemDisplay(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in updateItemDisplay()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int deleteItemDisplay(ItemDisplayDTO dto){

		try {
			logger.info("deleteItemDisplay() is called in ItemDisplayServiceImpl");
			return itemDisplayDao.deleteItemDisplay(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in deleteItemDisplay()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getTravelInsuranceDays(List<String> selectedVasItemList) {
		if (CollectionUtils.isNotEmpty(selectedVasItemList)) {
			String travelInsuranceItemId = vasDetailService.getItemIdExistInSelectionGrpList(GenericReportHelper.TRAVEL_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID, selectedVasItemList);
			int travelInsuranceItemIdInInt = (StringUtils.isNumeric(travelInsuranceItemId) && StringUtils.isNotBlank(travelInsuranceItemId))? Integer.parseInt(travelInsuranceItemId) : 0;
			ItemDisplayDTO itemDisplayDTO = getItemDisplay(travelInsuranceItemIdInInt, "en", "TRAVEL_INS_DAYS");
			if (itemDisplayDTO != null && StringUtils.isNotBlank(itemDisplayDTO.getHtml())) {
				return itemDisplayDTO.getHtml();
			} else {
				return "";
			}
		}
		return "";
	}
	
	public String getTravelInsuranceDays(String[] selectedVasItemList) {
		if (selectedVasItemList != null) {
			return getTravelInsuranceDays(Arrays.asList(selectedVasItemList));
		} else {
			return "";
		}
	}
	
	public String getTravelInsuranceDays(String selectedVasItem) {
		if (StringUtils.isNotBlank(selectedVasItem)) {
			return getTravelInsuranceDays(Arrays.asList(selectedVasItem));
		} else {
			return "";
		}
	}
	
}
	
	