package com.bomwebportal.lts.validator.disconnect;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.disconnect.LtsTerminateConstant;
import com.bomwebportal.util.Utility;

public class LtsTerminateServiceSelectionValidator implements Validator {

	private LtsCommonService ltsCommonService;
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	public boolean supports(Class clazz) {
		return LtsTerminateServiceSelectionFormDTO.class.isAssignableFrom(clazz);
	}

public void validate(Object command, Errors errors) {
		
	LtsTerminateServiceSelectionFormDTO form = (LtsTerminateServiceSelectionFormDTO) command;
		
	if(StringUtils.equals(form.getFormAction().toString(),"SUBMIT")){
		
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("dd/MM/yyyy");
		
		if (StringUtils.isBlank(form.getAppDate())) {
			errors.rejectValue("appDate", "lts.dis.appnt.date.required");
		}else{
			try {
				df.parse(form.getAppDate());
			} catch (ParseException e) {
				e.printStackTrace();
				errors.rejectValue("appDate", "lts.invalid.date");
			}
		}

		if(StringUtils.isEmpty(form.getDisconnectFormSerial())&& StringUtils.isEmpty(form.getWaiveDFormReason())) {
			errors.rejectValue("disconnectFormSerial", "lts.dFormSn.required");
			errors.rejectValue("waiveDFormReason", "lts.waiveDFormReason.required");
		}
		
		if(StringUtils.isEmpty(form.getDisconnectReason())){
			errors.rejectValue("disconnectReason", "lts.dReason.required");
		}else if(LtsTerminateConstant.DISCONNECT_REASON_DECEASED.equals(form.getDisconnectReason())
				&& StringUtils.isBlank(form.getDeceasedType())){
			errors.rejectValue("deceasedType", "lts.dis.deceasedCase.required");
		}
		
		if(form.getIddCallPlanProfileList() != null){
			for(IddCallPlanProfileLtsDTO callPlan : form.getIddCallPlanProfileList()){
				if(StringUtils.isBlank(callPlan.getAction())){
					errors.rejectValue("iddCallPlanProfileList", "lts.dis.callPlan.action.required");
				}
				if(!LtsConstant.IDD_ACTION_CHG_DCA.equals(callPlan.getAction())){
					callPlan.setNewDca(null);
				}
				if(!LtsConstant.IDD_ACTION_REMOVE.equals(callPlan.getAction())){
					callPlan.setPenaltyHandling(null);
				}
				if(LtsConstant.IDD_ACTION_CHG_DCA.equals(callPlan.getAction())
						&& StringUtils.isBlank(callPlan.getNewDca())){
					errors.rejectValue("iddCallPlanProfileList", "lts.dis.callPlan.newDca.required");
				}
			}
		}
		
		validateThirdParty(form, errors);
	}
}
	
	private void validateThirdParty(LtsTerminateServiceSelectionFormDTO form, Errors errors) {
		
		if (!form.isThirdParty()){
			return;
		}
		
		if (StringUtils.isBlank(form.getThirdTitle())) {
			errors.rejectValue("thirdTitle", "lts.title.required");
		}
		
		if (StringUtils.isBlank(form.getThirdLastName())) {
			errors.rejectValue("thirdLastName", "lts.applicantName.required");
		}

		if (StringUtils.isBlank(form.getThirdFirstName())) {
			errors.rejectValue("thirdFirstName", "lts.applicantName.required");
		}
		
		if (!StringUtils.isBlank(form.getThirdDocNum())) {
			validateDocIdValue(form.getThirdDocType(), form.getThirdDocNum(), errors, "thirdDocNum");

		}
//		
//		if (!form.isThirdIdVerify()){
//			errors.rejectValue("thirdIdVerify", "lts.verification.required");
//		}
		
		if (StringUtils.isBlank(form.getThirdRelationship())) {
			errors.rejectValue("thirdRelationship", "lts.invalid.relationship");
		}
		
		if (!StringUtils.isBlank(form.getThirdContactNum())) {
			ltsCommonService.validateContactValue(form.getThirdContactNum(), errors, "thirdContactNum");
		}
	}
	

	private void validateDocIdValue(String docType, String docId, Errors errors, String fieldName){
		if (LtsConstant.DOC_TYPE_HKID.equals(docType)){
			if (!Utility.validateHKID(docId)){
				errors.rejectValue(fieldName, "lts.invalid.hkid");	
			}else if (!Utility.validateHKIDcheckDigit(docId)){
				errors.rejectValue(fieldName, "lts.invalid.hkidCheckDigit");	
			}
		}else if (LtsConstant.DOC_TYPE_PASSPORT.equals(docType)){
			if(docId.length() < 6){
				errors.rejectValue(fieldName, "lts.invalid.length");
			}
			if(!StringUtils.isAlphanumeric(docId)){
				errors.rejectValue(fieldName, "lts.invalid.char");
			}
		}else if(LtsConstant.DOC_TYPE_HKBR.equals(docType)){
			if (!Utility.validateHKBR(docId)){
				errors.rejectValue(fieldName, "lts.invalid.hkbr");	
			}else if (!Utility.validateHKBRcheckDigit(docId)){
				errors.rejectValue(fieldName, "lts.invalid.hkbrCheckDigit");	
			}
		}else if(StringUtils.isEmpty(docType)){
			//errors.rejectValue(fieldName, "lts.invalid.doctype");
		}
	}
}
