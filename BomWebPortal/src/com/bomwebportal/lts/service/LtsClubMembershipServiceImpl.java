package com.bomwebportal.lts.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dto.bom.BomAttbDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.bom.OfferProfileLtsService;
import com.bomwebportal.lts.service.order.SaveSbOrderLtsService;
import com.bomwebportal.lts.theclub.dto.LtsTheClubResponseFormDTO;
import com.bomwebportal.lts.theclub.service.LtsTheClubPointService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.DateFormatHelper;

public class LtsClubMembershipServiceImpl implements LtsClubMembershipService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private OfferProfileLtsService offerProfileLtsService;
	private CodeLkupCacheService ltsEarnClubPtsItemTypeCodeLkupCacheService;
	private CodeLkupCacheService ltsClubCatalogueUrlCodeLkupCacheService;
	private OfferService offerService;
	private LtsTheClubPointService ltsTheClubPointService;
	private SaveSbOrderLtsService saveSbOrderLtsService;
	
	/* Only to be called at signed-off
	 * Will call the club API and update the club point earned to the item in BOMWEB_SUBC_ITEM_LTS if the API is successfully called*/
	public void earnClubPointsAndUpdateItems(SbOrderDTO order, String userId){
		if(order == null){
			return;
		}
		
		for(ServiceDetailDTO srv: order.getSrvDtls()){
			ItemDetailLtsDTO[] itemDtls = null;
			List<ItemDetailLtsDTO> itemList = null;
			
			if(srv instanceof ServiceDetailLtsDTO){
				ServiceDetailLtsDTO ltsSrv = ((ServiceDetailLtsDTO) srv);
				String custNum = ltsSrv.getAccount().getCustomer().getCustNo();
				if(StringUtils.equals("Y", ltsSrv.getRecontractInd())
						&& ltsSrv.getRecontract() != null){
					custNum = ltsSrv.getRecontract().getCustNum();
				}
				itemDtls = ltsSrv.getItemDtls();
				if(itemDtls != null && itemDtls.length > 0){
					itemList = Arrays.asList(itemDtls);
					if(earnClubPoints(itemList, order.getOrderId(), custNum, null)){
						ltsSrv.setItemDtls(itemList.toArray(new ItemDetailLtsDTO[0]));
						saveSbOrderLtsService.saveService(ltsSrv, order.getOrderId(), userId);
					}
				}
			}
		}
	}

	public boolean earnClubPoints(List<ItemDetailLtsDTO> itemList, String sbId, String custNum, String memberId) {
		boolean earnedInd = false;
		for(ItemDetailLtsDTO item: itemList){
			if(ltsEarnClubPtsItemTypeCodeLkupCacheService.get(item.getItemType()) != null){
				if(earnClubPoints(item, sbId, custNum, memberId)){
					earnedInd = true;
				}
			}
		}
		return earnedInd;
	}

	public boolean earnClubPoints(ItemDetailLtsDTO item, String sbId, String custNum, String memberId) {
		/*check if item type valid for earn club point*/
		if(ltsEarnClubPtsItemTypeCodeLkupCacheService.get(item.getItemType()) == null
				|| !LtsConstant.IO_IND_INSTALL.equals(item.getIoInd()) ){
			return false;
		}
		
		int earnedClubPoint = 0;
		
		String[] offerCds = offerService.getItemOfferCodes(item.getItemId());
		String appDate = DateFormatHelper.getSysDate("yyyyMMdd");
		
		if(offerCds != null && offerCds.length > 0){
			for(String offerCd: offerCds){
				
				int offerClubPoint = 0;
				String offerPCode = null;
				String psefCd = StringUtils.substring(offerCd, 2, 6);
				
				/*get earn club point attb of the offer*/
				List<BomAttbDTO> attbList = offerProfileLtsService.getBomAttb(offerCd, appDate);
				if(attbList != null && attbList.size() > 0){
					for(BomAttbDTO attb: attbList){
						if(LtsConstant.CLUB_POINT_EARN_ORD_SUB.equals(attb.getAttbTypeDesc())
							&& StringUtils.isNumeric(attb.getAttbValue())){
							offerClubPoint = Integer.parseInt(attb.getAttbValue());
						}
						if(LtsConstant.CLUB_POINT_EARN_ORD_SUB_PCODE.equals(attb.getAttbTypeDesc())){
							offerPCode = attb.getAttbValue();
						}
					}
				}
				
				/*Call API earnClubPoint by custNum or memberId*/
				if(StringUtils.isNotBlank(offerPCode) && offerClubPoint > 0){
					String theClubOrderId = generateTheClubOrderId(sbId, psefCd);
					
					logger.info("Calling ltsTheClubPointService.earnClubPoint()"
							+ ", sbId: " + sbId + ", itemId: " + item.getItemId() + ", psefCd: " + psefCd
							+ ", custNum: " + custNum == null? "-": custNum + ", memberId: " + memberId  == null? "-" : memberId
							+ ", offerPCode:" + offerPCode + ", offerClubPoint: " + offerClubPoint);
					
					LtsTheClubResponseFormDTO response = 
							ltsTheClubPointService.earnClubPoint(theClubOrderId, custNum, memberId, null, null, null, null, null, 
									1, null, psefCd, offerPCode, offerClubPoint, sbId, null, true, 
									false); //<-- testInd
					if(response != null && "SUCC".equals(response.getRtnCode())){
						earnedClubPoint += offerClubPoint;
					}				
				}
			}
			
		}
		if(earnedClubPoint > 0){
			item.setEarnedClubPts(Integer.toString(earnedClubPoint));
			item.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			return true;
		}
		
		return false;
	}
	
	private String generateTheClubOrderId(String sbId, String psefCd ){
		return sbId + psefCd;
	}
	
	public String getTheClubCatalogueUrl(String locale){
		if(!LtsConstant.LOCALE_CHI.equals(locale) && !LtsConstant.LOCALE_ENG.equals(locale)){
			locale = LtsConstant.LOCALE_ENG;
		}
		return (String) ltsClubCatalogueUrlCodeLkupCacheService.get(locale);
	}
	
	
	public OfferProfileLtsService getOfferProfileLtsService() {
		return offerProfileLtsService;
	}

	public void setOfferProfileLtsService(
			OfferProfileLtsService offerProfileLtsService) {
		this.offerProfileLtsService = offerProfileLtsService;
	}

	public CodeLkupCacheService getLtsEarnClubPtsItemTypeCodeLkupCacheService() {
		return ltsEarnClubPtsItemTypeCodeLkupCacheService;
	}

	public void setLtsEarnClubPtsItemTypeCodeLkupCacheService(
			CodeLkupCacheService ltsEarnClubPtsItemTypeCodeLkupCacheService) {
		this.ltsEarnClubPtsItemTypeCodeLkupCacheService = ltsEarnClubPtsItemTypeCodeLkupCacheService;
	}

	public OfferService getOfferService() {
		return offerService;
	}

	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

	public LtsTheClubPointService getLtsTheClubPointService() {
		return ltsTheClubPointService;
	}

	public void setLtsTheClubPointService(
			LtsTheClubPointService ltsTheClubPointService) {
		this.ltsTheClubPointService = ltsTheClubPointService;
	}

	public SaveSbOrderLtsService getSaveSbOrderLtsService() {
		return saveSbOrderLtsService;
	}

	public void setSaveSbOrderLtsService(SaveSbOrderLtsService saveSbOrderLtsService) {
		this.saveSbOrderLtsService = saveSbOrderLtsService;
	}

	public CodeLkupCacheService getLtsClubCatalogueUrlCodeLkupCacheService() {
		return ltsClubCatalogueUrlCodeLkupCacheService;
	}

	public void setLtsClubCatalogueUrlCodeLkupCacheService(
			CodeLkupCacheService ltsClubCatalogueUrlCodeLkupCacheService) {
		this.ltsClubCatalogueUrlCodeLkupCacheService = ltsClubCatalogueUrlCodeLkupCacheService;
	}



}
