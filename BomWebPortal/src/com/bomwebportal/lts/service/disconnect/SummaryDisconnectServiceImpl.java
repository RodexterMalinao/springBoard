package com.bomwebportal.lts.service.disconnect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatterBuilder;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.disconnect.DisconnectServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.Service0060DetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.Idd0060ProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;
import com.bomwebportal.lts.service.order.CallPlanLtsService;
import com.bomwebportal.lts.service.order.SummaryBaseServiceImpl;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.disconnect.LtsTerminateConstant;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.util.CommonUtil;

public class SummaryDisconnectServiceImpl extends SummaryBaseServiceImpl {
	
	private CodeLkupCacheService delDisconnectReasonLkupCacheService;
	private CodeLkupCacheService eyeDisconnectReasonLkupCacheService;
	private CodeLkupCacheService waiveDFormReasonLkupCacheService;
	private CallPlanLtsService callPlanLtsService;

	public DisconnectServiceSummaryDTO buildSummary(SbOrderDTO pSbOrder, String pLocale, boolean pIsEnquiry) {
	
		boolean isEyeOrder = true;
		ServiceDetailLtsDTO srvDtl = LtsSbOrderHelper.getDisconnectEyeService(pSbOrder.getSrvDtls());
						
		if(srvDtl == null){
			srvDtl = LtsSbOrderHelper.getDisconnectDelService(pSbOrder.getSrvDtls());
			isEyeOrder = false;
		}
				
		if (srvDtl == null){
			return null;
		}
		
		
		DisconnectServiceSummaryDTO srvSummary = new DisconnectServiceSummaryDTO();
		
		
		this.fillLtsSummaryDetail(srvSummary, pSbOrder, srvDtl);
		this.fillLtsCustomerDetail(srvSummary, srvDtl);
		this.fillDisconnectInfo(srvSummary, pSbOrder, srvDtl, isEyeOrder);
		this.fillAppointmentSummary(srvSummary, pSbOrder, srvDtl.getAppointmentDtl());
		//this.fillAppoinmentDetail(srvSummary, srvDtl.getAppointmentDtl());
		ItemDetailLtsDTO[] item = srvDtl.getItemDtls();
		this.fillDisconnectItemDisplay(srvSummary, item, pLocale);
		this.fillDisconnect0060SrvDtl(srvSummary, pSbOrder);
		ServiceCallPlanDetailLtsDTO[] srvCallPlanDtls = srvDtl.getSrvCallPlanDtls();
		this.fillDisconnectCallPlanSrvDtl(srvSummary, srvCallPlanDtls);
		this.fillSrvStatusState(pSbOrder.getOrderId(), srvSummary, srvDtl);
		this.fillSrvStatusStateLts(pSbOrder, srvSummary, srvDtl);	
		this.fillCallingCard(srvSummary, srvDtl);
		this.fillRemarks(srvSummary, srvDtl);
//		this.fillCollectDocList(srvSummary, pSbOrder);
		this.fillESignDetail(srvSummary, pSbOrder);
		srvSummary.setSrvType(LtsConstant.DAT_CD_DEL);
		srvSummary.setForceFieldVisitInd(srvDtl.getForceFieldVisitInd());
		

		ServiceDetailOtherLtsDTO imsSrvDtl = (ServiceDetailOtherLtsDTO) LtsSbOrderHelper.getImsService(pSbOrder);
		if(imsSrvDtl != null){
			this.fillImsDisconnectItemDisplay(srvSummary, imsSrvDtl.getOfferDtls(), pLocale);
		}
		
		return srvSummary;
	}
	
	private void fillCallingCard(DisconnectServiceSummaryDTO pSrvSummary, ServiceDetailDTO pDisconnectService){
		if (StringUtils.equals(((ServiceDetailLtsDTO)pDisconnectService).getDiscCallingCardInd(), "Y")) {
			pSrvSummary.setCallingCardHandling(LtsTerminateConstant.CALLING_CARD_HANDLING_DISCONNECT);
		}
		if (StringUtils.equals(((ServiceDetailLtsDTO)pDisconnectService).getDiscCallingCardInd(), "N")) {
			pSrvSummary.setCallingCardHandling(LtsTerminateConstant.CALLING_CARD_HANDLING_RETAIN);
		}
	}
	
