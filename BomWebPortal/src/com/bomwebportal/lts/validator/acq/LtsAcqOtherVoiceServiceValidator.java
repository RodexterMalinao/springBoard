package com.bomwebportal.lts.validator.acq;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqOtherVoiceServiceFormDTO.DuplexSrvType;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.validator.LtsCommonValidator;

public class LtsAcqOtherVoiceServiceValidator implements Validator {

	protected LtsOfferService ltsOfferService;
	
	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	
	public boolean supports(Class clazz) {
		return LtsAcqOtherVoiceServiceFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsAcqOtherVoiceServiceFormDTO form = (LtsAcqOtherVoiceServiceFormDTO) command;
		switch (form.getFormAction()) {
		case SUBMIT:
			
			if (form.getCreate2ndDel() == null) {
				errors.rejectValue("create2ndDel", "lts.create2ndDel.required");
				return;
			}
			
			// Validate Duplex handling option
			if (form.isDuplexProfile() && !form.isRetainRenewalDuplex()) {
				if (form.getDuplexXSrvType() == null || form.getDuplexYSrvType() == null) {
					errors.rejectValue("duplexXSrvType", "lts.duplexSrvType.required");
				}
				else if (form.getDuplexYSrvType() == form.getDuplexXSrvType()) {
					errors.rejectValue("duplexXSrvType", "lts.duplexSrvType.invalid");
				}
				else {
					if (form.isRemoveRenewalDuplex()) {
						if (DuplexSrvType.RETAIN != form.getDuplexYSrvType() && DuplexSrvType.RETAIN != form
								.getDuplexXSrvType()) {
							errors.rejectValue("duplexXSrvType", "lts.duplexSrvType.invalid");
						}
					}
					else {
						if (DuplexSrvType.UPGRADE != form.getDuplexYSrvType() && DuplexSrvType.UPGRADE != form
								.getDuplexXSrvType()) {
						errors.rejectValue("duplexXSrvType", "lts.duplexSrvType.invalid");
						}	
					}
				}
			}
			
			if (errors.hasFieldErrors()) {
				return;
			}
			
			if (!form.getCreate2ndDel().booleanValue()) {
				return;
			}
			
			String remarks = form.getSecondDelWqRemark();
			form.setSecondDelWqRemark(LtsCommonValidator.replaceControlCharacter(remarks));
		    
			if(LtsCommonValidator.containsSpecialChar(remarks)){
				errors.rejectValue("secondDelWqRemark", "lts.remarks.specialchar");
			}
			if(LtsCommonValidator.isLongerthan100(remarks)){
				errors.rejectValue("secondDelWqRemark", "lts.remarks.toolong");
			}
			
			if (!form.isDuplexProfile() || (form.isDuplexProfile() && (DuplexSrvType.CANCEL == form.getDuplexXSrvType()
					|| DuplexSrvType.CANCEL == form.getDuplexYSrvType()))) {
				if (form.isSecondDelDiffCust()) {
					errors.rejectValue("secondDelProfileValid", "lts.secondDelProfile.invalid");
				}
				else if (!form.isSecondDelProfileValid()) {
					errors.rejectValue("secondDelProfileValid", "lts.secondDelProfile.invalid");	
				}
				else if (form.isSecondDelDiffAddress() && form.getSecondDelEr() == null ){
					errors.rejectValue("secondDelEr", "lts.secondDelEr.required");
				}
				else if (form.isSecondDelDiffAddress() && form.getSecondDelEr() != null && form.getSecondDelEr().booleanValue()){
					if (form.getSecondDelBaCaChange() == null) {
						errors.rejectValue("secondDelBaCaChange", "lts.secondDelBaCaChange.required");
					}
				}
				else if (form.isSecondDelDiffAddress() && form.getSecondDelEr() != null && !form.getSecondDelEr().booleanValue()
						&& !form.isAllowSecondDelConfirmSameAddress()){
					errors.rejectValue("secondDelEr", "lts.secondDelEr.invalid");
				}
				else if (form.isSecondDelDiffAddress() && form.getSecondDelEr() != null && !form.getSecondDelEr().booleanValue()){
					if (form.isAllowSecondDelConfirmSameAddress() && (form.getSecondDelConfirmSameIa() == null || !form.getSecondDelConfirmSameIa())) {
						errors.rejectValue("secondDelConfirmSameIa", "lts.secondDelConfirmSameIa.required");
					}
				}
			}

			
			if (form.isShowSecondDelRedeemPremium()) {
				
				if (form.getSecondDelRedeemPremium() == null) {
					errors.rejectValue("secondDelRedeemPremium", "lts.secondDelRedeemPremium.required");
				}
				else if (form.getSecondDelRedeemPremium().booleanValue()) {
					errors.rejectValue("secondDelRedeemPremium", "lts.secondDelRedeemPremium.invalid");
				}
				
			}
			
			if (form.getSecondDelHotVasItemList()!= null && form.getSecondDelHotVasItemList().size() > 0
					&& !ltsOfferService.isItemSelected(form.getSecondDelHotVasItemList(), LtsConstant.ITEM_TYPE_VAS_2DEL_HOT)) {
				errors.rejectValue("secondDelHotVasItemList[0].itemId", "lts.secondDelTermPlan.required");
			}	
			
			List<ItemDetailDTO> itemList = form.getSecondDelIddItemList();
			
			if(itemList != null){
				for(int i=0; i<itemList.size(); i++){
					if(itemList.get(i).isSelected()){
						ItemAttbDTO[] attributes = itemList.get(i).getItemAttbs();
						if(attributes != null){
							for(int j=0; j<attributes.length; j++){
								if(LtsConstant.LOOKUP_IDD_PASSWORD_ATTRIBUTE_DESC.equals(attributes[i].getAttbDesc())){
									if(StringUtils.isNotBlank(attributes[j].getAttbValue()) && !StringUtils.isNumeric(attributes[j].getAttbValue())){
										errors.rejectValue("secondDelIddItemList[" + i + "].itemAttbs[" + j + "].attbValue", "lts.idd.password.numeric");
									}
									if(StringUtils.isNotBlank(attributes[j].getAttbValue()) && attributes[j].getAttbValue().length() != 4){
										errors.rejectValue("secondDelIddItemList[" + i + "].itemAttbs[" + j + "].attbValue", "lts.idd.password.invalidLength");
									}
								}
							}
						}
					}
				}
			}			
			
			break;
		case SEARCH_DN:
			if (StringUtils.isEmpty(form.getNew2ndDelSrvStatus())) {
				errors.rejectValue("new2ndDelSrvStatus", "lts.srvStatus.required");
			}
			else if (StringUtils.isEmpty(form.getNew2ndDelDn())) {
				errors.rejectValue("new2ndDelDn", "lts.serviceNum.required");
			}
			else if (!StringUtils.isNumeric(form.getNew2ndDelDn())) {
				errors.rejectValue("new2ndDelDn", "lts.serviceNum.invalid");	
			}
			break;
		case CUST_VERIFY:
			
			if (StringUtils.isBlank(form.getSecondDelDocNum())){
				errors.rejectValue("secondDelDocNum", "lts.docNum.required");
			}
			else if (form.getSecondDelDummyDoc() == null) {
				errors.rejectValue("secondDelDummyDoc", "lts.dummyDoc.required");
			}
			else if (form.getSecondDelThirdPartyAppl() == null) {
				errors.rejectValue("secondDelThirdPartyAppl", "lts.thirdPartyApplication.required");
			}
			else if (StringUtils.isNotBlank(form.getSecondDelDocNum()) && form.getSecondDelDocNum().trim().length() > 30) {
				errors.rejectValue("secondDelDocNum", "lts.docNum.invalid");
			}	
			break;
		case SEARCH_BY_FIRST_8_DIGITS:
			if (StringUtils.isBlank(form.getFirst5To8Digits())||form.getFirst5To8Digits().length()<5) {
				errors.rejectValue("searchErrMsg", "lts.acq.first8digits.required");	
			}
		break;
		case SEARCH_BY_LAST_3_DIGITS:
			if (StringUtils.isBlank(form.getLast1To3Digits())) {
				errors.rejectValue("searchErrMsg", "lts.acq.last3digits.required");	
			}
		break;
		case LOCK_NUMBER:
			if ((form.getNumSelectionStringList()!=null && form.getNumSelectionStringList().size()>1)) {
				errors.rejectValue("lockNumErrMsg", "lts.acq.lockNum.error2");	
			}
		break;
		default:
			break;
		} 
		
	}
	

}
