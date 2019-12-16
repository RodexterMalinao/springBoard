package com.bomwebportal.lts.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LtsAppointmentDAO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO.DuplexSrvType;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.dto.ret.RenewBasketPolicyDTO;
import com.bomwebportal.lts.dto.srvAccess.BmoDetailOutput;
import com.bomwebportal.lts.dto.srvAccess.BmoPermitDetail;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceTypeDTO;
import com.bomwebportal.lts.service.bom.BomOfferProductLkupService;
import com.bomwebportal.lts.service.bom.BomOrderStatusSynchService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsDrgService;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheServiceImpl;

public class LtsAppointmentServiceImpl implements LtsAppointmentService {
	private LtsAppointmentDAO ltsAppointmentDAO;
	private AppointmentDwfmService appointmentDwfmService;
	private ServiceProfileLtsDrgService serviceProfileLtsDrgService;
	private CodeLkupCacheServiceImpl ltsAppointmentCutOffLkupCacheService;
	private ImsProfileService imsProfileService;
	private BomOrderStatusSynchService bomOrderStatusSynchService;
	private LtsBasketService ltsBasketService;
	private LtsOfferService ltsOfferService;
	private BomOfferProductLkupService bomOfferProductLkupService;
	
	private SimpleDateFormat df = new SimpleDateFormat();
	
	public BomOfferProductLkupService getBomOfferProductLkupService() {
		return bomOfferProductLkupService;
	}

	public void setBomOfferProductLkupService(
			BomOfferProductLkupService bomOfferProductLkupService) {
		this.bomOfferProductLkupService = bomOfferProductLkupService;
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}

	public void setAppointmentDwfmService(AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}

	public LtsAppointmentDAO getLtsAppointmentDAO() {
		return ltsAppointmentDAO;
	}

	public void setLtsAppointmentDAO(LtsAppointmentDAO ltsAppointmentDAO) {
		this.ltsAppointmentDAO = ltsAppointmentDAO;
	}

	public ServiceProfileLtsDrgService getServiceProfileLtsDrgService() {
		return serviceProfileLtsDrgService;
	}

	public void setServiceProfileLtsDrgService(
			ServiceProfileLtsDrgService serviceProfileLtsDrgService) {
		this.serviceProfileLtsDrgService = serviceProfileLtsDrgService;
	}

	public CodeLkupCacheServiceImpl getLtsAppointmentCutOffLkupCacheService() {
		return ltsAppointmentCutOffLkupCacheService;
	}

