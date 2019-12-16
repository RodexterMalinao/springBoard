package com.bomwebportal.lts.validator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.util.Utility;


public class LtsPaymentValidator implements Validator{
	
	private LtsCommonService ltsCommonService;
	private LtsSalesInfoService ltsSalesInfoService;
	
	
	public boolean supports(Class clazz) {
		return LtsPaymentFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsPaymentFormDTO form = (LtsPaymentFormDTO)command;
		
		if(form.getSubmitInd().compareTo("SUBMIT") == 0){
			if(form.getSelectButton() != null){
				if(StringUtils.equals(form.getSelectButton(), "")){ //keepExist selected
					if(StringUtils.equals(form.getExistingPayMethodType(), "A")
							|| StringUtils.equals(form.getExistingPayMethodType(), "M")){
						if(form.isPrepayItemSelectedE() 
								&& form.isSalesMemoNumRequired()
								&& StringUtils.isBlank(form.getSalesMemoNum())){
							errors.rejectValue("salesMemoNum", "lts.salesmemonum.required");
						}
					}
					
//					if(StringUtils.isNotBlank(form.getRecontractAccountNo())){
//						if(StringUtils.equals(form.getExistingPayMethodType(), "C")){
//							validateCreditCardForm(form, errors);
//						}
//					}
				}else if(StringUtils.equals(form.getSelectButton(), "A")){
					validateAutoPayForm(form, errors);
					validateChangeReason(form, errors);
				}else if(StringUtils.equals(form.getSelectButton(), "C")){
					validateCreditCardForm(form, errors);
					validateChangeReason(form, errors);
				}
			}else{
				errors.rejectValue("selectButton", "lts.paymenttype.required");
			}
			
			if(StringUtils.isNotBlank(form.getSelectedBillItemId())){
				for(ItemDetailDTO itemDtl: form.getBillItemList()){
					itemDtl.setSelected(itemDtl.getItemId().equals(form.getSelectedBillItemId()));
				}
			}
			
			if(!form.isCsPortalExist() && !form.isCspNewReg()){
				if(form.getCsPortalItem() == null || !form.getCsPortalItem().isSelected()){
					boolean isBillItemSelected = false;
					if(form.getCsPortalItem() != null 
							&& form.getCsPortalItem().isSelected()){
						isBillItemSelected = true;
					}else{
						if(StringUtils.isNotBlank(form.getSelectedBillItemId())){
							isBillItemSelected = true;
						}else if(form.getBillItemList() != null
								&& form.getBillItemList().size() > 0){
							for(ItemDetailDTO item: form.getBillItemList()){
								if(item.isSelected()){
									isBillItemSelected = true;
									break;
								}
							}
							
						}
					}
					if(!isBillItemSelected){
						errors.rejectValue("selectedBillItemId", "lts.paperbill.required");
					}
				}
			}
			
				
			if(form.getPaperBillItem() != null 
					&& form.getPaperBillItem().isSelected() 
					&& StringUtils.equals(form.getPaperBillCharge(),"NONE") 
					&& form.isAllowModifyInd()
					&& !form.isDocTypeBR()){
				errors.rejectValue("paperBillCharge", "lts.paperbillcharge.required");
		    }
			
			
			if(StringUtils.equals(form.getPaperBillCharge(), "W") ){
               if(form.isAllowModifyInd() && StringUtils.isEmpty(form.getPaperWaiveReason())){
            	   errors.rejectValue("paperWaiveReason", "lts.paperbillreason.required");
               }else if(form.isAllowModifyInd() && StringUtils.equals(LtsConstant.PAPER_BILL_WAIVE_REASON_OTHER, form.getPaperWaiveReason())){
            	   if(StringUtils.isNotEmpty(form.getPaperBillWaiveOtherStaffId())){
            		   String category = ltsSalesInfoService.getStaffCategory(form.getPaperBillWaiveOtherStaffId());
						if(category == null){
							errors.rejectValue("paperWaiveReason", "lts.invalid.staffId");
						}else if(!Arrays.asList(LtsConstant.SALES_ROLES_ALL_MANAGERS).contains(category)){
							errors.rejectValue("paperWaiveReason", "lts.acq.staff.manager.required");
						}
            	   }else{
            		   errors.rejectValue("paperWaiveReason", "lts.staffId.required");
            	   }
               }
			}
			
			if(StringUtils.equals(form.getOptOut(),"Y")){ 
				if(form.getCsPortalItem() != null 
					&& !StringUtils.equals(form.getCsPortalItem().getItemType(),LtsBackendConstant.ITEM_TYPE_MYHKT_BILL))
				{
						if(StringUtils.isBlank(form.getOptOutReason())){
							errors.rejectValue("optOutReason", "lts.csp.optoutreason.required");
						}
				}
			}
			
			if(StringUtils.equals(form.getOptOutReason(), LtsConstant.CSP_OPT_OUT_REASON_OTHER)){
				if(StringUtils.isBlank(form.getOptOutRemark())){
					errors.rejectValue("optOutRemark", "lts.csp.optoutremark.required");
				}
			}		
			
			if(StringUtils.length(form.getOptOutRemark()) > 500){
				errors.rejectValue("optOutRemark", "lts.csp.optoutremark.tooLong");
			}
			
			if(!form.isCsPortalExist() && (StringUtils.contains(form.getCspEmail(),LtsConstant.CSP_DUMMY_EMAIL_DOMAIN )
					|| StringUtils.equals(form.getCspMobile(),LtsConstant.CSP_DUMMY_MOBILE_NUM))){
				form.setDummy(true);
			}else if (form.isCsPortalExist() && (StringUtils.contains(form.getClubEmail(),LtsConstant.CSP_DUMMY_EMAIL_DOMAIN )
					|| StringUtils.equals(form.getClubMobile(),LtsConstant.CSP_DUMMY_MOBILE_NUM))){
				form.setDummy(true);
			}else{
				form.setDummy(false);
			}
			
			if(form.isAllowChangeBa() && form.isChangeBa()){
				if (StringUtils.isBlank(form.getBillingAddress())) {
					errors.rejectValue("billingAddress", "lts.billingAddress.required");	
				}else{
					form.setBillingAddress(LtsCommonValidator.removeBaUnwantedBlanks(form.getBillingAddress()));
					if(!LtsCommonValidator.isValidBaFormat(form.getBillingAddress())){
						errors.rejectValue("billingAddress", "lts.invalid.billingAddress");	
					}else if(LtsCommonValidator.containsSpecialChar(form.getBillingAddress())){
						errors.rejectValue("billingAddress", "lts.invalid.billingAddress.specialChar");	
					}
				}
			}

			
			boolean selectedEmailBill = false;
			boolean selectedRedemMediaEmail = false;
			
			if (form.getEmailBillItem() != null && form.getEmailBillItem().isSelected()) {
				selectedEmailBill = true;
				if (StringUtils.isEmpty(form.getEmailBillAddress())) {
					errors.rejectValue("emailBillAddress", "lts.distributeEmail.required");
				}
				else {
					if (!LtsCommonValidator.isValidEmail(form.getEmailBillAddress())) {
						errors.rejectValue("emailBillAddress", "lts.distributeEmail.invalid");
					}
				}	
			}
			
			if (form.isRequireRedemPremium()) {
				if (StringUtils.isEmpty(form.getRedemptionMedia())) {
					errors.rejectValue("redemptionMedia", "lts.redemptionMedia.required");
				}
				else {
					if (LtsConstant.REDEMPTION_MEDIA_SMS.equals(form.getRedemptionMedia())) {
						if (StringUtils.isEmpty(form.getRedemMediaSmsNum())) {
							errors.rejectValue("redemMediaSmsNum", "lts.distributeSms.required");	
						}
						else {
							ltsCommonService.validateSmsNum(form.getRedemMediaSmsNum(), errors, "redemMediaSmsNum");
						}
					}
					if (LtsConstant.REDEMPTION_MEDIA_EMAIL.equals(form.getRedemptionMedia())) {
						selectedRedemMediaEmail = true;
						if (StringUtils.isEmpty(form.getRedemMediaEmailAddr())) {
							errors.rejectValue("redemMediaEmailAddr", "lts.distributeEmail.required");	
						}
						else {
							if (!LtsCommonValidator.isValidEmail(form.getRedemMediaEmailAddr())) {
								errors.rejectValue("redemMediaEmailAddr", "lts.distributeEmail.invalid");	
							}
						}
					}
				}
			}
			
			if (selectedEmailBill && selectedRedemMediaEmail) {
				if (!StringUtils.equalsIgnoreCase(form.getRedemMediaEmailAddr(), form.getEmailBillAddress())) {
					errors.rejectValue("redemMediaEmailAddr", "lts.emailbill.redemptionEmail.notMatch");
					errors.rejectValue("emailBillAddress", "lts.emailbill.redemptionEmail.notMatch");
				}
			}
			
		}else{
			if(StringUtils.isBlank(form.getSuspendReason()) 
					|| StringUtils.equals(form.getSuspendReason(), "NONE")){
				errors.rejectValue("suspendReason", "lts.reason.required");
			}
		}
		
		
	}
	
