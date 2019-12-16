package com.bomltsportal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.SummaryFormDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.LtsDateFormatHelper;
import com.bomltsportal.util.SbOrderHelper;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.util.LtsSbHelper;

public class SummaryServiceImpl implements SummaryService {

	ItemDetailService itemDetailService;
	NowTvChannelService nowTvChannelService;
	
	public void setSummary(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder, SummaryFormDTO summary, String locale){
		setIdentificationSummary(sbOrder, summary);
		setStatements(sbOrder, summary);
		summary.setEpdItemList(orderCapture.getApplicantInfoForm().getEpdItemList());
		setBasketSummary(orderCapture, sbOrder, summary, locale);
		if(BomLtsConstant.LOCALE_ENG.equals(orderCapture.getLang())){
			summary.setAddress(sbOrder.getAddress().getDisFullAddrDescEn());
		}else if(BomLtsConstant.LOCALE_CHI.equals(orderCapture.getLang())){
			summary.setAddress(sbOrder.getAddress().getDisFullAddrDescCh());
		}
//		if((!orderCapture.getApplicantInfoForm().isCustNameMatch() && orderCapture.getApplicantInfoForm().isCustExist())|| StringUtils.isNotEmpty(orderCapture.getApplicantInfoForm().getImportNum())){
//			summary.setNoPay(true);
//		}
	}
	
	private void setStatements(SbOrderDTO sbOrder, SummaryFormDTO summary){
		ServiceDetailLtsDTO eyeSrvDtl = (ServiceDetailLtsDTO) SbOrderHelper.getEyeServiceDetail(sbOrder.getSrvDtls());
		summary.setShowPrivacyStatement(StringUtils.equals("Y", eyeSrvDtl.getAccount().getCustomer().getCustOptOutInfo() == null? "" : eyeSrvDtl.getAccount().getCustomer().getCustOptOutInfo().getEmail()));
		
		summary.setShowCsPoralTheClubStatement(StringUtils.equals("Y", eyeSrvDtl.getAccount().getCustomer().getCsPortalInd()) && StringUtils.equals("Y", eyeSrvDtl.getAccount().getCustomer().getTheClubInd()));
		summary.setShowCsPoralStatement(StringUtils.equals("Y", eyeSrvDtl.getAccount().getCustomer().getCsPortalInd()) && !StringUtils.equals("Y", eyeSrvDtl.getAccount().getCustomer().getTheClubInd()));
		summary.setShowTheClubStatement(!StringUtils.equals("Y", eyeSrvDtl.getAccount().getCustomer().getCsPortalInd()) && StringUtils.equals("Y", eyeSrvDtl.getAccount().getCustomer().getTheClubInd()));
		summary.setShowExDirStatement(StringUtils.equals("N", eyeSrvDtl.getExDirInd()));
	}
	
	public void setIdentificationSummary(SbOrderDTO sbOrder, SummaryFormDTO summary){
		
		ServiceDetailDTO eyeSrvDtl = SbOrderHelper.getEyeServiceDetail(sbOrder.getSrvDtls());
		
		AppointmentDetailLtsDTO appt = eyeSrvDtl.getAppointmentDtl();
		CustomerDetailLtsDTO cust = eyeSrvDtl.getAccount().getCustomer();
		
		summary.setTitle(cust.getTitle());
		summary.setFamilyName(cust.getLastName());
		summary.setGivenName(cust.getFirstName());
		summary.setDocType(StringUtils.equals(cust.getIdDocType(), "HKID")?"sum.desc.hkid":"sum.desc.passport");
		summary.setDocNum(cust.getIdDocNum());
		summary.setDateOfBirth(cust.getDob());
		
		String startDateTime = appt.getAppntStartTime();
		String endDateTime = appt.getAppntEndTime();
		if(StringUtils.isNotEmpty(startDateTime) && StringUtils.isNotEmpty(endDateTime)){
			summary.setInstallDate(startDateTime.split(" ")[0]);
			summary.setInstallTime(LtsDateFormatHelper.convertToSBTimeSlot(startDateTime.split(" ")[1].concat("-").concat(endDateTime.split(" ")[1])));
		}
		
		summary.setBillLang(eyeSrvDtl.getAccount().getBillLang());
		summary.setSrvNum(eyeSrvDtl.getSrvNum());
		
		summary.setContactEmailAddr(sbOrder.getEsigEmailAddr());
		summary.setContactMobileNum(appt.getCustContactMobile());
	}
	
	public void setBasketSummary(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder, SummaryFormDTO summary, String locale){

		ServiceDetailLtsDTO delSrv = LtsSbHelper.getDelServices(sbOrder);
		ServiceDetailLtsDTO ltsSrv = LtsSbHelper.getLtsEyeService(sbOrder);
		ServiceDetailOtherLtsDTO imsSrv = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());
		
//		ServiceDetailDTO eyeSrvDtl = SbOrderHelper.getEyeServiceDetail(sbOrder.getSrvDtls());
//		ServiceDetailOtherLtsDTO
//		ItemDetailLtsDTO[] items = eyeSrvDtl.getItemDtls();
		
//		Map<String, ItemDetailLtsDTO> itemIdMap = new HashMap<String, ItemDetailLtsDTO>();
		