	private void fillDisconnect0060SrvDtl(DisconnectServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder){
			
		List<Idd0060ProfileLtsDTO> idd0060List = new ArrayList<Idd0060ProfileLtsDTO>();
		Service0060DetailLtsDTO[] idd0060Dtos =	pSbOrder.getSrv0060Dtls();
		
		if(idd0060Dtos == null){ 
			return;
		}
		
		for(Service0060DetailLtsDTO idd0060Dto : idd0060Dtos){
			Idd0060ProfileLtsDTO srv0060Dto = new Idd0060ProfileLtsDTO();
			srv0060Dto.setSrvNum(idd0060Dto.getSrvNum());
			srv0060Dto.setSrvType(idd0060Dto.getTypeOfSrv());
			srv0060Dto.setDatCode(idd0060Dto.getDatCd());
			srv0060Dto.setAction((StringUtils.equals(idd0060Dto.getIoInd(),LtsBackendConstant.IO_IND_OUT))? LtsBackendConstant.IDD_ACTION_REMOVE : LtsBackendConstant.IDD_ACTION_RETAIN);
			idd0060List.add(srv0060Dto);
		}
			pSrvSummary.setSrv0060DtlList(idd0060List);
	}
	
	private void fillDisconnectCallPlanSrvDtl(DisconnectServiceSummaryDTO pSrvSummary, ServiceCallPlanDetailLtsDTO[] pSrvCPDtls){
	
		if (pSrvCPDtls == null) {
			return;
		}
		
		String[] allCallPlanCodes = getAllPlanCodes(Arrays.asList(pSrvCPDtls));
		IddCallPlanProfileLtsDTO[] srvCPwithPlanCd = callPlanLtsService.mapIddCallPlan(allCallPlanCodes);
		
		try{
		
		List<IddCallPlanProfileLtsDTO> srvCpDtoList = new ArrayList<IddCallPlanProfileLtsDTO>();

		for(ServiceCallPlanDetailLtsDTO srvCPDtl : pSrvCPDtls)
		{
			for(IddCallPlanProfileLtsDTO srvCpDto : srvCPwithPlanCd)
			{
			
				if (StringUtils.equals(srvCPDtl.getPlanCd(), srvCpDto.getPlanCd())) {
					
					IddCallPlanProfileLtsDTO clonedIddCallPlanProfile = (IddCallPlanProfileLtsDTO)CommonUtil.cloneNestedSerializableObject(srvCpDto);
					clonedIddCallPlanProfile.setEffStartDate(srvCPDtl.getEffStartDate().substring(0,8));
					
					if(!StringUtils.equals(srvCPDtl.getEffEndDate(), null)){
						clonedIddCallPlanProfile.setEffEndDate(srvCPDtl.getEffEndDate().substring(0,8));
					}else{clonedIddCallPlanProfile.setEffEndDate(null);}

					if(!StringUtils.equals(srvCPDtl.getContractStartDate(), null)){
						clonedIddCallPlanProfile.setContractStartDate(srvCPDtl.getContractStartDate().substring(0,8));
					}else{clonedIddCallPlanProfile.setContractStartDate(null);}
					
					if(!StringUtils.equals(srvCPDtl.getContractEndDate(), null)){
						clonedIddCallPlanProfile.setContractEndDate(srvCPDtl.getContractEndDate().substring(0,8));
					}else{clonedIddCallPlanProfile.setContractEndDate(null);}
					
//					clonedIddCallPlanProfile.setAction((StringUtils.equals(srvCPDtl.getIoInd(),LtsBackendConstant.IO_IND_OUT))? LtsBackendConstant.IDD_ACTION_REMOVE : LtsBackendConstant.IDD_ACTION_RETAIN);
					clonedIddCallPlanProfile.setPlanCd(srvCPDtl.getPlanCd());
					clonedIddCallPlanProfile.setPlanType(srvCPDtl.getPlanType());
					clonedIddCallPlanProfile.setPlanHolderType((srvCPDtl.getPlanHolderType()));
					clonedIddCallPlanProfile.setPenaltyHandling(srvCPDtl.getPenaltyHandling());
					clonedIddCallPlanProfile.setGrossEffPrice(srvCPDtl.getPlanCharge());
					
					if(StringUtils.equals(srvCPDtl.getIoInd(),LtsBackendConstant.IO_IND_OUT)){
						clonedIddCallPlanProfile.setAction(LtsBackendConstant.IDD_ACTION_REMOVE);
					}else{
						if("Y".equals(srvCPDtl.getChgDcaInd())){
							clonedIddCallPlanProfile.setAction("Change DCA");
							clonedIddCallPlanProfile.setNewDca(srvCPDtl.getNewDca());
						}else if("A".equals(srvCPDtl.getChgDcaInd())){
							clonedIddCallPlanProfile.setAction("Change Account & DCA");
							clonedIddCallPlanProfile.setNewDca(srvCPDtl.getNewDca());
						}else{
							clonedIddCallPlanProfile.setAction(LtsBackendConstant.IDD_ACTION_RETAIN);
						}
					}
					
					/* Calculate remain contract in format 00y 00m 00d*/
					clonedIddCallPlanProfile.setRemainContract("--");
					if(!StringUtils.equals(clonedIddCallPlanProfile.getContractEndDate(), "00000000") && !StringUtils.isBlank(clonedIddCallPlanProfile.getContractEndDate()) ){
						LocalDate sysDateForCalc = LocalDate.now();
						LocalDate contractEndDateForCalc = LocalDate.parse(clonedIddCallPlanProfile.getContractEndDate(), DateTimeFormat.forPattern("yyyyMMdd"));
						LocalDate contractStartDateForCalc = LocalDate.parse(clonedIddCallPlanProfile.getContractStartDate(), DateTimeFormat.forPattern("yyyyMMdd"));
						
						if(contractEndDateForCalc.isAfter(sysDateForCalc)
								&& contractStartDateForCalc.isBefore(sysDateForCalc)){
							int daysToSubtract = (sysDateForCalc.getDayOfMonth() - 1);
							int monthsToSubtract = (sysDateForCalc.getMonthOfYear() - 1);
							sysDateForCalc = sysDateForCalc.minusDays(daysToSubtract);
							sysDateForCalc = sysDateForCalc.minusMonths(monthsToSubtract);
							contractEndDateForCalc = contractEndDateForCalc.minusDays(daysToSubtract);
							contractEndDateForCalc = contractEndDateForCalc.minusMonths(monthsToSubtract);
							Period remainPeriod = Period.fieldDifference(sysDateForCalc, contractEndDateForCalc);
							clonedIddCallPlanProfile.setRemainContract(remainPeriod.toString(
									new PeriodFormatterBuilder().printZeroAlways()
									.appendYears().appendSuffix("y ")
									.appendMonths().appendSuffix("m ")
									.appendDays().appendSuffix("d")
									.toFormatter()
							));

							if(remainPeriod.getYears() > 0
										|| remainPeriod.getMonths() > 3
										|| (remainPeriod.getMonths() == 3 && remainPeriod.getDays() > 0) ){
								clonedIddCallPlanProfile.setRemainContractGt3Mths(true);
							}
						}
					}
					
					
					srvCpDtoList.add(clonedIddCallPlanProfile);
				}
			}
		}

		Collections.sort(srvCpDtoList, new Comparator<IddCallPlanProfileLtsDTO>(){
			public int compare(IddCallPlanProfileLtsDTO o1, IddCallPlanProfileLtsDTO o2) {
				return o1.getPlanCd().compareTo(o2.getPlanCd());
			}
		});
		
		pSrvSummary.setSrvCPDtlList(srvCpDtoList);
		
		} catch(Exception e) {
			throw new AppRuntimeException(e.getMessage());
		}
		
	}
	