	public void setLtsAppointmentCutOffLkupCacheService(
			CodeLkupCacheServiceImpl ltsAppointmentCutOffLkupCacheService) {
		this.ltsAppointmentCutOffLkupCacheService = ltsAppointmentCutOffLkupCacheService;
	}

	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}

	public BomOrderStatusSynchService getBomOrderStatusSynchService() {
		return bomOrderStatusSynchService;
	}

	public void setBomOrderStatusSynchService(
			BomOrderStatusSynchService bomOrderStatusSynchService) {
		this.bomOrderStatusSynchService = bomOrderStatusSynchService;
	}
	
	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	
	public LtsSRDDTO getStandaloneVasEarliestSRD(OrderCaptureDTO orderCapture){
		String sysdate = LtsDateFormatHelper.getSysDate("dd/MM/yyyy");
		LtsSRDDTO ftgSRD = new LtsSRDDTO(sysdate); 
		if(!addExtraLeadTime(orderCapture, 0, ftgSRD, false)){
			ftgSRD.setInfo("Free To Go");
		}
		return ftgSRD;
	}
	
	private boolean isPeBasketSelected(OrderCaptureDTO orderCapture) {
		if(orderCapture.getLtsBasketSelectionForm() != null){
			BasketDetailDTO selectedBasket = ltsBasketService.getBasket(
					orderCapture.getLtsBasketSelectionForm().getSelectedBasketId(),
					LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
			
			return "Y".equals(selectedBasket.getPeInd());
		}
		
		return false;
	}
	
	private String getBasketEarliestSRD(OrderCaptureDTO orderCapture) {
		
		BasketDetailDTO selectedBasket = ltsBasketService.getBasket(
				orderCapture.getLtsBasketSelectionForm().getSelectedBasketId(),
				LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
		
		String existTpCatg = LtsSbOrderHelper.getExistTpCatg(orderCapture.getLtsServiceProfile());
		double remainContractMth = LtsSbOrderHelper.getRemainTpContractMth(orderCapture.getLtsServiceProfile());
		List<RenewBasketPolicyDTO> renewBasketPolicyList = ltsBasketService.getRenewBasketPolicyList(orderCapture.getBasketChannelId(), existTpCatg, selectedBasket.getBasketCatg(), String.valueOf(remainContractMth));

		if (renewBasketPolicyList == null || renewBasketPolicyList.isEmpty()) {
			return null;
		}

		return renewBasketPolicyList.get(0).getEarliestSrd();
	}
	
	public LtsSRDDTO getRetEarliestSRD(OrderCaptureDTO orderCapture, boolean isFieldVisitRequired) {
		
		String sysdate = LtsAppointmentHelper.getToday();
		String contractEndDate = orderCapture.getSbOrder() != null ? LtsSbOrderHelper.getProfileContractEndDate(orderCapture.getSbOrder())
				: LtsSbOrderHelper.getProfileContractEndDate(orderCapture.getLtsServiceProfile());
		
		ArrayList<LtsSRDDTO> sRDInfos = new ArrayList<LtsSRDDTO>();
		
		if(orderCapture.getLtsCustomerIdentificationForm() != null 
				&& LtsConstant.DOC_TYPE_PASSPORT.equals(orderCapture.getLtsCustomerIdentificationForm().getDocType())
				&& orderCapture.getLtsPaymentForm() != null
				&& LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(orderCapture.getLtsPaymentForm().getNewPayMethodType())
				&& orderCapture.getLtsPaymentForm().getPrePayItemC() != null
				&& orderCapture.getLtsPaymentForm().getPrePayItemC().isSelected()){
			sRDInfos.add(getPassportWithPrepaymentSrd(sysdate));
		}
		
		// BACK DATE ORDER
		if (orderCapture.getLtsMiscellaneousForm() != null 
				&& orderCapture.getLtsMiscellaneousForm().isBackDateOrder()
				&& StringUtils.isNotEmpty(orderCapture.getLtsMiscellaneousForm().getBackDate())) {
			
			String backDate = orderCapture.getLtsMiscellaneousForm().getBackDate();
			
			if (backDate.length() != 10) {
				backDate = LtsDateFormatHelper.reformatDate(orderCapture.getLtsMiscellaneousForm().getBackDate(), "dd/MM/yyyy HH:mm", "dd/MM/yyyy");
			}

			LtsSRDDTO backDateSRD = new LtsSRDDTO(backDate);
			backDateSRD.setInfo("Back Date Order");
			backDateSRD.setBackDateAppln(true);
			sRDInfos.add(backDateSRD);
		} else {
		
			
			if (StringUtils.isEmpty(contractEndDate) || LtsDateFormatHelper.isDateOverdue(contractEndDate, "dd/MM/yyyy")) {
				// FTG
				LtsSRDDTO ftgSRD = new LtsSRDDTO(sysdate); 
				if (!addExtraLeadTime(orderCapture, 0, ftgSRD, isFieldVisitRequired)) {
					ftgSRD.setInfo("Free To Go");
				}
				sRDInfos.add( ftgSRD );
			}
	
			String basketEarliestSrd = getBasketEarliestSRD(orderCapture);
			
			if (StringUtils.isNotEmpty(basketEarliestSrd)) {
				if (StringUtils.equals("0", basketEarliestSrd)) {
					// FTG
					LtsSRDDTO ftgSRD = new LtsSRDDTO(sysdate); 
					if (!addExtraLeadTime(orderCapture, 0, ftgSRD, isFieldVisitRequired)) {
						ftgSRD.setInfo("Free To Go");
					}
					sRDInfos.add(ftgSRD);	
				}
				
				if (StringUtils.containsIgnoreCase(basketEarliestSrd, "TP+")) {
					
					int dayDiff = LtsDateFormatHelper.getDateDiffInDays(sysdate, contractEndDate, "dd/MM/yyyy");
					String addDate = StringUtils.defaultIfEmpty(StringUtils.removeStartIgnoreCase(basketEarliestSrd, "TP+"), "1");
					LtsSRDDTO eariliestSRD = new LtsSRDDTO(sysdate);
					int leadTime = dayDiff + Integer.parseInt(addDate);
					if (!addExtraLeadTime(orderCapture, leadTime, eariliestSRD, isFieldVisitRequired)) {
						eariliestSRD.setInfo("Contract End Date + " + addDate);
						eariliestSRD.addDate(leadTime);	
					}
						
					sRDInfos.add(eariliestSRD);
				}
			}
			
			
		}

		if(sRDInfos == null || sRDInfos.size() == 0){
			// Default contract end date + 1
			LtsSRDDTO eariliestSRD = new LtsSRDDTO(contractEndDate);
			int dayDiff = LtsDateFormatHelper.getDateDiffInDays(sysdate, contractEndDate, "dd/MM/yyyy");
			int leadTime = dayDiff + 1;
			if (!addExtraLeadTime(orderCapture, leadTime, eariliestSRD, isFieldVisitRequired)) {
				eariliestSRD.setInfo("Contract End Date + 1");
				eariliestSRD.addDate(1);		
			}
			sRDInfos.add(eariliestSRD);
		}
		
		LtsSRDDTO earliestSRDInfo = Collections.max(sRDInfos);
		return earliestSRDInfo;
		
	}
	
	// Subc FFP T+2
	private boolean addExtraLeadTime(OrderCaptureDTO orderCapture, int maxLeadTime, LtsSRDDTO eariliestSRD, boolean isFieldVisitRequired) {
		
		if (isFieldVisitRequired) {
			return addEyeFieldServiceLeadTime(maxLeadTime, eariliestSRD);
		}
		
		if (orderCapture.getLtsMiscellaneousForm().isRecontract()
				&& orderCapture.getLtsRecontractForm() != null) {
			return addRecontractLeadTime(maxLeadTime, eariliestSRD);
		}
		
		if (orderCapture.getLtsMiscellaneousForm().isDnChange()
				&& (orderCapture.getLtsDnChangeForm() != null || orderCapture.getLtsDnChangeDuplexForm() != null)) {
			return addDnChangeLeadTime(maxLeadTime, eariliestSRD);
		}
		
		if (orderCapture.getSbOrder() != null) {
			ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
			if (ArrayUtils.isNotEmpty(ltsService.getItemDtls())) {
				for (ItemDetailLtsDTO subcItem : ltsService.getItemDtls()) {
					if (LtsConstant.ITEM_TYPE_FFP_HOT.equals(subcItem.getItemType())
							|| LtsConstant.ITEM_TYPE_FFP_OTHER.equals(subcItem.getItemType())
							|| LtsConstant.ITEM_TYPE_FFP_STAFF.equals(subcItem.getItemType())
							|| LtsConstant.ITEM_TYPE_VAS_FFP.equals(subcItem.getItemType())) {
						return addSubcFfpLeadTime(maxLeadTime, eariliestSRD);
					}
				}
				
			}
		}
		if (orderCapture.getLtsBasketVasDetailForm() != null) {
			if (ltsOfferService.isItemSelected(orderCapture.getLtsBasketVasDetailForm().getFfpHotItemList(), LtsConstant.ITEM_TYPE_FFP_HOT)
					|| ltsOfferService.isItemSelected(orderCapture.getLtsBasketVasDetailForm().getFfpOtherItemList(), LtsConstant.ITEM_TYPE_FFP_OTHER)
					|| ltsOfferService.isItemSelected(orderCapture.getLtsBasketVasDetailForm().getFfpStaffItemList(), LtsConstant.ITEM_TYPE_FFP_STAFF)
					|| ltsOfferService.isItemSetSelected(orderCapture.getLtsBasketVasDetailForm().getBundleVasSetList(), LtsConstant.ITEM_TYPE_VAS_FFP)) {
				return addSubcFfpLeadTime(maxLeadTime, eariliestSRD);
			}	
			
			// Cancel Profile Vas LeadTime
			if (orderCapture.getLtsBasketVasDetailForm().getProfileExistingVasItemList() != null
					&& !orderCapture.getLtsBasketVasDetailForm().getProfileExistingVasItemList().isEmpty()) {
				List<ItemDetailProfileLtsDTO> profileVasItemList = orderCapture.getLtsBasketVasDetailForm().getProfileExistingVasItemList();
				
				if (profileVasItemList != null && !profileVasItemList.isEmpty()) {
					for (ItemDetailProfileLtsDTO itemDetailProfileLts : profileVasItemList) {
						if (itemDetailProfileLts.getItemDetail() != null
								&& itemDetailProfileLts.getItemDetail().isSelected()) {
							return addCancelProfileVasLeadTime(maxLeadTime, eariliestSRD);
						}
					}	
				}	
			}
			
			
			// Cancel Mirror LeadTime
			if (orderCapture.getLtsBasketVasDetailForm().getImsProfileExistingItemList() != null
					&& !orderCapture.getLtsBasketVasDetailForm().getImsProfileExistingItemList().isEmpty()) {
				List<ItemDetailProfileLtsDTO> imsProfileItemList = orderCapture.getLtsBasketVasDetailForm().getImsProfileExistingItemList();
				
				if (imsProfileItemList != null && !imsProfileItemList.isEmpty()) {
					for (ItemDetailProfileLtsDTO itemDetailProfileLts : imsProfileItemList) {
						if (itemDetailProfileLts.getItemDetail() != null
								&& (LtsConstant.ITEM_TYPE_MIRROR_PLAN.equals(itemDetailProfileLts.getItemType()) 
										|| LtsConstant.ITEM_TYPE_EXPIRED_MIRROR_PLAN.equals(itemDetailProfileLts.getItemType()))
								&& itemDetailProfileLts.getItemDetail().isSelected()) {
							return addCancelMirrorLeadTime(maxLeadTime, eariliestSRD);
						}
					}	
				}	
			}
		}
		
		
		return false;
	}
	
	// Cancel Profile VAS T+2
	private boolean addCancelProfileVasLeadTime(int maxLeadTime, LtsSRDDTO eariliestSRD) {
		if (maxLeadTime < 2) {
			eariliestSRD.setInfo("Cancel Profile VAS T+2");
			eariliestSRD.addDate(2);
			return true;
		}
		return false;
	}
	
	// Cancel Profile VAS T+2
	private boolean addCancelMirrorLeadTime(int maxLeadTime, LtsSRDDTO eariliestSRD) {
		if (maxLeadTime < 2) {
			eariliestSRD.setInfo("Cancel Mirror T+2");
			eariliestSRD.addDate(2);
			return true;
		}
		return false;
	}
	
	
	// eye new device T+2
	private boolean addEyeFieldServiceLeadTime(int maxLeadTime, LtsSRDDTO eariliestSRD) {
		if (maxLeadTime < 2) {
			eariliestSRD.setInfo("Field visit for eye device T+2");
			eariliestSRD.addDate(2);
			return true;
		}
		return false;
	}
	
	
	// Subc FFP T+2
	private boolean addSubcFfpLeadTime(int maxLeadTime, LtsSRDDTO eariliestSRD) {
		if (maxLeadTime < 2) {
			eariliestSRD.setInfo("Subscribed Fixed Fee Plan T+2");
			eariliestSRD.addDate(2);
			return true;
		}
		return false;
	}
	
	// Recontract T+2
	private boolean addRecontractLeadTime(int maxLeadTime, LtsSRDDTO eariliestSRD) {
		if (maxLeadTime < 2) {
			eariliestSRD.setInfo("Recontract Order T+2");
			eariliestSRD.addDate(2);
			return true;
		}
		return false;
	}
	
	// DN Change T+2
	private boolean addDnChangeLeadTime(int maxLeadTime, LtsSRDDTO eariliestSRD) {
		if (maxLeadTime < 2) {
			eariliestSRD.setInfo("DN Change Order T+2");
			eariliestSRD.addDate(2);
			return true;
		}
		return false;
	}
	
	/*
	 * default: 
	 *  date = today
	 *  er = addrRolloutForm exist? addrRolloutForm.isEr : false
	 *  amend = false
	 * */
	public LtsSRDDTO getEarliestSRD(OrderCaptureDTO pOrderCaptureDTO){
		if(pOrderCaptureDTO.getLtsAddressRolloutForm() == null){
			return getEarliestSRD(pOrderCaptureDTO, false, LtsAppointmentHelper.getToday(), null, false);
		}else{
			return getEarliestSRD(pOrderCaptureDTO, pOrderCaptureDTO.getLtsAddressRolloutForm().isExternalRelocate(), LtsAppointmentHelper.getToday(), null, false);
		}
	}
	
	/*
	 * default: 
	 *  er = addrRolloutForm exist? addrRolloutForm.isEr : false
	 *  amend = false
	 * */
	public LtsSRDDTO getEarliestSRD(OrderCaptureDTO pOrderCaptureDTO, String pAppDate, boolean isWorkQueue){
		if(pOrderCaptureDTO.getLtsAddressRolloutForm() == null){
			return getEarliestSRD(pOrderCaptureDTO, false, pAppDate, null, isWorkQueue);
		}else{
			return getEarliestSRD(pOrderCaptureDTO, pOrderCaptureDTO.getLtsAddressRolloutForm().isExternalRelocate(), pAppDate, null, isWorkQueue);
		}
	}

	/*
	 * default: 
	 *  date = today
	 *  amend = false
	 * */
	public LtsSRDDTO getEarliestSRD(OrderCaptureDTO pOrderCaptureDTO, boolean pErInd){
		return getEarliestSRD(pOrderCaptureDTO, pErInd, LtsAppointmentHelper.getToday(), null, false);
	}
	
	public LtsSRDDTO getEarliestSRD(OrderCaptureDTO pOrderCaptureDTO, boolean pErInd, String pAppDate, String pOCID, boolean isWorkQueue) {
		
		ArrayList<LtsSRDDTO> sRDInfos = new ArrayList<LtsSRDDTO>();
		LtsSRDDTO startDate = new LtsSRDDTO(pAppDate); //new LtsSRDDTO(getToday());
		startDate.setInfo("");
		calculatePendingOrder(startDate, pOrderCaptureDTO, pOCID);
		String startDateString = startDate.getDateString();
		String deviceType = null;
		if(pOrderCaptureDTO.getLtsDeviceSelectionForm() != null){
			deviceType = pOrderCaptureDTO.getLtsDeviceSelectionForm().getSelectedDeviceType();
		}
//		boolean isDnChange = pOrderCaptureDTO.getLtsMiscellaneousForm() != null && pOrderCaptureDTO.getLtsMiscellaneousForm().isDnChange();
		
		int fieldPermit = isFieldPermitRequired(pOrderCaptureDTO);
		BmoPermitDetail bmoPermit = getBmoPermitLeadDays(pOrderCaptureDTO, startDateString);
		if(bmoPermit != null && !StringUtils.equals("0", bmoPermit.getPermitLeadDays())){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString);
			int permetLeadDays = Integer.parseInt(bmoPermit.getPermitLeadDays());
			srd.setLeadTime(permetLeadDays);
			srd.setInfo("Field Permit Required ");
			pOrderCaptureDTO.getLtsAppointmentForm().setBmoAlertMsg(LtsSbOrderHelper.newLineHtmlFilter(LtsSbOrderHelper.quotationFilter(bmoPermit.getAlertMsg())));
			pOrderCaptureDTO.getLtsAppointmentForm().setBmoRemark(LtsSbOrderHelper.newLineHtmlFilter(LtsSbOrderHelper.quotationFilter(bmoPermit.getBmoRemark())));
			srd.setReasonCd("01");
			sRDInfos.add(srd);
		}else
		if(fieldPermit != 0){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString);
			srd.setInfo("Field Permit Required ");
			srd.setReasonCd("01");
			srd.setLeadTime(fieldPermit);
			
			sRDInfos.add(srd);
		} 
		//UGRADE + RECONTRACT case T+5   ----Modified by Max.R.MENG
		if(pOrderCaptureDTO.getLtsMiscellaneousForm() != null && pOrderCaptureDTO.getLtsMiscellaneousForm().isRecontract()){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString);
			srd.setLeadTime(5);
			srd.setInfo("Recontract case ");
			srd.setReasonCd("52");
			sRDInfos.add(srd);
		}
		if(is2NPortIn(pOrderCaptureDTO)){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString);
			LtsAppointmentHelper.pushWorkingDays(srd, 7, true, this.getPublicHolidayList());
			srd.setInfo("2N Port In ");
			srd.setReasonCd("06");
			sRDInfos.add(srd);
		}
		//BUILD3.200 remove ims blacklist checking 
		/*if(isIMSBlacklistAddress(pOrderCaptureDTO)){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString);
			srd.setLeadTime(30);
			srd.setInfo("IMS Blacklist Address ");
			srd.setReasonCd("02");
			sRDInfos.add(srd);
		}else */
		if(isLTSBlacklistAddress(pOrderCaptureDTO)){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString);
			srd.setLeadTime(30);
			srd.setInfo("LTS Blacklist Address ");
			srd.setReasonCd("08");
			sRDInfos.add(srd);
		}else if(pErInd && pOrderCaptureDTO.getLtsServiceProfile().getPrimaryCust().isBlacklistCustInd()){
				LtsSRDDTO srd = new LtsSRDDTO(startDateString);
				srd.setLeadTime(30);
				srd.setInfo("External Relocate and Blacklist Customer ");
				srd.setReasonCd("07");
				sRDInfos.add(srd);
		}else if(is2NBW(pOrderCaptureDTO)){
			String assignedTec = null;
			String imsOrderType = null;
			String existingFsaTec = null;
			
			if(pOrderCaptureDTO.getModemTechnologyAissgn() != null){
				assignedTec = pOrderCaptureDTO.getModemTechnologyAissgn().getTechnology();
				imsOrderType = pOrderCaptureDTO.getModemTechnologyAissgn().getImsOrderType();
			}
			if(LtsConstant.ORDER_TYPE_CHANGE.equals(imsOrderType)){
				FsaDetailDTO selectedFsa = LtsSbOrderHelper.getSelectedFsa(pOrderCaptureDTO.getLtsModemArrangementForm());
				existingFsaTec = selectedFsa.getTechnology();
			}
			
			if(LtsSbOrderHelper.isSipServiceProfile(pOrderCaptureDTO.getLtsServiceProfile())
					&& isPeBasketSelected(pOrderCaptureDTO)){
				LtsSRDDTO srd = new LtsSRDDTO(startDateString);
				LtsAppointmentHelper.pushWorkingDays(srd, 14, false, this.getPublicHolidayList());
				srd.setInfo("2NBW and PE Basket selected for SIP ");
				srd.setReasonCd("54");
				sRDInfos.add(srd);
			}
			if(!StringUtils.equals(assignedTec, existingFsaTec)){ /*Have change*/
				if(!StringUtils.equals(assignedTec, LtsConstant.TECHNOLOGY_PON)){ /*Ignore if assgn tec = PON*/
					LtsSRDDTO srd = new LtsSRDDTO(startDateString);
					if(LtsConstant.TECHNOLOGY_ADSL.equals(existingFsaTec)){ 	/*Exist = A and upgraded to V */
						LtsAppointmentHelper.pushWorkingDays(srd, 14, false, this.getPublicHolidayList());
						srd.setInfo("2NBW and Technology Upgrade ");
						srd.setReasonCd("53");
					}else if(isVoiceSharePCD(pOrderCaptureDTO)){
						LtsAppointmentHelper.pushWorkingDays(srd, 14, false, this.getPublicHolidayList());
						srd.setInfo("2NBW and Voice Share PCD ");
						srd.setReasonCd("04");
					}else if(pErInd){
						LtsAppointmentHelper.pushWorkingDays(srd, 14, false, this.getPublicHolidayList());
						srd.setInfo("External Relocate and 2NBW ");
						srd.setReasonCd("03");
					}else {
						LtsAppointmentHelper.pushWorkingDays(srd, 14, false, this.getPublicHolidayList());
						srd.setInfo("2NBW and Not Voice Share PCD ");
						srd.setReasonCd("05");
					}
					sRDInfos.add(srd);
				}
			}
		}else if (pErInd){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString);
			srd.setInfo("External Relocate ");
			srd.setLeadTime(2);
			srd.setReasonCd("09");
			sRDInfos.add(srd);
		}
