package com.bomwebportal.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.mob.validate.dto.ResultDTO;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.util.Utility;

public class PaymentValidator implements Validator{
	private ValidateService validateService;

	
	public ValidateService getValidateService() {
		return validateService;
	}
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	public boolean supports(Class clazz) {
		return clazz.equals(PaymentDTO.class);
	}
	protected final Log logger = LogFactory.getLog(getClass());
	public void validate(Object obj, Errors errors) {
		PaymentDTO payment = (PaymentDTO) obj;
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)payment.getValue("customer");

		logger.info("Credit Card No: " + payment.getCreditCardNum());
		
		if (sessionCustomer.isStudentPlanSubInd()){
			ResultDTO validateMonthlyPayment = validateService.validateStudentPlanMonthlyPayment(payment.getPayMethodType(), "payMethodType", false);
			
			ResultDTO validateStudentPlanCreditCardInfo = validateService.validateStudentPlanCreditCardInfo(sessionCustomer.getContactName(), sessionCustomer.getIdDocType(), sessionCustomer.getIdDocNum(), payment.getThirdPartyInd(), payment.getCreditCardHolderName(), payment.getCreditCardDocType(), payment.getCreditCardDocNum(), "thirdPartyInd", "creditCardHolderName", "creditCardDocType", "creditCardDocNum");
			
			
			validateService.bindErrors(validateMonthlyPayment, errors);
			validateService.bindErrors(validateStudentPlanCreditCardInfo, errors);
			
			
			if (validateMonthlyPayment.hasError()||validateStudentPlanCreditCardInfo.hasError()){
				return;
			}
		}
		
		// check the credit card no submitted to ceks 
		if (!(payment.getCeksSubmit() != null && !"".equals(payment.getCeksSubmit().trim()) 
				&& "Y".equals(payment.getCeksSubmit().trim()))) {
			
		//C= credit card, M= cash , A=autoPay
		//credit card validate	
		if("C".equals(payment.getPayMethodType())) {
			
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardNum", "creditCardNum.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardHolderName", "creditCardHolderName.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardDocType", "idDocType.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardDocNum", "idDocNum.required");
				
				
				//Pattern check HKID || HKBR 
				if (payment.getCreditCardDocType()!= null ) {
					if ("HKID".equals(payment.getCreditCardDocType())&&!"".equals(payment.getCreditCardDocNum())){
						if (Utility.validateHKID(payment.getCreditCardDocNum())==false){
							errors.rejectValue("creditCardDocNum", "invalid.hkid");	
						  }else{
								if (Utility.validateHKIDcheckDigit(payment.getCreditCardDocNum())==false)
									errors.rejectValue("creditCardDocNum", "invalid.hkidCheckDigit");	
						  }
					}
					//add by Eliot 20110610
					if ("BS".equals(payment.getCreditCardDocType())&&!"".equals(payment.getCreditCardDocNum())){
						if (Utility.validateHKBR(payment.getCreditCardDocNum())==false){
							errors.rejectValue("creditCardDocNum", "invalid.hkbr");	
						  }else{
								if (Utility.validateHKBRcheckDigit(payment.getCreditCardDocNum())==false)
									errors.rejectValue("creditCardDocNum", "invalid.hkbrCheckDigit");	
						  }
						
					}
					
					if ("PASS".equals(payment.getCreditCardDocType())&&!"".equals(payment.getCreditCardDocNum())){
						if (Utility.validatePassport(payment.getCreditCardDocNum())==false){
							errors.rejectValue("creditCardDocNum", "invalid.pass", "Invalid passport number. Please input again.");	
						  }
					}
				}
				
	
				//select type checking 
				if("NONE".equals(payment.getCreditCardType())) {
					errors.rejectValue("creditCardType", "creditCardType.required");	
				}
				
				if("NONE".equals(payment.getCreditCardIssueBankCd())) {
					errors.rejectValue("creditCardIssueBankCd", "creditCardIssueBankCd.required");	
				}
				//add by wilson 20110202
				logger.info("validateCreditCardExpiryDate is called");
				if(!Utility.validateCreditCardExpiryDate(payment.getCreditExpiryMonth(), payment.getCreditExpiryYear())){
					errors.rejectValue("creditExpiryYear", "invalid.creditCardExpiryYear");	
				}
				logger.info("validateCreditCardNumber is called");
				//add by wilson 20110202
				if(!Utility.validateCreditCardNumber(payment.getCreditCardNum(), payment.getCreditCardType())){
					errors.rejectValue("creditCardNum", "invalid.CreditCardNum");	
				}

				
				if (sessionCustomer.isStudentPlanSubInd()   && !errors.hasErrors()){
					ResultDTO verifyStudentPlanCard = validateService.verifyStudentPlanCard(payment.getCreditCardDocNum(), payment.getCreditCardDocType(), payment.getCreditCardNum(), "creditCardNum");
					validateService.bindErrors(verifyStudentPlanCard, errors);
				}
				
				
			}
		} 
		