	private void validateChangeReason(LtsPaymentFormDTO ltsPaymentFormDTO, Errors errors){
//		if(!StringUtils.equals(ltsPaymentFormDTO.getExistingPayMethodType(), LtsConstant.PAYMENT_TYPE_CASH)){
//			if(StringUtils.isBlank(ltsPaymentFormDTO.getChangeReason())){
//				errors.rejectValue("changeReason", "lts.reason.required");
//			}
//		}
	}
	
	private void validateAutoPayForm(LtsPaymentFormDTO ltsPaymentFormDTO, Errors errors){
		/*if(ltsPaymentFormDTO.getThirdPartyBankAccount() == null){
			errors.rejectValue("thirdPartyBankAccount", "lts.thirdPartyApplication.required");
		}else if(ltsPaymentFormDTO.getThirdPartyBankAccount()){
			if(StringUtils.equals(ltsPaymentFormDTO.getBankAccHolderDocNum(), ltsPaymentFormDTO.getCustDocNum())
					&& StringUtils.equals(ltsPaymentFormDTO.getBankAccHolderDocType(), ltsPaymentFormDTO.getCustDocType())){
				errors.rejectValue("bankAccHolderDocNum", "lts.invaild.thirdpartyinfo");
			}
		}*/
		if (StringUtils.isBlank(ltsPaymentFormDTO.getBankAccHolderName())) {
			errors.rejectValue("bankAccHolderName", "lts.holdername.required");
		}
		try{
			if (StringUtils.isBlank(ltsPaymentFormDTO.getBankAccHolderDocType())) {
				errors.rejectValue("bankAccHolderDocType", "lts.invalid.doctype");
			}else if(StringUtils.equals(ltsPaymentFormDTO.getBankAccHolderDocType(), "HKID")
					&& !Utility.validateHKID(ltsPaymentFormDTO.getBankAccHolderDocNum())){
				errors.rejectValue("bankAccHolderDocNum", "lts.verification.required");
			}else if(StringUtils.equals(ltsPaymentFormDTO.getBankAccHolderDocType(), "HKBR")){
				if(!StringUtils.isNumeric(ltsPaymentFormDTO.getBankAccHolderDocNum()) 
						|| !Utility.validateHKBRcheckDigit(ltsPaymentFormDTO.getBankAccHolderDocNum())){
					errors.rejectValue("bankAccHolderDocNum", "lts.verification.required");
				}	
			}
		}catch(Exception e){
			errors.rejectValue("bankAccHolderDocNum", "lts.verification.required");
		}
		if (StringUtils.isBlank(ltsPaymentFormDTO.getBankAccHolderDocNum())) {
			errors.rejectValue("bankAccHolderDocNum", "lts.iddocnum.required");
		}
		if (StringUtils.isBlank(ltsPaymentFormDTO.getBankCode())) {
			errors.rejectValue("bankCode", "lts.bankcode.required");
		}
		if (StringUtils.isBlank(ltsPaymentFormDTO.getBranchCode())) {
			errors.rejectValue("branchCode", "lts.branchcode.required");
		}
		if (StringUtils.isBlank(ltsPaymentFormDTO.getBankAccNum())) {
			errors.rejectValue("bankAccNum", "lts.accountnum.required");
		}
		if (!StringUtils.isNumeric(ltsPaymentFormDTO.getAutoPayUpperLimit())){
			errors.rejectValue("autoPayUpperLimit", "lts.invalid.limit");
		}
		if(ltsPaymentFormDTO.isPrepayItemSelectedA() 
			&& ltsPaymentFormDTO.isSalesMemoNumRequired()
			&& StringUtils.isBlank(ltsPaymentFormDTO.getSalesMemoNum())){
			errors.rejectValue("salesMemoNum", "lts.salesmemonum.required");
		}
	}