	private String[] getAllPlanCodes(List<ServiceCallPlanDetailLtsDTO> pSrvCPDtls) {
		if(pSrvCPDtls == null || pSrvCPDtls.isEmpty()) {
			return null;
		}
		String[] callPlanCodes = new String[pSrvCPDtls.size()];
		for (int i = 0; i < callPlanCodes.length; i++) {
			callPlanCodes[i] = pSrvCPDtls.get(i).getPlanCd();
		}
		return callPlanCodes;
	}

	private void fillDisconnectInfo(DisconnectServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder, ServiceDetailLtsDTO pSrvDtlLts, boolean pIsEyeOrder){
		
		for(ServiceDetailDTO temp:pSbOrder.getSrvDtls()){
		
			
			if(temp instanceof ServiceDetailLtsDTO){
				
				if(pIsEyeOrder){
					pSrvSummary.setDisconnectReason((String)eyeDisconnectReasonLkupCacheService.get(temp.getDiscReasonCode()));
				}else{
					pSrvSummary.setDisconnectReason((String)delDisconnectReasonLkupCacheService.get(temp.getDiscReasonCode()));					
				}
				
				if(StringUtils.isNotBlank(temp.getCeaseRentalDate())){
					pSrvSummary.setCeaseRentalDate(temp.getCeaseRentalDate().substring(0,10));
				}
				pSrvSummary.setdFromSerialNum(((ServiceDetailLtsDTO) temp).getDFormSerial());
				pSrvSummary.setWaiveDfromReason((String) waiveDFormReasonLkupCacheService.get(((ServiceDetailLtsDTO) temp).getWaiveDFormReasonCd()));
				pSrvSummary.setnA(((ServiceDetailLtsDTO) temp).getDiscFiveNaInd());
				pSrvSummary.setLostEquipment(((ServiceDetailLtsDTO) temp).getLostEquipmentCd());
				pSrvSummary.setThirdPartyAppl(temp.getThirdPartyAppln());
				pSrvSummary.setNewBillingName(temp.getAccount().getAcctName());
				pSrvSummary.setBillingMedia(billMediaConvertFromCdToDesc(temp.getAccount().getBillMedia()));
				pSrvSummary.setCollectEquipAddr(((ServiceDetailLtsDTO) temp).getEqtCollectionAddr());
			}
			
			if(temp instanceof ServiceDetailOtherLtsDTO){
				String lostModemInd = ((ServiceDetailOtherLtsDTO) temp).getLostModem();
				if(StringUtils.isNotBlank(lostModemInd)){
					if(LtsConstant.LOST_MODEM_HAVE_EYE_USAGE.equals(lostModemInd)){
						pSrvSummary.setLostModem("Have eye usage");
					}else if(LtsConstant.LOST_MODEM_NO_EYE_USAGE.equals(lostModemInd)){
						pSrvSummary.setLostModem("No eye usage");
					}else{
						pSrvSummary.setLostModem("N");
					}
				}else{
					pSrvSummary.setLostModem("N");
				}
			}
		}
	}

	
	private void fillAppointmentSummary(DisconnectServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder, AppointmentDetailLtsDTO pAppt){
	 
		pSrvSummary.setApptTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(pAppt.getAppntStartTime(), pAppt.getAppntEndTime())));
		pSrvSummary.setApptDate(LtsDateFormatHelper.getDateFromDTOFormat(pAppt.getAppntStartTime()));
		pSrvSummary.setApplDate(pSbOrder.getAppDate().substring(0, 10));
		pSrvSummary.setApptSrd(pSbOrder.getSrvReqDate().split(" ")[0]);
