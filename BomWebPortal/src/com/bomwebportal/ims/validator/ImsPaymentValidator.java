package com.bomwebportal.ims.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.ims.dto.ui.ImsPaymentUI;
import com.bomwebportal.util.Utility;

public class ImsPaymentValidator implements Validator{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean supports(Class clazz) {
		return clazz.isAssignableFrom(ImsPaymentUI.class);
	}

	public boolean isValidDate(String date){
		logger.info("validate call date");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date testDate = null;
		
		String errorMessage = null;
		
		try{
			testDate = sdf.parse(date);
		}
		catch (ParseException e){
			errorMessage = "the date you provided is in an invalid date format.";
			logger.info(errorMessage);
			return false;
		}
		if (!sdf.format(testDate).equals(date)){
			errorMessage = "The date you provided is invalid.";
			logger.info(errorMessage);
			return false;
		}
		
		return true;
	}

	public boolean isPreviousDate(String date){
		logger.info("validate call date");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date testDate = null;
		Date todayDate = null;
		
		String errorMessage = null;
		
		try{
			testDate = sdf.parse(date);
		}
		catch (ParseException e){
			errorMessage = "The date you provided is in an invalid date format.";
			logger.info(errorMessage);
			return false;
		}
		
		try{
			todayDate = sdf.parse(sdf.format(new Date() ));
		}
		catch (ParseException e){
			errorMessage = "The date you provided is in an invalid date format.";
			logger.info(errorMessage);
			return false;
		}
		
		if (testDate.before(todayDate)||testDate.equals(todayDate)){
			return true;
		}else{
			return false;
		}
	}

	public boolean isValidTime(String time){
		logger.info("validate call time");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		Date testDate = null;
		
		String errorMessage = null;
		
		try{
			testDate = sdf.parse(time);
		}
		catch (ParseException e){
			errorMessage = "the time you provided is in an invalid time format.";
			logger.info(errorMessage);
			return false;
		}
		if (!sdf.format(testDate).equals(time)){
			errorMessage = "The time you provided is invalid.";
			logger.info(errorMessage);
			return false;
		}
		
		return true;
	}
	
	private boolean compareTime(String time){
		
	    Date inputTime = null;
	    Date currentTime = null;
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = sdf.format(cal.getTime());
//        System.out.println("Current date in String Format: " + strDate);
//        System.out.println("input date in String Format: " + time);

        SimpleDateFormat sdf1 = new SimpleDateFormat();
        sdf1.applyPattern("dd/MM/yyyy HH:mm");
        try{
        	currentTime = sdf1.parse(strDate);
            }
    		catch (ParseException e){
    		//errorMessage = "1 The time you provided is in an invalid time format.";
    		//logger.info(errorMessage);
    		return false;
    	}
    		
    	try{
            inputTime = sdf1.parse(time);
            }
    		catch (ParseException e){
    		//errorMessage = "1 The time you provided is in an invalid time format.";
    		//logger.info(errorMessage);
    		return false;
    	}
		
    	if ( inputTime.before( currentTime ) || inputTime.equals( currentTime )) {
	        return true;
	    }else{
	    	return false;
	    }
	}
	
	/**
	 * main validationr logic
	 */
	public void validate(Object obj, Errors errors){
		logger.info("validate is called");
		
		ImsPaymentUI payment = (ImsPaymentUI)obj;
		logger.info(payment.getSubmitTag());

		if("Y".equals(payment.getIsAmendOrder())) return;	//jacky 20150109
		
		//check rejectIfEmptyOrWhitespace
		if(payment.getsReasonCd() != null && payment.getSubmitTag().equals("S")){
			
			if ( ("Y").equals(payment.getIsCC()) )
			{
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "appMethod", "ims.pcd.payment.msg.015");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sourcecode", "ims.pcd.payment.msg.016");
			}
			
		}else if(payment.getSubmitTag().equals("R")){
			
		}else if(payment.getSubmitTag().equals("W")){
			
		}else if(payment.getSubmitTag().equals("U")){
			
		}else if(payment.getcReasonCd() != null && payment.getSubmitTag().equals("C")){
			
		}else{
		if(payment.getPayMtdType().equals("C")){
			

			logger.info("getCcHoldName():"+payment.getCcHoldName());
			if(payment.getCcHoldName()!=null && Utility.imsIsContainCreditCardPattern(payment.getCcHoldName())){
				errors.rejectValue("ccHoldName", "ims.pcd.payment.msg.017");		
			}
			
			if(payment.getCcHoldName()!=null && !"".equals(payment.getCcHoldName()) && !Utility.isEng(payment.getCcHoldName())){
				errors.rejectValue("ccHoldName", "ims.pcd.payment.msg.018");		
			}
	
			logger.info("getEmailAddr():"+payment.getEmailAddr());
			if(payment.getEmailAddr()!=null && Utility.imsIsContainCreditCardPattern(payment.getEmailAddr())){
				errors.rejectValue("emailAddr", "ims.pcd.payment.msg.019");		
			}
	
			logger.info("getCcIdDocNo():"+payment.getCcIdDocNo());
			if(payment.getCcIdDocNo()!=null && Utility.imsIsContainCreditCardPattern(payment.getCcIdDocNo())){
				errors.rejectValue("ccIdDocNo", "ims.pcd.payment.msg.020");		
			}
			
			ValidationUtils.rejectIfEmpty(errors, "ccVerified", "ims.pcd.payment.msg.021");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "thirdPartyInd", "ims.pcd.payment.msg.022");
			
			if(payment.getThirdPartyInd().equals("Y") && !("Y").equals(payment.getIsCC()))
			{
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ccIdDocType", "ims.pcd.payment.msg.023");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ccIdDocNo", "ims.pcd.payment.msg.024");
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ccHoldName", "ims.pcd.payment.msg.025");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ccType", "ims.pcd.payment.msg.026");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ccNum", "ims.pcd.payment.msg.027");
			
			
			
