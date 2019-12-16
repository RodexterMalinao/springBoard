package com.bomwebportal.lts.validator.acq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO.LtsAppointmentDetail;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;

public class LtsAcqAppointmentValidator implements Validator {
	
	private LtsCommonService ltsCommonService;
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	public boolean supports(Class clazz) {
		return LtsAcqAppointmentFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsAcqAppointmentFormDTO form = (LtsAcqAppointmentFormDTO)command;
		
		if(StringUtils.equals("CANCEL", form.getSubmitInd())
				|| StringUtils.equals("SEARCHPCD", form.getSubmitInd())
				|| StringUtils.equals("CLEARPCD", form.getSubmitInd())
				){
			return;
		}
		
		if(form.getPortLaterAppntDtl() != null
				&& form.getInstallAppntDtl() != null){
			LtsSRDDTO pipbEarliestSRD = new LtsSRDDTO(form.getInstallAppntDtl().getAppntDate());
			pipbEarliestSRD.addDate(1+LtsSbOrderHelper.getPipbMinDay()+2); //T+MINIMUM_PIPB_DAY+2, T=next day of SRD
			form.getPortLaterAppntDtl().setEarliestSRD(pipbEarliestSRD);
		}
		
		validateTimeSlot(form.getInstallAppntDtl(), errors, "installAppntDtl", form.isTentativeInd());
		validateTimeSlot(form.getPreWiringAppntDtl(), errors, "preWiringAppntDtl", form.isTentativeInd());
		validateTimeSlot(form.getSecDelInstallAppntDtl(), errors, "secDelInstallAppntDtl", form.isTentativeInd());
		validateTimeSlot(form.getPortLaterAppntDtl(), errors, "portLaterAppntDtl", form.isTentativeInd());
		
		if(StringUtils.equals("CONFIRM", form.getSubmitInd())){
			
			validateDate(form, errors);
			
			if(errors.hasErrors()){
				form.setErrorMsg("Appointment Failed");
			}
		}else if(StringUtils.equals("SUBMIT", form.getSubmitInd())){
			validateDate(form, errors);
			
			if(!form.isConfirmedInd()){
				errors.rejectValue("confirmedInd", "lts.confirmation.required");
			}
			String contactName = form.getInstallationContactName();
			String smsNum = StringUtils.remove(form.getInstallationMobileSMSAlert(), " ");
			String contact = StringUtils.remove(form.getInstallationContactNum(), " ");
			
			if(StringUtils.isEmpty(contactName)){
				errors.rejectValue("installationContactName", "lts.contactname.required");
			}
			
			if (StringUtils.isEmpty(contact)) {
				errors.rejectValue("installationContactNum", "lts.contactPhone.required");
			}else if (!ltsCommonService.isFixLineNumPrefix(contact, true)
					&& !ltsCommonService.isMobileNumPrefix(contact)) {
				errors.rejectValue("installationContactNum", "lts.invalid.contactPhone");
			}
			if (StringUtils.isNotEmpty(smsNum)){
				if (!ltsCommonService.isMobileNumPrefix(smsNum)) {
					errors.rejectValue("installationMobileSMSAlert", "lts.invalid.mobilenum");
				}
			}
			
			if (StringUtils.isNotBlank(form.getRemarks())){
				if(form.getRemarks().length() > 100){
					errors.rejectValue("remarks", "lts.remarks.toolong");
				}
			}
			if (StringUtils.isNotBlank(form.getQcRemarks())){
				if(form.getQcRemarks().length() > 100){
					errors.rejectValue("qcRemarks", "lts.remarks.toolong");
				}
			}
			if (StringUtils.isNotBlank(form.getSuspendRemarks())){
				if(form.getSuspendRemarks().length() > 100){
					errors.rejectValue("suspendRemarks", "lts.remarks.toolong");
				}
			}
			//BOM2018061
			if(form.getEpdItemList() != null && form.getEpdItemList().size() > 0){
				boolean selectedInd = false;
				for(ItemDetailDTO epdItem: form.getEpdItemList()){
					if(epdItem.isSelected()){
						selectedInd = true;
						if(LtsConstant.ITEM_TYPE_EPD_ACCEPT.equals(epdItem.getItemType())){
							if(StringUtils.isNotBlank(form.getInstallAppntDtl().getAppntDate())){
								LocalDateTime installDate = LocalDateTime.parse(form.getInstallAppntDtl().getAppntDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
								for(ItemAttbDTO attb: epdItem.getItemAttbs()){
									if(LtsConstant.ITEM_ATTB_FORMAT_DATE.equals(attb.getInputFormat())
											&& !"N".equals(attb.getVisualInd())){
										if(StringUtils.isBlank(attb.getAttbValue())){
											errors.rejectValue("epdItemList", "lts.invalid.date");
										}else{
											LocalDateTime epdCollectDate = LocalDateTime.parse(attb.getAttbValue(), DateTimeFormat.forPattern("dd/MM/yyyy"));
											if(!epdCollectDate.isAfter(installDate.plusDays(form.getEpdLeadDay()))){
												errors.rejectValue("epdItemList", "lts.invalid.date");
											}
										}
										break;
									}
								}
							}
						}
						break;
					}
					
				}
				
				if(!selectedInd){
					errors.rejectValue("epdItemList", "lts.option.required");
				}
			}
		} else if ("SUSPEND".equals(form.getSubmitInd())) {
			if(StringUtils.isBlank(form.getSuspendReason()) || form.getSuspendReason().compareTo("NONE") == 0){
				errors.rejectValue("suspendReason", "lts.reason.required");
			}
			if (StringUtils.isNotBlank(form.getSuspendRemarks())){
				if(form.getSuspendRemarks().length() > 100){
					errors.rejectValue("suspendRemarks", "lts.remarks.toolong");
				}
			}
		}
		
	}

	private void validateDate(LtsAcqAppointmentFormDTO form, Errors errors){
		
		if(errors.hasErrors()){
			return;
		}
		
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime instEarliestSRD = form.getInstallAppntDtl().getEarliestSRD().getSRDate();
		DateTime installDate = DateTime.parse(form.getInstallAppntDtl().getAppntDate(), format );
		
		/*DS: if Door knocked and not waive cooling off period, min = T+7*/
		if(form.isDsDoorKnockedInd() && !form.isWaiveCoolingOffInd()){
			DateTime notWaiveMinSRD = LocalDate.now().plusDays(7).toDateTimeAtStartOfDay();
			instEarliestSRD = instEarliestSRD.isAfter(notWaiveMinSRD)? instEarliestSRD: notWaiveMinSRD;
		}
		
		if(installDate.isBefore(instEarliestSRD)){
			errors.rejectValue("installAppntDtl.appntDate", "lts.invalid.date");
		}
		
		if(form.getSecDelInstallAppntDtl() != null){
			DateTime secDelDate = DateTime.parse( form.getSecDelInstallAppntDtl().getAppntDate(), format );
			if(secDelDate != null && secDelDate.isBefore(installDate)){
				errors.rejectValue("secDelInstallAppntDtl.appntDate", "lts.invalid.date");
				
			}else if(form.getSecDelInstallAppntDtl().getEarliestSRD() != null){
				DateTime secDelEarliestSRD = form.getSecDelInstallAppntDtl().getEarliestSRD().getSRDate();
				if(secDelDate.isBefore(secDelEarliestSRD)){
					errors.rejectValue("secDelInstallAppntDtl.appntDate", "lts.invalid.date");
				}
			}
		}
		
		DateTime preWiringDate = form.getPreWiringAppntDtl() != null ? DateTime.parse( form.getPreWiringAppntDtl().getAppntDate(), format ): null;

		if(preWiringDate != null && !installDate.isAfter(preWiringDate)){
			errors.rejectValue("preWiringAppntDtl.appntDate", "lts.invalid.date");
		}
		
		DateTime portLaterDate = form.getPortLaterAppntDtl() != null ? DateTime.parse( form.getPortLaterAppntDtl().getAppntDate(), format ): null;
		DateTime portLaterCutOverDate = form.getPortLaterAppntDtl() != null ? DateTime.parse( form.getPortLaterAppntDtl().getCutOverDate(), format ): null;
		
		int pipbMinDay = LtsSbOrderHelper.getPipbMinDay();
		if(portLaterDate != null && installDate.plusDays(pipbMinDay).isAfter(portLaterDate)){
			errors.rejectValue("portLaterAppntDtl.appntDate", "lts.acq.portLaterDate.invalid", new Object[]{pipbMinDay}, null);
		}else if(portLaterCutOverDate != null && installDate.plusDays(pipbMinDay).isAfter(portLaterCutOverDate)){
			errors.rejectValue("portLaterAppntDtl.cutOverDate", "lts.acq.portLaterDate.invalid", new Object[]{pipbMinDay}, null);
		}
		
		if(form.isChkPcdActivationDate()){
			DateTimeFormatter pcdDateformat = DateTimeFormat.forPattern("yyyyMMdd");
			DateTime pcdDate = DateTime.parse(form.getPcdActivationDate(), pcdDateformat);
			if(portLaterDate != null && pcdDate.isAfter(portLaterDate)){
				errors.rejectValue("portLaterAppntDtl.appntDate", "lts.acq.appointment.portDateMustSameOrLaterThanPCDActivationDate");
			}
		}
	}
	
	private void validateTimeSlot(LtsAppointmentDetail appointmentDtl, Errors errors, String rejectedDtl, boolean isTentative){
		if(appointmentDtl != null){
			if(StringUtils.isEmpty(appointmentDtl.getAppntDate())){
				errors.rejectValue(rejectedDtl+".appntDate", "lts.installdate.required");	
			}
			if(StringUtils.isEmpty(appointmentDtl.getAppntTimeSlot())){
				errors.rejectValue(rejectedDtl+".appntTimeSlotAndType", "lts.installtime.required");	
			}else{
				if(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE.equals(appointmentDtl.getAppntTimeSlotType())
						|| LtsConstant.APPOINTMENT_TIMESLOT_TYPE_RESTRICT.equals(appointmentDtl.getAppntTimeSlotType())){
					errors.rejectValue(rejectedDtl+".appntTimeSlotAndType", "lts.invalid.slot");
				}
			}
			
			if(appointmentDtl.isCutOverInd()){
				if(StringUtils.isBlank(appointmentDtl.getCutOverDate())){
					if(StringUtils.isBlank(appointmentDtl.getAppntDate())
							|| StringUtils.isBlank(appointmentDtl.getAppntTimeSlot())){
						errors.rejectValue(rejectedDtl+".cutOverDate", "lts.cutoverdatetime.required");
					}else{
						errors.rejectValue(rejectedDtl+".cutOverDate", "lts.cutoverdatetime.requiredagain");
					}
				}
				if(StringUtils.isBlank(appointmentDtl.getCutOverTime())){
					if(StringUtils.isBlank(appointmentDtl.getAppntDate())
							|| StringUtils.isBlank(appointmentDtl.getAppntTimeSlot())){
						errors.rejectValue(rejectedDtl+".cutOverTime", "lts.cutoverdatetime.required");
					}else{
						errors.rejectValue(rejectedDtl+".cutOverTime", "lts.cutoverdatetime.requiredagain");
					}
				}
				if(StringUtils.isNotBlank(appointmentDtl.getCutOverDate())
						&& StringUtils.isNotBlank(appointmentDtl.getCutOverTime())
						&& appointmentDtl.getEarliestSRD() != null 
						&& !isTentative){
					DateTime cutOverDate = DateTime.parse(appointmentDtl.getCutOverDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
					if(cutOverDate.isBefore(appointmentDtl.getEarliestSRD().getSRDate())){
						errors.rejectValue(rejectedDtl+".cutOverTime", "lts.acq.appnt.cutOver.invalid");
					}
				}
			}
		}
	}
	
}
