package com.bomwebportal.mob.ccs.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.MobCcsPaymentTransDTO;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsUrgentOrderManagementUI;
import com.bomwebportal.service.ReleaseLockService;

public class MobCcsUrgentOrderManagementValidator implements Validator{
	
	public boolean supports(Class arg0) {
		return arg0.equals(MobCcsUrgentOrderManagementUI.class);
	}

	public void validate(Object obj, Errors error) {
		
		MobCcsUrgentOrderManagementUI ui = (MobCcsUrgentOrderManagementUI) obj;
		if (!"FALLOUT".equals(ui.getActionType())){
			
			if (ui.getOrderLockInd()) {
				error.rejectValue("submitErrorPrint", "errorPath.orderLock", "Order is locked by other user");
			}
			
			if (!ui.getSupportDocVerifiedInd()) {
				error.rejectValue("supportDocVerifiedInd", "errorPath.supportNotCheck", "Support Document is not verified" );
			}
			
			if (!ui.getPaymantMonthlyVerifiedInd()) {
				error.rejectValue("paymantMonthlyVerifiedInd", "errorPath.monthlyNotCheck", "Payment Settlement is not verified" );
			}
			
			if (!ui.getPaymantUpfrontVerifiedInd()) {
				error.rejectValue("paymantUpfrontVerifiedInd", "errorPath.upfrontNotCheck", "Upfront Payment Method is not verified" );
			}
			
			if (ui.getPaymentTransList()!=null  && !ui.getPaymentTransList().isEmpty()){
				for (int i = 0; i < ui.getPaymentTransList().size(); i++) {
					if (ui.getPaymentTransList().get(i).isApproveFlag() && StringUtils.isBlank(ui.getPaymentTransList().get(i).getApproveCode())) {
						error.rejectValue("paymentTransList[" + i + "].approveCode", "errorPath.approveCodeEmpty", "Approve Code is empty" );
					}
					
	//				if (ui.getPaymentTransList().get(i).isBatchNumFlag() && StringUtils.isBlank(ui.getPaymentTransList().get(i).getBatchNum())) {
	//					error.rejectValue("paymentTransList[" + i + "].batchNum", "errorPath.batchNumEmpty", "Batch Number is empty" );
	//				}
				}
			}
			
//			ValidationUtils.rejectIfEmptyOrWhitespace(error, "paymentTransList.approveCode", "prepay.required", "Approval code required");
		} else if ("FALLOUT".equals(ui.getActionType())) {
			
			if (ui.getOrderLockInd()) {
				error.rejectValue("falloutReasonCd", "errorPath.orderLock", "Order is locked by other user");
			}
			
			if (StringUtils.isBlank(ui.getFalloutReasonCd())) {
				error.rejectValue("falloutReasonCd", "errorPath.falloutReasonCd", "Please select Fallout Reason" );
			}
		}
	}

}
