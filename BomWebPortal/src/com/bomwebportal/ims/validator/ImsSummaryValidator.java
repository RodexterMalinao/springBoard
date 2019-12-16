package com.bomwebportal.ims.validator;

//import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.ims.dto.ui.ImsSummaryUI;
import com.bomwebportal.ims.service.ValidateAccountNumService;
import com.bomwebportal.util.Utility;

public class ImsSummaryValidator implements Validator{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private ValidateAccountNumService validateAccountNumService;
	
	public ValidateAccountNumService getValidateAccountNumService() {
		return validateAccountNumService;
	}

	public void setValidateAccountNumService(
			ValidateAccountNumService validateAccountNumService) {
		this.validateAccountNumService = validateAccountNumService;
	}
	
	public boolean supports(Class clazz) {
		return clazz.isAssignableFrom(ImsSummaryUI.class);
	}
	/**
	 * main validationr logic
	 */
	public void validate(Object obj, Errors errors){
		logger.info("validate is called");
		
		ImsSummaryUI summary = (ImsSummaryUI)obj;		
		//logger.info(payment.getPayMtdType());


		logger.info("getEmailAddr():"+summary.getEmailAddr());

		
		//check rejectIfEmptyOrWhitespace
		if(summary.getcReasonCd() != null && summary.getSubmitTag().equals("C")){
			
		}else{
			
			
			
			if("M".equals(summary.getAccount().getPayment().getPayMtdType())
					&& !"Y".equals(summary.getCashFsPrepay()) 
					&& !"Y".equals(summary.getWaivedPrepay())
					&& !"Y".equals(summary.getIsCC())
					&& !"Y".equals(summary.getIsDS())	//jacky
					)
			{
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "primaryAcctNo", "ims.pcd.summary.msg.014");
				if(summary.getPrimaryAcctNo() != null
					&& !("").equals(summary.getPrimaryAcctNo())
					)
				{
					char theChar;
					boolean valid = true;
					int result = -1;
					String errorText = null;
					
					for(int idx = 0; idx < summary.getPrimaryAcctNo().length(); ++idx)
					{
						theChar = summary.getPrimaryAcctNo().charAt(idx);
						logger.info("theChar: " + theChar);
						if(!Character.isDigit(theChar))
						{
							valid = false;
//							errorText = "Pregen account should contain numbers only.";
							errors.rejectValue("primaryAcctNo", "ims.pcd.summary.msg.015");
							break;
						}
					}
					
					if(valid){
						long acctnb = Long.parseLong(summary.getPrimaryAcctNo());
						String srccode = null;
					
						result = validateAccountNumService.validateAccountNum(acctnb, srccode);

						if(result != 0){
							errorText = validateAccountNumService.validateAccountNumError(acctnb, srccode);
							errors.rejectValue("primaryAcctNo", "primaryAcctNo.error", errorText);
							//errors.rejectValue("primaryAcctNo", "primaryAcctNo.error");
						}
					}
				}
			}
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "langPreference", "langPreference.required");
			
	//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "printFormClickInd", "langPreference.required");
						
			String loginEmail = summary.getLoginId()+"@netvigator.com";
			
			if (summary.getDisMode() == null) {
				errors.rejectValue("distributionMode", "ims.pcd.summary.msg.016");
			} else {
				switch (summary.getDisMode()) {
					case E:
					{
						// kinman 05-11-2012
						//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "esigEmailAddr", null, "Please provide email address.");
	//					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "printPreviewFormSignedInd", null, "Please preview AF before signoff.");
	//					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clickDigitalSignButtonInd", null, "Please submit digital signature before signoff.");
						System.out.println("In ImsSummaryValidator, summary.getNoSupportingDoc():"+summary.getNoSupportingDoc());
				    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "esigEmailAddr", "ims.pcd.summary.msg.022");
				    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "SMSno", "ims.pcd.summary.msg.021");
						if(summary.getNoSupportingDoc().equals("false") && !"Y".equals(summary.getIsDS())){	//ims direct sales jacky 
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clickCaptureIpadButtonInd", "ims.pcd.summary.msg.017");
						}
						if (StringUtils.isNotBlank(summary.getEsigEmailAddr())) {
							String isds = summary.getIsDS(); // Eddie 2015-07-24
							if (!Utility.validateEmail(summary.getEsigEmailAddr())) {
								errors.rejectValue("esigEmailAddr",  "ims.pcd.summary.msg.018");
							}else if (Utility.imsIsContainCreditCardPattern(summary.getEmailAddr())){
								errors.rejectValue("esigEmailAddr", "ims.pcd.summary.msg.013");		
							}else if(("N").equalsIgnoreCase(isds)){
								if (summary.getEsigEmailAddr().substring(summary.getEsigEmailAddr().lastIndexOf("@") + 1).equalsIgnoreCase("netvigator.com")){
									String loginID = summary.getEsigEmailAddr().substring(0, summary.getEsigEmailAddr().indexOf("@"));
									String emailAddrStatus = validateAccountNumService.validateEmailAvailable(loginID);
									if(!(("A").equalsIgnoreCase(emailAddrStatus)))
										errors.rejectValue("esigEmailAddr", "ims.pcd.summary.msg.019");
								}else if (loginEmail.equalsIgnoreCase(summary.getEsigEmailAddr())){
									errors.rejectValue("esigEmailAddr", "ims.pcd.summary.msg.019");
								}
							}else if(("Y").equalsIgnoreCase(isds)){
								if (summary.getEsigEmailAddr().substring(summary.getEsigEmailAddr().lastIndexOf("@") + 1).equalsIgnoreCase("netvigator.com")){
									//String loginID = summary.getEsigEmailAddr().substring(0, summary.getEsigEmailAddr().indexOf("@"));
									//String emailAddrStatus = validateAccountNumService.validateEmailAvailable(loginID);
									//if(!(("A").equalsIgnoreCase(emailAddrStatus)))
										//errors.rejectValue("esigEmailAddr", null, "Netvigator Login ID is not allowed to be used here.");
								}else if (loginEmail.equalsIgnoreCase(summary.getEsigEmailAddr())){
									//errors.rejectValue("esigEmailAddr", null, "Netvigator Login ID is not allowed to be used here.");
								}
							}
						}
						//ims ds celia 20141125
//						if("Y".equals(summary.getIsDS())){
							if (StringUtils.isNotBlank(summary.getSMSno())) {
								if (!Utility.validateHKSMSPrefixIMS(summary.getSMSno()) || !Utility.validatePhoneNum(summary.getSMSno()) ) {
									errors.rejectValue("SMSno",  "ims.pcd.summary.msg.020");
								}
							}
//						}
						
	//					if (summary.getEsigEmailLang() == null) {
	//						errors.rejectValue("esigEmailLang", null, "Require Language.");
	//					}
						
	//					if (summary.getPrintPreviewFormSignedInd() != "Y") {
	//						errors.rejectValue("printPreviewFormSignedInd", null, "Require Preview Form Signed.");
	//					}
	//					
	//					if (summary.getClickDigitalSignButtonInd() != "Y") {
	//						errors.rejectValue("clickDigitalSignButtonInd", null, "Require Click Digital Signature Button.");
	//					}
						
						
						break;
					}
					case P:
					{
	//					if (!CollectMethod.P.equals(summary.getCollectMethod())) {
	//						errors.rejectValue("collectMethod", null, "Only allow Copy Collect Method = Paper when Distribution Mode = Paper");
	//					}
						
	//					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "printFormClickInd", "langPreference.required");
						break;
					}
					
