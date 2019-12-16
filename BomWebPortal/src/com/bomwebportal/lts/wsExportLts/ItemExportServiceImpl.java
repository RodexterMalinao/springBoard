package com.bomwebportal.lts.wsExportLts;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;

import com.bomwebportal.lts.dto.export.ItemDetailProfileExportLtsDTO;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.order.CallPlanLtsService;
import com.bomwebportal.lts.service.order.OfferItemService;
import com.bomwebportal.lts.util.LtsConstant;

public class ItemExportServiceImpl implements ItemExportService {

	private OfferItemService offerItemService;
	private LtsOfferService ltsOfferService;
	private CallPlanLtsService callPlanLtsService;
	protected Mapper dozerMapper;
	
	
	public ItemDetailProfileExportLtsDTO[] mapOffersToItem(OfferDetailProfileLtsDTO[] pOfferDtls, String[] pIddCallPlanCds) {
		
		List<OfferDetailProfileLtsDTO> offerList = new ArrayList<OfferDetailProfileLtsDTO>();
		for (int i=0; pOfferDtls!=null && i<pOfferDtls.length; ++i) {
			if (pOfferDtls[i].getExpiredMonths() <= 0) {
				offerList.add(pOfferDtls[i]);
			}
		}
		pOfferDtls = offerList.toArray(new OfferDetailProfileLtsDTO[offerList.size()]);
		List<ItemDetailProfileExportLtsDTO> itemDtlList = new ArrayList<ItemDetailProfileExportLtsDTO>();
		
		if (ArrayUtils.isNotEmpty(pOfferDtls)) {
			itemDtlList.addAll(this.getItems(pOfferDtls));	
		}
		if (ArrayUtils.isNotEmpty(pIddCallPlanCds)) {
			itemDtlList.addAll(this.getIddCallPlan(pIddCallPlanCds));
		}
		return itemDtlList.toArray(new ItemDetailProfileExportLtsDTO[itemDtlList.size()]);
	}
	
	private List<ItemDetailProfileExportLtsDTO> getItems(OfferDetailProfileLtsDTO[] pOfferDtls) {
		
		ItemDetailProfileExportLtsDTO itemDtl = null;
		List<ItemDetailProfileExportLtsDTO> itemDtlList = new ArrayList<ItemDetailProfileExportLtsDTO>();
		
		try {
			ItemDetailProfileLtsDTO[] itemDtls = this.offerItemService.getOfferItemMapping(pOfferDtls);
			
			if (ArrayUtils.isEmpty(itemDtls)) {
				return itemDtlList;
			}
			for (ItemDetailProfileLtsDTO itemDetailProfile : itemDtls) {
				if (StringUtils.isEmpty(itemDetailProfile.getItemId()) 
						|| !ArrayUtils.contains(new String[] {LtsConstant.ITEM_TYPE_SERVICE, LtsConstant.ITEM_TYPE_VAS}, itemDetailProfile.getItemType())) {
					continue;
				}
				itemDetailProfile.setItemDetail(this.ltsOfferService.getTpVasItemDetail(itemDetailProfile.getItemId(), LtsConstant.LOCALE_ENG));
				itemDtl = new ItemDetailProfileExportLtsDTO();
				BeanUtils.copyProperties(itemDetailProfile.getItemDetail(), itemDtl);
				this.dozerMapper.map(itemDetailProfile.getItemDetail(), itemDtl);
				BeanUtils.copyProperties(itemDetailProfile, itemDtl);
				this.dozerMapper.map(itemDetailProfile, itemDtl);
				itemDtlList.add(itemDtl);
			}
			return itemDtlList;
		} catch (Exception ex) {
			itemDtlList = new ArrayList<ItemDetailProfileExportLtsDTO>();
			itemDtl = new ItemDetailProfileExportLtsDTO();
			itemDtl.setReturnCd(1);
			itemDtl.setReturnMsg(ex.getMessage());
			itemDtlList.add(itemDtl);
			return itemDtlList;
		}
	}
	
	private List<ItemDetailProfileExportLtsDTO> getIddCallPlan(String[] pIddCallPlanCds) {
		
		List<ItemDetailProfileExportLtsDTO> callPlanList = new ArrayList<ItemDetailProfileExportLtsDTO>();
		IddCallPlanProfileLtsDTO[] callPlans = this.callPlanLtsService.mapIddCallPlan(pIddCallPlanCds);
		ItemDetailProfileExportLtsDTO itemDtl = null;
		
		if (ArrayUtils.isEmpty(callPlans)) {
			return callPlanList;
		}
		for (IddCallPlanProfileLtsDTO callPlan : callPlans) {
			itemDtl = new ItemDetailProfileExportLtsDTO();
			this.dozerMapper.map(callPlan, itemDtl);
			itemDtl.setDescription("||"+callPlan.getPlanCd()+"||"+callPlan.getDescription()); //twisted for BOM
			itemDtl.setArpuType("I");
			callPlanList.add(itemDtl);
		}
		return callPlanList;
	}

	public OfferItemService getOfferItemService() {
		return offerItemService;
	}

	public void setOfferItemService(OfferItemService offerItemService) {
		this.offerItemService = offerItemService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public Mapper getDozerMapper() {
		return dozerMapper;
	}

	public void setDozerMapper(Mapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}

	public CallPlanLtsService getCallPlanLtsService() {
		return callPlanLtsService;
	}

	public void setCallPlanLtsService(CallPlanLtsService callPlanLtsService) {
		this.callPlanLtsService = callPlanLtsService;
	}
}