		ItemDetailLtsDTO[] items = null;
		
		if(delSrv != null){
			items = delSrv.getItemDtls();
		}else if(ltsSrv != null){
			items = ltsSrv.getItemDtls();
		}
		
		if(items != null){
			List<String> itemIdList = new ArrayList<String>();
			
			for (int i=0; items!=null && i<items.length; ++i) {
//				itemIdMap.put(items[i].getItemId(), items[i]);
				itemIdList.add(items[i].getItemId());
			}

			if(itemIdList != null && !itemIdList.isEmpty()){
				List<ItemDetailDTO> itemList = itemDetailService.getItems(itemIdList.toArray(new String[0]), BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, locale, true, false);
				List<ItemDetailDTO> vasItemList = new ArrayList<ItemDetailDTO>();
				List<ItemDetailDTO> premiumItemList = new ArrayList<ItemDetailDTO>();
				List<ItemDetailDTO> contentItemList = new ArrayList<ItemDetailDTO>();
				List<ItemDetailDTO> nowTvItemList = new ArrayList<ItemDetailDTO>();
				ItemDetailDTO planItem = null;
				String billMethod = "";
				
				for(ItemDetailDTO item : itemList){
					if(BomLtsConstant.ITEM_TYPE_PLAN.equals(item.getItemType())){
						planItem = item;
					}else if(BomLtsConstant.ITEM_TYPE_MOOV.equals(item.getItemType()) 
							|| BomLtsConstant.ITEM_TYPE_VAS_HOT.equals(item.getItemType())
							|| BomLtsConstant.ITEM_TYPE_ONLINE_VAS.equals(item.getItemType())){
						vasItemList.add(item);
					}else if(BomLtsConstant.ITEM_TYPE_PREMIUM.equals(item.getItemType())){
						premiumItemList.add(item);
					}else if(BomLtsConstant.ITEM_TYPE_CONTENT.equals(item.getItemType())){
						contentItemList.add(item);
					}else if(BomLtsConstant.ITEM_TYPE_NOWTV_PAY.equals(item.getItemType())
							|| BomLtsConstant.ITEM_TYPE_NOWTV_SPEC.equals(item.getItemType())){
						nowTvItemList.add(item);
					}else if(BomLtsConstant.ITEM_TYPE_ONLINE_EBILL.equals(item.getItemType())){
						billMethod = item.getItemDisplayHtml();
					}
				}
				
				summary.setVasItemList(vasItemList);
				summary.setPremiumItemList(premiumItemList);
				summary.setContentItemList(contentItemList);
				summary.setNowTvItemList(nowTvItemList);
				summary.setPlanItem(planItem);
				summary.setBillMethod(billMethod);
			}
			
			if(summary.getNowTvItemList() != null && summary.getNowTvItemList().size() > 0){
				List<String> contentTvDescList = new ArrayList<String>();
				for(ChannelDetailLtsDTO channel : imsSrv.getChannels()){
					String channelDesc = nowTvChannelService.getChannelDescription(channel.getChannelId(), orderCapture.getLang());
					if(StringUtils.isNotEmpty(channelDesc)){
						contentTvDescList.add(channelDesc);
					}
				}
				summary.setNowTvDescList(contentTvDescList);
			}
		}
	}
	
	public String getDisplayAddress(AddressDetailLtsDTO pAddress) {
		
		if (pAddress == null) {
			return "";
		}
		StringBuilder targetIa = new StringBuilder();
		if (StringUtils.isNotBlank(pAddress.getUnitNo())) {
			targetIa.append("FLAT ").append(pAddress.getUnitNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getFloorNo())) {
			targetIa.append(pAddress.getFloorNo()).append("/FLOOR ").append(", ");	
		}
		return targetIa.append(getDisplayAddressWithoutFlatFoor(pAddress)).toString();
	}
	
	public String getDisplayAddressWithoutFlatFoor(AddressDetailLtsDTO pAddress){
		if (pAddress == null) {
			return "";
		}
		StringBuilder targetIa = new StringBuilder();
		if (StringUtils.isNotBlank(pAddress.getBuildNo())) {
			targetIa.append(pAddress.getBuildNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getHiLotNo())) {
			targetIa.append("LAND LOT NO ").append(pAddress.getHiLotNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getSectDesc())) {
			targetIa.append(pAddress.getSectDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getStrNo())) {
			targetIa.append(pAddress.getStrNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getStrName())) {
			targetIa.append(pAddress.getStrName()).append(" ");	
		}
		if (StringUtils.isNotBlank(pAddress.getStrCatDesc())) {
			targetIa.append(pAddress.getStrCatDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getDistDesc())) {
			targetIa.append(pAddress.getDistDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getAreaDesc())) {
			targetIa.append(pAddress.getAreaDesc()).append(", ");	
		}
		return targetIa.substring(0, targetIa.length()-2).toString();
	}

	public int calculateItemPaymentAmount(ItemDetailLtsDTO[] itemDtls){
		if(itemDtls == null || itemDtls.length == 0){
			return 0;
		}
		String[] itemTypes = {
				BomLtsConstant.ITEM_TYPE_MOOV, 
				BomLtsConstant.ITEM_TYPE_VAS_HOT, 
				BomLtsConstant.ITEM_TYPE_NOWTV_PAY,
				BomLtsConstant.ITEM_TYPE_NOWTV_SPEC,
				BomLtsConstant.ITEM_TYPE_ONLINE_VAS,
				BomLtsConstant.ITEM_TYPE_PLAN,
				BomLtsConstant.ITEM_TYPE_INSTALL};
		
		List<String> itemIds = new ArrayList<String>();
		for(ItemDetailLtsDTO item : itemDtls){
			if(Arrays.asList(itemTypes).contains(item.getItemType())){
				itemIds.add(item.getItemId());
			}
		}

		List<ItemDetailDTO> itemList = null;
		if(itemIds.size() > 0){
			itemList = itemDetailService.getItems(itemIds.toArray(new String[0]), BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, "en", false, false);
		}
		
		return calculateItemPaymentAmount(itemList);
	}
	
	
	public int calculateItemPaymentAmount(List<ItemDetailDTO> itemList){
		if(itemList == null || itemList.size() == 0){
			return 0;
		}
		int amt = 0;
		for(ItemDetailDTO item : itemList){
			if(StringUtils.isBlank(item.getRecurrentAmt()) || "0".equals(item.getRecurrentAmt())){
				if(StringUtils.isNotBlank(item.getMthToMthAmt()) && !"0".equals(item.getMthToMthAmt())){
					amt += Integer.parseInt(item.getMthToMthAmt());
				}
			}else{
				amt += Integer.parseInt(item.getRecurrentAmt());
			}
			if(!StringUtils.isBlank(item.getOneTimeAmt()) && !"0".equals(item.getOneTimeAmt())){
				amt += Integer.parseInt(item.getOneTimeAmt());
			}
		}
		return amt;
	}
	
