package com.bomltsportal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.bomltsportal.dao.OnlineBasketDAO;
import com.bomltsportal.dto.OnlineBasketDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.BasketDetailFormDTO;
import com.bomltsportal.dto.form.BasketSelectFormDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.google.common.collect.Lists;

public class BasketDetailServiceImpl implements BasketDetailService {

	
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	protected OnlineBasketDAO onlineBasketDAO;
	protected ItemDetailService itemDetailService;
	private NowTvChannelService nowTvChannelService;
	
	public OnlineBasketDAO getOnlineBasketDAO() {
		return onlineBasketDAO;
	}

	public void setOnlineBasketDAO(OnlineBasketDAO onlineBasketDAO) {
		this.onlineBasketDAO = onlineBasketDAO;
	}

	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}

	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}

	public NowTvChannelService getNowTvChannelService() {
		return nowTvChannelService;
	}

	public void setNowTvChannelService(NowTvChannelService nowTvChannelService) {
		this.nowTvChannelService = nowTvChannelService;
	}

	public List<BasketDetailDTO> getBasketList(String channelId,
			boolean parallelExtension, String displayType, String locale) {

		try {
			return onlineBasketDAO.getBasketDetailList(channelId,
					parallelExtension, displayType, locale);
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}

	}
	
	public List<BasketDetailDTO> getBasketHousingList(String channelId,
			boolean parallelExtension, String displayType, String locale, String housingType, String pServiceBoundary ) {

		try {
			return onlineBasketDAO.getBasketDetailHousingList(channelId,
					parallelExtension, displayType, locale, housingType, pServiceBoundary);
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}

	}
	
	public List<OnlineBasketDTO> getOnlineBasketList(OrderCaptureDTO orderCapture, 
			boolean parallelExtension, String displayType, String locale) {
		
		
		List<BasketDetailDTO> basketDetailList = getBasketList(orderCapture.getChannelId(), parallelExtension, displayType, locale);
		
		if (basketDetailList == null || basketDetailList.isEmpty()) {
			return null;
		}
		
		List<ItemDetailDTO> imgItemList = null;
		List<ItemSetDetailDTO> contItemSetList = null;
		List<ItemSetDetailDTO> premItemSetList = null;
		List<ItemSetDetailDTO> premDelItemSetList = null;
		
		OnlineBasketDTO onlineBasket = null;
		List<OnlineBasketDTO> onlineBasketList = new ArrayList<OnlineBasketDTO>();

		for (BasketDetailDTO basketDetail : basketDetailList) {


			if (BomLtsConstant.CHANNEL_ID_ONLINE_DEL.equals(orderCapture.getChannelId())) {
				premDelItemSetList = itemDetailService.getBasketItemSetList(getItemSetCriteria(orderCapture, basketDetail.getBasketId(), BomLtsConstant.ITEM_SET_TYPE_PREM_DEL, locale));
				if (premDelItemSetList != null && !premDelItemSetList.isEmpty()) {
					for (ItemSetDetailDTO itemSetDetail : premDelItemSetList) {
						if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
							for (ItemDetailDTO itemDetail : itemSetDetail.getItemDetails()) {
								itemDetail.setSelected(true);
							}
						}
					}
				}
			}
			
			imgItemList = itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					basketDetail.getBasketId(),
					BomLtsConstant.ITEM_TYPE_ITEM_IMG, BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, locale,
					true, false);
			
			contItemSetList = itemDetailService
					.getBasketItemSetList(getItemSetCriteria(orderCapture,
							basketDetail.getBasketId(),
							BomLtsConstant.ITEM_SET_TYPE_CONT_IMG, locale));
			
			premItemSetList = itemDetailService
					.getBasketItemSetList(getItemSetCriteria(orderCapture,
							basketDetail.getBasketId(),
							BomLtsConstant.ITEM_SET_TYPE_PREM_IMG, locale));
			
			onlineBasket = new OnlineBasketDTO();
			onlineBasket.setBasketDetail(basketDetail);
			onlineBasket.setImageItemList(imgItemList);
			onlineBasket.setContentImageSetList(contItemSetList);
			onlineBasket.setPremiumImageSetList(premItemSetList);
			onlineBasket.setPremiumDelItemSetList(premDelItemSetList);
			onlineBasketList.add(onlineBasket);
		}
		
		return onlineBasketList;
		
	}
	
	public List<OnlineBasketDTO> getOnlineBasketHousingList(OrderCaptureDTO orderCapture, 
			boolean parallelExtension, String displayType, String locale, String housingType, String pServiceBoundary) {
		
		
		List<BasketDetailDTO> basketDetailList = getBasketHousingList(orderCapture.getChannelId(), parallelExtension, displayType, locale, housingType, pServiceBoundary);
		
		if (basketDetailList == null || basketDetailList.isEmpty()) {
			return null;
		}
		
		List<ItemDetailDTO> imgItemList = null;
		List<ItemSetDetailDTO> contItemSetList = null;
		List<ItemSetDetailDTO> premItemSetList = null;
		List<ItemSetDetailDTO> premDelItemSetList = null;
		
		OnlineBasketDTO onlineBasket = null;
		List<OnlineBasketDTO> onlineBasketList = new ArrayList<OnlineBasketDTO>();

		for (BasketDetailDTO basketDetail : basketDetailList) {


			if (BomLtsConstant.CHANNEL_ID_ONLINE_DEL.equals(orderCapture.getChannelId())) {
				premDelItemSetList = itemDetailService.getBasketItemSetList(getItemSetCriteria(orderCapture, basketDetail.getBasketId(), BomLtsConstant.ITEM_SET_TYPE_PREM_DEL, locale));
				if (premDelItemSetList != null && !premDelItemSetList.isEmpty()) {
					for (ItemSetDetailDTO itemSetDetail : premDelItemSetList) {
						if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
							for (ItemDetailDTO itemDetail : itemSetDetail.getItemDetails()) {
								itemDetail.setSelected(true);
							}
						}
					}
				}
			}
			
			imgItemList = itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					basketDetail.getBasketId(),
					BomLtsConstant.ITEM_TYPE_ITEM_IMG, BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, locale,
					true, false);
			
			contItemSetList = itemDetailService
					.getBasketItemSetList(getItemSetCriteria(orderCapture,
							basketDetail.getBasketId(),
							BomLtsConstant.ITEM_SET_TYPE_CONT_IMG, locale));
			
			premItemSetList = itemDetailService
					.getBasketItemSetList(getItemSetCriteria(orderCapture,
							basketDetail.getBasketId(),
							BomLtsConstant.ITEM_SET_TYPE_PREM_IMG, locale));
			
			onlineBasket = new OnlineBasketDTO();
			onlineBasket.setBasketDetail(basketDetail);
			onlineBasket.setImageItemList(imgItemList);
			onlineBasket.setContentImageSetList(contItemSetList);
			onlineBasket.setPremiumImageSetList(premItemSetList);
			onlineBasket.setPremiumDelItemSetList(premDelItemSetList);
			onlineBasketList.add(onlineBasket);
		}
		
		return onlineBasketList;
		
	}
	
	
	public BasketDetailDTO getBasket(String basketId, String locale, String displayType)  {
		try {
			return onlineBasketDAO.getBasket(basketId, locale, displayType);
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}

		
	}
	
	private void setBundledNowtvChannel(OrderCaptureDTO orderCapture, BasketDetailFormDTO form, String locale, String basketId) {
		List<ItemSetDetailDTO> contItemSetList = form.getContItemSetList();
		
		if (contItemSetList == null || contItemSetList.isEmpty()) {
			return;
		}
		
		ItemDetailDTO[] itemDetails = null;
		for (ItemSetDetailDTO contItemSet : contItemSetList) {
			itemDetails = contItemSet.getItemDetails();
			
			if (ArrayUtils.isEmpty(itemDetails)) {
				continue;
			}
			
			for (ItemDetailDTO itemDetail : itemDetails) {
				if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_PAY, itemDetail.getItemType())
						|| StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_SPEC, itemDetail.getItemType())) {
					
					String deviceType = getSelectedDeviceType(orderCapture, basketId);
					
					List<ChannelGroupDetailDTO> channelGroupList = nowTvChannelService.getChannelGroupList(itemDetail.getItemType(), deviceType, locale, true, itemDetail.getCredit());
					form.setChannelGroupList(channelGroupList);
					return;
				}
			}
		}
		
	}
	
	public String getSelectedDeviceType(OrderCaptureDTO orderCapture, String basketId) {
		
		BasketSelectFormDTO basketSelectForm = orderCapture.getBasketSelectForm();
		
		if (basketSelectForm == null) {
			return null;
		}
		
		for (OnlineBasketDTO onlineBasket : basketSelectForm.getOnlineBasketList()) {
//			if (onlineBasket.isSelected()) {
//				return onlineBasket.getBasketDetail().getType();
//			} 
			if(StringUtils.equals(onlineBasket.getBasketDetail().getBasketId(), basketId)){
				return onlineBasket.getBasketDetail().getType();
			}
		}
		
		return null;
	}
	
	public void setBasketDetailForm(OrderCaptureDTO orderCapture, BasketDetailFormDTO form, String locale, String basketId){

		form.setDisplayBasketId(basketId);
		if(form.getPlanItemList() == null || form.getPlanItemList().size() == 0){
			List<ItemDetailDTO> planItemList = itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					basketId, BomLtsConstant.ITEM_TYPE_PLAN,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, locale, true, false);
			form.setPlanItemList(planItemList);
		}
		
		if(form.getContItemSetList() == null || form.getContItemSetList().size() == 0){
			List<ItemSetDetailDTO> contItemSetList = itemDetailService
					.getBasketItemSetList(getItemSetCriteria(orderCapture,
							basketId, BomLtsConstant.ITEM_TYPE_CONT_SET, locale));
			form.setContItemSetList(contItemSetList);
		}

		if(form.getPremiumItemSetList() == null || form.getPremiumItemSetList().size() == 0){
			List<ItemSetDetailDTO> premiumItemSetList = itemDetailService
					.getBasketItemSetList(getItemSetCriteria(orderCapture,
							basketId, BomLtsConstant.ITEM_TYPE_PREM_SET, locale));
			form.setPremiumItemSetList(premiumItemSetList);
		}
		
		if(form.getChannelGroupList() == null || form.getChannelGroupList().size() == 0){
			setBundledNowtvChannel(orderCapture, form, locale, basketId);
		}
		
		if(form.getBbRentalItemList() == null || form.getBbRentalItemList().size() == 0){
			List<ItemDetailDTO> bbRentalItemList = new ArrayList<ItemDetailDTO>();
			
			bbRentalItemList.addAll(itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					basketId, BomLtsConstant.ITEM_TYPE_BB_RENTAL,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCapture.getLang(), true, false));
			bbRentalItemList.addAll(itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					basketId, BomLtsConstant.ITEM_TYPE_BB_RENTAL_WAIVE,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCapture.getLang(), true, false));
			
			form.setBbRentalItemList(bbRentalItemList);
		}

		if(form.getInstallFeeItemList() == null || form.getInstallFeeItemList().size() == 0){
			List<ItemDetailDTO> installFeeItemList = new ArrayList<ItemDetailDTO>();
			installFeeItemList.addAll(itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					basketId, BomLtsConstant.ITEM_TYPE_INSTALL,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCapture.getLang(), true, false));
			installFeeItemList.addAll(itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					basketId, BomLtsConstant.ITEM_TYPE_INSTALL_WAIVE,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCapture.getLang(), true, false));
			
			form.setInstallFeeItemList(installFeeItemList);
		}
		
		
	}
	
	private ItemSetCriteriaDTO getItemSetCriteria(
			OrderCaptureDTO orderCapture, String basketId, String itemSetType, String locale) {
		
		ItemSetCriteriaDTO itemSetCriteria = new ItemSetCriteriaDTO();
		itemSetCriteria.setBasketId(basketId);
		itemSetCriteria.setLocale(locale);
		itemSetCriteria.setItemSetType(itemSetType);
		itemSetCriteria.setEligibleItemTypeList(Lists.newArrayList(BomLtsConstant.ITEM_TYPE_PLAN));
		itemSetCriteria.setSelectDefault(true);
		itemSetCriteria.setChannelId(orderCapture.getChannelId());
		itemSetCriteria.setDisplayType(BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC);
		itemSetCriteria.setApplnDate(DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy hh:mm")));
		
		return itemSetCriteria;
	}
	
}
