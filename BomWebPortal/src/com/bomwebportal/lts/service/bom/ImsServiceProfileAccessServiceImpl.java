package com.bomwebportal.lts.service.bom;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.service.order.ImsOfferLookupService;
import com.bomwebportal.lts.service.order.OfferActionService;
import com.bomwebportal.lts.service.order.OfferItemService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.LoggingService;

public class ImsServiceProfileAccessServiceImpl implements ImsServiceProfileAccessService {

    private ImsProfileService imsProfileService = null;
	private LoggingService loggingService = null;
	private OfferProfileLtsService offerProfileLtsService = null;
	private OfferItemService offerItemService = null;
	private OfferActionService offerActionService = null;
	private ImsOfferLookupService imsOfferLookupService = null;

	public String checkProductParmByFsa(String fsa, String parmName) {
		return imsProfileService.checkProductParmByFsa(fsa, parmName);
	}
	
	
	public FsaServiceDetailOutputDTO[] getServiceByCustomer(String pCustNum, String pUser) {
		
		FsaServiceDetailOutputDTO[] fsas = this.imsProfileService.getServiceByCustomer(pCustNum);
		this.loggingService.logSearchByCustomerNum(pUser, pCustNum);
		return fsas;
	}

	public FsaServiceDetailOutputDTO getServiceDetailByFSA(String pFsa, String pUser) {
		
		FsaServiceDetailOutputDTO fsa = this.imsProfileService.getServiceDetailByFSA(pFsa);
		this.loggingService.logSearchByService(pUser, LtsConstant.SERVICE_TYPE_IMS, pFsa);
		return fsa;
	}

	public FsaServiceDetailOutputDTO[] getServiceByDocument(String pDocType, String pDocNum, String pUser) {
		
		FsaServiceDetailOutputDTO[] fsas = this.imsProfileService.getServiceByDocument(pDocType, pDocNum);
		this.loggingService.logSearchByDoc(pUser, pDocType, pDocNum);
		return fsas;
	}

	public FsaServiceDetailOutputDTO[] getServiceByLogin(String pLogin, String pDomainType, String pUser) {
		
		FsaServiceDetailOutputDTO[] fsas = this.imsProfileService.getServiceByLogin(pLogin, pDomainType);
		this.loggingService.logSearchByLoginId(pUser, pLogin, pDomainType);
		return fsas;
	}
	
	public void getFsaOfferProfile(FsaServiceDetailOutputDTO pFsa, String pExistEyeType) {
		
		if (StringUtils.isBlank(pFsa.getFsa())) {
			return;
		}		
		pFsa.setOffers(this.offerProfileLtsService.getImsOfferProfile(pFsa.getFsa(), null));
		ItemDetailProfileLtsDTO[] items = this.filterOutUnrelatedImsItems(pFsa, this.offerItemService.getImsOfferItemMapping(pFsa.getOffers()));
		
		pFsa.setItems(items);
		
		if (StringUtils.isBlank(pExistEyeType)) {
			return;
		}
		this.offerActionService.getImsOfferActions(pFsa, pExistEyeType);
		
		for (int i=0; items!=null && i<items.length; ++i) {
			if (StringUtils.equals(LtsConstant.OFFER_TYPE_MIRROR_PLAN, items[i].getItemType())
					|| StringUtils.equals(LtsConstant.OFFER_TYPE_EXPIRED_MIRROR_PLAN, items[i].getItemType())) {
				pFsa.setExistMirrorInd("Y");
			}
		}
	}
	
