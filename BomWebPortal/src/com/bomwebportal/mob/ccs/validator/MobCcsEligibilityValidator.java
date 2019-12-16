package com.bomwebportal.mob.ccs.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.MobCcsEligibilityDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsValuePropAssgnDTO;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsEligibilityUI;
import com.bomwebportal.mob.ccs.service.MobCcsEligibilityService;
import com.bomwebportal.util.Utility;

public class MobCcsEligibilityValidator implements Validator {
	private MobCcsEligibilityService mobCcsEligibilityService;
	
	public MobCcsEligibilityService getMobCcsEligibilityService() {
		return mobCcsEligibilityService;
	}

	public void setMobCcsEligibilityService(
			MobCcsEligibilityService mobCcsEligibilityService) {
		this.mobCcsEligibilityService = mobCcsEligibilityService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(MobCcsEligibilityUI.class);
	}

	public void validate(Object command, Errors errors) {
		MobCcsEligibilityUI form = (MobCcsEligibilityUI) command;
		if (form.getIdDocType() == null) {
			errors.rejectValue("idDocType", null, "Requires ID Doc Type");
			return;
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idDocNum", null, "Requires ID Doc Num");
		if (StringUtils.isNotBlank(form.getIdDocNum())) {
			switch (form.getIdDocType()) {
			case HKID:
				if (!Utility.validateHKID(form.getIdDocNum())) {
					errors.rejectValue("idDocNum", null, "Invalid HKID number");
				}
				break;
			case PASS:
				if (!Utility.validatePassport(form.getIdDocNum())) {
					errors.rejectValue("idDocNum", null, "Invalid Passport number");
				}
				break;
			case BS:
				if (!Utility.validateHKBR(form.getIdDocNum())) {
					errors.rejectValue("idDocNum", null, "Invalid HKBR number");
				}
				break;
			default:
				errors.rejectValue("idDocNum", null, "Invalid number");
			}
		}
		
		boolean eligibilityValid = false;
		if (StringUtils.isNotBlank(form.getEligibility())) {
			List<MobCcsValuePropAssgnDTO> valuePropAssgnDTOs = this.mobCcsEligibilityService.getMobCcsValuePropAssgnDTOALL();
			for (MobCcsValuePropAssgnDTO dto : valuePropAssgnDTOs) {
				if (dto.getValuePropId().equals(form.getEligibility())) {
					eligibilityValid = true;
					break;
				}
			}
		}
		if (!eligibilityValid) {
			errors.rejectValue("eligibility", null, "Requires Eligibility");
		}
		
		if (errors.hasErrors()) {
			return;
		}
		// further check for duplicate
		MobCcsEligibilityDTO dto = this.mobCcsEligibilityService.getMobCcsEligibilityDTO(form.getIdDocType(), form.getIdDocNum(), form.getEligibility().toString());
		if (dto != null) {
			errors.rejectValue("eligibility", null, "Eligibility exists");
		}
	}

}
