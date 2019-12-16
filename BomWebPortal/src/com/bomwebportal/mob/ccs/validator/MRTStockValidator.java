package com.bomwebportal.mob.ccs.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.ui.MRTStockUI;
import com.bomwebportal.util.Utility;

public class MRTStockValidator implements Validator {

	public boolean supports(Class clazz) {
		return MRTStockUI.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {
		MRTStockUI form = (MRTStockUI) obj;

		if (StringUtils.isNotBlank(form.getOrderId())) {
			if (!StringUtils.isAlphanumeric(form.getOrderId()) || form.getOrderId().length() != 11) {
				errors.rejectValue("orderId", "mrtAdminOrderId.pattern");
			}
		} else {
			if (StringUtils.isNotBlank(form.getMsisdn())) {
				if (form.getMsisdn().startsWith("0")) {
					errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.leadingzero");
				}
				if (!StringUtils.isNumeric(form.getMsisdn())) {
					errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.numeric");
				}
				if ("PCCW3G".equals(form.getServiceType())) {
					if (form.getMsisdn().length() != 8) {
						errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.pccw3g");
					} else if (!Utility.validateHKMobilePrefix(form.getMsisdn())) {
						errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.pccw3gPrefix");
					}
				} else if ("UNICOM1C2N".equals(form.getServiceType())) {
					if (form.getMsisdn().length() != 11) {
						errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.unicom1c2n");
					} else if (!Utility.validateChinaMobilePrefix(form.getMsisdn())) {
						errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.unicom1c2nPrefix");
					}
				} else if (form.getMsisdn().length() != 8 && form.getMsisdn().length() != 11) {
					errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern");
				}
			}
		}
	}

}