//		else if (isDnChange) {
//			LtsSRDDTO srd = new LtsSRDDTO(startDateString);
//			srd.setInfo("DN Change Order ");
//			srd.setLeadTime(2);
//			sRDInfos.add(srd);
//		}
		
		if(pOrderCaptureDTO.getLtsCustomerIdentificationForm() != null 
				&& LtsConstant.DOC_TYPE_PASSPORT.equals(pOrderCaptureDTO.getLtsCustomerIdentificationForm().getDocType())
				&& pOrderCaptureDTO.getLtsPaymentForm() != null
				&& LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(pOrderCaptureDTO.getLtsPaymentForm().getNewPayMethodType())
				&& pOrderCaptureDTO.getLtsPaymentForm().getPrePayItemC() != null
				&& pOrderCaptureDTO.getLtsPaymentForm().getPrePayItemC().isSelected()){
			sRDInfos.add(getPassportWithPrepaymentSrd(startDateString));
		}
		
		sRDInfos.addAll(getNormalLeadTime(startDateString, deviceType, StringUtils.isNotBlank(pOCID),
					pOrderCaptureDTO.isChannelCs() || pOrderCaptureDTO.isChannelPremier()));
		
		//find largest
		LtsSRDDTO earliestSRDInfo = Collections.max(sRDInfos);
		
		if(isWorkQueue){
			int wqLeadtime = getWqLeadtime();
			if(wqLeadtime > 0){
				earliestSRDInfo.addLeadTime(wqLeadtime);
				earliestSRDInfo.setInfo(earliestSRDInfo.getInfo()+" & Workqueue leadtime ");
			}
		}

		earliestSRDInfo.setInfo(startDate.getInfo().concat(earliestSRDInfo.getInfo()));
		earliestSRDInfo.setBmoPermit(bmoPermit);
		return earliestSRDInfo;
	}       

	public LtsSRDDTO getSecDelEarliestSRD(OrderCaptureDTO orderCaptureDTO, String pAppDate, String pOCID) {
		
		orderCaptureDTO.getLtsOtherVoiceServiceForm();
		LtsSRDDTO startDate = new LtsSRDDTO(pAppDate);
		startDate.setInfo("");
		calculatePendingOrder(startDate, orderCaptureDTO, pOCID);
		if(isNewSecDel2NBW(orderCaptureDTO)){
			startDate.setInfo("New 2nd Del 2NBW ");
			startDate.setReasonCd("04");
//			pushWorkingDays(startDate, 14, false);
			LtsAppointmentHelper.pushWorkingDays(startDate, 14, false, this.getPublicHolidayList());
		}
		else if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCaptureDTO.getOrderType())) {
			startDate.setInfo("2nd DEL Order T+2 ");
			startDate.addDate(2);
		}
		return startDate;
	}
	
	private int getWqLeadtime(){
		try {
			return Integer.parseInt((String)ltsAppointmentCutOffLkupCacheService.get("WQ_LEADTIME"));
		} catch (Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	private LtsSRDDTO getPassportWithPrepaymentSrd(String pAppDate){
		int leadtime = 0;
		try {
			leadtime = Integer.parseInt((String)ltsAppointmentCutOffLkupCacheService.get("PASSPORT_PREPAY_CASE"));
		} catch (Exception e){
			e.printStackTrace();
		}
		LtsSRDDTO srd = new LtsSRDDTO(pAppDate, leadtime, "55");
		return srd;
	}
	
	public List<LtsSRDDTO> getNormalLeadTime(String pAppDate, String deviceType, boolean isAmendment, boolean isCCMode){
		String cutOffTimeString = null;
		Calendar cutOffTime = null;
		int defaultLeadTime = 0, beforeCutOff = 0, afterCutOff = 0, eye3aLeadTime = 0, ccModeLeadTime = 0;
		try {
			for(LookupItemDTO cutOffLkup : ltsAppointmentCutOffLkupCacheService.getCodeLkupDAO().getCodeLkup()){
				if("CUT_OVER_TIME".equals(cutOffLkup.getItemKey())){
					cutOffTimeString = (String) cutOffLkup.getItemValue();
				}else if("DEFAULT".equals(cutOffLkup.getItemKey())){
					defaultLeadTime = Integer.parseInt((String)cutOffLkup.getItemValue());
				}else if("BEFORE_CUT_OVER".equals(cutOffLkup.getItemKey())){
					beforeCutOff = Integer.parseInt((String)cutOffLkup.getItemValue());
				}else if("AFTER_CUT_OVER".equals(cutOffLkup.getItemKey())){
					afterCutOff = Integer.parseInt((String)cutOffLkup.getItemValue());
				}else if("EYE3A_NORMAL".equals(cutOffLkup.getItemKey())){
					eye3aLeadTime = Integer.parseInt((String)cutOffLkup.getItemValue());;
				}else if("CC_MODE_NORMAL".equals(cutOffLkup.getItemKey())){
					ccModeLeadTime = Integer.parseInt((String)cutOffLkup.getItemValue());;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		List<LtsSRDDTO> srdList = new ArrayList<LtsSRDDTO>();
		
		if(StringUtils.equals(deviceType, LtsConstant.DEVICE_TYPE_EYE3A) && eye3aLeadTime > 0){
			LtsSRDDTO eye3aNormalLeadTimeSrd = new LtsSRDDTO(pAppDate);
			eye3aNormalLeadTimeSrd.setInfo("EYE3A Normal Lead Time ");
			eye3aNormalLeadTimeSrd.setReasonCd("30");
			eye3aNormalLeadTimeSrd.setLeadTime(eye3aLeadTime);
			srdList.add(eye3aNormalLeadTimeSrd);
		}
		if(isCCMode && ccModeLeadTime > 0){
			LtsSRDDTO ccModeSrd = new LtsSRDDTO(pAppDate);
			ccModeSrd.setInfo("Call Center/Premier Team Lead Time ");
			ccModeSrd.setReasonCd("31");
			ccModeSrd.setLeadTime(ccModeLeadTime);
			srdList.add(ccModeSrd);
		}else{
			LtsSRDDTO normalLeadTimeSrd = new LtsSRDDTO(pAppDate);
			Calendar now = new GregorianCalendar();
			if(isAmendment){
				normalLeadTimeSrd.setInfo("Amendment Lead Time ");
			}else{
				normalLeadTimeSrd.setInfo("Normal Lead Time ");
			}

			if(!StringUtils.isBlank(cutOffTimeString)){
				int hr = Integer.parseInt(cutOffTimeString.split(":")[0]);
				int min = Integer.parseInt(cutOffTimeString.split(":")[1]);
				cutOffTime = new GregorianCalendar(0, 0, 0, hr, min);
				if(now.get(Calendar.HOUR_OF_DAY) < cutOffTime.get(Calendar.HOUR_OF_DAY)){
					if(now.get(Calendar.MINUTE) < cutOffTime.get(Calendar.MINUTE)){
						normalLeadTimeSrd.setLeadTime(beforeCutOff);
					}else{
						normalLeadTimeSrd.setLeadTime(afterCutOff);
					}
				}else{
					normalLeadTimeSrd.setLeadTime(afterCutOff);
				}
			}else{
				normalLeadTimeSrd.setLeadTime(defaultLeadTime);
			}
			
			normalLeadTimeSrd.setReasonCd("00");
			srdList.add(normalLeadTimeSrd);
		}
		return srdList;
	}
	
	private void calculatePendingOrder(LtsSRDDTO srd, OrderCaptureDTO orderCaptureDTO, String pOCID){
		
		Date pendingOrder = null;
		Date ltsPendingOrderDate = null;
		Date imsPendingOrderDate = null;
		String ltsPendingOrderId = null; 
		String imsPendingOrderId = null;
		String pendingOrderId = null; 
		
		df.applyPattern("dd/MM/yyyy");
		
		PendingOrdStatusDetailDTO pendingOrderDTO = this.serviceProfileLtsDrgService.getPendingOrder(orderCaptureDTO.getLtsServiceProfile().getSrvNum(), orderCaptureDTO.getLtsServiceProfile().getSrvType());
		if(pendingOrderDTO != null){
			String dateString = pendingOrderDTO.getSrd();
			ltsPendingOrderId = pendingOrderDTO.getOcid();
			if(!StringUtils.equals(pOCID, ltsPendingOrderId)){
				if(!StringUtils.isBlank(dateString)){
					try {
						ltsPendingOrderDate = df.parse(dateString);
					} catch (ParseException pe) {
						pe.printStackTrace();
					}
				}
			}else{
				ltsPendingOrderDate = null;
			}
		}
		
		if(orderCaptureDTO.getLtsModemArrangementForm() != null && orderCaptureDTO.getModemTechnologyAissgn() != null){
			if(!StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, orderCaptureDTO.getModemTechnologyAissgn().getModemArrangment())){
				FsaDetailDTO fsa = getSelectedFsa(orderCaptureDTO);
				if(fsa != null){
					PendingOrdStatusDetailDTO imsPendingOrderDTO = imsProfileService.getPendingOrder(Integer.toString(fsa.getFsa()));
					if(imsPendingOrderDTO != null){
						String dateString = imsPendingOrderDTO.getSrd();
						imsPendingOrderId = imsPendingOrderDTO.getOcid();
						if(!StringUtils.equals(pOCID, imsPendingOrderId)){
							try {
								df.applyPattern("dd/MM/yyyy");
								imsPendingOrderDate = df.parse(dateString);
							} catch (ParseException pe) {
								pe.printStackTrace();
							}
						}
					}
				}
			}else{
				imsPendingOrderDate = null;
			}
		}
		
		if(ltsPendingOrderDate != null){
			if(imsPendingOrderDate != null){
				if(ltsPendingOrderDate.after(imsPendingOrderDate)){
					pendingOrder = ltsPendingOrderDate;
					pendingOrderId = ltsPendingOrderId;
				}else{
					pendingOrder = imsPendingOrderDate;
					pendingOrderId = imsPendingOrderId;
				}
			}else{
				pendingOrder = ltsPendingOrderDate;
				pendingOrderId = ltsPendingOrderId;
			}
		}else{
			if(imsPendingOrderDate != null){
				pendingOrder = imsPendingOrderDate;
				pendingOrderId = imsPendingOrderId;
			}
		}
		
		if(pendingOrder != null){
			Calendar pendingOrderSRD = new GregorianCalendar();
			Calendar today = new GregorianCalendar();
			pendingOrderSRD.setTime(pendingOrder);
			if(pendingOrderSRD.compareTo(today) >= 0){
				pendingOrderSRD.add(Calendar.DATE, srd.getLeadTime());
				srd.setDate(pendingOrderSRD);
				srd.setInfo("PendingOrder ("
						+ pendingOrderId
						+ ") Exist + 2 days "
						+ "& ".concat(srd.getInfo()));
				srd.addDate(2);
			}
		}
	}
	
	public boolean isBBShortage(OrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getSbOrder() != null
				&& checkImsL1Status(orderCaptureDTO.getSbOrder())){
			return false;
		}
		
		if(orderCaptureDTO.getModemTechnologyAissgn() != null){
			if(orderCaptureDTO.getModemTechnologyAissgn().isBbShortage()){
				String newTech = orderCaptureDTO.getModemTechnologyAissgn().getTechnology();
				FsaDetailDTO selectedFsa = getSelectedFsa(orderCaptureDTO);
				if(selectedFsa != null){
					boolean isFsaEr = orderCaptureDTO.getLtsModemArrangementForm().isExistingFsaER()
							|| orderCaptureDTO.getLtsModemArrangementForm().isOtherFsaER();
					String originalTech = selectedFsa.getTechnology();
					if(StringUtils.equals(newTech, originalTech) && !isFsaEr){
						return false;
					}else{
						return true;
					}
				}
			}
			return orderCaptureDTO.getModemTechnologyAissgn().isBbShortage();
		}
		return false;
	}
	
	private boolean is2NBW(OrderCaptureDTO orderCaptureDTO){
		try{
			if(orderCaptureDTO.getNewAddressRollout() != null){
				return orderCaptureDTO.getNewAddressRollout().isIs2nBuilding();
			}else{
				return orderCaptureDTO.getLtsServiceProfile().getAddress().getAddressRollout().isIs2nBuilding();
			}
		}catch(NullPointerException npe){
			npe.printStackTrace();
			return false;
		}
	}
	
	private boolean isNewSecDel2NBW(OrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getLtsServiceProfile().getDuplexNum() != null){ //is duplex
			DuplexSrvType dnXSrvType, dnYSrvType;
			dnXSrvType = orderCaptureDTO.getLtsOtherVoiceServiceForm().getDuplexXSrvType();
			dnYSrvType = orderCaptureDTO.getLtsOtherVoiceServiceForm().getDuplexYSrvType();
			
			if((DuplexSrvType.NEW_2ND_DEL.equals(dnXSrvType))
					|| DuplexSrvType.NEW_2ND_DEL.equals(dnYSrvType)){ //duplex as second del
				return is2NBW(orderCaptureDTO);
			}
		}else{ // is not duplex
			if(LtsConstant.INVENT_STS_RESERVED.equals(orderCaptureDTO.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())
					|| LtsConstant.INVENT_STS_PRE_ASSIGN.equals(orderCaptureDTO.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())){
				return is2NBW(orderCaptureDTO);
			}else if(LtsConstant.INVENT_STS_WORKING.equals(orderCaptureDTO.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())
					&& orderCaptureDTO.getLtsOtherVoiceServiceForm().isSecondDelDiffAddress()){
				if(orderCaptureDTO.getLtsOtherVoiceServiceForm().getSecondDelConfirmSameIa() != null
						&& orderCaptureDTO.getLtsOtherVoiceServiceForm().getSecondDelConfirmSameIa()){
					return is2NBW(orderCaptureDTO);
				}
				return is2NBW(orderCaptureDTO);
			}
		}
		
		return false;
	}
	
	private boolean isVoiceSharePCD(OrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getLtsServiceProfile().isSharedBsn()){
			return true;
		}
		return false;
	}
	
	private boolean is2NPortIn(OrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getLtsServiceProfile().isTwoNBuildInd()
				|| orderCaptureDTO.getLtsServiceProfile().isDuplexTwoNBuildInd()){
			return true;
		}
		return false;
	}
	
	private int isFieldPermitRequired(OrderCaptureDTO orderCaptureDTO){
		int fieldPermit = 0;
		if(orderCaptureDTO.getNewAddressRollout() != null){
			String leadtime = orderCaptureDTO.getNewAddressRollout().getFieldWorkPermit();
			if(!StringUtils.isBlank(leadtime) && StringUtils.isNumeric(leadtime)){
				fieldPermit = Integer.parseInt(leadtime);
			}
		}else if(orderCaptureDTO.getLtsServiceProfile().getAddress().getAddressRollout() != null){
			String leadtime = orderCaptureDTO.getLtsServiceProfile().getAddress().getAddressRollout().getFieldWorkPermit();
			if(!StringUtils.isBlank(leadtime) && StringUtils.isNumeric(leadtime)){
				fieldPermit = Integer.parseInt(leadtime);
			}
		}
		return fieldPermit;
	}
	
	private BmoPermitDetail getBmoPermitLeadDays(OrderCaptureDTO orderCaptureDTO, String appDate){
		ResourceDetailInputDTO input;
		try{
			input = getResourceDetailInput(orderCaptureDTO, appDate, 
					LtsConstant.DWFM_INPUT_TYPE_ALL);
		}catch(Exception e){
			return null;
		}
		BmoDetailOutput output= appointmentDwfmService.getBmoPermits(input);
//		int largestLeadDay = 0;
		if(output != null && output.getBmoDtls() != null && output.getBmoDtls().length > 0){
			BmoPermitDetail largestBmoLeadDay = output.getBmoDtls()[0];
			for(int i = 0; i < output.getBmoDtls().length; i++){
				int largestLeadDay = Integer.parseInt(largestBmoLeadDay.getPermitLeadDays());
				int currentLeadDay = Integer.parseInt(output.getBmoDtls()[i].getPermitLeadDays());
				largestBmoLeadDay = largestLeadDay > currentLeadDay? largestBmoLeadDay: output.getBmoDtls()[i];
			}
			return largestBmoLeadDay;
		}
		return null;
	}
	
	private boolean isIMSBlacklistAddress(OrderCaptureDTO orderCaptureDTO){
		/*ignore IMS blacklist if use existing fsa*/
		if(orderCaptureDTO.getLtsModemArrangementForm() != null
				&& ModemType.SHARE_EX_FSA.equals(orderCaptureDTO.getLtsModemArrangementForm().getModemType())){
			return false;
		}
			
		if(orderCaptureDTO.getNewAddressRollout() != null){
			return orderCaptureDTO.getNewAddressRollout().isImsAddressBlacklist();
		}else if(orderCaptureDTO.getLtsServiceProfile() != null){
			return orderCaptureDTO.getLtsServiceProfile().getAddress().getAddressRollout().isImsAddressBlacklist();
		}
		return false;
	}

	private boolean isLTSBlacklistAddress(OrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getSbOrder() != null
				&& validateLtsLegacyStatus(orderCaptureDTO.getSbOrder())){
			return false;
		}
		if(orderCaptureDTO.getNewAddressRollout() != null){
			return orderCaptureDTO.getNewAddressRollout().isLtsAddressBlacklist();
		}else if(orderCaptureDTO.getLtsServiceProfile() != null){
			return orderCaptureDTO.getLtsServiceProfile().getAddress().getAddressRollout().isLtsAddressBlacklist();
		}
		return false;
	}
	
	public ResourceDetailInputDTO getResourceDetailInput(
			OrderCaptureDTO orderCaptureDTO, String date, String type){
		final String PROD_SUB_TYPE_CD_V = "VSO-";
		final String SRV_TYPE_CD_SO = "SO";
		String prodSubTypeCd = getProdSubTypeCd(orderCaptureDTO)[1];
		String fromProdSubTypeCd = getProdSubTypeCd(orderCaptureDTO)[0];
		
		ResourceDetailInputDTO resourceInput = new ResourceDetailInputDTO();
		
//		type = "ALL", "UPGRADE", "2NDDEL"
		List<ResourceTypeDTO> resourceTypeDTOList = new ArrayList<ResourceTypeDTO>(); 
		if(StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_ALL)
				|| StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_UPGRADE)){
			ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO();
			resourceTypeDTO.setProdSubTypeCd(prodSubTypeCd);
			resourceTypeDTO.setProdType("I");
			resourceTypeDTO.setSrvType(SRV_TYPE_CD_SO);
			resourceTypeDTO.setFromProdSubTypeCd(fromProdSubTypeCd);
			resourceTypeDTO.setOrderType(StringUtils.substring(prodSubTypeCd, 3, 4));
//			resourceTypeDTO.setOrderType(orderCaptureDTO.getModemTechnologyAissgn().getImsOrderType());

			resourceTypeDTOList.add(resourceTypeDTO);
		}
		if(StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_ALL)
				|| StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_2NDDEL)){
			if(orderCaptureDTO.getLtsOtherVoiceServiceForm().getCreate2ndDel() != null
					&& orderCaptureDTO.getLtsOtherVoiceServiceForm().getCreate2ndDel()){
				ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO();
				resourceTypeDTO.setProdSubTypeCd(PROD_SUB_TYPE_CD_V);
				resourceTypeDTO.setProdType("V");
				resourceTypeDTO.setSrvType(SRV_TYPE_CD_SO);
				resourceTypeDTO.setFromProdSubTypeCd(PROD_SUB_TYPE_CD_V);
				if(LtsConstant.INVENT_STS_RESERVED.equals(orderCaptureDTO.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())
						|| LtsConstant.INVENT_STS_PRE_ASSIGN.equals(orderCaptureDTO.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())){
					resourceTypeDTO.setOrderType(LtsConstant.ORDER_TYPE_INSTALL);
				}else if (LtsConstant.INVENT_STS_WORKING.equals(orderCaptureDTO.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())){
					resourceTypeDTO.setOrderType(LtsConstant.ORDER_TYPE_CHANGE);
				}
				resourceTypeDTOList.add(resourceTypeDTO);
			}
		}

		resourceInput.setRscTypes(resourceTypeDTOList.toArray(new ResourceTypeDTO[0]));
		
		resourceInput.setApptDate(LtsAppointmentHelper.reformatToDwfmDate(date));
		String[] addressInfo = getAreaDistrictCd(orderCaptureDTO);
		resourceInput.setArea(addressInfo[0]);
		resourceInput.setDistrict(addressInfo[1]);
		resourceInput.setAddrChangeInd(addressInfo[2]);
		resourceInput.setSrvBoundary(addressInfo[3]);
		resourceInput.setDrgPermitDays(String.valueOf(isFieldPermitRequired(orderCaptureDTO)));
		resourceInput.setSystemId("SPB");
		resourceInput.setUser(orderCaptureDTO.getLtsSalesInfoForm().getStaffId());
		return resourceInput;
	}
	
	public PrebookAppointmentInputDTO getPrebookAppointmentInput(OrderCaptureDTO orderCaptureDTO, String date, String timeSlot, String staffId, String timeSlotType){
		String prodSubTypeCd = getProdSubTypeCd(orderCaptureDTO)[1];
		String[] addrDtl = getAreaDistrictCd(orderCaptureDTO);
		String area = addrDtl[0];
		String district = addrDtl[1];
		return this.createPrebookAppointmentInput(date, timeSlot, prodSubTypeCd, area, district, staffId, timeSlotType);
	}
	
	public PrebookAppointmentInputDTO getTermPrebookAppointmentInput(TerminateOrderCaptureDTO orderCaptureDTO, String date, String timeSlot, String staffId, String timeSlotType){
		String prodSubTypeCd = getTerminateProdSubTypeCd(orderCaptureDTO)[1];
		String[] addrDtl = getTermAreaDistrictCd(orderCaptureDTO);
		String area = addrDtl[0];
		String district = addrDtl[1];
		return this.createPrebookAppointmentInput(date, timeSlot, prodSubTypeCd, area, district, staffId, timeSlotType);
	}
	
	private String[] getProdSubTypeCd(OrderCaptureDTO orderCaptureDTO){
		
		if (orderCaptureDTO.getModemTechnologyAissgn() == null) {
			return new String[]{"VSO-", null};
		}
		
		String prodSubTypeCd = null;
		String frProdSubTypeCd = null;
		
		ModemType m = orderCaptureDTO.getLtsModemArrangementForm().getModemType();
		if(StringUtils.equals(orderCaptureDTO.getModemTechnologyAissgn().getTechnology(), LtsConstant.TECHNOLOGY_PON)){
			if(ModemType.STANDALONE.equals(m)
					||  ModemType.SHARE_TV.equals(m)
					|| ModemType.SHARE_PCD.equals(m)
					|| ModemType.SHARE_BUNDLE.equals(m)){
				prodSubTypeCd = "FTHI";
				frProdSubTypeCd = "FTHI";
			}else if(ModemType.SHARE_EX_FSA.equals(m)){
				if(isInstallUpgradePon(orderCaptureDTO, true)){
					prodSubTypeCd = "FTHI";
				}else{
					prodSubTypeCd = "FTHC";
				}
				frProdSubTypeCd = "FTHC";
			}else if(ModemType.SHARE_OTHER_FSA.equals(m)){
				if(isInstallUpgradePon(orderCaptureDTO, false)){
					prodSubTypeCd = "FTHI";
				}else{
					prodSubTypeCd = "FTHC";
				}
				frProdSubTypeCd = "FTHC";
			}
		}else{
			if(ModemType.STANDALONE.equals(m)
					|| ModemType.SHARE_TV.equals(m)
					|| ModemType.SHARE_PCD.equals(m)
					|| ModemType.SHARE_BUNDLE.equals(m)){
				prodSubTypeCd = "PCDI";
				frProdSubTypeCd = "PCDI";
			}else if(ModemType.SHARE_EX_FSA.equals(m)
					|| ModemType.SHARE_OTHER_FSA.equals(m)){
				prodSubTypeCd = "PCDC";
				frProdSubTypeCd = "PCDC";
			}
		}
		
		String[] prodSubTypeCds = {prodSubTypeCd, frProdSubTypeCd};
		return prodSubTypeCds;
	}
	
