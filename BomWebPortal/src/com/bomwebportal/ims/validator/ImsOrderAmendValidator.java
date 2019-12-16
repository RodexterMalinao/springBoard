package com.bomwebportal.ims.validator;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AppointmentTimeSlotDTO;
import com.bomwebportal.ims.dto.ui.AmendOrderImsUI;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

public class ImsOrderAmendValidator implements Validator{

    private Gson gson = new Gson();
	protected final Log logger = LogFactory.getLog(getClass());
	private ImsOrderAmendService imsOrderAmendservice;

	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}

	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}
	
	public boolean supports(Class clazz) {
		return clazz.isAssignableFrom(AmendOrderImsUI.class);
	}

	
	
	/**
	 * main validationr logic
	 */
	public void validate(Object obj, Errors errors){
		AmendOrderImsUI amend = (AmendOrderImsUI)obj;
		amend.printAmend();
		logger.info("amend order_id:" +amend.getOrderImsUI().getOrderId()+ " ui:" + gson.toJson(amend));	
//		if(true)return;

		if(amend.getApplicantName()!=null && Utility.imsIsContainCreditCardPattern(amend.getApplicantName())){
			logger.info("getApplicantName():"+amend.getApplicantName());
			errors.rejectValue("commonErrorArea", "ContainCCPattern.imsOrderAmend.appName");			
		}

		if(amend.getCancelRemark()!=null &&Utility.imsIsContainCreditCardPattern(amend.getCancelRemark())){
			logger.info("amend.getCancelRemark():"+amend.getCancelRemark());
			errors.rejectValue("commonErrorArea", "ContainCCPattern.imsOrderAmend.OcRemark");			
		}

		if(amend.getCcHolderName()!=null &&Utility.imsIsContainCreditCardPattern(amend.getCcHolderName())){
			logger.info("getCcHolderName():"+amend.getCcHolderName());
			errors.rejectValue("commonErrorArea", "ContainCCPattern.CCholderName");			
		}

		if(amend.getAmendRemark1()!=null &&Utility.imsIsContainCreditCardPattern(amend.getAmendRemark1())){
			logger.info("getAmendRemark1():"+amend.getAmendRemark1());
			errors.rejectValue("commonErrorArea", "ContainCCPattern.imsOrderAmend.FsRemark1");			
		}

		if(amend.getAmendRemark2()!=null &&Utility.imsIsContainCreditCardPattern(amend.getAmendRemark2())){
			logger.info("getAmendRemark2():"+amend.getAmendRemark2());
			errors.rejectValue("commonErrorArea", "ContainCCPattern.imsOrderAmend.FsRemark2");		
		}

		if(amend.getAmendRemark3()!=null &&Utility.imsIsContainCreditCardPattern(amend.getAmendRemark3())){
			logger.info("getAmendRemark3():"+amend.getAmendRemark3());
			errors.rejectValue("commonErrorArea", "ContainCCPattern.imsOrderAmend.FsRemark3");	
		}

		if((ImsConstants.IMS_AMEND_WQ_NATURE_CancelOrder_FS.equals(amend.getAmendNature1())||
			ImsConstants.IMS_AMEND_WQ_NATURE_CancelOrder_FS.equals(amend.getAmendNature2())||
			ImsConstants.IMS_AMEND_WQ_NATURE_CancelOrder_FS.equals(amend.getAmendNature3()))
				&&Utility.imsIsContainCreditCardPattern(amend.getFsCancelRemark())){
			logger.info("getFsCancelRemark():"+amend.getFsCancelRemark());
			errors.rejectValue("commonErrorArea", "ContainCCPattern.imsOrderAmend.OcRemarkFS");	
		}
		
		if(imsOrderAmendservice.isPendingExist(amend.getOrderImsUI().getOrderId())){
			errors.rejectValue("commonErrorArea", "PendingAmendExist");
		}
		
		if(!imsOrderAmendservice.isOCIDexist(amend.getOrderImsUI().getOrderId())){
			errors.rejectValue("commonErrorArea", "OCIDnotExist");
		}
		
		if(StringUtils.isNotBlank( amend.getEmailAddr()))
			if (!Utility.validateEmail(amend.getEmailAddr())) {
				errors.rejectValue("emailAddr", "emailAddr.error", "Invalid Email format");
			}
		if (StringUtils.isNotBlank(amend.getSMSno())) {
			if (!Utility.validateHKSMSPrefixIMS(amend.getSMSno()) || !Utility.validatePhoneNum(amend.getSMSno()) ) {
				errors.rejectValue("SMSno", "SMSno.error", "Invalid mobile phone number format");
			}
		}

		String amendType="";		
		if("Y".equalsIgnoreCase(amend.getIsCancelOrderEnabled())){ // Cancel order alone
			amendType+="CancelOrder";
		}else if("Y".equals(amend.getIsDOrderSelfPickEnabled())){
			amendType+="FSAmend";
		}else if("Y".equals(amend.isFS())){
			amendType+="FSAmend";
		}else if("Y".equalsIgnoreCase(amend.getIsUpdateCreditEnabled())&&
				"Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())){
			amendType+="UpdateCC";
			amendType+="AmendSRD";
		}else if("Y".equalsIgnoreCase(amend.getIsUpdateCreditEnabled())){ // CC alone
			amendType+="UpdateCC";
		}else if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())){ // Appointment alone
			amendType+="AmendSRD";
		} 
		
		if(("NTV-A".equals(amend.getOrderImsUI().getOrderType()) || amend.getOrderImsUI().isOrderTypeNowRet() || "DS".equals(amend.getOrderImsUI().getImsOrderType()))
				&&"Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())){
			amendType+="DSSRD";//for DS black list address
			if("Y".equals(amend.getIsDsSupport())){
				amendType+="DsSupport";//for DS support can change SRD even bom order suspend
			}
		}
		
		AppointmentTimeSlotDTO appTimeSlotDTO = null;
		try {
			appTimeSlotDTO= imsOrderAmendservice.getAmendSRDInfoStorProd(amend.getOrderImsUI().getOrderId(), amendType);
		} catch (DAOException e) {
			e.printStackTrace();
		} 		
		amend.setBomSRD(appTimeSlotDTO.getApptDate());
		
		String configFiles = "";
		configFiles = "imsReportConstant.xml";
		MessageSource msg = new ClassPathXmlApplicationContext(configFiles);
		
		logger.info("appTimeSlotDTO getErrorCode():"+appTimeSlotDTO.getErrorCode());
		logger.info("appTimeSlotDTO getErrorMsg():"+appTimeSlotDTO.getErrorMsg());
		logger.info("appTimeSlotDTO getReturnValue():"+appTimeSlotDTO.getReturnValue());
		if(appTimeSlotDTO.getErrorCode()!=0 || appTimeSlotDTO.getReturnValue()!=0){
			if(appTimeSlotDTO.getErrorCode()!=11&&appTimeSlotDTO.getErrorCode()!=12){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commonErrorArea", "Amend"+appTimeSlotDTO.getErrorCode());
			}else{
				String special = (String) appTimeSlotDTO.getErrorMsg().subSequence(appTimeSlotDTO.getErrorMsg().indexOf("$")+1, appTimeSlotDTO.getErrorMsg().length());
				String errorMsg = msg.getMessage("Amend"+appTimeSlotDTO.getErrorCode(), new Object[] {special}, null, new Locale("en", "US"));
				logger.info("special:"+special);
				logger.info("errorMsg :"+errorMsg);
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commonErrorArea", null, errorMsg);
			}
		}
		if(amend.getCcType() != null && !("").equals(amend.getCcType()) 
				&& amend.getCcNum()!= null && !("").equals(amend.getCcNum())
		){
			if(amend.getCcNum().substring(0, 1).equals("4") && !amend.getCcType().equals("V")){
				errors.rejectValue("ccType", "cardType.unmatch");
			}else if((amend.getCcNum().substring(0, 2).equals("51") || amend.getCcNum().substring(0, 2).equals("52")
					|| amend.getCcNum().substring(0, 2).equals("53") || amend.getCcNum().substring(0, 2).equals("54")
					|| amend.getCcNum().substring(0, 2).equals("55")) && !amend.getCcType().equals("M")){
				errors.rejectValue("ccType", "cardType.unmatch");
			}else if((amend.getCcNum().substring(0, 2).equals("34") || amend.getCcNum().substring(0, 2).equals("37"))
					&& !amend.getCcType().equals("A")){
				errors.rejectValue("ccType", "cardType.unmatch");
			}
		}
	}
}
