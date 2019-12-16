package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.profile.ItemActionLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferActionLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.service.CodeLkupCacheService;

public class OfferActionServiceImpl implements OfferActionService {

	private CodeLkupCacheService ltsOfferActionCache = null;
	private CodeLkupCacheService imsOfferActionCache = null;
	private CodeLkupCacheService imsViOfferActionCache = null;
	private CodeLkupCacheService ltsEquipOfferActionCache = null;
	private CodeLkupCacheService deviceCodeLkupCacheService = null;
	private CodeLkupCacheService ltsTvOfferActionCache = null;
	private CodeLkupCacheService ltsChannelOfferActionCache = null;
	private CodeLkupCacheService ltsRestrictedOfferActionCache = null;
	private ConditionLtsService conditionLtsService = null;
	

	public void getLtsRestrictedOfferAction(ServiceDetailProfileLtsDTO pService) {
		pService.setRestrictedOfferActions(this.getLtsRestrictedOfferActions(pService.getOfferProfiles()));
	}
	
	public void getLtsOfferActions(ServiceDetailProfileLtsDTO pService) {
		
		String type = (StringUtils.isBlank(pService.getExistEyeType()) ? LtsBackendConstant.SERVICE_TYPE_DEL : pService.getExistEyeType()).toUpperCase();
		ItemDetailProfileLtsDTO[] items = pService.getItemDetails();
				
		for (int i=0; items!=null && i<items.length; ++i) {
			
			if (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_SERVICE, items[i].getItemType())
					|| StringUtils.equals(LtsBackendConstant.ITEM_TYPE_EXPIRED_SERVICE, items[i].getItemType())) {
				LookupItemDTO[] deviceLkup = (LookupItemDTO[])this.deviceCodeLkupCacheService.get(CodeLkupCacheService.WILD_CARDS);
				ItemActionLtsDTO itemAction = null;
				
				for (int j=0; deviceLkup!=null && j<deviceLkup.length; ++j) {
					
					if (StringUtils.equalsIgnoreCase(type, (String)deviceLkup[j].getItemValue())) {
						continue;
					}
					itemAction = new ItemActionLtsDTO();
					itemAction.setAction(LtsBackendConstant.IO_IND_OUT);
					itemAction.setPenaltyHandle(items[i].getTpExpiredMonths() > 0 ? null : LtsBackendConstant.OFFER_HANDLE_AUTO_WAIVE);
					itemAction.setToProd(((String)deviceLkup[j].getItemValue()).toUpperCase());
					items[i].addItemAction(itemAction);
				}
			} else {
				this.getLtsOfferAction(items[i], type);	
				items[i].setItemActions(this.getItemActions(items[i]));
			}
		}
	}
	
	public void getImsOfferActions(FsaServiceDetailOutputDTO pFsa, String pExistEyeType) {
		
		String eyeType = (StringUtils.isBlank(pExistEyeType) ? LtsBackendConstant.SERVICE_TYPE_DEL : pExistEyeType).toUpperCase();
		ItemDetailProfileLtsDTO[] items = pFsa.getItems();
		
		for (int i=0; items!=null && i<items.length; ++i) {
			this.getImsOfferAction(items[i], eyeType, pFsa.getSrvType());	
			items[i].setItemActions(this.getItemActions(items[i]));
		}
	}
	
	public void getLtsEquipmentAction(ServiceDetailProfileLtsDTO pService) {
		
		String fromEyeType = (StringUtils.isBlank(pService.getExistEyeType()) ? LtsBackendConstant.SERVICE_TYPE_DEL : pService.getExistEyeType()).toUpperCase();
		List<OfferActionLtsDTO> equipActionList = new ArrayList<OfferActionLtsDTO>();
		LookupItemDTO[] deviceLkup = (LookupItemDTO[])this.deviceCodeLkupCacheService.get(CodeLkupCacheService.WILD_CARDS);
		
		for (int i=0; deviceLkup!=null && i<deviceLkup.length; ++i) {
			equipActionList.addAll(this.getLtsEquipOfferAction(pService.getOfferProfiles(), fromEyeType, ((String)deviceLkup[i].getItemValue()).toUpperCase()));
		}
		pService.setDeviceOfferActions(equipActionList.toArray(new OfferActionLtsDTO[equipActionList.size()]));
	}
	
	public void getLtsTvAction(ServiceDetailProfileLtsDTO pService) {
		pService.setBundleTvOfferActions(this.getLtsTvOfferAction(pService.getOfferProfiles(), LtsBackendConstant.LTS_PRODUCT_TYPE_EYE_2_A));
	}
	
	public void getLtsChannelAction(ServiceDetailProfileLtsDTO pService) {
		pService.setChannelOfferActions(this.getLtsChannelAction(pService.getOfferProfiles(), LtsBackendConstant.LTS_PRODUCT_TYPE_EYE_2_A));
	}
		
	private ItemActionLtsDTO[] getItemActions(ItemDetailProfileLtsDTO pItem) {
		
		OfferDetailProfileLtsDTO[] offers = pItem.getOffers();
		Map<String,List<OfferActionLtsDTO>> offerMap = new HashMap<String,List<OfferActionLtsDTO>>();
		OfferActionLtsDTO[] offerActions = null;
		List<OfferActionLtsDTO> actionList = null;
		
		for (int i=0; offers!=null && i<offers.length; ++i) {
			offerActions = offers[i].getOfferActions();
			
			for (int j=0; offerActions!=null && j<offerActions.length; ++j) {
				
				try {
					Integer.parseInt(offerActions[j].getAction());
					offerActions[j].setAction(this.getOfferActionByCondition(offers[i], offerActions[j].getAction()));
				} catch (NumberFormatException e) {}
				if (offerMap.containsKey(offerActions[j].getToProd())) {
					actionList = offerMap.get(offerActions[j].getToProd());
				} else {
					actionList = new ArrayList<OfferActionLtsDTO>();
					offerMap.put(offerActions[j].getToProd(), actionList);
				}
				actionList.add(offerActions[j]);
			}
		}
		Iterator<String> it = offerMap.keySet().iterator();
		List<ItemActionLtsDTO> itemActionList = new ArrayList<ItemActionLtsDTO>();
		ItemActionLtsDTO itemAction = null;
		// Fall back due to wrongly charged penalty for eye device penalty item. 
		boolean hasCondition = pItem.getTpExpiredMonths() <= 0 && StringUtils.isNotEmpty(pItem.getConditionEffEndDate());
		
		while (it.hasNext()) {
			itemAction = new ItemActionLtsDTO();
			itemAction.setToProd(it.next());
			actionList = offerMap.get(itemAction.getToProd());
			
			for (int i=0; i<actionList.size(); ++i) {
				if (StringUtils.equals(LtsBackendConstant.OFFER_ACTION_PENALTY, actionList.get(i).getAction())) {
					itemAction.setAction(LtsBackendConstant.IO_IND_OUT);
					itemAction.setPenaltyHandle(hasCondition ? LtsBackendConstant.OFFER_HANDLE_AUTO_GENERATE : null);
				} else if (!StringUtils.equals(LtsBackendConstant.OFFER_HANDLE_AUTO_GENERATE, itemAction.getPenaltyHandle())
						&& StringUtils.equals(LtsBackendConstant.OFFER_ACTION_TERMINATE, actionList.get(i).getAction())) {
					itemAction.setAction(LtsBackendConstant.IO_IND_OUT);
					itemAction.setPenaltyHandle(hasCondition ? LtsBackendConstant.OFFER_HANDLE_AUTO_WAIVE : null);
				} else if (StringUtils.isEmpty(itemAction.getAction())) {
					itemAction.setAction(LtsBackendConstant.IO_IND_SPACE);
				}
			}
			itemActionList.add(itemAction);
		}
		return itemActionList.toArray(new ItemActionLtsDTO[itemActionList.size()]);
	}
	
	private OfferActionLtsDTO[] getLtsRestrictedOfferActions(OfferDetailProfileLtsDTO[] pOfferDtl) {
		
		if (ArrayUtils.isEmpty(pOfferDtl)) {
			return null;
		}
		
		OfferActionLtsDTO offerAction = null;
		StringBuilder keySb = null;
		String[] psefCds = null;
		List<OfferActionLtsDTO> restrictedOfferActionList = new ArrayList<OfferActionLtsDTO>();
		
		for (int i=0; pOfferDtl!=null && i<pOfferDtl.length; ++i) {
			psefCds = pOfferDtl[i].getPsefs();
		
			for (int j=0; psefCds!=null && j<psefCds.length; ++j) {
				keySb = new StringBuilder();
				keySb.append("*");
				keySb.append("|");
				keySb.append("*");
				keySb.append("|");
				keySb.append(psefCds[j]);
				offerAction = (OfferActionLtsDTO)this.ltsRestrictedOfferActionCache.get(keySb.toString());
				if (offerAction != null) {
					restrictedOfferActionList.add(offerAction);
				}
			}
		}
		
		if (restrictedOfferActionList.isEmpty()) {
			return null;
		}
		return restrictedOfferActionList.toArray(new OfferActionLtsDTO[restrictedOfferActionList.size()]);
	}
	
	private void getLtsOfferAction(ItemDetailProfileLtsDTO pItem, String pExistEyeType) {
		
		OfferDetailProfileLtsDTO[] offers = pItem.getOffers();
		
		if (ArrayUtils.isEmpty(offers)) {
			return;
		}
		LookupItemDTO[] deviceLkup = (LookupItemDTO[])this.deviceCodeLkupCacheService.get(CodeLkupCacheService.WILD_CARDS);
		OfferActionLtsDTO offerAction = null;
		StringBuilder keySb = null;
		
		boolean isDeviceExist = false;
		if (ArrayUtils.isNotEmpty(deviceLkup)) {
			for (LookupItemDTO device : deviceLkup) {
				if (StringUtils.equalsIgnoreCase(pExistEyeType, (String)device.getItemValue())) {
					isDeviceExist = true;
					break;
				}
			}
			
			if (!isDeviceExist) {
				LookupItemDTO existDevice = new LookupItemDTO();
				existDevice.setItemValue(pExistEyeType);
				deviceLkup = (LookupItemDTO[])ArrayUtils.add(deviceLkup, existDevice);
			}
			
		}
		for (int i=0; deviceLkup!=null && i<deviceLkup.length; ++i) {
			
//			if (StringUtils.equalsIgnoreCase(pExistEyeType, (String)deviceLkup[i].getItemValue())) {
//				continue;
//			}
			for (int j=0; j<offers.length; ++j) {
				String[] psefCds = offers[j].getPsefs();
				
				for (int k=0; psefCds!=null && k<psefCds.length; ++k) {
					keySb = new StringBuilder();
					keySb.append(pExistEyeType);
					keySb.append("|");
					keySb.append(((String)deviceLkup[i].getItemValue()).toUpperCase());
					keySb.append("|");
					keySb.append(psefCds[k]);
					offerAction = (OfferActionLtsDTO)this.ltsOfferActionCache.get(keySb.toString());
					
					if (offerAction != null) {
						offers[j].addOfferAction(offerAction);
						if (StringUtils.equals(LtsBackendConstant.LTS_OFFER_TYPE_VOICE, offerAction.getType())) {
							pItem.setVoiceItem(true);
						}
					} else {
						offers[j].addOfferAction(this.createDefaultAction(((String)deviceLkup[i].getItemValue()).toUpperCase()));
					}
				}
			}
		}
	}
	
	private void getImsOfferAction(ItemDetailProfileLtsDTO pItem, String pExistEyeType, String pExistFsaType) {
		
		OfferDetailProfileLtsDTO[] offers = pItem.getOffers();
		
		if (ArrayUtils.isEmpty(offers)) {
			return;
		}
		LookupItemDTO[] deviceLkup = (LookupItemDTO[])this.deviceCodeLkupCacheService.get(CodeLkupCacheService.WILD_CARDS);
		OfferActionLtsDTO offerAction = null;
		StringBuilder keySb = null;
		String action = null;
		
		for (int i=0; deviceLkup!=null && i<deviceLkup.length; ++i) {
			if (StringUtils.equalsIgnoreCase(pExistEyeType, (String)deviceLkup[i].getItemValue())) {
				continue;
			}
			keySb = new StringBuilder();
			
			for (int j=0; j<offers.length; ++j) {
				keySb.append(pExistEyeType);
				keySb.append("|");
				keySb.append(((String)deviceLkup[i].getItemValue()).toUpperCase());
				keySb.append("|");
				keySb.append(LtsBackendConstant.ITEM_TYPE_EXPIRED_MIRROR_PLAN.equals(pItem.getItemType()) ? offers[j].getOfferSubCd() + "X" : offers[j].getOfferSubCd());
				offerAction = (OfferActionLtsDTO)this.imsOfferActionCache.get(keySb.toString());
				
				if (offerAction == null) {
					offerAction = this.createDefaultAction(((String)deviceLkup[i].getItemValue()).toUpperCase());
				}
				offers[j].addOfferAction(offerAction);
			}
			for (int j=0; j<offers.length; ++j) {
				keySb = new StringBuilder();
				keySb.append(pExistEyeType);
				keySb.append("|");
				keySb.append(((String)deviceLkup[i].getItemValue()).toUpperCase());
				keySb.append("|");
				keySb.append(offers[j].getOfferSubCd());
				
				offerAction = (OfferActionLtsDTO)this.imsViOfferActionCache.get(keySb.toString());
				
				if (offerAction != null  && (action = this.getImsViOfferActionByCondition(offers[j], pExistFsaType, offerAction.getAction())) != null) {
					offerAction.setAction(action);
					offers[j].addOfferAction(offerAction);
				}
			}
		}
	}
	
	private List<OfferActionLtsDTO> getLtsEquipOfferAction(OfferDetailProfileLtsDTO[] pOfferDtl, String pExistEyeType, String pToEyeType) {
		
		OfferActionLtsDTO offerAction = null;
		StringBuilder keySb = null;
		String[] psefCds = null;
		List<OfferActionLtsDTO> equipActionList = new ArrayList<OfferActionLtsDTO>();
		
		for (int i=0; pOfferDtl!=null && i<pOfferDtl.length; ++i) {
			psefCds = pOfferDtl[i].getPsefs();
		
			for (int j=0; psefCds!=null && j<psefCds.length; ++j) {
				keySb = new StringBuilder();
				keySb.append(pExistEyeType);
				keySb.append("|");
				keySb.append(pToEyeType);
				keySb.append("|");
				keySb.append(psefCds[j]);
				offerAction = (OfferActionLtsDTO)this.ltsEquipOfferActionCache.get(keySb.toString());
			
				if (offerAction != null) {
					if (StringUtils.equals(LtsBackendConstant.OFFER_ACTION_TERMINATE, offerAction.getAction()) 
							|| StringUtils.equals(LtsBackendConstant.OFFER_ACTION_PENALTY, offerAction.getAction()) ) {
						offerAction.setAction(LtsBackendConstant.IO_IND_OUT);
					} else if (StringUtils.equals(LtsBackendConstant.OFFER_ACTION_CARRY_FORWARD, offerAction.getAction())) {
						offerAction.setAction(LtsBackendConstant.IO_IND_SPACE);
					}
					equipActionList.add(offerAction);
				}
			}
		}
		return equipActionList;
	}
	
	private OfferActionLtsDTO[] getLtsTvOfferAction(OfferDetailProfileLtsDTO[] pOfferDtls, String pToProd) {
		
		OfferActionLtsDTO offerAction = null;
		StringBuilder keySb = null;
		String[] psefCds = null;
		List<OfferActionLtsDTO> tvActionList = new ArrayList<OfferActionLtsDTO>();
		
		for (int i=0; pOfferDtls!=null && i<pOfferDtls.length; ++i) {
			psefCds = pOfferDtls[i].getPsefs();
		
			for (int j=0; psefCds!=null && j<psefCds.length; ++j) {
				keySb = new StringBuilder();
				keySb.append("*");
				keySb.append("|");
				keySb.append(pToProd);
				keySb.append("|");
				keySb.append(psefCds[j]);
				offerAction = (OfferActionLtsDTO)this.ltsTvOfferActionCache.get(keySb.toString());
			
				if (offerAction != null) {
					if (StringUtils.equals(LtsBackendConstant.OFFER_ACTION_TERMINATE, offerAction.getAction()) 
							|| StringUtils.equals(LtsBackendConstant.OFFER_ACTION_PENALTY, offerAction.getAction()) ) {
						offerAction.setAction(LtsBackendConstant.IO_IND_OUT);
					} else if (StringUtils.equals(LtsBackendConstant.OFFER_ACTION_CARRY_FORWARD, offerAction.getAction())) {
						offerAction.setAction(LtsBackendConstant.IO_IND_SPACE);
					}
					tvActionList.add(offerAction);
				}
			}
		}
		return tvActionList.toArray(new OfferActionLtsDTO[tvActionList.size()]);
	}
	
	private OfferActionLtsDTO[] getLtsChannelAction(OfferDetailProfileLtsDTO[] pOfferDtls, String pToProd) {
		
		OfferActionLtsDTO offerAction = null;
		StringBuilder keySb = null;
		String[] psefCds = null;
		List<OfferActionLtsDTO> channelActionList = new ArrayList<OfferActionLtsDTO>();
		
		for (int i=0; pOfferDtls!=null && i<pOfferDtls.length; ++i) {
			psefCds = pOfferDtls[i].getPsefs();
		
			for (int j=0; psefCds!=null && j<psefCds.length; ++j) {
				keySb = new StringBuilder();
				keySb.append("*");
				keySb.append("|");
				keySb.append(pToProd);
				keySb.append("|");
				keySb.append(psefCds[j]);
				offerAction = (OfferActionLtsDTO)this.ltsChannelOfferActionCache.get(keySb.toString());
				
				if (offerAction == null || (StringUtils.equals(LtsBackendConstant.CHANNEL_ACTION_TP, offerAction.getAction())
						&& pOfferDtls[i].getExpiredMonths() > 0)) {
					continue;
				} 
				channelActionList.add(offerAction);
			}
		}
		return channelActionList.toArray(new OfferActionLtsDTO[channelActionList.size()]);
	}
	
	
	private OfferActionLtsDTO createDefaultAction(String pToProd) {
		
		OfferActionLtsDTO offerAction = new OfferActionLtsDTO();
		offerAction.setAction(LtsBackendConstant.OFFER_ACTION_CARRY_FORWARD);
		offerAction.setToProd(pToProd);
		offerAction.setType("DUMMY");
		return offerAction;
	}
	
	private String getOfferActionByCondition(OfferDetailProfileLtsDTO pOffer, String pConditionKey) {
		return this.conditionLtsService.getConditionResult(pConditionKey, String.valueOf(pOffer.getExpiredMonths() * -1));
	}
	
	private String getImsViOfferActionByCondition(OfferDetailProfileLtsDTO pOffer, String pExistFsaType, String pConditionKey) {
		return this.conditionLtsService.getConditionResult(pConditionKey, String.valueOf(pOffer.getExpiredMonths() * -1), pExistFsaType);
	}

	public CodeLkupCacheService getLtsOfferActionCache() {
		return ltsOfferActionCache;
	}

	public void setLtsOfferActionCache(CodeLkupCacheService ltsOfferActionCache) {
		this.ltsOfferActionCache = ltsOfferActionCache;
	}

	public CodeLkupCacheService getImsOfferActionCache() {
		return imsOfferActionCache;
	}

	public void setImsOfferActionCache(CodeLkupCacheService imsOfferActionCache) {
		this.imsOfferActionCache = imsOfferActionCache;
	}

	public CodeLkupCacheService getDeviceCodeLkupCacheService() {
		return deviceCodeLkupCacheService;
	}

	public void setDeviceCodeLkupCacheService(
			CodeLkupCacheService deviceCodeLkupCacheService) {
		this.deviceCodeLkupCacheService = deviceCodeLkupCacheService;
	}

	public CodeLkupCacheService getLtsEquipOfferActionCache() {
		return ltsEquipOfferActionCache;
	}

	public void setLtsEquipOfferActionCache(
			CodeLkupCacheService ltsEquipOfferActionCache) {
		this.ltsEquipOfferActionCache = ltsEquipOfferActionCache;
	}

	public CodeLkupCacheService getLtsTvOfferActionCache() {
		return ltsTvOfferActionCache;
	}

	public void setLtsTvOfferActionCache(CodeLkupCacheService ltsTvOfferActionCache) {
		this.ltsTvOfferActionCache = ltsTvOfferActionCache;
	}

	public CodeLkupCacheService getLtsChannelOfferActionCache() {
		return ltsChannelOfferActionCache;
	}

	public void setLtsChannelOfferActionCache(
			CodeLkupCacheService ltsChannelOfferActionCache) {
		this.ltsChannelOfferActionCache = ltsChannelOfferActionCache;
	}

	public ConditionLtsService getConditionLtsService() {
		return conditionLtsService;
	}

	public void setConditionLtsService(ConditionLtsService conditionLtsService) {
		this.conditionLtsService = conditionLtsService;
	}

	public CodeLkupCacheService getImsViOfferActionCache() {
		return imsViOfferActionCache;
	}

	public void setImsViOfferActionCache(CodeLkupCacheService imsViOfferActionCache) {
		this.imsViOfferActionCache = imsViOfferActionCache;
	}

	public CodeLkupCacheService getLtsRestrictedOfferActionCache() {
		return ltsRestrictedOfferActionCache;
	}

	public void setLtsRestrictedOfferActionCache(
			CodeLkupCacheService ltsRestrictedOfferActionCache) {
		this.ltsRestrictedOfferActionCache = ltsRestrictedOfferActionCache;
	}
	
}
