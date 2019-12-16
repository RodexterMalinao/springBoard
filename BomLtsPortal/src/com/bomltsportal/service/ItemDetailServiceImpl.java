package com.bomltsportal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.OfferDetailDAO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemAttbInfoDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;

public class ItemDetailServiceImpl implements ItemDetailService {

	
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	protected OfferDetailDAO offerDetailDao;


	public OfferDetailDAO getOfferDetailDao() {
		return offerDetailDao;
	}

	public void setOfferDetailDao(OfferDetailDAO offerDetailDao) {
		this.offerDetailDao = offerDetailDao;
	}
	
	public List<ItemDetailDTO> getBasketItemList(String channelId,
			String basketId, String itemType, String displayType,
			String locale, boolean selectDefault, boolean includeItemAttb) {
		try {

			List<ItemDetailDTO> itemDetailList = this.offerDetailDao
					.getBasketItemList(basketId, itemType, locale,
							selectDefault, displayType, channelId, null);
			
			if (itemDetailList == null) {
				return null;
			}
			
			for (ItemDetailDTO itemDetail : itemDetailList) {
				if (includeItemAttb) {
					itemDetail.setItemAttbs(this.getItemAttb(itemDetail
							.getItemId()));
				}
				
				if (StringUtils.equals(itemDetail.getItemType(), BomLtsConstant.ITEM_TYPE_NOWTV_PAY)
						|| StringUtils.equals(itemDetail.getItemType(), BomLtsConstant.ITEM_TYPE_NOWTV_SPEC)) {
					itemDetail.setCredit(this.offerDetailDao.getNowtvCredit(itemDetail.getItemId()));
				}
			}
			

			return itemDetailList;
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getItemList(String channelId, String itemType,
			String displayType, String locale, boolean selectDefault,
			boolean includeItemAttb) {
		try {

			return this.offerDetailDao.getItemList(itemType, locale, selectDefault, includeItemAttb, null, channelId, displayType, null);

		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getItems(String[] itemIds, String displayType,
			String locale, boolean selectDefault, boolean includeItemAttb) {
		try {

			List<ItemDetailDTO> itemDetailList = offerDetailDao.getItem(
					itemIds, displayType, locale, selectDefault);

			if (itemDetailList == null) {
				return null;
			}
			if (includeItemAttb) {
				for (ItemDetailDTO itemDetail : itemDetailList) {
					itemDetail.setItemAttbs(this.getItemAttb(itemDetail.getItemId()));
				}
			}
			
			return itemDetailList;

		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public ItemAttbDTO[] getItemAttb(String pItemId) {
		List<ItemAttbDTO> attbList = new ArrayList<ItemAttbDTO>();
		try {
			attbList = this.offerDetailDao.getItemAttb(pItemId);

			if (attbList != null && !attbList.isEmpty()) {
				for (ItemAttbDTO itemAttb : attbList) {
					if (StringUtils.isEmpty(itemAttb.getAttbInfoKey())) {
						continue;
					}
					List<ItemAttbInfoDTO> itemAttbInfoList = this.offerDetailDao
							.getItemAttbInfoList(itemAttb.getAttbInfoKey());
					itemAttb.setItemAttbInfoList(itemAttbInfoList);
				}
			}

			return attbList.toArray(new ItemAttbDTO[0]);
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getDummyBasketItemList(String channelId,
			String itemType, String displayType, String locale,
			boolean selectDefault, boolean includeItemAttb) {

		try {
			List<ItemDetailDTO> itemDetailList = this.offerDetailDao
					.getDummyBasketItemList(channelId, itemType, displayType,
							locale, selectDefault, null);

			if (itemDetailList == null) {
				return null;
			}
			
			if (includeItemAttb) {
				for (ItemDetailDTO itemDetail : itemDetailList) {
					itemDetail.setItemAttbs(this.getItemAttb(itemDetail
							.getItemId()));
				}
			}

			return itemDetailList;
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemSetDetailDTO> getBasketItemSetList(ItemSetCriteriaDTO itemSetCriteria)  {

		try {
			return offerDetailDao.getBasketItemSetList(itemSetCriteria);
			
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ExclusiveItemDetailDTO> getExclusiveItemDetailList(
			List<ItemDetailDTO> itemDetailListA,
			List<ItemDetailDTO> itemDetailListB, String locale,
			boolean isSelectedItemOnly) {
		try {
			return this.offerDetailDao.getExclusiveItemList(itemDetailListA, itemDetailListB, locale, isSelectedItemOnly);
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public ValidationResultDTO validateExclusiveItem(
			List<ItemDetailDTO> itemDetailListA,
			List<ItemDetailDTO> itemDetailListB, String locale) {

		List<ExclusiveItemDetailDTO> exclusiveItemDetailList = getExclusiveItemDetailList(
				itemDetailListA, itemDetailListB, locale, true);

		if (exclusiveItemDetailList == null
				|| exclusiveItemDetailList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}

		List<String> errorMsgList = new ArrayList<String>();

		for (ExclusiveItemDetailDTO exclusiveItemDetail : exclusiveItemDetailList) {

			if (StringUtils.isNotBlank(exclusiveItemDetail.getItemADesc())
					&& StringUtils.isNotBlank(exclusiveItemDetail
							.getItemBDesc())) {
				errorMsgList.add("Cannot select "
						+ exclusiveItemDetail.getItemADesc().split("<br />")[0]
						+ " and "
						+ exclusiveItemDetail.getItemBDesc().split("<br />")[0]
						+ " together.");
			}

		}

		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}

		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	public boolean isItemSelected(List<ItemDetailDTO> itemDetailList, String itemType) {
		if (itemDetailList == null || itemDetailList.isEmpty() ) {
			return false;
		}
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (StringUtils.isNotEmpty(itemType)
					&& !StringUtils.equals(itemType, itemDetail.getItemType())) {
				continue;
			}
			if (itemDetail.isSelected()) {
				return true;
			}
		}
		return false;
	}
	
	public String getItemEligibleCardPrefix(String itemId) {
		try {
			return this.offerDetailDao.getItemEligibleCardPrefix(itemId);
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
}
