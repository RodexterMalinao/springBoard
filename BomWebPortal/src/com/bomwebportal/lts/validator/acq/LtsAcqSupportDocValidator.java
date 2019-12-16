package com.bomwebportal.lts.validator.acq;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsSupportDocFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSupportDocFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.validator.LtsCommonValidator;

public class LtsAcqSupportDocValidator implements Validator {

	private LtsCommonService ltsCommonService;
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	public boolean supports(Class clazz) {
		return LtsAcqSupportDocFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		
		LtsAcqSupportDocFormDTO form = (LtsAcqSupportDocFormDTO) command;
		
		if (StringUtils.isNotBlank(form.getDistributeSms())) {
		    if (!ltsCommonService.isMobileNumPrefix(form.getDistributeSms()))  {
			    errors.rejectValue("distributeSms", "lts.distributeSms.invalid");
		    }
		}
		
		switch (form.getFormAction()) {
		case SUBMIT:
			if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE, form.getDistributionMode())) {
				if(!form.isSendEmail() && !form.isSendSms() ){
					errors.rejectValue("distributionMode", "lts.distributeOption.required");
				}else{
					if (form.isSendEmail()) {
						if (StringUtils.isEmpty(form.getDistributeEmail())) {
							errors.rejectValue("distributeEmail", "lts.distributeEmail.required");
						}
						else if (!LtsCommonValidator.isValidEmail(form.getDistributeEmail())) {
							errors.rejectValue("distributeEmail", "lts.distributeEmail.invalid");
						}	
					}
					if (form.isSendSms()) {
						if (StringUtils.isEmpty(form.getDistributeSms())) {
							errors.rejectValue("distributeSms", "lts.distributeSms.required");
						}
						else if (!ltsCommonService.isMobileNumPrefix(form.getDistributeSms()))  {
							errors.rejectValue("distributeSms", "lts.distributeSms.invalid");
						}
					}
				}
				/*if(form.isChannelDS()){
					if (StringUtils.isEmpty(form.getDistributeEmail())) {
						errors.rejectValue("distributeEmail", "lts.distributeEmail.required");
					}
					else if (!LtsCommonValidator.isValidEmail(form.getDistributeEmail())) {
						errors.rejectValue("distributeEmail", "lts.distributeEmail.invalid");
					}
					if (StringUtils.isEmpty(form.getDistributeSms())) {
						errors.rejectValue("distributeSms", "lts.distributeSms.required");
					}
					else if (!ltsCommonService.isMobileNumPrefix(form.getDistributeSms()))  {
						errors.rejectValue("distributeSms", "lts.distributeSms.invalid");
					}
				}*/
			}
			
			if (StringUtils.isEmpty(form.getDistributeLang())) {
				errors.rejectValue("distributeLang", "lts.distributeLang.required");
			}
			if (StringUtils.isNotBlank(form.getSuspendRemarks())){ 
				if(form.getSuspendRemarks().length() > 100){
					errors.rejectValue("suspendRemarks", "lts.remarks.toolong");
				}
			}
			if (StringUtils.isEmpty(form.getSignoffMode())) {
				errors.rejectValue("signoffMode", "lts.signoffMode.required");
			}
			break;
		case SUSPEND:
			if (StringUtils.isBlank(form.getSuspendReason())
					|| StringUtils.equals(form.getSuspendReason(), "NONE")) {
				errors.rejectValue("suspendReason", "lts.reason.required");
			}
			if (StringUtils.isNotBlank(form.getSuspendRemarks())){
				if(form.getSuspendRemarks().length() > 100){
					errors.rejectValue("suspendRemarks", "lts.remarks.toolong");
				}
			}
			break;
		default:
			break;
		}
	}

}