	private void validateCreditCardForm(LtsPaymentFormDTO ltsPaymentFormDTO, Errors errors){
		boolean isThirdParty = false;
		if(ltsPaymentFormDTO.getThirdPartyCard() == null){
			errors.rejectValue("thirdPartyCard", "lts.thirdPartyApplication.required");
		}else if(ltsPaymentFormDTO.getThirdPartyCard()){
			isThirdParty = true;
			if(StringUtils.equals(ltsPaymentFormDTO.getCardHolderDocNum(), ltsPaymentFormDTO.getCustDocNum())
					&& StringUtils.equals(ltsPaymentFormDTO.getCardHolderDocType(), ltsPaymentFormDTO.getCustDocType())){
				errors.rejectValue("cardHolderDocNum", "lts.invaild.thirdpartyinfo");
			}
		}
		if (StringUtils.isBlank(ltsPaymentFormDTO.getCardHolderName())) {
			errors.rejectValue("cardHolderName", "lts.holdername.required");
		}

		if(!isThirdParty){
			if (StringUtils.isBlank(ltsPaymentFormDTO.getCardHolderDocNum())) {
				errors.rejectValue("cardHolderDocNum", "lts.iddocnum.required");
			}
			if (StringUtils.isBlank(ltsPaymentFormDTO.getCardHolderDocType())) {
				errors.rejectValue("cardHolderDocType", "lts.invalid.doctype");
			}
		}
		
		if(StringUtils.isNotBlank(ltsPaymentFormDTO.getCardHolderDocNum()) ){
			if (StringUtils.isNotBlank(ltsPaymentFormDTO.getCardHolderDocType())) {
				if(StringUtils.equals(ltsPaymentFormDTO.getCardHolderDocType(), "HKID")
					&& !Utility.validateHKID(ltsPaymentFormDTO.getCardHolderDocNum())){
				errors.rejectValue("cardHolderDocNum", "lts.verification.required");
				}else if(StringUtils.equals(ltsPaymentFormDTO.getCardHolderDocType(), "HKBR")){
					if(!StringUtils.isNumeric(ltsPaymentFormDTO.getCardHolderDocNum()) 
							|| !Utility.validateHKBRcheckDigit(ltsPaymentFormDTO.getCardHolderDocNum())){
						errors.rejectValue("cardHolderDocNum", "lts.verification.required");
					}	
				}
			}
		}
		if (StringUtils.isBlank(ltsPaymentFormDTO.getCardNum())) {
			errors.rejectValue("cardNum", "lts.cardnum.required");
		}
		if (StringUtils.isBlank(ltsPaymentFormDTO.getCardType())) {
			errors.rejectValue("cardType", "lts.invalid.cardtype");
		}
		if (ltsPaymentFormDTO.isCardVerifyRequired() && !ltsPaymentFormDTO.isCardVerified()) {
			errors.rejectValue("cardVerified", "lts.verification.required");
		}
		
		int expireMonth = ltsPaymentFormDTO.getExpireMonth();
		int expireYear = ltsPaymentFormDTO.getExpireYear();
		Calendar today = GregorianCalendar.getInstance();
		if(expireYear == today.get(Calendar.YEAR)){
			if(expireMonth < today.get(Calendar.MONTH)+1+3){
				errors.rejectValue("expireYear", "lts.invaild.ccexpiredate");
			}
		}else if(expireYear - today.get(Calendar.YEAR) == 1){
			if(expireMonth+12 < today.get(Calendar.MONTH)+1+3){
				errors.rejectValue("expireYear", "lts.invaild.ccexpiredate");
			}
		}else if(expireYear < today.get(Calendar.YEAR)){
			errors.rejectValue("expireYear", "lts.invaild.ccexpiredate");
		}
		
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}

}
