package com.bomwebportal.lts.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.form.LtsAppointmentFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;

public class LtsAppointmentValidator implements Validator{
	
	private LtsCommonService ltsCommonService;
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public boolean supports(Class clazz) {
		return LtsAppointmentFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsAppointmentFormDTO ltsAppointmentFormDTO = (LtsAppointmentFormDTO)command;
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("dd/MM/yyyy");
		int preWiringTime = 0, installTime = 0;
//		ltsAppointmentFormDTO.setErrorMsg(null);
		
		if(StringUtils.equals("CONFIRM", ltsAppointmentFormDTO.getSubmitInd())){
			if(ltsAppointmentFormDTO.getTentativeInd().equals("false")){
				if(StringUtils.equals(ltsAppointmentFormDTO.getPcdOrderExistInd(), LtsConstant.FALSE_IND)
						&& StringUtils.isNotBlank(ltsAppointmentFormDTO.getPCDOrderId())){
					
					if(StringUtils.equals(ltsAppointmentFormDTO.getIaDiffInd(), LtsConstant.TRUE_IND)){
						if(StringUtils.equals(ltsAppointmentFormDTO.getAllowIaConfirmInd(), LtsConstant.TRUE_IND)){
							if(!ltsAppointmentFormDTO.isConfirmIa()){
								errors.rejectValue("confirmCust", "lts.confirmation.required");
							}
						}else{
							errors.rejectValue("confirmCust", "lts.unmatch.addr");
						}
					}
					if(StringUtils.equals(ltsAppointmentFormDTO.getCustDiffInd(), LtsConstant.TRUE_IND)){
						if(StringUtils.equals(ltsAppointmentFormDTO.getAllowCustConfirmInd(), LtsConstant.TRUE_IND)){
							if(!ltsAppointmentFormDTO.isConfirmCust()){
								errors.rejectValue("confirmCust", "lts.confirmation.required");
							}
						}else{
							errors.rejectValue("confirmCust", "lts.unmatch.cust");
						}
					}
					
				}
				if (StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationDate())) {
					errors.rejectValue("installationDate", "lts.installdate.required");
				}else{
					try {
						Date installDate = df.parse(ltsAppointmentFormDTO.getInstallationDate());
						LtsSRDDTO earliestSRD = ltsAppointmentFormDTO.getEarliestSRD();
						if(installDate.before(earliestSRD.getDate().getTime())){
							errors.rejectValue("installationDate", "lts.invalid.date");
						}
					} catch (ParseException e) {
						e.printStackTrace();
						errors.rejectValue("installationDate", "lts.invalid.date");
					}
				}
				if (StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationTime())) {
					errors.rejectValue("installationTime", "lts.installtime.required");
				}else{
					if(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE.equals(ltsAppointmentFormDTO.getInstallationType())
							|| LtsConstant.APPOINTMENT_TIMESLOT_TYPE_RESTRICT.equals(ltsAppointmentFormDTO.getInstallationType())){
						errors.rejectValue("installationTime", "lts.invalid.slot");
					}
					installTime = Integer.parseInt(ltsAppointmentFormDTO.getInstallationTime().split("-")[0].split(":")[0]);
					if(StringUtils.equals(ltsAppointmentFormDTO.getInstallationDate(), ltsAppointmentFormDTO.getPreWiringDate())
							&& installTime < preWiringTime){
						errors.rejectValue("preWiringTime", "lts.late.prewiringtime");
					}
				}
				
				if(ltsAppointmentFormDTO.getSecDelInd().compareTo(LtsConstant.TRUE_IND) == 0){
					Date installDate;
					Date secDelInstallDate;
					try {
						installDate = df.parse(ltsAppointmentFormDTO.getInstallationDate());
						if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationDate())){
							errors.rejectValue("secDelInstallationDate", "lts.invalid.date");
						}else {
							secDelInstallDate = df.parse(ltsAppointmentFormDTO.getSecDelInstallationDate());
							if(installDate.after(secDelInstallDate)){
								errors.rejectValue("secDelInstallationDate", "lts.invalid.date");
							}else if(ltsAppointmentFormDTO.getSecDelEarliestSRD() != null 
									&& ltsAppointmentFormDTO.getSecDelEarliestSRD().getDate().getTime().after(secDelInstallDate)){
								errors.rejectValue("secDelInstallationDate", "lts.invalid.date");
								/*if(ltsAppointmentFormDTO.getSecDelEarliestSrdReason() != null){
									ltsAppointmentFormDTO.setErrorMsg(ltsAppointmentFormDTO.getSecDelEarliestSrdReason());
								}*/
							}
						}
							
						
						if (ltsAppointmentFormDTO.isSecDelFieldVisitRequired()) {
							if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationTime())){
								errors.rejectValue("secDelInstallationTime", "lts.installtime.required");
							}else{
								if(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE.equals(ltsAppointmentFormDTO.getSecDelInstallationType())){
									errors.rejectValue("secDelInstallationTime", "lts.invalid.slot");
								}
							}	
						}
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}else{
				if(ltsAppointmentFormDTO.getSecDelInd().compareTo(LtsConstant.TRUE_IND) == 0){
					Date secDelInstallDate;
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationDate())){
						errors.rejectValue("secDelInstallationDate", "lts.invalid.date");
					}else {
						try {
							secDelInstallDate = df.parse(ltsAppointmentFormDTO.getSecDelInstallationDate());
							if(ltsAppointmentFormDTO.getSecDelEarliestSRD() != null 
								&& ltsAppointmentFormDTO.getSecDelEarliestSRD().getDate().getTime().after(secDelInstallDate)){
								errors.rejectValue("secDelInstallationDate", "lts.invalid.date");
							}
						} catch (ParseException e) {
							errors.rejectValue("secDelInstallationDate", "lts.invalid.date");
						}
					}
				}
			}
			if(StringUtils.equals(ltsAppointmentFormDTO.getCutOverInd(), LtsConstant.TRUE_IND)){
				if(StringUtils.isBlank(ltsAppointmentFormDTO.getCutOverDate())){
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationDate())
							|| StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationTime())){
						errors.rejectValue("cutOverDate", "lts.cutoverdatetime.required");
					}else{
						errors.rejectValue("cutOverDate", "lts.cutoverdatetime.requiredagain");
					}
				}
				if(StringUtils.isBlank(ltsAppointmentFormDTO.getCutOverTime())){
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationDate())
							|| StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationTime())){
						errors.rejectValue("cutOverTime", "lts.cutoverdatetime.required");
					}else{
						errors.rejectValue("cutOverTime", "lts.cutoverdatetime.requiredagain");
					}
				}
			}
			if(StringUtils.equals(ltsAppointmentFormDTO.getSecDelCutOverInd(), LtsConstant.TRUE_IND)){
				if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelCutOverDate())){
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationDate())
							|| StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationTime())){
						errors.rejectValue("secDelCutOverDate", "lts.cutoverdatetime.required");
					}else{
						errors.rejectValue("secDelCutOverDate", "lts.cutoverdatetime.requiredagain");
					}
				}
				if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelCutOverTime())){
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationDate())
							|| StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationTime())){
						errors.rejectValue("secDelCutOverTime", "lts.cutoverdatetime.required");
					}else{
						errors.rejectValue("secDelCutOverTime", "lts.cutoverdatetime.requiredagain");
					}
				}
			}
			if(errors.getErrorCount() == 0){
				ltsAppointmentFormDTO.setConfirmedInd(LtsConstant.TRUE_IND);
				ltsAppointmentFormDTO.setErrorMsg(null);
			}else {
				ltsAppointmentFormDTO.setConfirmedInd(LtsConstant.FALSE_IND);
				if(ltsAppointmentFormDTO.getErrorMsg() == null){
					ltsAppointmentFormDTO.setErrorMsg("Appointment Failed");
				}
			}
		}else if(StringUtils.equals("SUBMIT", ltsAppointmentFormDTO.getSubmitInd())){
			
			if(!StringUtils.equals(ltsAppointmentFormDTO.getConfirmedInd(), LtsConstant.TRUE_IND)){
				errors.rejectValue("confirmedInd", "lts.confirmation.required");
			}else{
				if(StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationDate())){
					errors.rejectValue("installationDate", "lts.installdate.required");
				}else{
					try {
						Date installDate = df.parse(ltsAppointmentFormDTO.getInstallationDate());
						LtsSRDDTO earliestSRD = ltsAppointmentFormDTO.getEarliestSRD();
						if(installDate.before(earliestSRD.getDate().getTime())){
							errors.rejectValue("installationDate", "lts.invalid.date");
						}
					} catch (ParseException e) {
						e.printStackTrace();
						errors.rejectValue("installationDate", "lts.invalid.date");
					}
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationTime())){
						errors.rejectValue("installationTime", "lts.installtime.required");
					}else{
						if(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE.equals(ltsAppointmentFormDTO.getInstallationType())){
							errors.rejectValue("installationTime", "lts.invalid.slot");
						}
					}
				}
				//cutover
				if(StringUtils.equals(ltsAppointmentFormDTO.getCutOverInd(), LtsConstant.TRUE_IND)){
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getCutOverDate())){
						if(StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationDate())
								|| StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationTime())){
							errors.rejectValue("cutOverDate", "lts.cutoverdatetime.required");
						}else{
							errors.rejectValue("cutOverDate", "lts.cutoverdatetime.requiredagain");
						}
					}
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getCutOverTime())){
						if(StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationDate())
								|| StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationTime())){
							errors.rejectValue("cutOverTime", "lts.cutoverdatetime.required");
						}else{
							errors.rejectValue("cutOverTime", "lts.cutoverdatetime.requiredagain");
						}
					}
				}
				
				//secdel
				if(StringUtils.equals(ltsAppointmentFormDTO.getSecDelInd(), LtsConstant.TRUE_IND)){
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationDate())){
						errors.rejectValue("secDelInstallationDate", "lts.installdate.required");
					}else{
						if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationTime())){
							errors.rejectValue("secDelInstallationTime", "lts.installtime.required");
						}else{
							if(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE.equals(ltsAppointmentFormDTO.getSecDelInstallationType())){
								errors.rejectValue("secDelInstallationTime", "lts.invalid.slot");
							}
						}
					}
				}
				
				//secdelcutover
				if(StringUtils.equals(ltsAppointmentFormDTO.getSecDelCutOverInd(), LtsConstant.TRUE_IND)){
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelCutOverDate())){
						if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationDate())
								|| StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationTime())){
							errors.rejectValue("secDelCutOverDate", "lts.cutoverdatetime.required");
						}else{
							errors.rejectValue("secDelCutOverDate", "lts.cutoverdatetime.requiredagain");
						}
					}
					if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelCutOverTime())){
						if(StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationDate())
								|| StringUtils.isBlank(ltsAppointmentFormDTO.getSecDelInstallationTime())){
							errors.rejectValue("secDelCutOverTime", "lts.cutoverdatetime.required");
						}else{
							errors.rejectValue("secDelCutOverTime", "lts.cutoverdatetime.requiredagain");
						}
					}
				}
				
				if(errors.getErrorCount() > 0){
					errors.rejectValue("confirmedInd", "lts.cancel.appointment");
				}
			}
			
			if (StringUtils.isBlank(ltsAppointmentFormDTO.getInstallationContactName())) {
				errors.rejectValue("installationContactName", "lts.contactname.required");
			}
			
			//BOM2018061
			if(ltsAppointmentFormDTO.getEpdItemList() != null && ltsAppointmentFormDTO.getEpdItemList().size() > 0){
				boolean selectedInd = false;
				for(ItemDetailDTO epdItem: ltsAppointmentFormDTO.getEpdItemList()){
					if(epdItem.isSelected()){
						selectedInd = true;
						if(LtsConstant.ITEM_TYPE_EPD_ACCEPT.equals(epdItem.getItemType())){
							if(StringUtils.isNotBlank(ltsAppointmentFormDTO.getInstallationDate())){
								LocalDateTime installDate = LocalDateTime.parse(ltsAppointmentFormDTO.getInstallationDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
								for(ItemAttbDTO attb: epdItem.getItemAttbs()){
									if(LtsConstant.ITEM_ATTB_FORMAT_DATE.equals(attb.getInputFormat())
											&& !"N".equals(attb.getVisualInd())){
										if(StringUtils.isEmpty(attb.getAttbValue())){
											errors.rejectValue("epdItemList", "lts.invalid.date");
										}else{
											LocalDateTime epdCollectDate = LocalDateTime.parse(attb.getAttbValue(), DateTimeFormat.forPattern("dd/MM/yyyy"));
											if(!epdCollectDate.isAfter(installDate.plusDays(ltsAppointmentFormDTO.getEpdLeadDay()))){
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
			
			
			
			String smsNum = StringUtils.remove(ltsAppointmentFormDTO.getInstallationMobileSMSAlert(), " ");
			String contact = StringUtils.remove(ltsAppointmentFormDTO.getInstallationContactNum(), " ");
			
			if (StringUtils.isEmpty(contact)) {
				errors.rejectValue("installationContactNum", "lts.contactPhone.required");
			}else  {
				ltsCommonService.validateContactValue(contact, errors, "installationContactNum");
			}
			if (StringUtils.isNotEmpty(smsNum)){
				ltsCommonService.validateSmsNum(smsNum, errors, "installationContactNum");
			}
			
			String remarks = ltsAppointmentFormDTO.getRemarks();
			ltsAppointmentFormDTO.setRemarks(LtsCommonValidator.replaceControlCharacter(remarks));
		    
			if(LtsCommonValidator.containsSpecialChar(remarks)){
				errors.rejectValue("remarks", "lts.remarks.specialchar");
			}
			if(LtsCommonValidator.isLongerthan100(remarks)){
				errors.rejectValue("remarks", "lts.remarks.toolong");
			}
			ltsAppointmentFormDTO.setInstallationContactNum(contact);
			ltsAppointmentFormDTO.setInstallationMobileSMSAlert(smsNum);
			
		}else if(StringUtils.equals("EDF", ltsAppointmentFormDTO.getSubmitInd())){
			
		}else if(!StringUtils.equals("CANCEL", ltsAppointmentFormDTO.getSubmitInd())
				&& !StringUtils.equals("CLEAR", ltsAppointmentFormDTO.getSubmitInd())){
			if(StringUtils.isBlank(ltsAppointmentFormDTO.getSuspendReason()) 
					|| ltsAppointmentFormDTO.getSuspendReason().compareTo("NONE") == 0){
				errors.rejectValue("suspendReason", "lts.reason.required");
			}
		}
		
	}

}