//		pSrvSummary.setApplTime(pSbOrder.getSalesProcessDate().substring(11,16));
//		pSrvSummary.setApplTime(pSbOrder.getAppDate().substring(11,pSbOrder.getAppDate().length()));
	
	}
	
	private void fillRemarks(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
		pSrvSummary.setAddOnRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
		pSrvSummary.setCustRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_CUST_REMARK));
		pSrvSummary.setOrderRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_ORDER_REMARK));
		pSrvSummary.setEarliestSrdReasonRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_EARLIEST_SRD_REMARK));
		pSrvSummary.setFsRemark(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_2ND_DEL));
	}
	
	protected void fillImsDisconnectItemDisplay(DisconnectServiceSummaryDTO pSrvSummary, ImsOfferDetailDTO[] pItems, String pLocale){
		if (pItems == null) {
			return;
		}
		
		ItemDetailSummaryDTO itemDtlSummary = null;
		
		for (int i=0; pItems!=null && i<pItems.length; ++i) {
			if(StringUtils.isEmpty(pItems[i].getItemId())){
				continue;
			}
			
			ItemDetailDTO itemDtl = this.ltsOfferService.getTpVasItemDetail(pItems[i].getItemId(), pLocale);
			
			if (itemDtl == null) {
				continue;
			}
			
			itemDtlSummary = new ItemDetailSummaryDTO();
			itemDtlSummary.setItemId(pItems[i].getItemId());
			itemDtlSummary.setItemDisplayHtml(itemDtl.getItemDisplayHtml());
			itemDtlSummary.setTpCatg(itemDtl.getTpCatg());
			itemDtlSummary.setContractMonth(itemDtl.getContractMonth());
			itemDtlSummary.setPenaltyHandling(getPenaltyHandlingDesc(pItems[i].getPenaltyHandling()));
			itemDtlSummary.setGrossEffPrice(itemDtl.getGrossEffPrice());
//			itemDtlSummary.setExistStartDate(pItems[i].getExistStartDate());
//			itemDtlSummary.setExistEndDate(pItems[i].getExistEndDate());
			itemDtlSummary.setNetEffPrice(itemDtl.getNetEffPrice());
			
			pSrvSummary.addImsVasItem(itemDtlSummary);
		}
		
		
	}
		
	protected void fillDisconnectItemDisplay(DisconnectServiceSummaryDTO pSrvSummary, ItemDetailLtsDTO[] pItems, String pLocale) {
		
		if (pItems == null) {
			return;
		}

		
		ItemDetailSummaryDTO itemDtlSummary = null;
		
		for (int i=0; pItems!=null && i<pItems.length; ++i) {
			ItemDetailDTO itemDtl = this.ltsOfferService.getTpVasItemDetail(pItems[i].getItemId(), pLocale);
			
			if (itemDtl == null) {
				continue;
			}
			
			itemDtlSummary = new ItemDetailSummaryDTO();
			itemDtlSummary.setItemId(pItems[i].getItemId());
			itemDtlSummary.setItemDisplayHtml(itemDtl.getItemDisplayHtml());
			itemDtlSummary.setTpCatg(itemDtl.getTpCatg());
			itemDtlSummary.setContractMonth(itemDtl.getContractMonth());
			itemDtlSummary.setPenaltyHandling(getPenaltyHandlingDesc(pItems[i].getPenaltyHandling()));
			itemDtlSummary.setGrossEffPrice(itemDtl.getGrossEffPrice());
			itemDtlSummary.setExistStartDate(pItems[i].getExistStartDate());
			itemDtlSummary.setExistEndDate(pItems[i].getExistEndDate());
			itemDtlSummary.setNetEffPrice(itemDtl.getNetEffPrice());
			
		    if(StringUtils.equals(pItems[i].getProfileItemType(), LtsConstant.PROFILE_ITEM_TYPE_SERVICE)){
		    	pSrvSummary.addDisSrvItem(itemDtlSummary);
		    }
		    else if(StringUtils.equals(pItems[i].getProfileItemType(), LtsConstant.PROFILE_ITEM_TYPE_VAS)){
		    	pSrvSummary.addVasItem(itemDtlSummary);
		    }
		}

//		
//		Map<String, ItemDetailLtsDTO> itemIdMap = new HashMap<String, ItemDetailLtsDTO>();
//		List<ItemDetailDTO> itemDescList =  this.ltsOfferService.getItemWithAttb(itemIdMap.keySet().toArray(new String[itemIdMap.size()]), LtsConstant.DISPLAY_TYPE_ITEM_SELECT, pLocale, false);
//
//		
//		for (int i=0; pItems!=null && i<pItems.length; ++i) {
//			itemIdMap.put(pItems[i].getItemId(), pItems[i]);
//		}
//		for (int i=0; i<itemDescList.size(); ++i) {
//			ItemDetailLtsDTO itemDtlDto =itemIdMap.get(itemDescList.get(i).getItemId());
//			itemDtlSummary = this.generateItemSummaryDTO(itemDescList.get(i), itemDtlDto);
//			itemIdMap.remove(itemDtlSummary.getItemId());
//			
//		}
	}
	
	