//	private String[] getTermProdSubTypeCd(TerminateOrderCaptureDTO orderCaptureDTO){
//		String prodSubTypeCd = "PCDC";
//		String frProdSubTypeCd = "PCDC";
//		
//		
//		String[] prodSubTypeCds = {frProdSubTypeCd, prodSubTypeCd};
//		return prodSubTypeCds;
//	}
	
//	private String getFromProdSubTypeCd(String prodSubTypeCd){
//		if(StringUtils.endsWith(prodSubTypeCd, "I")){
//			return "";
//		}else{
//			return prodSubTypeCd;
//		}
//	}
	
	public String[] getAreaDistrictCd(OrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getLtsAddressRolloutForm().isExternalRelocate() 
				|| orderCaptureDTO.getLtsServiceProfile() == null){
			String[] s = {orderCaptureDTO.getLtsAddressRolloutForm().getAreaCode(),
					orderCaptureDTO.getLtsAddressRolloutForm().getDistrictCode(),
					"Y", orderCaptureDTO.getLtsAddressRolloutForm().getServiceBoundary()};
			return s;
		}else{
			String[] s = {orderCaptureDTO.getLtsServiceProfile().getAddress().getAreaCd(),
					orderCaptureDTO.getLtsServiceProfile().getAddress().getDistrictCd(),
					"N", orderCaptureDTO.getLtsServiceProfile().getAddress().getSrvBdry()};
			return s;
		}
	}
	
	public String[] getTermAreaDistrictCd(TerminateOrderCaptureDTO orderCaptureDTO){
		
		String[] s = {orderCaptureDTO.getLtsServiceProfile().getAddress().getAreaCd(),
				orderCaptureDTO.getLtsServiceProfile().getAddress().getDistrictCd(),
				"N", orderCaptureDTO.getLtsServiceProfile().getAddress().getSrvBdry()};
		return s;
	}
	
