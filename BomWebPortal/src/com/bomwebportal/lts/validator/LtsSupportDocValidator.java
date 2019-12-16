package com.bomwebportal.lts.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsSupportDocFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsSupportDocValidator implements Validator {

	private LtsCommonService ltsCommonService;
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	public boolean supports(Class clazz) {
		return LtsSupportDocFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		
		LtsSupportDocFormDTO form = (LtsSupportDocFormDTO) command;
		
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
			}
			
			if (StringUtils.isEmpty(form.getDistributeLang())) {
				errors.rejectValue("distributeLang", "lts.distributeLang.required");
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
			break;
		default:
			break;
		}
	}

}
