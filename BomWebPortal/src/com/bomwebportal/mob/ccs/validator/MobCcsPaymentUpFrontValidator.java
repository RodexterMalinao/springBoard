package com.bomwebportal.mob.ccs.validator;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.util.Utility;

public class MobCcsPaymentUpFrontValidator implements Validator{
	
	public boolean supports(Class clazz) {
		return clazz.equals(PaymentUpFrontUI.class);
	}
	protected final Log logger = LogFactory.getLog(getClass());
	public void validate(Object obj, Errors errors) {
		PaymentUpFrontUI payment = (PaymentUpFrontUI) obj;
		if (payment.isByPassValidation() || payment.getActionType().equalsIgnoreCase("REFRESH_SELF")){
			return; //add by wilson 20120302, by pass validation
		}
		/**Student Plan**/
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)payment.getValue("customer");
		if (StringUtils.isEmpty(payment.getOrderId())){
			if (sessionCustomer.isStudentPlanSubInd()){
				if(!"M".equals(payment.getPayMethodType())) {
					errors.rejectValue("payMethodType", "dummy", "Upfront payment method must be cash for student plan.");		
				}
			}
		}else{
			if (sessionCustomer.isStudentPlanSubInd() && !"SBO".equalsIgnoreCase(payment.getOrderId().substring(1,4))){
				if(!"M".equals(payment.getPayMethodType())) {
					errors.rejectValue("payMethodType", "dummy", "Upfront payment method must be cash for student plan.");		
				}
			}
		}
		/**Student Plan End**/
		logger.info("Credit Card No: " + payment.getCreditCardNum());
		logger.debug("payment.getCeksSubmit():"+payment.getCeksSubmit());
		// check the credit card no submitted to ceks 
		//if (!(payment.getCeksSubmit() != null && !"".equals(payment.getCeksSubmit().trim()) 
		//		&& "Y".equals(payment.getCeksSubmit().trim()))) {
			logger.debug("payment.getCeksSubmit():"+payment.getPayMethodType());
			//M - Cash, C - Credit Card, I Credit Card Installement
			//credit card validate	
			if("C".equals(payment.getPayMethodType())) {
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardNum", "creditCardNum.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardHolderName", "creditCardHolderName.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentCombination", "paymentCombination.required");
	
				//select type checking 
				if("NONE".equals(payment.getCreditCardType())) {
					errors.rejectValue("creditCardType", "creditCardType.required");	
				}
				
				if("NONE".equals(payment.getCreditCardIssueBankCd())) {
					errors.rejectValue("creditCardIssueBankCd", "creditCardIssueBankCd.required");	
				}
				//add by wilson 20110202
				logger.info("validateCreditCardExpiryDate is called");
				logger.info("validateCreditCardExpiryDate ==>payment.getOrderId():"+payment.getOrderId());
				if (StringUtils.isEmpty(payment.getOrderId())) {//new order 
					if(!Utility.validateUpfrontCreditCardExpiryDate(payment.getCreditExpiryMonth(), payment.getCreditExpiryYear())){
						errors.rejectValue("creditExpiryYear", "invalid.callCenterCreditCardExpiryYear");	
					}
				}else{//recall with appDate
					Date appDate= (Date)payment.getValue("appDate");
					logger.info("validateCreditCardExpiryDateByAppDate ==> appDate:"+appDate);
					if (!Utility.validateUpfrontCreditCardExpiryDateByAppDate(payment.getCreditExpiryMonth(),	payment.getCreditExpiryYear(), appDate)) {
						errors.rejectValue("creditExpiryYear", "invalid.callCenterCreditCardExpiryYear");
					}
				}
				logger.info("validateCreditCardNumber is called");
				//add by wilson 20110202
				if(!Utility.validateCreditCardNumber(payment.getCreditCardNum(), payment.getCreditCardType())){
					errors.rejectValue("creditCardNum", "invalid.CreditCardNum");	
				}
				
				//OP for SBO shop only
				if (StringUtils.isNotEmpty(payment.getOrderId())) {// order id check.
					if ("OP".equals(payment.getPaymentCombination())) {//OP
						if (!StringUtils.startsWith(payment.getOrderId(), "CSBOM")) {
							errors.rejectValue("paymentCombination", "dummy", "OP only for online shop order");	
						}
					}
				}else{
					if ("OP".equals(payment.getPaymentCombination())) {//OP
						if (!StringUtils.startsWith(payment.getOrderId(), "CSBOM")) {
							errors.rejectValue("paymentCombination", "dummy", "OP only for online shop order");	
						}
					}
					
				}
	
			}
			//Credit Card Installement validate	
			if("I".equals(payment.getPayMethodType())) {
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardNum", "creditCardNum.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardHolderName", "creditCardHolderName.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ccInstSchedule", "ccInstSchedule.required");

				//select type checking 
				if("NONE".equals(payment.getCreditCardType())) {
					errors.rejectValue("creditCardType", "creditCardType.required");	
				}
				
				if("NONE".equals(payment.getCreditCardIssueBankCd())) {
					errors.rejectValue("creditCardIssueBankCd", "creditCardIssueBankCd.required");	
				}
				
				if("NONE".equals(payment.getCcInstSchedule())) {
					errors.rejectValue("ccInstSchedule", "ccInstSchedule.required");	
				}
			
			
				logger.info("validateCreditCardExpiryDate is called");
				logger.info("validateCreditCardExpiryDate ==>payment.getOrderId():"+payment.getOrderId());
				if (StringUtils.isEmpty(payment.getOrderId())) {//new order 
					if(!Utility.validateCreditCardExpiryDate(payment.getCreditExpiryMonth(), payment.getCreditExpiryYear())){
						errors.rejectValue("creditExpiryYear", "invalid.creditCardExpiryYear");	
					}
				}else{//recall with appDate
					Date appDate= (Date)payment.getValue("appDate");
					logger.info("validateCreditCardExpiryDateByAppDate ==> appDate:"+appDate);
					if (!Utility.validateCreditCardExpiryDateByAppDate(payment.getCreditExpiryMonth(),	payment.getCreditExpiryYear(), appDate)) {
						errors.rejectValue("creditExpiryYear", "invalid.creditCardExpiryYear");
					}
				}
				
				logger.info("validateCreditCardNumber is called");
			
				if(!Utility.validateCreditCardNumber(payment.getCreditCardNum(), payment.getCreditCardType())){
					errors.rejectValue("creditCardNum", "invalid.CreditCardNum");	
				}
	
			
				if("Y".equals(payment.getRecallCCInstWarnInd())) {
					errors.rejectValue("recallCCInstWarnInd", "invalid.ccInstScheduleRecallErr");	
				}
			}
			
			
			//Cash Validation
			if("M".equals(payment.getPayMethodType())) {
				if("Y".equals(payment.getUpfrontCCInd())) {
					errors.rejectValue("payMethodType", "dummy", "Upfront payment method must be credit card / credit card installment for selected basket.");			
				}
			}
		//} 
		
	}
	
}
