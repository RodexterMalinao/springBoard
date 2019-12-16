package com.bomwebportal.lts.validator.acq;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.util.Utility;

public class LtsAcqPaymentMethodValidator implements Validator {

	public boolean supports(Class clazz) {
		return LtsAcqPaymentMethodFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsAcqPaymentMethodFormDTO form = (LtsAcqPaymentMethodFormDTO) command;
		if(StringUtils.equals(form.getSubmitInd(), "SUBMIT")){
			for(int i = 0; i <  form.getPaymentMethodDtlList().size(); i++){
				PaymentMethodDtl payMtdDtl = form.getPaymentMethodDtlList().get(i);
				validatePaymentMethod(form, payMtdDtl, errors, "paymentMethodDtlList[" + i + "].");
			}
			if (StringUtils.isNotBlank(form.getSuspendRemarks())){
				if(form.getSuspendRemarks().length() > 100){
					errors.rejectValue("suspendRemarks", "lts.remarks.toolong");
				}
			}
		}else{
			if(StringUtils.isBlank(form.getSuspendReason()) 
					|| StringUtils.equals(form.getSuspendReason(), "NONE")){
				errors.rejectValue("suspendReason", "lts.reason.required");
			}
			if (StringUtils.isNotBlank(form.getSuspendRemarks())){
				if(form.getSuspendRemarks().length() > 100){
					errors.rejectValue("suspendRemarks", "lts.remarks.toolong");
				}
			}
		}

	}

	private void validatePaymentMethod(LtsAcqPaymentMethodFormDTO form, PaymentMethodDtl payMtdDtl, Errors errors, String pathPrefix){
		if(StringUtils.isBlank(payMtdDtl.getNewPayMethodType())){
			errors.rejectValue(pathPrefix+"newPayMethodType", "lts.paymenttype.required");
		}else if(StringUtils.equals(payMtdDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CASH)){
			validatePrepayment(payMtdDtl, payMtdDtl.getCashPrePayItem(), form.isSalesMemoNumRequired(), errors, pathPrefix);
		}else if(StringUtils.equals(payMtdDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY) &&
				!StringUtils.equals(payMtdDtl.getNewPayMethodType(), payMtdDtl.getExistingPayMethodType())){
			validateAutoPayForm(form, payMtdDtl, errors, pathPrefix);
			validatePrepayment(payMtdDtl, payMtdDtl.getAutopayPrePayItem(), form.isSalesMemoNumRequired(), errors, pathPrefix);
		}else if(StringUtils.equals(payMtdDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD) &&
				!StringUtils.equals(payMtdDtl.getNewPayMethodType(), payMtdDtl.getExistingPayMethodType())){
			validateCreditCardForm(form, payMtdDtl, errors, pathPrefix);
			validatePrepayment(payMtdDtl, payMtdDtl.getCreditCardPrePayItem(), false, errors, pathPrefix);
		}
	}
	
	private void validatePrepayment(PaymentMethodDtl payMtdDtl, ItemDetailDTO item, boolean salesMemoRequired, Errors errors, String pathPrefix){
		if(item == null){
			return;
		}
		
		if(item.isSelected() && salesMemoRequired){
			if(StringUtils.isBlank(payMtdDtl.getSalesMemoNum())){
				errors.rejectValue(pathPrefix+"salesMemoNum", "lts.salesmemonum.required");
			}
		}
	}
	
	private void validateAutoPayForm(LtsAcqPaymentMethodFormDTO form, PaymentMethodDtl payMtdDtl, Errors errors, String pathPrefix){
		/*if(payMtdDtl.getThirdPartyBankAccount() == null){
			errors.rejectValue(pathPrefix+"thirdPartyBankAccount", "lts.thirdPartyApplication.required");
		}else if(payMtdDtl.getThirdPartyBankAccount()){
			if(StringUtils.equals(payMtdDtl.getBankAccHolderDocNum(), form.getCustDocNum())
					&& StringUtils.equals(payMtdDtl.getBankAccHolderDocType(), form.getCustDocType())){
				errors.rejectValue(pathPrefix+"bankAccHolderDocNum", "lts.invaild.thirdpartyinfo");
			}
		}*/
		if (StringUtils.isBlank(payMtdDtl.getBankAccHolderName())) {
			errors.rejectValue(pathPrefix+"bankAccHolderName", "lts.holdername.required");
		}
		try{
			if (StringUtils.isBlank(payMtdDtl.getBankAccHolderDocType())) {
				errors.rejectValue(pathPrefix+"bankAccHolderDocType", "lts.invalid.doctype");
			}else if(StringUtils.equals(payMtdDtl.getBankAccHolderDocType(), "HKID")
					&& !Utility.validateHKID(payMtdDtl.getBankAccHolderDocNum())){
				errors.rejectValue(pathPrefix+"bankAccHolderDocNum", "lts.verification.required");
			}else if(StringUtils.equals(payMtdDtl.getBankAccHolderDocType(), "HKBR")){
				if(!StringUtils.isNumeric(payMtdDtl.getBankAccHolderDocNum()) 
						|| !Utility.validateHKBRcheckDigit(payMtdDtl.getBankAccHolderDocNum())){
					errors.rejectValue(pathPrefix+"bankAccHolderDocNum", "lts.verification.required");
				}	
			}
		}catch(Exception e){
			errors.rejectValue(pathPrefix+"bankAccHolderDocNum", "lts.verification.required");
		}
		if (StringUtils.isBlank(payMtdDtl.getBankAccHolderDocNum())) {
			errors.rejectValue(pathPrefix+"bankAccHolderDocNum", "lts.iddocnum.required");
		}
		if (StringUtils.isBlank(payMtdDtl.getBankCode())) {
			errors.rejectValue(pathPrefix+"bankCode", "lts.bankcode.required");
		}
		if (StringUtils.isBlank(payMtdDtl.getBranchCode())) {
			errors.rejectValue(pathPrefix+"branchCode", "lts.branchcode.required");
		}
		if (StringUtils.isBlank(payMtdDtl.getBankAccNum())) {
			errors.rejectValue(pathPrefix+"bankAccNum", "lts.accountnum.required");
		}
		if (!StringUtils.isNumeric(payMtdDtl.getAutoPayUpperLimit())){
			errors.rejectValue(pathPrefix+"autoPayUpperLimit", "lts.invalid.limit");
		}
	}
	
