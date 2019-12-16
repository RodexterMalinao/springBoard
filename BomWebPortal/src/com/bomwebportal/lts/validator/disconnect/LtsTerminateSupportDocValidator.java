package com.bomwebportal.lts.validator.disconnect;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateSupportDocFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsTerminateSupportDocValidator implements Validator {

	private LtsCommonService ltsCommonService;
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	public boolean supports(Class clazz) {
		return LtsTerminateSupportDocFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		
		LtsTerminateSupportDocFormDTO form = (LtsTerminateSupportDocFormDTO) command;
		
		switch (form.getFormAction()) {
		case SUBMIT:
			if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE, form.getDistributionMode())) {
				if (StringUtils.isEmpty(form.getDistributeSms())) {
					errors.rejectValue("distributeSms", "lts.distributeSms.required");
				}
				else if (!ltsCommonService.isMobileNumPrefix(form.getDistributeSms()))  {
					errors.rejectValue("distributeSms", "lts.distributeSms.invalid");
				}
			}
			
			if (StringUtils.isEmpty(form.getDistributeLang())) {
				errors.rejectValue("distributeLang", "lts.distributeLang.required");
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