//	public String reformatToDwfmDate(String date){
//		df.applyPattern("dd/MM/yyyy");
//		Date a = null;
//		try {
//			a = df.parse(date);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		df.applyPattern("yyyyMMdd");
//		return df.format(a);
//	}
	
	public String[] reformatDateTimeSlot(String date, String timeSlot){
		String[] time = StringUtils.split(timeSlot, '-');
		if(time.length > 1){
			String[] s = {date.concat(" ").concat(time[0]),
					date.concat(" ").concat(time[1])};
			return s;
		}else{
			String[] s = {date, date};
			return s;
		}
	}

	public List<String> getPublicHolidayList() {
		try {
			List<String> holidayList = ltsAppointmentDAO.getHolidayList();
			return holidayList;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean getChangePonTimeSlotInd(OrderCaptureDTO orderCaptureDTO){
		if(StringUtils.equals(LtsConstant.ORDER_TYPE_SB_UPGRADE, orderCaptureDTO.getOrderType())
				&& orderCaptureDTO.getLtsModemArrangementForm() != null){
			ModemType m = orderCaptureDTO.getLtsModemArrangementForm().getModemType();
			if(ModemType.SHARE_EX_FSA.equals(m)){
				return isInstallUpgradePon(orderCaptureDTO, true);
			}
			if(ModemType.SHARE_OTHER_FSA.equals(m)){
				return isInstallUpgradePon(orderCaptureDTO, false);
			}
		}
		return false;
	}
	
	public boolean getTermChangePonTimeSlotInd(TerminateOrderCaptureDTO orderCaptureDTO){
		return false;
	}

	private boolean isInstallUpgradePon(OrderCaptureDTO orderCaptureDTO, boolean pExistFsaInd){
		boolean ponInd = StringUtils.equals(orderCaptureDTO.getModemTechnologyAissgn().getTechnology(), LtsConstant.TECHNOLOGY_PON);
		FsaDetailDTO selectedFsa = getSelectedFsa(orderCaptureDTO, pExistFsaInd);
		String fsaTechology = null;
	
		if(selectedFsa != null){
			fsaTechology = selectedFsa.getTechnology();
			if(ponInd){
				if(LtsConstant.TECHNOLOGY_PON.equals(fsaTechology)){
						if(pExistFsaInd
								? !orderCaptureDTO.getLtsModemArrangementForm().isExistingFsaER()
								: !orderCaptureDTO.getLtsModemArrangementForm().isOtherFsaER()
							){
							return false;
						}else{
							return true;
						}
				}else{
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * will check for ModemType in ModemArrangementForm
	 * return null if not share fsa
	 */
	private FsaDetailDTO getSelectedFsa(OrderCaptureDTO orderCaptureDTO){
		ModemType m = orderCaptureDTO.getLtsModemArrangementForm().getModemType();
		FsaDetailDTO selectedFsa = null;
		if(ModemType.SHARE_EX_FSA.equals(m)){
			selectedFsa = getSelectedFsa(orderCaptureDTO, true);
		}
		if(ModemType.SHARE_OTHER_FSA.equals(m)){
			selectedFsa = getSelectedFsa(orderCaptureDTO, false);
		}
		return selectedFsa;
	}
	
	private FsaDetailDTO getSelectedFsa(OrderCaptureDTO orderCaptureDTO, boolean pExistFsaInd){
		FsaDetailDTO selectedFsa = null;
		if(orderCaptureDTO.getLtsModemArrangementForm() != null){
			if(pExistFsaInd && orderCaptureDTO.getLtsModemArrangementForm().getExistingFsaList() != null){
				for(FsaDetailDTO fsa: orderCaptureDTO.getLtsModemArrangementForm().getExistingFsaList()){
					if(fsa.isSelected()){
						selectedFsa = fsa;
						break;
					}
				}
			}else if(!pExistFsaInd && orderCaptureDTO.getLtsModemArrangementForm().getOtherFsaList() != null){
				for(FsaDetailDTO fsa: orderCaptureDTO.getLtsModemArrangementForm().getOtherFsaList()){
					if(fsa.isSelected()){
						selectedFsa = fsa;
						break;
					}
				}
			}
		}
		return selectedFsa;
	}


	private boolean validateLtsLegacyStatus(SbOrderDTO sbOrder){
		if(sbOrder == null){
			return false;
		}
		ServiceDetailDTO srvDtl = LtsSbOrderHelper.getLtsService(sbOrder);
		OrderStatusSynchDTO status = getOrderStatus(sbOrder.getOrderId(), srvDtl);
		if(status == null){
			return false;
		}
		
		return StringUtils.equals("D", status.getBomLegacyStatus());
	}

	private boolean checkImsL1Status(SbOrderDTO sbOrder){
		if(sbOrder == null){
			return false;
		}
		ServiceDetailDTO srvDtl = LtsSbOrderHelper.getImsService(sbOrder);
		OrderStatusSynchDTO status = getOrderStatus(sbOrder.getOrderId(), srvDtl);
		
		if(status == null){
			return false;
		}
		
		return StringUtils.equals("D", status.getL1OrdStatus());
	}
	
	private OrderStatusSynchDTO getOrderStatus(String orderId, ServiceDetailDTO srvDtl){
		if(srvDtl == null){
			return null;
		}
		
		OrderStatusSynchDTO[] status = bomOrderStatusSynchService.getBomOrderStatus(
				orderId, srvDtl.getTypeOfSrv(), 
				srvDtl.getSrvNum(), srvDtl.getCcServiceId(), srvDtl.getCcServiceMemNum());
		for(OrderStatusSynchDTO tempStatus: status){
			if(StringUtils.equals(srvDtl.getCcServiceId(), tempStatus.getCcServiceId())){
				return tempStatus;
			}
		}
		return null;
	}
	
	
	/* =============== NEW =================*/
	
	public BmoPermitDetail getBmoPermitLeadDays(ResourceDetailInputDTO input, String appDate){
		BmoDetailOutput output= appointmentDwfmService.getBmoPermits(input);
		if(output != null && output.getBmoDtls() != null && output.getBmoDtls().length > 0){
			BmoPermitDetail largestBmoLeadDay = output.getBmoDtls()[0];
			for(int i = 0; i < output.getBmoDtls().length; i++){
				int largestLeadDay = Integer.parseInt(largestBmoLeadDay.getPermitLeadDays());
				int currentLeadDay = Integer.parseInt(output.getBmoDtls()[i].getPermitLeadDays());
				largestBmoLeadDay = largestLeadDay > currentLeadDay? largestBmoLeadDay: output.getBmoDtls()[i];
			}
			return largestBmoLeadDay;
		}
		return null;
	}

	private String[] getTerminateProdSubTypeCd(TerminateOrderCaptureDTO order){
		if(order.getLtsServiceProfile().getExistEyeType() != null){
			String[] prodSubTypeCds = {"PCDD", "PCDD"};
			return prodSubTypeCds;
		}else{
			String[] prodSubTypeCds = {"VSO-", "VSO-"};
			return prodSubTypeCds;
		}
	}
	
	private String[] getTerminateProdSubTypeCd(SbOrderDTO sbOrder){
		ServiceDetailLtsDTO eyeService = LtsSbHelper.getLtsEyeService(sbOrder);
		
		if(eyeService != null){
			String[] prodSubTypeCds = {"PCDD", "PCDD"};
			return prodSubTypeCds;
		}else{
			String[] prodSubTypeCds = {"VSO-", "VSO-"};
			return prodSubTypeCds;
		}
	}
	
	public ResourceDetailInputDTO getTermResourceDetailInput(
			TerminateOrderCaptureDTO order, String date){
		
		String[] prodSubTypeCd = getTerminateProdSubTypeCd(order);
		AddressDetailProfileLtsDTO addressDtl = order.getLtsServiceProfile().getAddress();
		String fieldPermitString 	= order.getLtsServiceProfile().getAddress().getAddressRollout().getFieldWorkPermit();
		int fieldPermit 			= StringUtils.isNotBlank(fieldPermitString)?Integer.parseInt(fieldPermitString):0;
		
		ResourceDetailInputDTO input = createResourceDetailInput(
				prodSubTypeCd[0], prodSubTypeCd[1], date, LtsConstant.DWFM_INPUT_TYPE_DISCONNECT, 
				false, false, fieldPermit, order.getLtsSalesInfoForm().getStaffId(), 
				addressDtl.getAreaCd(), addressDtl.getDistrictCd(), "N", addressDtl.getSrvBdry() );
		
		return input;
	}
	
	public ResourceDetailInputDTO getTermResourceDetailInput(
			SbOrderDTO sbOrder, String type, String date, String userId){
		
		String[] prodSubTypeCd = getTerminateProdSubTypeCd(sbOrder);
		AddressDetailLtsDTO addressDtl = sbOrder.getAddress();
		String fieldPermitString 	= sbOrder.getAddress() != null && sbOrder.getAddress().getAddrInventory() != null ? sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay(): null;
		int fieldPermit 			= StringUtils.isNotBlank(fieldPermitString)?Integer.parseInt(fieldPermitString):0;
		
		ResourceDetailInputDTO input = createResourceDetailInput(
				prodSubTypeCd[0], prodSubTypeCd[1], date, type, 
				false, false, fieldPermit, userId, 
				addressDtl.getAreaCd(), addressDtl.getDistNo(), "N", addressDtl.getSerbdyno() );
		
		return input;
	}
	
	public ResourceDetailInputDTO getResourceDetailInput(
			SbOrderDTO sbOrder, String type, String date, String userId){

		if(sbOrder == null){
			return null;
		}
		
		ServiceDetailLtsDTO coreService = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailLtsDTO secDelService = LtsSbOrderHelper.get2ndDelService(sbOrder);
		
		/*params for resourceDetailInput*/
		String prodSubTypeCd[] 		= getProdSubTypeCd(sbOrder);
		boolean isCreate2ndDel 		= secDelService != null;
		boolean is2ndDelReserved	= secDelService != null && "Y".equals(secDelService.getReservedDnInd());
		AddressDetailLtsDTO addressDtl = sbOrder.getAddress();
		String fieldPermitString 	= sbOrder.getAddress() != null && sbOrder.getAddress().getAddrInventory() != null ? sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay(): null;
		int fieldPermit 			= StringUtils.isNotBlank(fieldPermitString)?Integer.parseInt(fieldPermitString):0;
		
		ResourceDetailInputDTO input = createResourceDetailInput(
				prodSubTypeCd[0], prodSubTypeCd[1], date, type, 
				isCreate2ndDel, is2ndDelReserved, fieldPermit, userId, 
				addressDtl.getAreaCd(), addressDtl.getDistNo(), coreService.getErInd(), addressDtl.getSerbdyno() );
		
		return input;
	}

	private String[] getProdSubTypeCd(SbOrderDTO sbOrder){
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		ModemType m 				= imsService != null && imsService.getShareFsaType() != null ?ModemType.valueOf(imsService.getShareFsaType()) : null;
		String assignTech 			= imsService != null ? imsService.getAssgnTechnology() : null;
		boolean isInstallUpgradePon = isInstallUpgradePon(sbOrder);
		return getProdSubTypeCd(m, assignTech, isInstallUpgradePon);
	}
	
	public boolean isInstallUpgradePon(SbOrderDTO sbOrder){
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())
				|| LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())) {
			return false;
		}
		
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		if(imsService != null
				&& isInstallUpgradePon(
						imsService.getAssgnTechnology(), imsService.getExistTechnology(), 
						"Y".equals(imsService.getErInd()), ModemType.valueOf(imsService.getShareFsaType()))){
			return true;
		}
		return false;
	}
	
	private ResourceTypeDTO createResourceTypeDto(String prodSubTypeCd, String fromProdSubTypeCd, 
			String prodType, String srvType, String orderType ){
		ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO();
		resourceTypeDTO.setProdSubTypeCd(prodSubTypeCd);
		resourceTypeDTO.setProdType(prodType);
		resourceTypeDTO.setSrvType(srvType);
		resourceTypeDTO.setFromProdSubTypeCd(fromProdSubTypeCd);
		resourceTypeDTO.setOrderType(StringUtils.isBlank(orderType)? StringUtils.substring(prodSubTypeCd, 3, 4): orderType);

		return resourceTypeDTO;
	}
	
	private ResourceDetailInputDTO createResourceDetailInput(
			String prodSubTypeCd, String fromProdSubTypeCd, String date, String type, 
			boolean isCreate2ndDel, boolean is2ndDelReserved, int fieldPermit, String userId,
			String area, String district, String addrChangeInd, String srvBoundary){
		final String PROD_SUB_TYPE_CD_V = "VSO-";
		final String SRV_TYPE_CD_SO = "SO";
		
		ResourceDetailInputDTO resourceInput = new ResourceDetailInputDTO();
		
//		type = "ALL", "UPGRADE", "2NDDEL"
		List<ResourceTypeDTO> resourceTypeDTOList = new ArrayList<ResourceTypeDTO>(); 

		if(StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_ALL)
				|| StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_UPGRADE)){
			resourceTypeDTOList.add(createResourceTypeDto(prodSubTypeCd, fromProdSubTypeCd, "I", SRV_TYPE_CD_SO, null));
		}else if(StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_DISCONNECT)){
			resourceTypeDTOList.add(createResourceTypeDto(prodSubTypeCd, fromProdSubTypeCd, "I", SRV_TYPE_CD_SO, null));
		}
		
		if(StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_ALL)
				|| StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_2NDDEL)){
			if(isCreate2ndDel){
				resourceTypeDTOList.add(createResourceTypeDto(PROD_SUB_TYPE_CD_V, 
											is2ndDelReserved? null: PROD_SUB_TYPE_CD_V, 
											"V", SRV_TYPE_CD_SO, 
											is2ndDelReserved? LtsConstant.ORDER_TYPE_INSTALL : LtsConstant.ORDER_TYPE_CHANGE));
			}
		}
		
		resourceInput.setRscTypes(resourceTypeDTOList.toArray(new ResourceTypeDTO[0]));
		
		resourceInput.setApptDate(LtsAppointmentHelper.reformatToDwfmDate(date));
		resourceInput.setArea(area);
		resourceInput.setDistrict(district);
		resourceInput.setAddrChangeInd(addrChangeInd);
		resourceInput.setSrvBoundary(srvBoundary);
		resourceInput.setDrgPermitDays(String.valueOf(fieldPermit));
		resourceInput.setSystemId("SPB");
		resourceInput.setUser(userId);
		return resourceInput;
	}
	
	/*output = {ProdSubTypeCd, FromProdSubTypeCd}*/
	private String[] getProdSubTypeCd(ModemType m, String technology, boolean isInstallUpgradePon){
		
		if (StringUtils.isBlank(technology) || m == null) {
			return new String[]{"VSO-", null}; 
		}
		
		String prodSubTypeCd = null;
		String frProdSubTypeCd = null;
		
//		ModemType m = orderCaptureDTO.getLtsModemArrangementForm().getModemType();
		if(StringUtils.equals(technology, LtsConstant.TECHNOLOGY_PON)){
			if(ModemType.STANDALONE.equals(m)
					||  ModemType.SHARE_TV.equals(m)
					|| ModemType.SHARE_PCD.equals(m)
					|| ModemType.SHARE_BUNDLE.equals(m)){
				prodSubTypeCd = "FTHI";
				frProdSubTypeCd = "FTHI";
			}else if(ModemType.SHARE_EX_FSA.equals(m)
					|| ModemType.SHARE_OTHER_FSA.equals(m)){
				if(isInstallUpgradePon){
					prodSubTypeCd = "FTHI";
				}else{
					prodSubTypeCd = "FTHC";
				}
				frProdSubTypeCd = "FTHC";
			}
		}else{
			if(ModemType.STANDALONE.equals(m)
					|| ModemType.SHARE_TV.equals(m)
					|| ModemType.SHARE_PCD.equals(m)
					|| ModemType.SHARE_BUNDLE.equals(m)){
				prodSubTypeCd = "PCDI";
				frProdSubTypeCd = "PCDI";
			}else if(ModemType.SHARE_EX_FSA.equals(m)
					|| ModemType.SHARE_OTHER_FSA.equals(m)){
				prodSubTypeCd = "PCDC";
				frProdSubTypeCd = "PCDC";
			}
		}
		
		String[] prodSubTypeCds = {prodSubTypeCd, frProdSubTypeCd};
		return prodSubTypeCds;
	}
	
	private boolean isInstallUpgradePon(String assgnTech, String fsaTech, boolean isFsaEr, ModemType m){

		if(LtsConstant.TECHNOLOGY_PON.equals(assgnTech)){
			if(LtsConstant.TECHNOLOGY_PON.equals(fsaTech)){
				if(m == ModemType.SHARE_EX_FSA || m == ModemType.SHARE_OTHER_FSA){
					return isFsaEr;
				}
			}else{
				return true;
			}
		}
		return false;
		
	}

	public PrebookAppointmentInputDTO getPrebookAppointmentInput(SbOrderDTO sbOrder, String date, String timeSlot, String staffId, String timeSlotType){
		String prodSubTypeCd = getProdSubTypeCd(sbOrder)[0];
		AddressDetailLtsDTO addressDtl = sbOrder.getAddress();
		
		PrebookAppointmentInputDTO input = createPrebookAppointmentInput(
				date, timeSlot, prodSubTypeCd, addressDtl.getAreaCd(), addressDtl.getDistNo(), staffId, timeSlotType);
		
		return input;
	}
	
	private PrebookAppointmentInputDTO createPrebookAppointmentInput(
			String date, String timeSlot, String prodSubTypeCd, 
			String area, String district, String staffId, String timeSlotType){
		String dateString = LtsAppointmentHelper.reformatToDwfmDate(date);
		String[] time = timeSlot.split("-");
		String startDate = "";
		String endDate = "";
		String[] startTime = time[0].split(":");
		String[] endTime = time[1].split(":");
		startDate = startDate.concat(dateString).concat(startTime[0]).concat(startTime[1]);
		endDate = endDate.concat(dateString).concat(endTime[0]).concat(endTime[1]);
		
		PrebookAppointmentInputDTO input = new PrebookAppointmentInputDTO();
		input.setProdSubTypeCd(prodSubTypeCd);
		input.setArea(area);
		input.setDistrict(district);
		input.setApptDateStartDate(startDate);
		input.setApptDateEndDate(endDate);
		input.setSystemId("SPB");
		input.setUser(staffId);
		input.setApptType(timeSlotType);
		return input;
	}
	
	//BOM2018061
	public int getEpdLeadTime(){
		try {
			return Integer.parseInt((String)ltsAppointmentCutOffLkupCacheService.get("EPD_LEAD_TIME"));
		} catch (Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	/*copy attb value from old epd item, mainly for locale change case*/
	public List<ItemDetailDTO> getNewEpdItemList(String basketChannelId, String applicationDate, String locale, List<ItemDetailDTO> oldEpdItemList){
		List<ItemDetailDTO> epdItemList = getEpdItemList(basketChannelId, applicationDate, locale);
		
		ltsOfferService.copyItemListItemAttbValues(oldEpdItemList, epdItemList);
		
		return epdItemList;
	}
	
	public List<ItemDetailDTO> getEpdItemList(String basketChannelId, String applicationDate, String locale){
		List<ItemDetailDTO> epdItemList = new ArrayList<ItemDetailDTO>();

		epdItemList.addAll(ltsOfferService.getItemList(
				LtsConstant.ITEM_TYPE_EPD_FORFEIT, locale, false, true, basketChannelId, applicationDate));
		epdItemList.addAll(ltsOfferService.getItemList(
				LtsConstant.ITEM_TYPE_EPD_ACCEPT, locale, false, true, basketChannelId, applicationDate));
		epdItemList.addAll(ltsOfferService.getItemList(
				LtsConstant.ITEM_TYPE_EPD_UNDER_CONSIDERATION, locale, false, true, basketChannelId, applicationDate));
		
		return epdItemList;
	}
}
