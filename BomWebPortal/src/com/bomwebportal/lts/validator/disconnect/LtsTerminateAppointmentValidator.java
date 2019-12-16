package com.bomwebportal.lts.validator.disconnect;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateAppointmentFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.validator.LtsCommonValidator;

public class LtsTerminateAppointmentValidator implements Validator {

	private LtsCommonService ltsCommonService;
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public boolean supports(Class clazz) {
		return LtsTerminateAppointmentFormDTO.class.isAssignableFrom(clazz);
	}
	public void validate(Object command, Errors errors) {
		LtsTerminateAppointmentFormDTO form = (LtsTerminateAppointmentFormDTO)command;
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("dd/MM/yyyy");
//		form.setAppntErrorMsg(null);
		
		if(StringUtils.equals("CONFIRM", form.getSubmitInd())){
			if (StringUtils.isBlank(form.getAppntDate())) {
				errors.rejectValue("appntDate", "lts.dis.appnt.date.required");
			}else{
				try {
					df.parse(form.getAppntDate());
				} catch (ParseException e) {
					e.printStackTrace();
					errors.rejectValue("appntDate", "lts.invalid.date");
				}
			}

			if (StringUtils.isBlank(form.getAppntTimeSlotAndType())) {
				errors.rejectValue("appntTimeSlotAndType", "lts.dis.appnt.time.required");
			}else{
				if(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE.equals(form.getAppntTimeSlotType())
						|| LtsConstant.APPOINTMENT_TIMESLOT_TYPE_RESTRICT.equals(form.getAppntTimeSlotType())){
					errors.rejectValue("appntTimeSlotAndType", "lts.invalid.slot");
				}
			}
			
			validateBackDate(form, errors);
			
			if(errors.getErrorCount() == 0){
				form.setConfirmedInd(LtsConstant.TRUE_IND);
				form.setAppntErrorMsg(null);
			}else {
				form.setConfirmedInd(LtsConstant.FALSE_IND);
				if(form.getAppntErrorMsg() == null){
					form.setAppntErrorMsg("Appointment Failed");
				}
			}
		}else if(StringUtils.equals("SUBMIT", form.getSubmitInd())){
			
			if(form.isFieldVisitRequired()){
				if(!StringUtils.equals(form.getConfirmedInd(), LtsConstant.TRUE_IND)){
					errors.rejectValue("confirmedInd", "lts.confirmation.required");
				}else{
					if(StringUtils.isBlank(form.getAppntDate())){
						errors.rejectValue("appntDate", "lts.dis.appnt.date.required");
					}else{
						try {
							df.parse(form.getAppntDate());
						} catch (ParseException e) {
							e.printStackTrace();
							errors.rejectValue("appntDate", "lts.invalid.date");
						}

						if(StringUtils.isBlank(form.getAppntTimeSlotAndType())){
							errors.rejectValue("appntTimeSlotAndType", "lts.dis.appnt.time.required");
						}else{
							if(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE.equals(form.getAppntTimeSlotType())){
								errors.rejectValue("appntTimeSlotAndType", "lts.invalid.slot");
							}
						}
						
						validateBackDate(form, errors);
					}
					
					if(errors.getErrorCount() > 0){
						errors.rejectValue("confirmedInd", "lts.cancel.appointment");
					}
				}
			}else{
				validateBackDate(form, errors);
			}
			
			if (StringUtils.isBlank(form.getCustContactName())) {
				errors.rejectValue("custContactName", "lts.contactname.required");
			}
			
			
			String fixLineNum = StringUtils.remove(form.getCustContactNum(), " ");
			String mobileNum = StringUtils.remove(form.getCustContactMobileNum(), " ");
			if (StringUtils.isNotEmpty(fixLineNum)){
				if(!ltsCommonService.isFixLineNumPrefix(fixLineNum, true)) {
					errors.rejectValue("custContactNum", "lts.invalid.fixlinenum");
				}
			}
			
			if (StringUtils.isEmpty(mobileNum)
					&& StringUtils.isEmpty(fixLineNum)){
				errors.rejectValue("custContactMobileNum", "lts.fixmobcontactPhone.required");
				errors.rejectValue("custContactNum", "lts.fixmobcontactPhone.required");
			}
			if (StringUtils.isNotEmpty(mobileNum)){
				if (!ltsCommonService.isMobileNumPrefix(mobileNum)) {
					errors.rejectValue("custContactMobileNum", "lts.invalid.mobilenum");
				}
			}
			String remarks = form.getRemarks();
			form.setRemarks(LtsCommonValidator.replaceControlCharacter(remarks));
		  
			if(LtsCommonValidator.containsSpecialChar(remarks)){
				errors.rejectValue("remarks", "lts.remarks.specialchar");
			}
			if(LtsCommonValidator.isLongerThanX(remarks, 50)){
				errors.rejectValue("remarks", "lts.remarks.toolong.50");
			}
			form.setCustContactNum(fixLineNum);
			form.setCustContactMobileNum(mobileNum);
			
		}else if(!StringUtils.equals("CANCEL", form.getSubmitInd())
				&& !StringUtils.equals("CLEAR", form.getSubmitInd())){
			if(StringUtils.isBlank(form.getSuspendReason()) 
					|| form.getSuspendReason().compareTo("NONE") == 0){
				errors.rejectValue("suspendReason", "lts.reason.required");
			}
		}
		
	}

	private void validateBackDate(LtsTerminateAppointmentFormDTO form, Errors errors){
		if(!form.isAllowBackDate() && StringUtils.isNotBlank(form.getAppntDate())){
			DateTime appntDate = DateTime.parse(form.getAppntDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
			if(appntDate.isBeforeNow()){
				errors.rejectValue("appntDate", "lts.dis.backDate.not.allow");
			}
		}
	}
}