//	
//	private void fillCollectDocList(DisconnectServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder){
//	List<CollectDocDto> collectDocList = new ArrayList<CollectDocDto>();
//	HashMap<String, String> faxSerialMap = ltsOrderDocumentService.getFaxSerialMap(pSbOrder.getOrderId()); // new HashMap<String, String>();
//	
//	if (pSbOrder.getAllOrdDocAssgns() == null){
//		return;
//	}
//	
//	for(AllOrdDocAssgnLtsDTO ordDocAssgn: pSbOrder.getAllOrdDocAssgns()){
//		CollectDocDto collectDoc = new CollectDocDto();
//		collectDoc.setDocType(ordDocAssgn.getDocType());
//		collectDoc.setDocTypeDisplay((String) ltsDocTypeLkupCacheService.get(ordDocAssgn.getDocType()));
//		collectDoc.setWaiveReason(ordDocAssgn.getWaiveReason());
//		collectDoc.setWaiveReasonDisplay((String) ltsWaiveReasonCacheService.get(ordDocAssgn.getWaiveReason()));
//		collectDoc.setMarkDelInd(ordDocAssgn.getMarkDelInd());
//		collectDoc.setCollectedInd(ordDocAssgn.getCollectedInd());
//		collectDoc.setFaxSerial(faxSerialMap.get(ordDocAssgn.getDocType()));
//		collectDocList.add(collectDoc);
//	}
//	
//	pSrvSummary.setCollectDocList(collectDocList);
//}
	
	
	private String getPenaltyHandlingDesc(String penaltyHandling) {
		if (StringUtils.isEmpty(penaltyHandling)) {
			return null;
		}
		
		if (LtsConstant.PENALTY_ACTION_GENERATE.equals(penaltyHandling)) {
			return "Generate";
		}
		
		if (LtsConstant.PENALTY_ACTION_AUTO_WAIVE.equals(penaltyHandling)) {
			return "Auto Waive";
		}
		
		if (LtsConstant.PENALTY_ACTION_AWAIT_APPROVAL.equals(penaltyHandling)) {
			return "Manual Waive (Await Approval)";
		}
		
		if (LtsConstant.PENALTY_ACTION_MANUAL_WAIVE.equals(penaltyHandling)) {
			return "Manual Waive";
		}
		
		return null; 
	}

	public CodeLkupCacheService getDelDisconnectReasonLkupCacheService() {
		return delDisconnectReasonLkupCacheService;
	}


	public void setDelDisconnectReasonLkupCacheService(
			CodeLkupCacheService delDisconnectReasonLkupCacheService) {
		this.delDisconnectReasonLkupCacheService = delDisconnectReasonLkupCacheService;
	}


	public CodeLkupCacheService getEyeDisconnectReasonLkupCacheService() {
		return eyeDisconnectReasonLkupCacheService;
	}


	public void setEyeDisconnectReasonLkupCacheService(
			CodeLkupCacheService eyeDisconnectReasonLkupCacheService) {
		this.eyeDisconnectReasonLkupCacheService = eyeDisconnectReasonLkupCacheService;
	}


	public CodeLkupCacheService getWaiveDFormReasonLkupCacheService() {
		return waiveDFormReasonLkupCacheService;
	}


	public void setWaiveDFormReasonLkupCacheService(
			CodeLkupCacheService waiveDFormReasonLkupCacheService) {
		this.waiveDFormReasonLkupCacheService = waiveDFormReasonLkupCacheService;
	}

	public CallPlanLtsService getCallPlanLtsService() {
		return callPlanLtsService;
	}

	public void setCallPlanLtsService(CallPlanLtsService callPlanLtsService) {
		this.callPlanLtsService = callPlanLtsService;
	}

}