	/*
	 * 	If there is special mode offer (e.g. VCPP000192) in shared FSA, system will ignore all mirror plans (e.g. VCPP000118) and that mirror plan will not be OUT.
	 *	In additional,
	 *	If there is 1L2B mirror offer in shared FSA, system will ignore 2L2B or nLnB mirror plans.
	 */
	private ItemDetailProfileLtsDTO[] filterOutUnrelatedMirrorPlan(ItemDetailProfileLtsDTO[] pItems) {
		
		List<String> vasNLNBList = imsOfferLookupService.getOfferIdListByProductParam(LtsConstant.PRODUCT_PARAM_NLNB_VAS, LtsConstant.PRODUCT_PARAM_VALUE_NLNB_VAS);
		List<String> vas2L2BList = imsOfferLookupService.getOfferIdListByProductParam(LtsConstant.PRODUCT_PARAM_2L2B_VAS, LtsConstant.PRODUCT_PARAM_VALUE_2L2B_VAS);
		List<String> vas1L2BSpecialList = imsOfferLookupService.getOfferIdListByProductParam(LtsConstant.PRODUCT_PARAM_1L2B_VAS_SPECIAL, LtsConstant.PRODUCT_PARAM_VALUE_1L2B_VAS_SPECIAL);
		List<String> vas1L2BMirrorList = imsOfferLookupService.getOfferIdListByProductParam(LtsConstant.PRODUCT_PARAM_1L2B_VAS_MIRROR, LtsConstant.PRODUCT_PARAM_VALUE_1L2B_VAS_MIRROR);
		
		boolean is1L2BSpeicalExist = false;
		boolean is1L2BMirrorExist = false;
		
		for (ItemDetailProfileLtsDTO profileItem : pItems) {
			if (ArrayUtils.isNotEmpty(profileItem.getOffers())) {
				for (OfferDetailProfileLtsDTO offerDetailProfile : profileItem.getOffers()) {
					if (vas1L2BMirrorList.contains(offerDetailProfile.getOfferId())) {
						is1L2BMirrorExist = true;
					}
					if (vas1L2BSpecialList.contains(offerDetailProfile.getOfferId())) {
						is1L2BSpeicalExist = true;
					}
				}
			}
		}
		
		if (!is1L2BSpeicalExist && !is1L2BMirrorExist) {
			return pItems;
		}
		
		List<ItemDetailProfileLtsDTO> profileItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		
		for (ItemDetailProfileLtsDTO profileItem : pItems) {
			
			boolean ignoreItem = false;
			if (is1L2BSpeicalExist && LtsConstant.ITEM_TYPE_MIRROR_PLAN.equals(profileItem.getItemType())) {
				ignoreItem = true;
			}
			else if (ArrayUtils.isNotEmpty(profileItem.getOffers())) {
				for (OfferDetailProfileLtsDTO offerDetailProfile : profileItem.getOffers()) {
					if (is1L2BMirrorExist) {
						if (vasNLNBList.contains(offerDetailProfile.getOfferId())
								|| vas2L2BList.contains(offerDetailProfile.getOfferId())) {
							ignoreItem = true;
						}
					}
				}
			}
			if (!ignoreItem) {
				profileItemList.add(profileItem);	
			}
		}
		
		return profileItemList.toArray(new ItemDetailProfileLtsDTO[profileItemList.size()]);
	}
	
	private ItemDetailProfileLtsDTO[] filterOutUnrelatedImsItems(FsaServiceDetailOutputDTO pFsa, ItemDetailProfileLtsDTO[] pItems) {
		
		if (ArrayUtils.isEmpty(pItems)) {
			return null;
		}
		
		ItemDetailProfileLtsDTO[] filteredMirrorPlanItems = filterOutUnrelatedMirrorPlan(pItems);
		
		if (StringUtils.equals(LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN, pFsa.getSrvType()) 
				|| StringUtils.equals(LtsConstant.IMS_PRODUCT_TYPE_PCD, pFsa.getSrvType())) {
			return filteredMirrorPlanItems;
		}
		
		List<ItemDetailProfileLtsDTO> itemList = new ArrayList<ItemDetailProfileLtsDTO>();
		
		for (int i=0; filteredMirrorPlanItems!=null && i<filteredMirrorPlanItems.length; ++i) {
			if (!StringUtils.equals(LtsConstant.ITEM_TYPE_VI, filteredMirrorPlanItems[i].getItemType())) {
				itemList.add(filteredMirrorPlanItems[i]);
			}
		}
		return itemList.toArray(new ItemDetailProfileLtsDTO[itemList.size()]);
	}
	
	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}

	public LoggingService getLoggingService() {
		return loggingService;
	}

	public void setLoggingService(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	public OfferProfileLtsService getOfferProfileLtsService() {
		return offerProfileLtsService;
	}

	public void setOfferProfileLtsService(
			OfferProfileLtsService offerProfileLtsService) {
		this.offerProfileLtsService = offerProfileLtsService;
	}

	public OfferItemService getOfferItemService() {
		return offerItemService;
	}

	public void setOfferItemService(OfferItemService offerItemService) {
		this.offerItemService = offerItemService;
	}

	public OfferActionService getOfferActionService() {
		return offerActionService;
	}

	public void setOfferActionService(OfferActionService offerActionService) {
		this.offerActionService = offerActionService;
	}

	public ImsOfferLookupService getImsOfferLookupService() {
		return imsOfferLookupService;
	}

	public void setImsOfferLookupService(ImsOfferLookupService imsOfferLookupService) {
		this.imsOfferLookupService = imsOfferLookupService;
	}
	
}