//	public int calculateItemPaymentAmount(ItemDetailLtsDTO[] itemDtls, boolean isRecurrentAmt){
//		String[] itemTypes = {
//				BomLtsConstant.ITEM_TYPE_MOOV, 
//				BomLtsConstant.ITEM_TYPE_VAS_HOT, 
//				BomLtsConstant.ITEM_TYPE_NOWTV_PAY,
//				BomLtsConstant.ITEM_TYPE_NOWTV_SPEC,
//				BomLtsConstant.ITEM_TYPE_ONLINE_VAS};
////		String planType = BomLtsConstant.ITEM_TYPE_PLAN;
//		
//		List<String> itemIds = new ArrayList<String>();
//		List<String> planId = new ArrayList<String>();
//		for(ItemDetailLtsDTO item : itemDtls){
//			if(Arrays.asList(itemTypes).contains(item.getItemType())){
//				itemIds.add(item.getItemId());
//			}
//			if(BomLtsConstant.ITEM_TYPE_PLAN.equals(item.getItemType())){
//				planId.add(item.getItemId());
//			}
//		}
//		int amt = 0;
//		List<ItemDetailDTO> itemList = null;
//		if(itemIds.size() > 0){
//			itemList = itemDetailService.getItems(itemIds.toArray(new String[0]), BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, "en", false, false);
//		}
//		List<ItemDetailDTO> planItemList = new ArrayList<ItemDetailDTO>();
//		if(planId.size() > 0){
//			planItemList = itemDetailService.getItems(planId.toArray(new String[0]), BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, "en", false, false);
//		}
//		if(planItemList.size() > 0){
//			if(StringUtils.isEmpty(planItemList.get(0).getRecurrentAmt()) || "0".equals(planItemList.get(0).getRecurrentAmt())){
//				amt += calculateItemPaymentAmount(planItemList, false);
//				if(itemList != null && itemList.size() != 0){
//					amt += calculateItemPaymentAmount(itemList, false);
//				}
//			}else{
//				amt += calculateItemPaymentAmount(planItemList, true);
//				if(itemList != null && itemList.size() != 0){
//					amt += calculateItemPaymentAmount(itemList, true);
//				}
//			}
//		}
//		return amt;
//	}
//	
//	public int calculateItemPaymentAmount(List<ItemDetailDTO> itemList, boolean isRecurrentAmt) {
//		int amt = 0;
//		if(isRecurrentAmt){
//			for(ItemDetailDTO item : itemList){
//				amt += StringUtils.isBlank(item.getRecurrentAmt())?0:Integer.parseInt(item.getRecurrentAmt());
//			}
//		}else{
//			for(ItemDetailDTO item : itemList){
//				amt += StringUtils.isBlank(item.getMthToMthAmt())?0:Integer.parseInt(item.getMthToMthAmt());
//			}
//		}
//		return amt;
//	}
	
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

}
