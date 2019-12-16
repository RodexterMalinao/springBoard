package com.bomwebportal.mob.ccs.validator;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.ui.StockManualDetailUI;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.DateUtils;
import com.bomwebportal.util.Utility;

public class DoaHandlingValidator implements Validator {
	
	protected final Log logger = LogFactory.getLog(getClass());

	public boolean supports(Class clazz) {
		return clazz.equals(StockManualDetailUI.class);
	}
	
	public void validate(Object obj, Errors errors) {
		
		StockManualDetailUI ui = (StockManualDetailUI) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"deliveryDateStr", "deliveryDateStr.required", "Delivery Date is empty");
		
		Date nextDay = DateUtils.dateAfterdays(new Date(), 1);
		Date lastDay = DateUtils.dateAfterdays(new Date(), 28);
		
		if (ui.getDeliveryDateStr() != null && !ui.getDeliveryDateStr().isEmpty()) {
			if (DateUtils.daysDiffBetween(Utility.string2Date(ui.getDeliveryDateStr()), nextDay) < 0) {
				errors.rejectValue("deliveryDateStr",
						"deliveryDateStr.invalid",
						"Delivery Date must be on or after " + Utility.date2String(nextDay, BomWebPortalConstant.DATE_FORMAT));
			}
			
			if (DateUtils.daysDiffBetween(Utility.string2Date(ui.getDeliveryDateStr()), lastDay) > 0) {
				errors.rejectValue("deliveryDateStr",
						"deliveryDateStr.invalid",
						"Delivery Date must be on or before " + Utility.date2String(lastDay, BomWebPortalConstant.DATE_FORMAT));
			}
		}
	}
	
}