		//autopay validate
		if("A".equals(payment.getPayMethodType())) {
		
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bankAcctHolderIdType", "idDocType.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bankAcctHolderIdNum", "idDocNum.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autopayApplDateStr", "appDate.required");
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autopayUpperLimitAmt", "autopayUpperLimitAmt.required");

		
			if("NONE".equals(payment.getBankCode())) {
				errors.rejectValue("bankCode", "bankCode.required");	
			}
			if("NONE".equals(payment.getBranchCode())) {
				errors.rejectValue("branchCode", "branchCode.required");	
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bankAcctHolderName", "bankAcctHolderName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bankAcctNum", "bankAcctNum.required");
			
			//Pattern check HKID 
			if (payment.getCreditCardDocType()!= null ) {
				if ("HKID".equals(payment.getBankAcctHolderIdType())&&!"".equals(payment.getBankAcctHolderIdNum())){
					if (Utility.validateHKID(payment.getBankAcctHolderIdNum())==false){
						errors.rejectValue("bankAcctHolderIdNum", "invalid.hkid");	
					  }else{
							if (Utility.validateHKIDcheckDigit(payment.getCreditCardDocNum())==false)
								errors.rejectValue("bankAcctHolderIdNum", "invalid.hkidCheckDigit");							
					  }
				}
			
				//add by Eliot 20110621
				if ("BS".equals(payment.getBankAcctHolderIdType())&&!"".equals(payment.getBankAcctHolderIdNum())){
					if (Utility.validateHKBR(payment.getBankAcctHolderIdNum())==false){
						errors.rejectValue("bankAcctHolderIdNum", "invalid.hkbr");	
					  }else{
							if (Utility.validateHKBRcheckDigit(payment.getBankAcctHolderIdNum())==false)
								errors.rejectValue("bankAcctHolderIdNum", "invalid.hkbrCheckDigit");	
					  }
				
				}
				
				if ("PASS".equals(payment.getBankAcctHolderIdType())&&!"".equals(payment.getBankAcctHolderIdNum())){
					if (Utility.validatePassport(payment.getBankAcctHolderIdNum())==false){
						errors.rejectValue("bankAcctHolderIdNum", "invalid.pass", "Invalid passport number. Please input again.");	
					  }
				}
			}
			
			if ( !Utility.validateNumber(payment.getBankAcctNum())){
				errors.rejectValue("bankAcctNum", "invalid.numberOnly");	
			}
			if (!Utility.validateBankAcctNum(payment.getBankAcctNum())) {
				errors.rejectValue("bankAcctNum", null, "Invalid Bank Account Number");
			}
			
		/*	if (payment.getAutopayUpperLimitAmt() != null && payment.getAutopayUpperLimitAmt().length() > 0){
				if (!Utility.validateNumber(payment.getAutopayUpperLimitAmt())){
					errors.rejectValue("autopayUpperLimitAmt", "invalid.numberOnly");	
				}
			}*/
			if(!Utility.isValidDate(payment.getAutopayApplDateStr())){
				errors.rejectValue("autopayApplDateStr", "invalid.Date");
			}
			
		
		}
	}
	
}
