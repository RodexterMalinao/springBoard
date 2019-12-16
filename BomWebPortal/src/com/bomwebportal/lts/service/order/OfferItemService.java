package com.bomwebportal.lts.service.order;

import java.util.Map;

import com.bomwebportal.lts.dto.order.OfferItemRelationDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;

public interface OfferItemService {

	public abstract ItemDetailProfileLtsDTO[] getOfferItemMapping(OfferDetailProfileLtsDTO[] pOfferDtls);
	
	public abstract Map<String, OfferItemRelationDTO> getNoTpItems();

	public abstract String[] generateFreeVasItem(ItemDetailProfileLtsDTO[] pItems, String pDeviceType, String pContractPeriod, String pChannelId, String pOrderType);
	
	public abstract String[] generateFreeVasItem(ItemDetailProfileLtsDTO[] pItems, String pDeviceType, String pContractPeriod, String pChannelId, String pOrderType, boolean isSubmitDisForm);
	
	public abstract String getEyeDeviceType(ServiceDetailLtsDTO pSrvDtlLts);
	
	public abstract boolean isExpiredItem(ItemDetailProfileLtsDTO pItemProfile);
	
	public abstract ItemDetailProfileLtsDTO[] getImsOfferItemMapping(OfferDetailProfileLtsDTO[] pOfferDtls);
	
	public abstract String[] getOffersByItemId(String pItemId);
	
	public abstract void updateItemActionByPurchases(ItemDetailProfileLtsDTO[] pItemProfiles, String[] pInItemIds, String pToProd);
	
	public boolean isFreeVasExist(ItemDetailProfileLtsDTO[] pItems);
	
	public boolean isPaidVasExist(ItemDetailProfileLtsDTO[] pItems);
	
}