			if(payment.getCcType() != null && !("").equals(payment.getCcType()) 
					&& payment.getCcNum()!= null && !("").equals(payment.getCcNum())
			){
				if(payment.getCcNum().substring(0, 1).equals("4") && !payment.getCcType().equals("V")){
					errors.rejectValue("ccType", "ims.pcd.payment.msg.028");
				}else if((payment.getCcNum().substring(0, 2).equals("51") || payment.getCcNum().substring(0, 2).equals("52")
						|| payment.getCcNum().substring(0, 2).equals("53") || payment.getCcNum().substring(0, 2).equals("54")
						|| payment.getCcNum().substring(0, 2).equals("55")) && !payment.getCcType().equals("M")){
					errors.rejectValue("ccType", "ims.pcd.payment.msg.028");
				}else if((payment.getCcNum().substring(0, 2).equals("34") || payment.getCcNum().substring(0, 2).equals("37"))
						&& !payment.getCcType().equals("A")){
					errors.rejectValue("ccType", "ims.pcd.payment.msg.028");
				}
			}
			Calendar today = Calendar.getInstance();
			int year = today.get(Calendar.YEAR);
			int month = today.get(Calendar.MONTH)+1;
			//logger.info("Year: " + payment.getExpiryYear());
			
			if ( payment.getCcExpDate() != null && !payment.getCcExpDate().isEmpty() )
			{
				if(Integer.parseInt(payment.getExpiryYear())==year){
					if(Integer.parseInt(payment.getExpiryMonth()) <= (month+2)){
						errors.rejectValue("expiryMonth", "ims.pcd.payment.msg.029");
					}
				}
				//logger.info(Integer.parseInt(payment.getExpiryYear())-(year));
				if(Integer.parseInt(payment.getExpiryYear())-(year)==1){
					//logger.info(Integer.parseInt(payment.getExpiryMonth()));
					if(Integer.parseInt(payment.getExpiryMonth())<1 && month==10){
						//logger.info(Integer.parseInt(payment.getExpiryMonth()));
						errors.rejectValue("expiryMonth", "ims.pcd.payment.msg.029");
					}
					if(Integer.parseInt(payment.getExpiryMonth())<2 && month==11){
						errors.rejectValue("expiryMonth", "ims.pcd.payment.msg.029");
					}
					if(Integer.parseInt(payment.getExpiryMonth())<3 && month==12){
						errors.rejectValue("expiryMonth", "ims.pcd.payment.msg.029");
					}
				}
			}
			
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ccExpDate", "ims.pcd.payment.msg.030");
		}
		
		
		
		if(payment.getBillMedia().equals("E")){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddr", "ims.pcd.payment.msg.031");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesCd", "ims.pcd.payment.msg.032");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesName", "salesName.required");
		
		if ("Y".equals(payment.getIsDS())){	//jacky 20141203
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesContactNum", "ims.pcd.payment.msg.033");
			if(!StringUtils.isEmpty(payment.getSalesContactNum())) { 
				String pattern = "[569][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
				if (!payment.getSalesContactNum().matches(pattern)) errors.rejectValue("salesContactNum", "ims.pcd.payment.msg.034");
			}
		} else 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesContactNum", "ims.pcd.payment.msg.035");
		
		
		if ( ("Y").equals(payment.getIsCC()) )
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "appMethod", "ims.pcd.payment.msg.015");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sourcecode", "ims.pcd.payment.msg.016");
		}
		else
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shopCd", "ims.pcd.payment.msg.036");
		}
		
		//Tony
		//Validate call date and call time 
		if ( ("Y").equals(payment.getIsCC()) )
		{
			if(payment.getSalesReferrerDTO() != null){
				if(!(StringUtils.isEmpty(payment.getSalesReferrerDTO().getReferrerAppMethod()) && StringUtils.isEmpty(payment.getSalesReferrerDTO().getReferrerSourcecode()) 
					  && StringUtils.isEmpty(payment.getSalesReferrerDTO().getReferrerStaffName()) && StringUtils.isEmpty(payment.getSalesReferrerDTO().getReferrerStaffNo()) )){
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesReferrerDTO.referrerAppMethod", "ims.pcd.payment.msg.037");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesReferrerDTO.referrerSourcecode", "ims.pcd.payment.msg.038");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesReferrerDTO.referrerStaffNo", "ims.pcd.payment.msg.039");
				
				}
			}
			
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "callDate", "ims.pcd.payment.msg.040");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "callTime", "ims.pcd.payment.msg.041");
			if (!payment.getCallDate().isEmpty()){
				logger.info("date validation result "+isValidDate(payment.getCallDate()));
				if (!isValidDate(payment.getCallDate())){
					errors.rejectValue("callDate", "callDate.invalid");
				}else{
					if (!isPreviousDate(payment.getCallDate())){
						errors.rejectValue("callDate", "callDate.before");
					}else{
						if (!payment.getCallTime().isEmpty()){
							logger.info("time validation result "+isValidTime(payment.getCallTime()));
							if (!isValidTime(payment.getCallTime())){
								errors.rejectValue("callTime", "callTime.invalid");
							}else{
								if (!compareTime(payment.getCallDate()+" "+payment.getCallTime())){
									errors.rejectValue("callTime", "callTime.before");
								}
							}
						}
					}
				}
			}
		}
		
		
	}
	}

}
