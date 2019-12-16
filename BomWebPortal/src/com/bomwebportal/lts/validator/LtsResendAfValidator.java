package com.bomwebportal.lts.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsResendAfFormDTO;
import com.bomwebportal.lts.dto.form.LtsResendAfFormDTO.SearchMethod;

public class LtsResendAfValidator  implements Validator {

	
	public boolean supports(Class clazz) {
		return LtsResendAfFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {

		LtsResendAfFormDTO form = (LtsResendAfFormDTO) command;
		
		switch (form.getFormAction()) {
		case SEARCH:
			if(SearchMethod.ORDER_ID == form.getSearchMethod()) {
				if (StringUtils.isBlank(form.getSearchOrderId())) {
					errors.rejectValue("searchOrderId", "lts.resendAf.orderId.required");
				}
			}
			if (SearchMethod.REQ_DATE == form.getSearchMethod()) {
				if (StringUtils.isBlank(form.getSearchRequestDate())) {
					errors.rejectValue("searchRequestDate", "lts.resendAf.requestDate.required");
				}
			}
			break;
		case PREVIEW:
			if (StringUtils.isBlank(form.getPreviewOrderId())) {
				errors.rejectValue("previewOrderId", "lts.resendAf.selectOrder.required");
			}
			break;
		case SEND:
			if (StringUtils.equals("E", form.getResendMethod())) {
				if (StringUtils.isBlank(form.getResendEmail())
						|| !LtsCommonValidator.isValidEmail(form.getResendEmail())) {
					errors.rejectValue("resendEmail", "lts.resendAf.resendEmail.required");
				}
			}
			if (StringUtils.equals("S", form.getResendMethod())) {
				if (StringUtils.isBlank(form.getResendSms())) {
					errors.rejectValue("resendSms", "lts.resendAf.resendSms.required");
				}
			}
			break;
		default:
			break;
		}
	}
	
}
