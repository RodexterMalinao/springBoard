package com.bomwebportal.mob.ds.validator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ds.dto.MobDsPaymentTransDTO;
import com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO;
import com.bomwebportal.util.Utility;

public class MobDsPaymentUpfrontValidator implements Validator{

	protected final Log logger = LogFactory.getLog(getClass());

	public boolean supports(Class clazz) {
		return clazz.equals(MobDsPaymentUpfrontDTO.class);
	}

	public void validate(Object obj, Errors errors) {
		MobDsPaymentUpfrontDTO mobDsPaymentUpfrontDto = (MobDsPaymentUpfrontDTO) obj;
		String actionType = mobDsPaymentUpfrontDto.getActionType();

		if (!"ceksSubmit".equals(actionType) && !"AddCash".equals(actionType)
				&& !"AddCC".equals(actionType) && !"AddCCInstallment".equals(actionType)
				&& !"DELETEROW".equals(actionType)) {
			List<MobDsPaymentTransDTO> mobDsPaymentTransDto = mobDsPaymentUpfrontDto.getPaymentTransList();
			double totalPaid = 0;
			double totalPaidHs = 0;
			for (int i = 0; i < mobDsPaymentTransDto.size(); i++) {
				MobDsPaymentTransDTO payment = mobDsPaymentTransDto.get(i);
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentTransList[" + i + "].paymentAmount", "dummy", "Input the payment amount");
				String amount = "" + payment.getPaymentAmount();
				if (!"".equals(amount.trim())) {
					if (!Utility.validateMoney(amount) || payment.getPaymentAmount() < 0) {
						errors.rejectValue("paymentTransList[" + i + "].paymentAmount", "dummy", "Input a valid payment amount");
					}
				} else {
					errors.rejectValue("paymentTransList[" + i + "].paymentAmount", "dummy", "Input the payment amount");
				}
				if (payment.getStoreCd() != null) {
					if ("NONE".equals(payment.getStoreCd().trim())) {
						errors.rejectValue("paymentTransList[" + i + "].storeCd", "dummy", "Input the store code");
					} else {
						if ("NONE".equals(payment.getPaymentItemCd()) && !("I".equals(payment.getPaymentType()) && "YPM".equals(payment.getStoreCd()))) {
							errors.rejectValue("paymentTransList[" + i + "].paymentItemCd", "dummy", "Input the payment item code");
						}
					}
				} 
				if ("M".equals(payment.getPaymentType())) {
					/*if (payment.getPaymentStoreCd() != null) {
						if ("".equals(payment.getPaymentStoreCd().trim())) {
							errors.rejectValue("paymentTransList[" + i + "].paymentStoreCd", "dummy", "Input the 7-11 shop code");
						} 
					} 
					if ("".equals(payment.getInvoiceNo().trim())) {
						errors.rejectValue("paymentTransList[" + i + "].invoiceNo", "dummy", "Input the invoice No.");
					}	*/				
				} else if ("C".equals(payment.getPaymentType())) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentTransList[" + i + "].approveCode", "dummy", "Input the approve code");
					if (payment.getCcType() != null) {
						if ("NONE".equals(payment.getCcType())) {
							errors.rejectValue("paymentTransList[" + i + "].ccType", "creditCardType.required");
						}
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentTransList[" + i + "].ccNum", "creditCardNum.required");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentTransList[" + i + "].ccHolderName", "creditCardHolderName.required");
						if("NONE".equals(payment.getCcIssueBank())) {
							errors.rejectValue("paymentTransList[" + i + "].ccIssueBank", "creditCardIssueBankCd.required");	
						}
						if(!Utility.validateCreditCardExpiryDate(payment.getCcExpiryMonth(), payment.getCcExpiryYear())){
							errors.rejectValue("paymentTransList[" + i + "].ccExpiryYear", "invalid.creditCardExpiryYear");	
						}
						if(!Utility.validateCreditCardNumber(payment.getCcNum(), payment.getCcType())){
							errors.rejectValue("paymentTransList[" + i + "].ccNum", "invalid.CreditCardNum");	
						}
					}
				}else if ("I".equals(payment.getPaymentType())){
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentTransList[" + i + "].approveCode", "dummy", "Input the approve code");
					if (payment.getCcType() != null && (payment.getStoreCd() == null || "YPM".equals(payment.getStoreCd()))){
						if ("NONE".equals(payment.getCcType())) {
							errors.rejectValue("paymentTransList[" + i + "].ccType", "creditCardType.required");
						}
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentTransList[" + i + "].ccNum", "creditCardNum.required");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentTransList[" + i + "].ccHolderName", "creditCardHolderName.required");
						if("NONE".equals(payment.getCcIssueBank())) {
							errors.rejectValue("paymentTransList[" + i + "].ccIssueBank", "creditCardIssueBankCd.required");	
						}
						if(!Utility.validateCreditCardExpiryDate(payment.getCcExpiryMonth(), payment.getCcExpiryYear())){
							errors.rejectValue("paymentTransList[" + i + "].ccExpiryYear", "invalid.creditCardExpiryYear");	
						}
						if(!Utility.validateCreditCardNumber(payment.getCcNum(), payment.getCcType())){
							errors.rejectValue("paymentTransList[" + i + "].ccNum", "invalid.CreditCardNum");	
						}
						if(payment.getCcInstSchedule() == 0) {
							errors.rejectValue("paymentTransList[" + i + "].ccInstSchedule", "dummy", "Input the installment period");
						}
					}
					totalPaidHs += payment.getPaymentAmount();
				}	
				totalPaid += payment.getPaymentAmount();
				
				if (!"".equals(payment.getTransDate().trim()) && payment.getTransDate() != null) {
					if (!Utility.isValidDate(payment.getTransDate())) {
						errors.rejectValue("paymentTransList[" + i + "].transDate", "invalid.Date");
					}
				} else {
					errors.rejectValue("paymentTransList[" + i + "].transDate", "dummy", "Input the transaction date");
				}
				
			}
			if (mobDsPaymentUpfrontDto.getTotalUpfrontAmount() != totalPaid) {
				errors.rejectValue("totalPaidAmount", "dummy", "Payment amounts do not match, please check it again.");
			}
			
			if (mobDsPaymentUpfrontDto.getHsUpfrontAmount() < totalPaidHs) {
				errors.rejectValue("hsUpfrontAmount", "dummy", "Payment amount of card installment should not be greater than " + mobDsPaymentUpfrontDto.getHsUpfrontAmount());
			}
		}
	}
}