					case S:
					{
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "SMSno", "ims.pcd.summary.msg.021");
						if (StringUtils.isNotBlank(summary.getSMSno())) {
							if (!Utility.validateHKSMSPrefixIMS(summary.getSMSno()) || !Utility.validatePhoneNum(summary.getSMSno()) ) {
								errors.rejectValue("SMSno",  "ims.pcd.summary.msg.020");
							}
						}
						break;
					} 
					
					case I:
					{
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddr", "ims.pcd.summary.msg.022");
						if (StringUtils.isNotBlank(summary.getEmailAddr())) {
							String isds = summary.getIsDS(); // Eddie 2015-07-24
							if (!Utility.validateEmail(summary.getEmailAddr())) {
								errors.rejectValue("emailAddr", "ims.pcd.summary.msg.018");
							}else if (Utility.imsIsContainCreditCardPattern(summary.getEmailAddr())){
								errors.rejectValue("emailAddr", "ims.pcd.summary.msg.013");		
							}else if(("N").equalsIgnoreCase(isds)){
								if (summary.getEmailAddr().substring(summary.getEmailAddr().lastIndexOf("@") + 1).equalsIgnoreCase("netvigator.com")){
									String loginID = summary.getEmailAddr().substring(0, summary.getEmailAddr().indexOf("@"));
									String emailAddrStatus = validateAccountNumService.validateEmailAvailable(loginID);
									if(!(("A").equalsIgnoreCase(emailAddrStatus)))
										errors.rejectValue("emailAddr",  "ims.pcd.summary.msg.019");
								}else if (loginEmail.equalsIgnoreCase(summary.getEmailAddr())){
									errors.rejectValue("emailAddr",  "ims.pcd.summary.msg.019");
								}
							}else if(("Y").equalsIgnoreCase(isds)){
								if (summary.getEmailAddr().substring(summary.getEmailAddr().lastIndexOf("@") + 1).equalsIgnoreCase("netvigator.com")){
									//String loginID = summary.getEmailAddr().substring(0, summary.getEmailAddr().indexOf("@"));
									//String emailAddrStatus = validateAccountNumService.validateEmailAvailable(loginID);
									//if(!(("A").equalsIgnoreCase(emailAddrStatus)))
										//errors.rejectValue("emailAddr", null, "Netvigator Login ID is not allowed to be used here.");
								}else if (loginEmail.equalsIgnoreCase(summary.getEmailAddr())){
									//errors.rejectValue("emailAddr", null, "Netvigator Login ID is not allowed to be used here.");
								}
							}
						}
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "SMSno", "ims.pcd.summary.msg.021");
						if (StringUtils.isNotBlank(summary.getSMSno())) {
							if (!Utility.validateHKSMSPrefixIMS(summary.getSMSno()) || !Utility.validatePhoneNum(summary.getSMSno()) ) {
								errors.rejectValue("SMSno",  "ims.pcd.summary.msg.020");
							}
						}
						break;
						
					}
				}
			}
			
			try{
				if(errors.hasErrors()){
									
						if(errors.getFieldError() != null && errors.getFieldErrorCount() > 0){
							for(Object e : errors.getFieldErrors()){
								FieldError fe = (FieldError)e;
								logger.debug("Field error (filed: " + fe.getField() + " , rejected value: " + fe.getDefaultMessage() + ")");
								
							}
						}
					
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
	}
}
