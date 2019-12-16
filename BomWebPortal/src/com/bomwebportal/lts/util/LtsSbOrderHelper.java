package com.bomwebportal.lts.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.ItemCriteriaDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO.ItemCriteriaBuilder;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.LineTypeSelectionDTO;
import com.bomwebportal.lts.dto.NowTvServiceDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.SignatureDTO;
import com.bomwebportal.lts.dto.form.LtsBasketServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketVasDetailFormDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerIdentificationFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketServiceFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.order.SignatureLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemActionLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferActionLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupMemberProfileDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupProfileDTO;
import com.google.common.collect.Lists;

public class LtsSbOrderHelper extends LtsSbHelper {
	
    public static boolean isOrderDS(SbOrderDTO sbOrder) {
    	return LtsConstant.ORDER_MODE_DIRECT_SALES.equals(sbOrder.getOrderMode())    	
    	|| LtsConstant.ORDER_MODE_DIRECT_SALES_NOW_TV_QA.equals(sbOrder.getOrderMode());
    }
    
    public static boolean isStandaloneVasOrder(OrderCaptureDTO orderCapture){
    	if( LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				&& LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType()) ){
    		return true;
    	}
    	return false;
    }
	
	public static boolean isImsServiceChangeOffer(ServiceDetailOtherLtsDTO imsService) {
		
		ImsOfferDetailDTO[] imsOfferDetails =  imsService.getOfferDtls();
		
		if (ArrayUtils.isEmpty(imsOfferDetails)) {
			return false;
		}
		
		for (ImsOfferDetailDTO imsOfferDetail : imsOfferDetails) {
			if (LtsConstant.IO_IND_INSTALL.equals(imsOfferDetail.getIoInd())
					|| LtsConstant.IO_IND_OUT.equals(imsOfferDetail.getIoInd())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static String getLetterPrintDocName(String docName) {
		String theDocName = null;
		if (LtsConstant.SEND_DOCUMENT_AF.equals(docName)){
			theDocName = LtsConstant.SEND_LETTER_PRINT_AF;
		}else{
			theDocName = LtsConstant.SEND_LETTER_PRINT_COVER_LETTER;
		}
		return theDocName;
	}
	
	public static boolean isSecondContractWithSameOffer(ServiceDetailProfileLtsDTO serviceProfile) {
		if(!isSecondContract(serviceProfile)) {
			return false;
		}
		
		ItemDetailProfileLtsDTO currentTpItem = getProfileServiceTermPlan(serviceProfile);
		
		// Check contain same offer with expired TP
		if (ArrayUtils.isNotEmpty(serviceProfile.getItemDetails())) {
			for (ItemDetailProfileLtsDTO profileItem : serviceProfile.getItemDetails()) {
				if (LtsConstant.ITEM_TYPE_EXPIRED_SERVICE.equals(profileItem.getItemType())) {
					if (isContainSameOffer(currentTpItem, profileItem)) {
						return true;
					}
				}
			}
		}
		// Check contain same offer with ended TP
		if (ArrayUtils.isNotEmpty(serviceProfile.getEndedItemDetails())) {
			for (ItemDetailProfileLtsDTO endedProfileItem : serviceProfile.getEndedItemDetails()) {
				if (LtsConstant.ITEM_TYPE_EXPIRED_SERVICE.equals(endedProfileItem.getItemType())) {
					if (isContainSameOffer(currentTpItem, endedProfileItem)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isContainSameOffer(ItemDetailProfileLtsDTO profileItemA, ItemDetailProfileLtsDTO profileItemB){
		if (ArrayUtils.isEmpty(profileItemA.getOffers())
				|| ArrayUtils.isEmpty(profileItemB.getOffers()) ) {
			return false;
		}
		
		for (OfferDetailProfileLtsDTO offerA : profileItemA.getOffers()) {
			for(OfferDetailProfileLtsDTO offerB : profileItemB.getOffers()) {
				if (StringUtils.equals(offerA.getOfferId(), offerB.getOfferId())
						&& !StringUtils.equals("DUMMY", offerA.getOfferType())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public  static String getSmsTemplateId(SbOrderDTO sbOrder){
		String templateId = null;
		if(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())){
			ServiceDetailLtsDTO srvDtl = LtsSbOrderHelper.getLtsEyeService(sbOrder);

			if(LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(sbOrder.getOrderSubType())){
				templateId = LtsConstant.SMS_TEMPLATE_SIGNOFF_STANDALONE_VAS_MASS;
			}else{
				if (!LtsConstant.ORDER_MODE_PREMIER.equals(sbOrder.getOrderMode())) {
					if(srvDtl != null){
						templateId = LtsConstant.SMS_TEMPLATE_ID_EYE_SMS_RENEWAL;
					}
					else {
						templateId = LtsConstant.SMS_TEMPLATE_ID_DEL_SMS_RENEWAL;
					}		
				}else{
					if(srvDtl != null){
						templateId = LtsConstant.SMS_TEMPLATE_ID_EYE_SMS_RENEWAL_PREMIER;
					}
					else {
						templateId = LtsConstant.SMS_TEMPLATE_ID_DEL_SMS_RENEWAL_PREMIER;
					}		
				}
			}
			
			

		}
		else if(LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())){
			ServiceDetailLtsDTO srvDtl = LtsSbOrderHelper.getLtsEyeService(sbOrder);
			if(srvDtl != null){
				templateId = LtsConstant.SMS_TEMPLATE_ID_EYE_SMS_TERMINATION;
			}
			else {
				templateId = LtsConstant.SMS_TEMPLATE_ID_DEL_SMS_TERMINATION;
			}	
		}
		else {	
			ServiceDetailLtsDTO srvDtl = LtsSbOrderHelper.getLtsEyeService(sbOrder);
			if (LtsConstant.ORDER_MODE_CALL_CENTER.equals(sbOrder.getOrderMode())
					|| LtsConstant.ORDER_MODE_RETAIL.equals(sbOrder.getOrderMode())) {
				templateId = srvDtl!=null ? LtsConstant.SMS_TEMPLATE_ID_EYE_SMS_CALL_CENTER:
					LtsConstant.SMS_TEMPLATE_ID_DEL_SMS_CALL_CENTER;
			}
			if (LtsConstant.ORDER_MODE_PREMIER.equals(sbOrder.getOrderMode())) {
				templateId = srvDtl!=null ? LtsConstant.SMS_TEMPLATE_ID_EYE_SMS_PREMIER:
					LtsConstant.SMS_TEMPLATE_ID_DEL_SMS_PREMIER;
			}
			if (LtsConstant.ORDER_MODE_DIRECT_SALES.equals(sbOrder.getOrderMode())
					|| LtsConstant.ORDER_MODE_DIRECT_SALES_NOW_TV_QA.equals(sbOrder.getOrderMode())) {
				if (StringUtils.equals("Y", sbOrder.getPrepaymentSlipInd())) {
					templateId = srvDtl!=null ? LtsConstant.SMS_TEMPLATE_ID_EYE_SMS_DIRECT_SALES_WITH_PREPAYMENT:
						LtsConstant.SMS_TEMPLATE_ID_DEL_SMS_DIRECT_SALES_WITH_PREPAYMENT;
				} else {
					templateId = srvDtl!=null ? LtsConstant.SMS_TEMPLATE_ID_EYE_SMS_DIRECT_SALES:
						LtsConstant.SMS_TEMPLATE_ID_DEL_SMS_DIRECT_SALES;
				}
			}
		}
		return templateId;
	}
	
	public static String getEmailTemplateId(SbOrderDTO sbOrder, boolean prePayment ){
		String templateId = null;
		
		if(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())){

			if(LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(sbOrder.getOrderSubType())){
				templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_MASS;
				if (LtsConstant.ORDER_MODE_PREMIER.equals(sbOrder.getOrderMode())) {
					templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_PREMIER;
				}
				if (LtsConstant.ORDER_MODE_RETAIL.equals(sbOrder.getOrderMode())) {
					templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_RETAIL;
				}
				if (LtsConstant.ORDER_MODE_CALL_CENTER.equals(sbOrder.getOrderMode())) {
					templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_CALL_CENTER;
				}
			}else{
				ServiceDetailLtsDTO srvDtl = LtsSbOrderHelper.getLtsEyeService(sbOrder);
				
				if(!LtsConstant.ORDER_MODE_PREMIER.equals(sbOrder.getOrderMode())){			
					if(srvDtl != null){
						templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_RENEWAL;
					}
					else {
						templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_RENEWAL_DEL;
					}
				}else{
					if(srvDtl != null){
						templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_RENEWAL_PREMIER;
					}
					else {
						templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_RENEWAL_PREMIER_DEL;
					}
				}
			}
			
		} else if (LtsSbOrderHelper.isNewAcquistionOrder(sbOrder)){
			ServiceDetailLtsDTO eyeService = LtsSbOrderHelper.getLtsEyeService(sbOrder);
			ServiceDetailLtsDTO delService = LtsSbOrderHelper.getDelServices(sbOrder);
			if (eyeService!=null) {
				if (LtsConstant.ORDER_MODE_PREMIER.equals(sbOrder.getOrderMode())) {
					templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_PREMIER;
				}
				if (LtsConstant.ORDER_MODE_RETAIL.equals(sbOrder.getOrderMode())) {
					templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_RETAIL;
				}
				if (LtsConstant.ORDER_MODE_CALL_CENTER.equals(sbOrder.getOrderMode())) {
					templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_CALL_CENTER;
				}
				if (LtsConstant.ORDER_MODE_DIRECT_SALES.equals(sbOrder.getOrderMode())
						|| LtsConstant.ORDER_MODE_DIRECT_SALES_NOW_TV_QA.equals(sbOrder.getOrderMode())) {
					if (prePayment) {
						templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_DIRECT_SALES_WITH_PREPAYMENT;
					} else {
						templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_DIRECT_SALES;
					}
				}
			}
			if (delService!=null) {
				if (LtsConstant.ORDER_MODE_PREMIER.equals(sbOrder.getOrderMode())) {
					templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_PREMIER;
				}
				if (LtsConstant.ORDER_MODE_RETAIL.equals(sbOrder.getOrderMode())) {
					templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_RETAIL;
				}
				if (LtsConstant.ORDER_MODE_CALL_CENTER.equals(sbOrder.getOrderMode())) {
					templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_CALL_CENTER;
				}
				if (LtsConstant.ORDER_MODE_DIRECT_SALES.equals(sbOrder.getOrderMode())
						|| LtsConstant.ORDER_MODE_DIRECT_SALES_NOW_TV_QA.equals(sbOrder.getOrderMode())) {
					if (prePayment) {
						templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_DIRECT_SALES_WITH_PREPAYMENT;
					} else {
						templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_DIRECT_SALES;
					}
				}
			}				
		} else {
			if (LtsConstant.ORDER_MODE_RETAIL.equals(sbOrder.getOrderMode())) {
				templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF;
			}
			if (LtsConstant.ORDER_MODE_CALL_CENTER.equals(sbOrder.getOrderMode())) {
				templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_CALL_CENTER;			
			}
			if (LtsConstant.ORDER_MODE_PREMIER.equals(sbOrder.getOrderMode())) {
				templateId = LtsConstant.EMAIL_TEMPLATE_SIGNOFF_PREMIER;
			}
		}
		return templateId;
	}
	

	public static boolean isSecondContract(ServiceDetailProfileLtsDTO serviceProfile) {
		String[] secondContractNatures = new String[] {"Retention", "Retention(eye2A)", "Retention (Tab)"};
		
		ItemDetailProfileLtsDTO[] profileItems = serviceProfile.getItemDetails();
		
		if (ArrayUtils.isEmpty(profileItems)) {
			return false;
		}
		
		ItemDetailProfileLtsDTO profileSrvTpItem = getProfileServiceTermPlan(serviceProfile);
		
		if (profileSrvTpItem == null || profileSrvTpItem.getItemDetail() == null) {
			return false;
		}
		// Check current contract
		for (String secondContractNature : secondContractNatures) {
			if (StringUtils.containsIgnoreCase(profileSrvTpItem.getItemDetail().getNature(), secondContractNature)) {
				return true;
			}
		}
		
		// If FTG case, check previous contract
		if (profileSrvTpItem.getTpExpiredMonths() >= 0 || StringUtils.isBlank(profileSrvTpItem.getConditionEffEndDate())) {
			for (ItemDetailProfileLtsDTO profileItem : profileItems) {
				if (LtsConstant.ITEM_TYPE_EXPIRED_SERVICE.equals(profileItem.getItemType())) {
					for (String secondContractNature : secondContractNatures) {
						if (profileItem.getItemDetail() != null && StringUtils.containsIgnoreCase(profileItem.getItemDetail().getNature(), secondContractNature)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
		
	public static AccountDetailProfileLtsDTO getProfileAccount(ServiceDetailProfileLtsDTO ltsServiceProfile, String acctChrgType) {
		AccountDetailProfileLtsDTO[] profileAccounts = ltsServiceProfile.getAccounts();
		
		if (ArrayUtils.isNotEmpty(profileAccounts)) {
			for (AccountDetailProfileLtsDTO profileAccount : profileAccounts) {
				if (StringUtils.containsIgnoreCase(profileAccount.getAcctChrgType(), acctChrgType)) {
					return profileAccount;
				}
			}
		}
		return null;
	}
	
	public static boolean isRequireRedemPremium(OrderCaptureDTO orderCapture) {
		if (orderCapture.getSbOrder() != null) {
			ServiceDetailDTO ltsPrimaryService = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
			ItemDetailLtsDTO[] subcItems = ((ServiceDetailLtsDTO)ltsPrimaryService).getItemDtls();
			if (ArrayUtils.isNotEmpty(subcItems)) {
				for (ItemDetailLtsDTO subcItem : subcItems) {
					if (LtsConstant.ITEM_TYPE_PREMIUM.equals(subcItem.getItemType())) {
						return true;
					}
				}
			}
		}
		
		if (orderCapture.getLtsPremiumSelectionForm() != null) {
			if (orderCapture.getLtsPremiumSelectionForm().getPremiumSetList() != null
					&& !orderCapture.getLtsPremiumSelectionForm().getPremiumSetList().isEmpty()) {
				for (ItemSetDetailDTO premiumSet : orderCapture.getLtsPremiumSelectionForm().getPremiumSetList()) {
					if (ArrayUtils.isNotEmpty(premiumSet.getItemDetails())) {
						for (ItemDetailDTO premiumItem : premiumSet.getItemDetails()) {
							if (premiumItem.isSelected()) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public static boolean isBundle2GTp(ServiceDetailProfileLtsDTO ltsServiceProfile) {
		final String TP_SUNDAY = "SUNDAY";
		final String TP_2G_BUNDLE = "2G";

		ItemDetailProfileLtsDTO[] profileItems = ltsServiceProfile.getItemDetails();
		
		if (ArrayUtils.isEmpty(profileItems)) {
			return false;
		}
		for(ItemDetailProfileLtsDTO profileItem : profileItems) {
			
			if (profileItem.getItemDetail() == null) {
				continue;
			}
			
			if ((LtsConstant.ITEM_TYPE_SERVICE.equals(profileItem.getItemType())
					|| (LtsConstant.ITEM_TYPE_EXPIRED_SERVICE.equals(profileItem.getItemType()) 
							&& (StringUtils.isBlank(profileItem.getConditionEffEndDate())
							  || LtsDateFormatHelper.getMonthDiff(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), profileItem.getConditionEffEndDate(), "dd/MM/yyyy") <= 6)))) {
				
				if (StringUtils.isNotEmpty(profileItem.getItemDetail().getTpCatg())
						&& (StringUtils.contains(profileItem.getItemDetail().getTpCatg(), TP_2G_BUNDLE)
								|| StringUtils.contains(profileItem.getItemDetail().getTpCatg(), TP_SUNDAY))) {
					return true;
				}	
			}
		}
		return false;
	}
	
	public static double getRemainTpContractMth(ServiceDetailProfileLtsDTO serviceProfile) {
		ItemDetailProfileLtsDTO[]  profileItemDetails = serviceProfile.getItemDetails();
		
		if (ArrayUtils.isEmpty(profileItemDetails)) {
			return 0.0;
		}
		
		for (ItemDetailProfileLtsDTO profileItemDetail : profileItemDetails) {
			if (LtsConstant.ITEM_TYPE_SERVICE.equals(profileItemDetail.getItemType())) {
				if (StringUtils.isEmpty(profileItemDetail.getConditionEffEndDate())) {
					continue;
				}
				if (LtsDateFormatHelper.isDateOverdue(profileItemDetail.getConditionEffEndDate(), "dd/MM/yyyy")) {
					return 0.0;
				}
				return LtsDateFormatHelper.getMonthDiff(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), profileItemDetail.getConditionEffEndDate() , "dd/MM/yyyy");
			}
		}
		return 0.0;
	}
	
	public static ItemDetailProfileLtsDTO getTheLastestExpiredTermPlan(ServiceDetailProfileLtsDTO ltsServiceProfile) {
		if (ArrayUtils.isEmpty(ltsServiceProfile.getItemDetails())) {
			return null;
		}
		
		ItemDetailProfileLtsDTO expiredTermPlan = null;
		for (ItemDetailProfileLtsDTO profileItem : ltsServiceProfile.getItemDetails()) {
			if (LtsConstant.ITEM_TYPE_EXPIRED_SERVICE.equals(profileItem.getItemType())) {
				// Set first looped TP as return object.
				if (expiredTermPlan == null) {
					expiredTermPlan = profileItem;
					continue;
				}
				// Skip TP that without condition end date.
				if (StringUtils.isEmpty(profileItem.getConditionEffEndDate())) {
					continue;
				}
				if (StringUtils.isEmpty(expiredTermPlan.getConditionEffEndDate())) {
					expiredTermPlan = profileItem;
					continue;
				}
				// Set latest condition end date TP as return object.
				if (LtsDateFormatHelper.getDateDiffInDays(expiredTermPlan.getConditionEffEndDate(), profileItem.getConditionEffEndDate(), "dd/MM/yyyy") > 0) {
					expiredTermPlan = profileItem;
				}
			}
		}
		return expiredTermPlan;
	}
	
	public static double getTpExpireMth(ServiceDetailProfileLtsDTO serviceProfile, String orderType) {
		// Upgrade
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderType)) {
			ItemDetailProfileLtsDTO currentProfileTp = getProfileServiceTermPlan(serviceProfile);
			if (currentProfileTp == null) {
				return 0.0;
			}
			if (StringUtils.isNotEmpty(currentProfileTp.getConditionEffEndDate())) {
				return currentProfileTp.getTpExpiredMonths();
			}
			
			ItemDetailProfileLtsDTO expiredProfileTp = getTheLastestExpiredTermPlan(serviceProfile);
			
			return expiredProfileTp == null ? 0.0 : expiredProfileTp.getTpExpiredMonths();
		}
		// Renewal
		return getTpExpireMth(serviceProfile);
	}
	
	
	public static double getTpExpireMth(ServiceDetailProfileLtsDTO serviceProfile) {
		ItemDetailProfileLtsDTO[]  profileItemDetails = serviceProfile.getItemDetails();
		
		if (ArrayUtils.isEmpty(profileItemDetails)) {
			return 0.0;
		}
		
		for (ItemDetailProfileLtsDTO profileItemDetail : profileItemDetails) {
			if (LtsConstant.ITEM_TYPE_SERVICE.equals(profileItemDetail.getItemType())) {
				if (StringUtils.isEmpty(profileItemDetail.getConditionEffEndDate())) {
					continue;
				}
				if (LtsDateFormatHelper.isDateOverdue(profileItemDetail.getConditionEffEndDate(), "dd/MM/yyyy")) {
					return 0.0;
				}
				return LtsDateFormatHelper.getMonthDiff(profileItemDetail.getConditionEffEndDate(), LtsDateFormatHelper.getSysDate("dd/MM/yyyy") , "dd/MM/yyyy");
			}
		}
		return 0.0;
	}
	
	
	public static String getExistTpCatg(ServiceDetailProfileLtsDTO serviceProfile) {
		ItemDetailProfileLtsDTO[]  profileItemDetails = serviceProfile.getItemDetails();
		
		if (ArrayUtils.isEmpty(profileItemDetails)) {
			return LtsConstant.BASKET_CATEGORY_OTHER;
		}
		
		ItemDetailProfileLtsDTO currentTermPlan = getProfileServiceTermPlan(serviceProfile);
		if (isFreeToGoService(serviceProfile)) {
			currentTermPlan = getExpiredTermPlanWithin6Mths(serviceProfile);
		}
		
		if (currentTermPlan != null && currentTermPlan.getItemDetail() != null) {
			// For existing TP, IT will lookup existing TP CAT by "IDD" and exclude "SUNDAY".
			if (StringUtils.isEmpty(serviceProfile.getExistEyeType())
					&& StringUtils.containsIgnoreCase(currentTermPlan.getItemDetail().getTpCatg(), "IDD")
					&& !StringUtils.containsIgnoreCase(currentTermPlan.getItemDetail().getTpCatg(), "SUNDAY")) {
				return LtsConstant.BASKET_CATEGORY_IDD;
			}				
			
			if (StringUtils.equals("Y", currentTermPlan.getItemDetail().getIsPremiumTp())) {
				return LtsConstant.BASKET_CATEGORY_PREMIUM;
			}
			
			if (LtsConstant.BASKET_CATEGORY_REBATE.equalsIgnoreCase(currentTermPlan.getItemDetail().getTpCatg())) {
				return LtsConstant.BASKET_CATEGORY_REBATE;
			}
		}
		return LtsConstant.BASKET_CATEGORY_OTHER;
	}
	
	public static String getExpiredContractEndDate(SbOrderDTO sbOrder) {
		if (sbOrder == null) {
			return null;
		}
		ServiceDetailLtsDTO ltsService = getLtsService(sbOrder);
		ItemDetailLtsDTO[] subcItems = ltsService.getItemDtls();
		if (ArrayUtils.isEmpty(subcItems)) {
			return null;
		}
		
		for (ItemDetailLtsDTO subcItem : subcItems) {
			if (LtsConstant.ITEM_LTS_TP.equals(subcItem.getItemType())
					&& LtsConstant.ITEM_TYPE_EXPIRED_SERVICE.equals(subcItem.getProfileItemType())) {
				return subcItem.getExistEndDate();
			}
		}
		return null;
	}
	
	
	public static String getEndedContractEndDate(ServiceDetailProfileLtsDTO serviceProfile) {
		ItemDetailProfileLtsDTO[] profileItems = serviceProfile.getEndedItemDetails();
		
		if (ArrayUtils.isEmpty(profileItems)) {
			return null;
		}
		
		for (ItemDetailProfileLtsDTO profileItem : profileItems) {
			if (LtsConstant.ITEM_TYPE_EXPIRED_SERVICE.equals(profileItem.getItemType())
					&& profileItem.getTpExpiredMonths() > 0) {
				return profileItem.getConditionEffEndDate();
			}
		}
		return null;
	}
	
	public static String getExpiredContractEndDate(ServiceDetailProfileLtsDTO serviceProfile) {
		
		ItemDetailProfileLtsDTO[] profileItems = serviceProfile.getItemDetails();
		
		if (ArrayUtils.isEmpty(profileItems)) {
			return null;
		}
		
		for (ItemDetailProfileLtsDTO profileItem : profileItems) {
			if (LtsConstant.ITEM_TYPE_EXPIRED_SERVICE.equals(profileItem.getItemType())) {
				return profileItem.getConditionEffEndDate();
			}
		}
		return null;
	}
	
	public static String getProfileContractEndDate(SbOrderDTO sbOrder) {
		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		if(ltsService == null || ArrayUtils.isEmpty(ltsService.getItemDtls())){
			return null;
		}
		for(ItemDetailLtsDTO item : ltsService.getItemDtls()){
			if(StringUtils.equals(item.getItemType(), LtsConstant.ITEM_LTS_TP) 
					&& StringUtils.equals(item.getProfileItemType(), LtsConstant.PROFILE_ITEM_TYPE_SERVICE)){
				return LtsDateFormatHelper.getDateFromDTOFormat(item.getExistEndDate());
			}
		}
		return null;
	}
	
	public static String getProfileContractEndDate(ServiceDetailProfileLtsDTO serviceProfile) {
		
		ItemDetailProfileLtsDTO profileServiceTp = getProfileServiceTermPlan(serviceProfile);
		if (profileServiceTp == null) {
			return null;
		}
		return profileServiceTp.getConditionEffEndDate();
	}
	
	public static ItemDetailProfileLtsDTO getProfileServiceTermPlan(ServiceDetailProfileLtsDTO serviceProfile) {
		ItemDetailProfileLtsDTO[] profileItems = serviceProfile.getItemDetails();
		
		if (ArrayUtils.isEmpty(profileItems)) {
			return null;
		}
		
		for (ItemDetailProfileLtsDTO profileItem : profileItems) {
			if (LtsConstant.ITEM_TYPE_SERVICE.equals(profileItem.getItemType())) {
				return profileItem;
			}
		}
		return null;
	}
	
	public static String getBasketChannelId(BomSalesUserDTO bomSalesUser, OrderCaptureDTO orderCapture) {
		
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())) {
			if (LtsConstant.CHANNEL_ID_PREMIER.equals(String.valueOf(bomSalesUser.getChannelId()))) {
				return LtsConstant.CHANNEL_ID_SPRINGBOARD_PREMIER;
			}
			return LtsConstant.CHANNEL_ID_SPRINGBOARD_RETAIL;
		}
		else if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			
			if(LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType())){
				// Standalone VAS PT
				if (LtsConstant.CHANNEL_ID_PREMIER.equals(String.valueOf(bomSalesUser.getChannelId()))) {
					if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
						return LtsConstant.CHANNEL_ID_SPRINGBOARD_STANDALONE_VAS_PT_DEL;
					}
					return LtsConstant.CHANNEL_ID_SPRINGBOARD_STANDALONE_VAS_PT_EYE;
				}
				
				// Standalone VAS MASS
				if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
					return LtsConstant.CHANNEL_ID_SPRINGBOARD_STANDALONE_VAS_MASS_DEL;
				}
				return LtsConstant.CHANNEL_ID_SPRINGBOARD_STANDALONE_VAS_MASS_EYE; 
			}else{
				// RET PT
				if (LtsConstant.CHANNEL_ID_PREMIER.equals(String.valueOf(bomSalesUser.getChannelId()))) {
					if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
						return LtsConstant.CHANNEL_ID_SPRINGBOARD_RET_PT_DEL;
					}
					return LtsConstant.CHANNEL_ID_SPRINGBOARD_RET_PT_EYE;
				}
				
				// RET MASS
				if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
					return LtsConstant.CHANNEL_ID_SPRINGBOARD_RET_MASS_DEL;
				}
				return LtsConstant.CHANNEL_ID_SPRINGBOARD_RET_MASS_EYE; 
			}
			
		}
		return null;
	}
	
	public static boolean isProfileItemExpired(ItemDetailProfileLtsDTO profileItem) {
		if (profileItem == null) {
			return false;
		}
		
		if (LtsConstant.ITEM_TYPE_EXPIRED_MIRROR_PLAN.equals(profileItem.getItemType())
				|| LtsConstant.ITEM_TYPE_EXPIRED_SERVICE.equals(profileItem.getItemType())
				|| LtsConstant.ITEM_TYPE_EXPIRED_VAS.equals(profileItem.getItemType())) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isOrderGeneratePenalty(SbOrderDTO sbOrder) {
		
		
		ServiceDetailDTO[] serviceDetails = sbOrder.getSrvDtls();
		
		if (ArrayUtils.isEmpty(serviceDetails)) {
			return false;
		}
		ItemDetailLtsDTO[] itemDetails = null;
		ImsOfferDetailDTO[] imsOfferDetails = null;
		for (ServiceDetailDTO serviceDetail : serviceDetails) {
			
			// LTS Services
			if (serviceDetail instanceof ServiceDetailLtsDTO) {
				itemDetails = ((ServiceDetailLtsDTO)serviceDetail).getItemDtls();
				if (ArrayUtils.isEmpty(itemDetails)) {
					continue;
				}		
				for (ItemDetailLtsDTO itemDetail : itemDetails) {
					if (LtsConstant.ITEM_TYPE_ADMIN_CHARGE.equals(itemDetail.getItemType())) {
						return true;
					}
					if (LtsConstant.ITEM_TYPE_CANCEL_CHARGE.equals(itemDetail.getItemType())) {
						return true;
					}
					if (LtsConstant.PENALTY_ACTION_GENERATE.equals(itemDetail.getPenaltyHandling())) {
						return true;
					}	
				}
			}
			
			// IMS Service
			if (serviceDetail instanceof ServiceDetailOtherLtsDTO) {
				imsOfferDetails = ((ServiceDetailOtherLtsDTO)serviceDetail).getOfferDtls();
				if (ArrayUtils.isEmpty(imsOfferDetails)) {
					continue;
				}
				for (ImsOfferDetailDTO imsOfferDetail : imsOfferDetails) {
					if (LtsConstant.PENALTY_ACTION_GENERATE.equals(imsOfferDetail.getPenaltyHandling())) {
						return true;
					}	
				}
			}
		}
		
		return false;
	}
	
	public static boolean isProfileBundleTv(ServiceDetailProfileLtsDTO serviceProfile) {
		if (ArrayUtils.isNotEmpty(serviceProfile.getChannelOfferActions())) {
			return true;
		}
		return false;
	}
	
	public static boolean isProfileMirrorTv(ServiceDetailProfileLtsDTO serviceProfile) {
		if (ArrayUtils.isNotEmpty(serviceProfile.getItemDetails())) {
			for (ItemDetailProfileLtsDTO profileItem : serviceProfile.getItemDetails()) {
				if (LtsConstant.ITEM_TYPE_MIRROR_PLAN.equals(profileItem.getItemType())
						|| LtsConstant.ITEM_TYPE_EXPIRED_MIRROR_PLAN.equals(profileItem.getItemType())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getBundleTvInd(OrderCaptureDTO orderCapture, ServiceDetailProfileLtsDTO serviceProfile) {
		
		if (orderCapture.getLtsDeviceSelectionForm() == null) {
			return null;
		}
		
		String selectedDevice = orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType();
		OfferActionLtsDTO[] bundleTvOfferActions = serviceProfile.getBundleTvOfferActions();
		
		if (ArrayUtils.isEmpty(bundleTvOfferActions)) {
			return null;
		}
		
		for (OfferActionLtsDTO bundleTvOfferAction : bundleTvOfferActions) {
			if (LtsConstant.DEVICE_TYPE_1020.equals(selectedDevice)
					&& LtsConstant.EYE_TYPE_EYE2A.equals(bundleTvOfferAction.getToProd())) {
				return "Y"; 
			}
			if (LtsConstant.DEVICE_TYPE_EYE3A.equals(selectedDevice)
					&& LtsConstant.EYE_TYPE_EYE3A.equals(bundleTvOfferAction.getToProd())) {
				return "Y";
			}
		}
		
		return null;
	}
	
	public static boolean isEdfRefRequired(SbOrderDTO sbOrder) {
		ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		if (imsService == null) {
			return false;
		}
		
		if (!(LtsConstant.ORDER_MODE_CALL_CENTER.equals(sbOrder.getOrderMode())
				|| LtsConstant.ORDER_MODE_PREMIER.equals(sbOrder.getOrderMode()))) {
			return false;
		}
		
		if (StringUtils.equals("Y", imsService.getErInd())) {
			if (LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN.equals(imsService.getFromProd())
					&& StringUtils.equals(imsService.getFromProd(), imsService.getToProd())) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public static FsaDetailDTO getSelectedFsa(LtsModemArrangementFormDTO pModemArrangementForm) {
		if (pModemArrangementForm == null) {
			return null;
		}
		if (ModemType.SHARE_EX_FSA == pModemArrangementForm.getModemType()
				&& pModemArrangementForm.getExistingFsaList() != null) {
			for (FsaDetailDTO fsaDetail : pModemArrangementForm.getExistingFsaList()) {
				if (fsaDetail.isSelected()) {
					return fsaDetail;
				}
			}
		}
		if (ModemType.SHARE_OTHER_FSA == pModemArrangementForm.getModemType()
				&& pModemArrangementForm.getOtherFsaList() != null) {
			for (FsaDetailDTO fsaDetail : pModemArrangementForm.getOtherFsaList()) {
				if (fsaDetail.isSelected()) {
					return fsaDetail;
				}
			}
		}
		return null;
	}

	public static boolean isExistingEyeProfile(ServiceDetailProfileLtsDTO serviceProfile) {
		if (serviceProfile.getSrvGrp() != null 
				&& (StringUtils.equals(serviceProfile.getSrvGrp().getGrpType(), LtsBackendConstant.EYE_GROUP_TYPE_EYE)
						|| StringUtils.equals(serviceProfile.getSrvGrp().getGrpType(), LtsBackendConstant.EYE_GROUP_TYPE_EYEX)
						|| StringUtils.equals(serviceProfile.getSrvGrp().getGrpType(), LtsBackendConstant.EYE_GROUP_TYPE_EYE3))) {
			return true;
		}
		return false;
	}
	
	public static boolean isSipServiceProfile(ServiceDetailProfileLtsDTO serviceProfile) {
		if (serviceProfile.getSrvGrp() != null ){
			ServiceGroupMemberProfileDTO[] profileGroupMembers = serviceProfile.getSrvGrp().getGrpMems();
			boolean isNcrExist = false, isTelExist = false;
			if (profileGroupMembers != null) {
				for(ServiceGroupMemberProfileDTO grpMem: profileGroupMembers){
					if(LtsConstant.DAT_CD_NCR.equals(grpMem.getDatCd())){
						isNcrExist = true;
					}else if(LtsConstant.DAT_CD_TEL.equals(grpMem.getDatCd())){
						isTelExist = true;
					}
				}
				if(isNcrExist && !isTelExist){
					return true;
				}
			}
		}
		return false;
	}
	
	public static ItemDetailLtsDTO getSecDelTermPlanItem(ServiceDetailLtsDTO secDelService) {
		ItemDetailLtsDTO[] itemDetails = secDelService.getItemDtls();
		
		if (ArrayUtils.isEmpty(itemDetails)) {
			return null;
		}

		for (ItemDetailLtsDTO itemDetail : itemDetails) {
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_VAS_2DEL_HOT, itemDetail.getItemType())){ 
				return itemDetail;
			}
		}
		
		return null;
	}
	
	private static double getSelectedItemRecurrentAmt(List<ItemDetailDTO> itemDetailList) {
		if (itemDetailList != null && !itemDetailList.isEmpty()) {
			for (ItemDetailDTO itemDetail : itemDetailList) {
				if (itemDetail.isSelected()
						&& StringUtils.isNotEmpty(itemDetail.getRecurrentAmt())) {
					return Double.parseDouble(itemDetail.getRecurrentAmt()); 
				}
			}
		}
		return 0.0;
	}
	
	public static double getAdditionalFee(OrderCaptureDTO orderCapture, LtsBasketServiceFormDTO basketServiceForm, LtsBasketVasDetailFormDTO basketVasForm) {
		double additionalFee = 0.0;
		
		if (basketServiceForm != null) {
			additionalFee += getSelectedItemRecurrentAmt(basketServiceForm.getContentItemList());
			additionalFee += getSelectedItemRecurrentAmt(basketServiceForm.getMoovItemList());
		}
		
		if (basketVasForm != null) {
			if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
					&& LtsConstant.EYE_TYPE_EYE3A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
				additionalFee += getSelectedItemRecurrentAmt(basketVasForm.getFfpHotItemList());
				additionalFee += getSelectedItemRecurrentAmt(basketVasForm.getFfpOtherItemList());
				additionalFee += getSelectedItemRecurrentAmt(basketVasForm.getFfpStaffItemList());	
			}
		}
		return additionalFee;
	}
	
	public static double getNewTpArpu(OrderCaptureDTO orderCapture) {
		double newArpu = 0.0;
		
		if (orderCapture.getSelectedBasket() != null
				&& StringUtils.isNotBlank(orderCapture.getSelectedBasket().getEffectivePrice())) {
			return Double.parseDouble(orderCapture.getSelectedBasket().getEffectivePrice());
		}
		LtsBasketServiceFormDTO form = orderCapture.getLtsBasketServiceForm();
		if (form.getPlanItemList() != null && !form.getPlanItemList().isEmpty()) {
			for (ItemDetailDTO itemDetail : form.getPlanItemList()) {
				if (itemDetail.isSelected()
						&& StringUtils.isNotEmpty(itemDetail.getRecurrentAmt())) {
					newArpu += Double.parseDouble(itemDetail.getRecurrentAmt()); 
				}
			}
		}
		return newArpu;
	}
	
	public static double getNewArpu(OrderCaptureDTO orderCapture, LtsBasketServiceFormDTO basketServiceform, LtsBasketVasDetailFormDTO basketVasForm) {
		
		ServiceDetailProfileLtsDTO ltsServiceProfile = orderCapture.getLtsServiceProfile();
		String toProd = null;
		
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())) {
			toProd = LtsConstant.DEVICE_TYPE_1020.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType()) ? LtsConstant.LTS_PRODUCT_TYPE_EYE_2_A :
				LtsConstant.DEVICE_TYPE_EYE3A.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType()) ? LtsConstant.LTS_PRODUCT_TYPE_EYE_3_A : null;
		}
		else if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			toProd = StringUtils.defaultIfEmpty(orderCapture.getLtsServiceProfile().getExistEyeType(), LtsConstant.LTS_PRODUCT_TYPE_DEL);
		}
		
		double newArpu = getNewTpArpu(orderCapture);
		
		for (ItemDetailProfileLtsDTO profileItem : ltsServiceProfile.getItemDetails()) {
			// Other non-voice VAS
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_VAS, profileItem.getItemType())) {
				
				ItemActionLtsDTO itemAction = profileItem.getItemActionByToProd(toProd);
				
				if (itemAction != null && StringUtils.equals(LtsConstant.IO_IND_OUT, itemAction.getAction())) {
					continue;
				}
				
				if (!profileItem.isVoiceItem() && StringUtils.isNotEmpty(profileItem.getItemDetail().getGrossEffPrice())) {
					newArpu += Double.parseDouble(profileItem.getItemDetail().getGrossEffPrice());
				}
			}
		}
		

		return newArpu + getAdditionalFee(orderCapture, basketServiceform, basketVasForm);
	}
	
	public static double getCancelProfileItemAmt(LtsBasketVasDetailFormDTO form) {
		double arpuReduce = 0.0;
//		if (form.getProfileAutoOutVasItemList() != null && !form.getProfileAutoOutVasItemList().isEmpty()) {
//			for (ItemDetailProfileLtsDTO autoOutProfileItem : form.getProfileAutoOutVasItemList()) {
//				if (!autoOutProfileItem.isVoiceItem() && autoOutProfileItem.getItemDetail() != null) {
//					arpuReduce += Double.parseDouble(autoOutProfileItem.getItemDetail().getGrossEffPrice());
//				}
//			}
//		}
		
		if (form.getProfileExistingVasItemList() != null && !form.getProfileExistingVasItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO manualOutProfileItem : form.getProfileExistingVasItemList()) {
				if (!manualOutProfileItem.isVoiceItem() 
						&& manualOutProfileItem.getItemDetail() != null
						&& manualOutProfileItem.getItemDetail().isSelected()) {
					arpuReduce += Double.parseDouble(manualOutProfileItem.getItemDetail().getGrossEffPrice());
				}
			}
		}
		return arpuReduce;
	}
	
	public static double getProfileArpu(OrderCaptureDTO orderCapture) {
		double profileArpu = 0.0;
		ServiceDetailProfileLtsDTO ltsServiceProfile = orderCapture.getLtsServiceProfile();
		String orderType = orderCapture.getOrderType();
		if (ltsServiceProfile == null || ArrayUtils.isEmpty(ltsServiceProfile.getItemDetails())) {
			return profileArpu;
		}
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderType) 
				&& !orderCapture.isChannelPremier()) {
			profileArpu += getRenewalProfileTpArpu(ltsServiceProfile);
		}
		else {
			profileArpu += getProfileTpArpu(ltsServiceProfile, orderType);			
		}
		
		for (ItemDetailProfileLtsDTO profileItem : ltsServiceProfile.getItemDetails()) {
			// Other non-voice VAS
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_VAS, profileItem.getItemType())) {
				if (!profileItem.isVoiceItem() && StringUtils.isNotEmpty(profileItem.getItemDetail().getGrossEffPrice())) {
					profileArpu += Double.parseDouble(profileItem.getItemDetail().getGrossEffPrice());
				}
			}
		}
		return profileArpu;
	}
	
	public static boolean isFreeToGoService(ServiceDetailProfileLtsDTO ltsServiceProfile) {
		ItemDetailProfileLtsDTO currentTermPlan = getProfileServiceTermPlan(ltsServiceProfile);
		return 	StringUtils.isEmpty(currentTermPlan.getConditionEffEndDate())
				|| LtsDateFormatHelper.isDateOverdue(currentTermPlan.getConditionEffEndDate(), "dd/MM/yyyy");
	}
	
	public static ItemDetailProfileLtsDTO getExpiredTermPlanWithin6Mths(ServiceDetailProfileLtsDTO ltsServiceProfile) {
		if (ArrayUtils.isEmpty(ltsServiceProfile.getItemDetails())) {
			return null;
		}
		
		for (ItemDetailProfileLtsDTO profileItem : ltsServiceProfile.getItemDetails()) {
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_EXPIRED_SERVICE, profileItem.getItemType())
					&& profileItem.getItemDetail() != null) {
				if (StringUtils.isNotEmpty(profileItem.getConditionEffEndDate())
						&& LtsDateFormatHelper.getMonthDiff(profileItem.getConditionEffEndDate(), LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), "dd/MM/yyyy") <= 6) {
					return profileItem;
				}
			}
		}
		
		return null;
	}
	
	public static ItemDetailProfileLtsDTO getEndedTermPlanWithin6Mths(ServiceDetailProfileLtsDTO ltsServiceProfile) {
		if (ArrayUtils.isEmpty(ltsServiceProfile.getEndedItemDetails())) {
			return null;
		}
		
		for (ItemDetailProfileLtsDTO endedProfileItem : ltsServiceProfile.getEndedItemDetails()) {
			// Ended Core Term Plan
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_EXPIRED_SERVICE, endedProfileItem.getItemType())
					&& endedProfileItem.getItemDetail() != null) {
				if (StringUtils.isNotEmpty(endedProfileItem.getConditionEffEndDate())
						&& LtsDateFormatHelper.getMonthDiff(endedProfileItem.getConditionEffEndDate(), LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), "dd/MM/yyyy") <= 6) {
					return endedProfileItem;
				}
			}
		}
		
		return null;
	}
	
	public static double getRenewalProfileTpArpu(ServiceDetailProfileLtsDTO ltsServiceProfile) {
		
		double currentProfileTpArpu = 0.0;
		double expiredProfileTpArpu = 0.0;
		double endedProfileTpArpu = 0.0;
		if (ltsServiceProfile == null || 
				(ArrayUtils.isEmpty(ltsServiceProfile.getItemDetails())
						&& ArrayUtils.isEmpty(ltsServiceProfile.getEndedItemDetails()))) {
			return currentProfileTpArpu;
		}
	
		// if FTG, use last contract (within 6 month) arpu; 
		boolean isFreeToGo = isFreeToGoService(ltsServiceProfile);
		
		ItemDetailProfileLtsDTO currentTermPlanItem = getProfileServiceTermPlan(ltsServiceProfile);
		if (currentTermPlanItem != null && currentTermPlanItem.getItemDetail() != null
				&& StringUtils.isNotEmpty(currentTermPlanItem.getItemDetail().getGrossEffPrice())) {
			currentProfileTpArpu += Double.parseDouble(currentTermPlanItem.getItemDetail().getGrossEffPrice());
		}
		
		ItemDetailProfileLtsDTO expiredTermPlanItem = getExpiredTermPlanWithin6Mths(ltsServiceProfile);
		if (expiredTermPlanItem != null && expiredTermPlanItem.getItemDetail() != null 
				&& StringUtils.isNotEmpty(expiredTermPlanItem.getItemDetail().getGrossEffPrice())) {
			expiredProfileTpArpu += Double.parseDouble(expiredTermPlanItem.getItemDetail().getGrossEffPrice());
		}
		
		ItemDetailProfileLtsDTO endedTermPlanItem = getEndedTermPlanWithin6Mths(ltsServiceProfile);
		if (endedTermPlanItem != null && endedTermPlanItem.getItemDetail() != null
				&& StringUtils.isNotEmpty(endedTermPlanItem.getItemDetail().getGrossEffPrice())) {
			endedProfileTpArpu += Double.parseDouble(endedTermPlanItem.getItemDetail().getGrossEffPrice());
		}
		
		if (isFreeToGo) {
			if (expiredProfileTpArpu != 0.0 ) {
				if (endedProfileTpArpu == 0.0 || expiredProfileTpArpu <= endedProfileTpArpu) {
					return expiredProfileTpArpu;
				}
			}
			
			if (endedProfileTpArpu != 0.0) {
				if ( expiredProfileTpArpu == 0.0 || endedProfileTpArpu <= expiredProfileTpArpu) {
					return endedProfileTpArpu;	
				}
			}
		}
		return currentProfileTpArpu;
	}
	
	public static double getProfileTpArpu(ServiceDetailProfileLtsDTO ltsServiceProfile, String orderType) {
		
		double profileTpArpu = 0.0;
		
		if (ltsServiceProfile == null || ArrayUtils.isEmpty(ltsServiceProfile.getItemDetails())) {
			return profileTpArpu;
		}
		
		for (ItemDetailProfileLtsDTO profileItem : ltsServiceProfile.getItemDetails()) {
			
			// Core Term Plan
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_SERVICE, profileItem.getItemType())
					&& profileItem.getItemDetail() != null) {
				if (StringUtils.isNotEmpty(profileItem.getItemDetail().getGrossEffPrice())) {
					profileTpArpu += Double.parseDouble(profileItem.getItemDetail().getGrossEffPrice());
				}
			}
		}
		
		return profileTpArpu;
	}
	
	public static AccountDetailProfileLtsDTO getPrimaryAccount(ServiceDetailProfileLtsDTO serviceProfile) {
		if (ArrayUtils.isEmpty(serviceProfile.getAccounts())) {
			return null;
		}
		
		for (AccountDetailProfileLtsDTO profileAccount : serviceProfile.getAccounts()) {
			if (profileAccount.isPrimaryAcctInd()) {
				return profileAccount;
			}
		}
		
		return null;
	}
	
	public static AccountDetailProfileLtsDTO getAcqPrimaryAccount(AccountDetailProfileLtsDTO[] accountDetailProfileLtsDTO) {
		if (ArrayUtils.isEmpty(accountDetailProfileLtsDTO)) {
			return null;
		}
		
		for (AccountDetailProfileLtsDTO profileAccount : accountDetailProfileLtsDTO) {
			if (profileAccount.isPrimaryAcctInd()) {
				return profileAccount;
			}
		}
		
		return null;
	}
	
	public static ServiceDetailDTO getDisconnectService(SbOrderDTO sbOrder){
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if(LtsConstant.LTS_PRODUCT_TYPE_REMOVE.equals(serviceDetail.getToProd())){
				return serviceDetail;
				
			}
		}
		return null;
	}

	public static ServiceDetailDTO getImsService(SbOrderDTO sbOrder) {
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (isImsService(serviceDetail)) {
				return serviceDetail;
			}
		}
		return null;
	}
	
	public static ServiceDetailDTO getDuplexService(SbOrderDTO sbOrder) {
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (isDuplexService(serviceDetail)) {
				return serviceDetail;
			}
		}
		return null;
	}
	
	public static ServiceDetailDTO getNew2ndDelService(SbOrderDTO sbOrder) {
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (isNew2ndDelService(serviceDetail)) {
				return serviceDetail;
			}
		}
		return null;
	}
	
	public static ServiceDetailLtsDTO get2ndDelService(SbOrderDTO sbOrder) {
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (is2ndDelService(serviceDetail)) {
				return (ServiceDetailLtsDTO)serviceDetail;
			}
		}
		return null;
	}
	
	public static ServiceDetailDTO getUpgradeService(SbOrderDTO sbOrder) {
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (isUpgradeService(serviceDetail)) {
				return serviceDetail;
			}
		}
		return null;
	}
	
	public static boolean isUpgradeService(ServiceDetailDTO serviceDetail) {
		if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
			return false;
		}
		if (LtsConstant.LTS_SRV_TYPE_PE.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType()) ||
				LtsConstant.LTS_SRV_TYPE_SIP.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())) {
			return true;
		}
		return false;
	}

	public static boolean isRenewalService(ServiceDetailDTO serviceDetail) {
		if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
			return false;
		}
		if (((ServiceDetailLtsDTO)serviceDetail).getFromSrvType().equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())) {
			return true;
		}
		return false;
	}
	
	public static boolean isDuplexService(ServiceDetailDTO serviceDetail) {
		if (!(serviceDetail instanceof ServiceDetailLtsDTO) || isUpgradeService(serviceDetail)) {
			return false;
		}
		if (LtsConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO)serviceDetail).getFromSrvType())
				|| LtsConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO)serviceDetail).getFromSrvType())) {
			return true;
		}
		return false;
	}
	
	public static boolean isNew2ndDelService(ServiceDetailDTO serviceDetail) {
		if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
			return false;
		}
		if (LtsConstant.LTS_SRV_TYPE_NEW.equals(((ServiceDetailLtsDTO)serviceDetail).getFromSrvType()) &&
				LtsConstant.LTS_SRV_TYPE_2DEL.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())) {
			return true;
		}
		return false;
	}
	
	public static boolean isNewAcqDelService(ServiceDetailDTO serviceDetail) {
		if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
			return false;
		}
		if (LtsConstant.LTS_SRV_TYPE_NEW.equals(((ServiceDetailLtsDTO)serviceDetail).getFromSrvType()) &&
				LtsConstant.LTS_SRV_TYPE_DEL.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())) {
			return true;
		}
		return false;
	}
	
	public static boolean isNewAcqEyeService(ServiceDetailDTO serviceDetail) {
		if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
			return false;
		}
		if (LtsConstant.LTS_SRV_TYPE_NEW.equals(((ServiceDetailLtsDTO)serviceDetail).getFromSrvType())){
			if (LtsConstant.LTS_SRV_TYPE_PE.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType()) ||
					LtsConstant.LTS_SRV_TYPE_SIP.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isImsService(ServiceDetailDTO serviceDetail) {
		if (!(serviceDetail instanceof ServiceDetailOtherLtsDTO)) {
			return false;
		}
		return true;
	}
	
	public static boolean isFutureProfileAction(String pAction) {
		return StringUtils.equals(pAction, LtsConstant.IO_IND_INSTALL) || StringUtils.equals(pAction, LtsConstant.IO_IND_SPACE);
	}
	
	public static boolean isCurrentProfileAction(String pAction) {
		return StringUtils.equals(pAction, LtsConstant.IO_IND_OUT) || StringUtils.equals(pAction, LtsConstant.IO_IND_SPACE);
	}
	
	public static boolean isContainString(String[] pStrings, String pString) {
		
		for (int i = 0; i < pStrings.length; i++) {
			if (StringUtils.equals(pStrings[i], pString)) {
				return true;
			}
		}
		return false;
	}
	
	public static NowTvServiceDetailDTO getNowTvServiceProfile(OrderCaptureDTO orderCapture) {

		if (orderCapture == null || orderCapture.getLtsModemArrangementForm() == null 
				|| StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			return null;
		}
		List<FsaDetailDTO> fsaDetailList = null; 
		
		switch (orderCapture.getLtsModemArrangementForm().getModemType()) {
		case SHARE_EX_FSA:
				fsaDetailList = orderCapture.getLtsModemArrangementForm().getExistingFsaList();
			break; 
		case SHARE_OTHER_FSA:	
			fsaDetailList = orderCapture.getLtsModemArrangementForm().getOtherFsaList();
			break; 
		default:
			break;
		}
		
		if (fsaDetailList != null && !fsaDetailList.isEmpty()) {
			for (FsaDetailDTO fsaDetail : fsaDetailList) {
				if (fsaDetail.isSelected()) {
					return fsaDetail.getNowTvServiceDetail();
				}
			}
		}
		return null;
	}
	
	public static boolean isNewNowTvService(OrderCaptureDTO orderCapture) {

		if (orderCapture == null || orderCapture.getLtsModemArrangementForm() == null) {
			return false;
		}
		
		List<FsaDetailDTO> fsaDetailList = null;
		
		switch (orderCapture.getLtsModemArrangementForm().getModemType()) {
		case SHARE_TV: 
		case SHARE_BUNDLE:
			return true;
		case SHARE_EX_FSA: 
			fsaDetailList = orderCapture.getLtsModemArrangementForm().getExistingFsaList();
			break;
		case SHARE_OTHER_FSA:
			fsaDetailList = orderCapture.getLtsModemArrangementForm().getOtherFsaList();
			break;
		default:
			break;
		}
		
		if (fsaDetailList == null || fsaDetailList.isEmpty()) {
			return false;
		}
		
		for (FsaDetailDTO fsaDetail : fsaDetailList) {
			if (fsaDetail.isSelected()) {
				if (FsaServiceType.PCD_HDTV == fsaDetail.getNewService()
						|| FsaServiceType.PCD_SDTV == fsaDetail.getNewService()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static ServiceDetailLtsDTO[] getDuplexServices(ServiceDetailDTO[] pSrvDtls) {
		
		List<ServiceDetailLtsDTO> duplexSrvList = new ArrayList<ServiceDetailLtsDTO>();
		
		for (int i=0; pSrvDtls!=null && i<pSrvDtls.length; ++i) {
			if (pSrvDtls[i] instanceof ServiceDetailLtsDTO && StringUtils.isNotEmpty(((ServiceDetailLtsDTO)pSrvDtls[i]).getDuplexInd())) {
				duplexSrvList.add((ServiceDetailLtsDTO)pSrvDtls[i]);
			}
		}
		return duplexSrvList.toArray(new ServiceDetailLtsDTO[duplexSrvList.size()]);
	}
	
	public static String quotationFilter(String pString){
		return StringUtils.replaceChars(pString, "\"", "'");
	}
	
	public static String newLineHtmlFilter(String pString){
		return StringUtils.replace(pString, "\n", "<br/>");
	}
	
	public static String getSalesMemoNum(ServiceDetailDTO serviceDetail) {
		PaymentMethodDetailLtsDTO[] paymentMethodDetails = serviceDetail.getAccount().getPaymethods();
		
		if (ArrayUtils.isEmpty(paymentMethodDetails)) {
			return null;
		}
		
		for (PaymentMethodDetailLtsDTO paymentMethodDetail : paymentMethodDetails) {
			if ((StringUtils.equals(LtsConstant.IO_IND_INSTALL, paymentMethodDetail.getAction()) 
					|| StringUtils.equals(LtsConstant.IO_IND_SPACE, paymentMethodDetail.getAction()))
					&& StringUtils.isNotBlank(paymentMethodDetail.getSalesMemoNum())) {
				return paymentMethodDetail.getSalesMemoNum();
			}
				
		}
		return null;
	}
		
	public static SignatureLtsDTO[] convertSignature(List<SignatureDTO> pSigns) {
		
		if (pSigns == null || pSigns.size() == 0) {
			return null;
		}
		SignatureLtsDTO[] signs = new SignatureLtsDTO[pSigns.size()];
		
		for (int i=0; i<signs.length; ++i) {
			signs[i] = new SignatureLtsDTO();
			signs[i].setSignatureBytes(pSigns.get(i).getSignatureBytes());
			signs[i].setSignType(pSigns.get(i).getSignType().name());
		}
		return signs;
	}
	
	public static double getAdditionalFee(LtsAcqBasketServiceFormDTO form) {
		double additionalFee = 0.0;
		
		if (form.getContentItemList() != null && !form.getContentItemList().isEmpty()) {
			for (ItemDetailDTO itemDetail : form.getContentItemList()) {
				if (itemDetail.isSelected()
						&& StringUtils.isNotEmpty(itemDetail.getRecurrentAmt())) {
					additionalFee += Double.parseDouble(itemDetail.getRecurrentAmt()); 
				}
			}
		}
		if (form.getMoovItemList() != null && !form.getMoovItemList().isEmpty()) {
			for (ItemDetailDTO itemDetail : form.getMoovItemList()) {
				if (itemDetail.isSelected()
						&& StringUtils.isNotEmpty(itemDetail.getRecurrentAmt())) {
					additionalFee += Double.parseDouble(itemDetail.getRecurrentAmt()); 
				}
			}
		}
		return additionalFee;
	}
	
	public static boolean isPrewiringVasItemSelected(SbOrderDTO sbOrder){
		ServiceDetailLtsDTO srvDtl = LtsSbOrderHelper.getLtsService(sbOrder);
		for(ItemDetailLtsDTO item: srvDtl.getItemDtls()){
			if(StringUtils.equals(item.getItemType(), LtsConstant.ITEM_TYPE_VAS_PRE_WIRING)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isProfileMirror(OrderCaptureDTO orderCapture) {
		ItemDetailProfileLtsDTO[] profileItems = orderCapture.getLtsServiceProfile().getItemDetails();
		if (ArrayUtils.isEmpty(profileItems)) {
			for (ItemDetailProfileLtsDTO profileItem : profileItems) {
				if (LtsConstant.ITEM_TYPE_MIRROR_PLAN.equals(profileItem.getItemType())
						|| LtsConstant.ITEM_TYPE_EXPIRED_MIRROR_PLAN.equals(profileItem.getItemType())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String generateWgLoginId(String srvNum) {
		return StringUtils.lowerCase(LtsConstant.FSA_SRV_TYPE_WG) + getDisplaySrvNum(srvNum);
	}
	
	public static double getTenure(OrderCaptureDTO orderCaptureDTO) {

		double ltsTenure = orderCaptureDTO.getLtsServiceProfile()
				.getLtsTenure();
		double imsTenure = orderCaptureDTO.getLtsServiceProfile()
				.getImsTenure();

		return ltsTenure > imsTenure ? ltsTenure : imsTenure;
	}
	
	public static ItemSetCriteriaDTO getItemSetCriteria(
			OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser, String itemSetType, String locale) {
		
		ItemSetCriteriaDTO itemSetCriteria = new ItemSetCriteriaDTO();
		itemSetCriteria.setBasketId(orderCapture.getLtsBasketSelectionForm().getSelectedBasketId());
		itemSetCriteria.setLocale(locale);
		itemSetCriteria.setItemSetType(itemSetType);
		itemSetCriteria.setEligibleItemTypeList(Lists.newArrayList(LtsConstant.ITEM_TYPE_PLAN));
		itemSetCriteria.setSelectDefault(true);
		itemSetCriteria.setChannelId(orderCapture.getBasketChannelId());
		itemSetCriteria.setDisplayType(LtsConstant.DISPLAY_TYPE_ITEM_SELECT);
		itemSetCriteria.setApplnDate(orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		itemSetCriteria.setAreaCode(bomSalesUser.getAreaCd());
		itemSetCriteria.setTeamCode(bomSalesUser.getShopCd());
		itemSetCriteria.setSrvTenure(getTenure(orderCapture));
		itemSetCriteria.setEligibleDocType(getTargetCustomerDocType(orderCapture));
		
		if (LtsConstant.ITEM_SET_TYPE_PREMIER_TEAM_VAS.equals(itemSetType) 
				|| LtsConstant.ITEM_SET_TYPE_TEAM_VAS.equals(itemSetType)) {
			itemSetCriteria.setBaseContractPeriod(orderCapture.getSelectedBasket().getContractPeriod());
		}
		
		return itemSetCriteria;
	}
	
	public static ItemSetCriteriaDTO getItemSetCriteria(
			AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser, String itemSetType, String locale) {
		
		ItemSetCriteriaDTO itemSetCriteria = new ItemSetCriteriaDTO();
		itemSetCriteria.setBasketId(acqOrderCapture.getLtsAcqBasketSelectionForm().getSelectedBasketId());
		itemSetCriteria.setLocale(locale);
		itemSetCriteria.setItemSetType(itemSetType);
		itemSetCriteria.setEligibleItemTypeList(Lists.newArrayList(LtsConstant.ITEM_TYPE_PLAN));
		itemSetCriteria.setSelectDefault(true);
		itemSetCriteria.setChannelId(acqOrderCapture.getBasketChannelId());
		itemSetCriteria.setDisplayType(LtsConstant.DISPLAY_TYPE_ITEM_SELECT);
		itemSetCriteria.setAreaCode(bomSalesUser.getAreaCd());
		itemSetCriteria.setTeamCode(bomSalesUser.getShopCd());
		itemSetCriteria.setApplnDate(DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy hh:mm")));
		itemSetCriteria.setHousingType(acqOrderCapture.getAddressRollout().getHousingType());
		itemSetCriteria.setSrvBoundary(acqOrderCapture.getAddressRollout().getSrvBdary());
		return itemSetCriteria;
	}

	public static ItemCriteriaBuilder getItemCriteriaDefaultBuilder (OrderCaptureDTO orderCapture, String locale){
		if(orderCapture == null){
			return null;
		}
		
		String basketChannelId = orderCapture.getBasketChannelId();
		String applnDate = orderCapture.getLtsMiscellaneousForm() == null ? null : orderCapture.getLtsMiscellaneousForm().getApplicationDate();
		String osType = orderCapture.getSelectedBasket() == null?  null : orderCapture.getSelectedBasket().getOsType();
		
		ItemCriteriaBuilder criteriaBuilder = new ItemCriteriaBuilder()
			.setLocale(locale)
			.setChannelId(basketChannelId)
			.setDisplayType(LtsConstant.DISPLAY_TYPE_ITEM_SELECT)
			.setGetAttrInd(true)
			.setSelectDefault(true)
			.setApplnDate(applnDate == null ? DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy hh:mm")) : applnDate)
			.setOsType(osType);		
		
		return criteriaBuilder;
	}
	
	public static ItemCriteriaBuilder getItemCriteriaDefaultBuilder (String basketChannelId, String locale, String applnDate){
		
		ItemCriteriaBuilder criteriaBuilder = new ItemCriteriaBuilder()
			.setLocale(locale)
			.setChannelId(basketChannelId)
			.setDisplayType(LtsConstant.DISPLAY_TYPE_ITEM_SELECT)
			.setGetAttrInd(true)
			.setSelectDefault(true)
			.setApplnDate(applnDate == null ? DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy hh:mm")) : applnDate);		
		
		return criteriaBuilder;
	}
	
    public static ItemCriteriaBuilder getAcqItemCriteriaDefaultBuilder (String basketChannelId, String locale, String osType){
		
		ItemCriteriaBuilder criteriaBuilder = new ItemCriteriaBuilder()
			.setLocale(locale)
			.setChannelId(basketChannelId)
			.setDisplayType(LtsConstant.DISPLAY_TYPE_ITEM_SELECT)
			.setGetAttrInd(true)
			.setSelectDefault(true)
			.setOsType(osType);		
		
		return criteriaBuilder;
	}
	
	public static ItemCriteriaDTO getItemCriteria (String itemType, String basketChannelId, String locale, String applnDate){
		return getItemCriteriaDefaultBuilder(basketChannelId, locale, applnDate).setItemType(itemType).build();
	}
	
	public static String generateDefaultPassword(int maxLength){
		Calendar seed = Calendar.getInstance();
		Random generater = new Random(seed.getTimeInMillis());
		int random = generater.nextInt();
		String randomNum = String.valueOf(Math.abs(random%9999));
		while(randomNum.length() < maxLength){ 
			randomNum = "0" + randomNum;
		}
		return randomNum;
	}

	public static boolean isAgeOverByMonth(String dob){
		if(StringUtils.isNotBlank(dob)){
			dob = "01" + StringUtils.substring(dob, 2);
		}
		return isAgeOver(dob, LtsDateFormatHelper.getSysDate("dd/MM/yyyy"));
	}
	
	public static boolean isAgeOver(String dob){
		return isAgeOver(dob, LtsDateFormatHelper.getSysDate("dd/MM/yyyy"));
	}
	
	public static boolean isAgeOver(String dob, String compareDate){
		return isAgeOver(dob, compareDate, LtsConstant.AGE_OF_ELDERLY_APPLICATION);
	}
	
	public static boolean isAgeOver(String dob, String compareDate, int overAge){
		if(StringUtils.isNotBlank(dob)){
			Period diff = new Period(LocalDate.parse(dob, DateTimeFormat.forPattern("dd/MM/yyyy")), LocalDate.parse(compareDate,
					DateTimeFormat.forPattern("dd/MM/yyyy")));
			if(diff.getYears() >= overAge){
				return true;
			}
		}
		return false;
	}
	
	public static String getProfileDateOfBirth(CustomerDetailProfileLtsDTO custProfile) {
		if (custProfile == null || StringUtils.isBlank(custProfile.getDob())) {
			return null;
		}
		String dob = custProfile.getDob();
		String dateFormat = "dd/MM/yyyy";
		if (dob.length() > 10) {
			dob = StringUtils.substring(dob, 0, 10);
			dateFormat = "yyyy-MM-dd";
		}
		return LtsDateFormatHelper.reformatDate(dob, dateFormat, "dd/MM/yyyy");
	}

	public static String getSelectedLineType(LtsModemArrangementFormDTO form) {
		if (form == null 
				|| !ModemType.STANDALONE.equals(form.getModemType())
				|| form.getLineTypeSelectionList() == null
				|| form.getLineTypeSelectionList().isEmpty()) {
			return null;
		}
		
		for (LineTypeSelectionDTO lineType : form.getLineTypeSelectionList()) {
			if (lineType.isSelected()) {
				return lineType.getTechnology();
			}
		}
		return null;
	}
	
	public static String getNoEyeFsa(LtsModemArrangementFormDTO form) {
		
		if (form == null || form.getExistingFsaList() == null 
				|| form.getExistingFsaList().isEmpty()) {
			return null;
		}
		
		StringBuilder noEyeFsaList = new StringBuilder();
		for (FsaDetailDTO fsaDetail : form.getExistingFsaList()) {
			if (fsaDetail.getFsaProfile() != null 
					&& StringUtils.equals("Y", fsaDetail.getFsaProfile().getNoEyeInd())) {
				if (noEyeFsaList.length() != 0) {
					noEyeFsaList.append(" || ");	
				}
				noEyeFsaList.append(fsaDetail.getFsa());
			}
		}
		
		return noEyeFsaList.toString();
	}
	
	public static String[] addresslinesFixer(String[] addresslines){
		List<String> addresslineList = new ArrayList<String>();
		for(String addressline: addresslines){
			if(StringUtils.isNotBlank(addressline)){
				addresslineList.add(addressline);
			}
		}
		
		return addresslineList.toArray(new String[0]);
	}
	
	public static boolean allow4BBHProfile(ServiceDetailProfileLtsDTO serviceProfile) {

		// 1. HKID number = A/B
		// 2. Passport number = DUMMY + A/B
		// 3. DOB = aged 60 or above.
		CustomerDetailProfileLtsDTO customerProfile = serviceProfile.getPrimaryCust();
		if (customerProfile != null) {
			if (LtsConstant.DOC_TYPE_HKID.equals(customerProfile.getDocType())
					&& (StringUtils.startsWithIgnoreCase(customerProfile.getDocNum(), "A")
							|| StringUtils.startsWithIgnoreCase(customerProfile.getDocNum(), "B"))) {
				return true;
			}
			
			if (LtsConstant.DOC_TYPE_PASSPORT.equals(customerProfile.getDocType())) {
				for (String dummyDocPattern : LtsConstant.CSP_DUMMY_DOC_STRINGS) {
					if (StringUtils.startsWithIgnoreCase(customerProfile.getDocNum(), dummyDocPattern)) {
						String tempDocNum = StringUtils.removeStartIgnoreCase(customerProfile.getDocNum(), dummyDocPattern);
						if (StringUtils.startsWithIgnoreCase(tempDocNum, "A")
								|| StringUtils.startsWithIgnoreCase(tempDocNum, "B")) {
							return true;
						}
					}
				}
			}
			
			if (StringUtils.isNotEmpty(customerProfile.getDob()) 
					&& isAgeOver(getProfileDateOfBirth(customerProfile), LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), 60)) {
				return true;
			}
		}
		
		// 4. eye2 start date on / before Dec 31, 2013.
		try {
			ItemDetailProfileLtsDTO[] profileItems = serviceProfile.getItemDetails();
			final Date compareDate = DateUtils.parseDate("01/01/2014", new String[] {"dd/MM/yyyy"});
			if (ArrayUtils.isNotEmpty(profileItems)) {
				for (ItemDetailProfileLtsDTO profileItem : profileItems) {
					OfferDetailProfileLtsDTO[] offerDetails = profileItem.getOffers(); 
					if (ArrayUtils.isNotEmpty(offerDetails)) {
						for (OfferDetailProfileLtsDTO offerDetail : offerDetails) {
							if (offerDetail.getPsefSet() != null
									&& offerDetail.getPsefSet().contains(LtsConstant.RESTRICTED_PSEF_4BBH)) {
								if (StringUtils.isNotBlank(offerDetail.getEffStartDate())) {
									Date offerStartDate = DateUtils.parseDate(offerDetail.getEffStartDate(),  new String[] {"dd/MM/yyyy"});
									if (offerStartDate.before(compareDate)) {
										return true;
									}
								}
							}
						}
					}
				}
			}	
		}
		catch (Exception e) {
			throw new AppRuntimeException(ExceptionUtils.getFullStackTrace(e));
		}
		return false;
	}
	
	public static boolean isRecontractOrder(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsMiscellaneousForm().isRecontract() && orderCapture.getLtsRecontractForm() != null) {
			return true;
		}
		return false;
	}
	
	
	public static boolean isOptOutAllMeans(LtsCustomerIdentificationFormDTO custIdenForm){
		if (custIdenForm == null) {
			return false;
		}
		if (custIdenForm.isLtsPdpoEmail() 
				&& custIdenForm.isLtsPdpoDirectMailing()
				&& custIdenForm.isLtsPdpoOutbound()
				&& custIdenForm.isLtsPdpoSms()
				&& custIdenForm.isLtsPdpoWebBill()
				&& custIdenForm.isLtsPdpoBm()
				&& custIdenForm.isLtsPdpoBillInsert()
				&& custIdenForm.isLtsPdpoSmsEye()
				&& custIdenForm.isLtsPdpoCdOutdial() ) {
			return true;
		}
		return false;
	}
	
	public static boolean isDeceasedCase(LtsTerminateServiceSelectionFormDTO form) {
		return LtsConstant.DISCONNECT_REA_CD_DECEASED.equals(form.getDisconnectReason());
	}
	
	public static boolean isSplitAccount(ServiceDetailProfileLtsDTO ltsServiceProfile){
		
		if (ArrayUtils.isEmpty(ltsServiceProfile.getAccounts()) || ltsServiceProfile.getAccounts().length < 2) {
			return false;
		}
		
		for(AccountDetailProfileLtsDTO profileAcctDtl: ltsServiceProfile.getAccounts()){
			String chrgType = profileAcctDtl.getAcctChrgType();
			if(chrgType.contains("R") && !chrgType.contains("I")
					|| chrgType.contains("I") && !chrgType.contains("R")){
				return true;
			}
		}
		return false;
	}
	
	public static String getLostModemInd(SbOrderDTO sbOrder){
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO) LtsSbOrderHelper.getImsService(sbOrder);
		if(imsService != null && StringUtils.isNotBlank(imsService.getLostModem())){
			return imsService.getLostModem();
		}
		return null;
	}
	
	public static boolean isForceRetainOutItem(OrderCaptureDTO orderCapture) {
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		if (sbOrder != null) {
			ServiceDetailLtsDTO ltsCoreService = getLtsService(sbOrder);
			ItemDetailLtsDTO[] itemDetails = ltsCoreService.getItemDtls();
			if (ArrayUtils.isNotEmpty(itemDetails)) {
				for (ItemDetailLtsDTO itemDetail : itemDetails) {
					if (LtsConstant.IO_IND_OUT.equals(itemDetail.getIoInd()) &&
							StringUtils.equalsIgnoreCase("Y", itemDetail.getForceRetain())) {
						return true;
					}
				}
			}
		}
		else if (orderCapture.getLtsBasketVasDetailForm() != null) {
			List<ItemDetailProfileLtsDTO> autoOutProfileVasItemList = orderCapture.getLtsBasketVasDetailForm().getProfileAutoOutVasItemList();
			if (autoOutProfileVasItemList != null && !autoOutProfileVasItemList.isEmpty()) {
				for (ItemDetailProfileLtsDTO autoOutProfileVasItem : autoOutProfileVasItemList) {
					if (autoOutProfileVasItem.isForceRetain()) {
						return true;
					} 
				}
			}
		}
		return false;
	}
	
	public static String getBasketDisplayType(SbOrderDTO sbOrder) {		
		String displayType = null;	
		if (isOnlineAcquistionOrder(sbOrder)) {
			displayType = LtsConstant.DISPLAY_TYPE_ONLINE_DESC;
		}else{
			displayType = LtsConstant.DISPLAY_TYPE_RP_PROMOT;
		}
		return displayType;
	}
	
	public static String getBasketId(ServiceDetailLtsDTO ltsServiceDetail) {		
		if(ltsServiceDetail.getItemDtls() != null){
			for (ItemDetailLtsDTO itemDetail : ltsServiceDetail.getItemDtls()) {
				if (StringUtils.isNotBlank(itemDetail.getBasketId())) {
					return itemDetail.getBasketId();
				}
			}
		}
		return null;
	}
	
	public static CustomerDetailProfileLtsDTO  getTargetCustomerProfile(OrderCaptureDTO order){
		if(isRecontractOrder(order)){
			return order.getLtsRecontractForm().getCustDetailProfile();
		}else{
			return order.getLtsServiceProfile().getPrimaryCust();
		}
	}

	public static String  getTargetCustomerDocType(OrderCaptureDTO order){
		if(isRecontractOrder(order)){
			if(order.getLtsRecontractForm().getCustDetailProfile() != null){
				return order.getLtsRecontractForm().getCustDetailProfile().getDocType();
			}else{
				return order.getLtsRecontractForm().getDocType();
			}
		}else{
			return order.getLtsServiceProfile().getPrimaryCust().getDocType();
		}
	}
	
	public static boolean isEyeProfileParalleExtension(ServiceDetailProfileLtsDTO serviceProfile) {
		ServiceGroupProfileDTO serviceGroup = serviceProfile.getSrvGrp();
		//return false if not eye profile
		if (serviceGroup == null || ArrayUtils.isEmpty(serviceGroup.getGrpMems())) {
			return false;
		}
		
		for (ServiceGroupMemberProfileDTO srvGrpMem : serviceGroup.getGrpMems()) {
			if (StringUtils.equals(serviceProfile.getSrvNum(), srvGrpMem.getSrvNum())) {
				return LtsConstant.DAT_CD_DEL.equals(srvGrpMem.getDatCd());
			}
		}
		
		return false;
	}
	
	public static boolean isEyeProfile(OrderCaptureDTO orderCapture){
		if(orderCapture.getLtsServiceProfile().getExistEyeType() == null
				&& ( orderCapture.getLtsServiceProfile().getSrvGrp() == null
						|| StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getSrvGrp().getGrpId()) )){
			return false;
		}
		
		return true;
	}
	
}

