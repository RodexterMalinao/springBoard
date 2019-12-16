package com.bomwebportal.mob.ccs.validator;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.DoaRequestDTO;
import com.bomwebportal.mob.ccs.dto.ui.DoaRequestUI;
import com.bomwebportal.mob.ccs.service.MobCcsDoaRequestService;
import com.bomwebportal.service.OrderService;

public class MobCcsDoaRequestValidator implements Validator {
	//private static final String ORDER_STATUS_COMPLETED = "02";
	//private static final String ORDER_STATUS_FALLOUT = "99";
	
	//private static final String CHECK_POINT_FALLOUT = "999";
	
	//private static final String ACTION_NAME = "AU04";
	
	private static final String REASON_CD_DOA_SAVED = "N000";
	//private static final String REASON_CD_DOA_AFTER_DELIVERY = "N001";

	private OrderService orderService;
	private MobCcsDoaRequestService mobCcsDoaRequestService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public boolean supports(Class clazz) {
		return DoaRequestUI.class.equals(clazz);
	}

	public MobCcsDoaRequestService getMobCcsDoaRequestService() {
		return mobCcsDoaRequestService;
	}

	public void setMobCcsDoaRequestService(
			MobCcsDoaRequestService mobCcsDoaRequestService) {
		this.mobCcsDoaRequestService = mobCcsDoaRequestService;
	}

	public void validate(Object command, Errors errors) {
		DoaRequestUI form = (DoaRequestUI) command;

		OrderDTO orderDto = this.orderService.getOrder(form.getOrderId());
		if (orderDto == null) {
			errors.rejectValue("orderId", "dummy", "Unknown Order");
		}
		
		if (!errors.hasErrors()) {
			if (mobCcsDoaRequestService.isInitNewDoaRequest(orderDto)) {
				
			} else if (this.mobCcsDoaRequestService.isUpdateDoaRequest(orderDto)) {
				DoaRequestDTO doaRequest = this.mobCcsDoaRequestService.getDoaRequestDTO(form.getRowId());
				if (doaRequest == null) {
					errors.rejectValue("orderId", "dummy", "Unknown DOA record");
				} else {
					// validate if record still available for update
					if (!REASON_CD_DOA_SAVED.equals(doaRequest.getStatus())) {
						errors.rejectValue("orderId", "dummy", "DOA record status not in N000");
					}
				}
			} else {
				errors.rejectValue("orderId", "dummy", "Status not allowed to create DOA request");
			}
		}

		if (!errors.hasErrors()) {
			if (form.isApproved()) {
				if (form.getStocks() == null || form.getStocks().length == 0) {
					errors.rejectValue("stocks", "dummy", "Select at least 1 Item");
				}
				if ((form.getReasons() == null || form.getReasons().length == 0)
						&& StringUtils.isBlank(form.getRemarks())) {
					errors.rejectValue("reasons", "dummy", "Select at least 1 Reason or input Remarks");
				}
				if (form.getRemarks() != null && form.getRemarks().length() > 500) {
					errors.rejectValue("remarks", "dummy", "Remarks over 500 characters");
				}
				if (!this.mobCcsDoaRequestService.approveByManager(orderDto.getOrderId())) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mktSerialId", "dummy", "Requires Serial #");
					
					if (StringUtils.isNotBlank(form.getMktSerialId())) {
						if (this.mobCcsDoaRequestService.isMktSerialIdPatternValid(form.getMktSerialId())) {
							if (this.mobCcsDoaRequestService.isMktSerialIdExists(form.getMktSerialId())) {
								errors.rejectValue("mktSerialId", null, "Serial: " + form.getMktSerialId() + " in used");
							}
						} else {
							errors.rejectValue("mktSerialId", null, "Invalid Serial pattern");
						}	
					}
				}
			} else {
				// save draft
				if ((form.getStocks() == null || form.getStocks().length == 0)
						&& (form.getReasons() == null || form.getReasons().length == 0)
						&& StringUtils.isBlank(form.getRemarks())) {
					errors.rejectValue("orderId", "dummy", "Select at least 1 Item / Reason, or input Remark to create draft");
				}
			}
		}
	}
}