	private void validateCreditCardForm(LtsAcqPaymentMethodFormDTO form, PaymentMethodDtl payMtdDtl, Errors errors, String pathPrefix){
		boolean isThirdParty = false;
		if(payMtdDtl.getThirdPartyCard() == null){
			errors.rejectValue(pathPrefix+"thirdPartyCard", "lts.thirdPartyApplication.required");
		}else if(payMtdDtl.getThirdPartyCard()){
			isThirdParty = true;
			if(StringUtils.equals(payMtdDtl.getCardHolderDocNum(), form.getCustDocNum())
					&& StringUtils.equals(payMtdDtl.getCardHolderDocType(), form.getCustDocType())){
				errors.rejectValue(pathPrefix+"cardHolderDocNum", "lts.invaild.thirdpartyinfo");
			}
		}
		if (StringUtils.isBlank(payMtdDtl.getCardHolderName())) {
			errors.rejectValue(pathPrefix+"cardHolderName", "lts.holdername.required");
		}
		
		if (isContainCreditCardPattern(payMtdDtl.getCardHolderName())) {
        	errors.rejectValue(pathPrefix+"cardHolderName", "lts.holdername.invalid.ccpattern");
        }

		if(!isThirdParty){
			if (StringUtils.isBlank(payMtdDtl.getCardHolderDocNum())) {
				errors.rejectValue(pathPrefix+"cardHolderDocNum", "lts.iddocnum.required");
			}
			if (StringUtils.isBlank(payMtdDtl.getCardHolderDocType())) {
				errors.rejectValue(pathPrefix+"cardHolderDocType", "lts.invalid.doctype");
			}
		}
		
		if(StringUtils.isNotBlank(payMtdDtl.getCardHolderDocNum()) ){
			if (StringUtils.isNotBlank(payMtdDtl.getCardHolderDocType())) {
				if(StringUtils.equals(payMtdDtl.getCardHolderDocType(), "HKID")
					&& !Utility.validateHKID(payMtdDtl.getCardHolderDocNum())){
				errors.rejectValue(pathPrefix+"cardHolderDocNum", "lts.verification.required");
				}else if(StringUtils.equals(payMtdDtl.getCardHolderDocType(), "HKBR")){
					if(!StringUtils.isNumeric(payMtdDtl.getCardHolderDocNum()) 
							|| !Utility.validateHKBRcheckDigit(payMtdDtl.getCardHolderDocNum())){
						errors.rejectValue(pathPrefix+"cardHolderDocNum", "lts.verification.required");
					}	
				}
			}
		}
		if (StringUtils.isBlank(payMtdDtl.getCardNum())) {
			errors.rejectValue(pathPrefix+"cardNum", "lts.cardnum.required");
		}
		if (StringUtils.isBlank(payMtdDtl.getCardType())) {
			errors.rejectValue(pathPrefix+"cardType", "lts.invalid.cardtype");
		}
		if (payMtdDtl.isCardVerifyRequired() && !payMtdDtl.isCardVerified()) {
			errors.rejectValue(pathPrefix+"cardVerified", "lts.verification.required");
		}
		
		int expireMonth = payMtdDtl.getExpireMonth();
		int expireYear = payMtdDtl.getExpireYear();
		Calendar today = GregorianCalendar.getInstance();
		if(expireYear == today.get(Calendar.YEAR)){
			if(expireMonth < today.get(Calendar.MONTH)+1+3){
				errors.rejectValue(pathPrefix+"expireYear", "lts.invaild.ccexpiredate");
			}
		}else if(expireYear - today.get(Calendar.YEAR) == 1){
			if(expireMonth+12 < today.get(Calendar.MONTH)+1+3){
				errors.rejectValue(pathPrefix+"expireYear", "lts.invaild.ccexpiredate");
			}
		}else if(expireYear < today.get(Calendar.YEAR)){
			errors.rejectValue(pathPrefix+"expireYear", "lts.invaild.ccexpiredate");
		}
		
	}
	
    private boolean isContainCreditCardPattern(String s) {
		
		if (StringUtils.isEmpty(s)) {
			return false;
		}
		
		final String expression = "\\b.*(?:\\d[ -]*?){13,16}.*\\b";
		String content = StringUtils.replace(s.trim(), "\n", "");
        content = StringUtils.replace(content, "\r", "");
        content = StringUtils.replace(content, "\\", "");
        content = StringUtils.replace(content, "/", "");
        content = StringUtils.replace(content, ",", "");
        return content.matches(expression);
	}

}
