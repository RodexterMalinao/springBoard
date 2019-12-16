package com.bomwebportal.lts.validator;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsOrderAmendmentFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.util.Utility;

public class OrderAmendmentValidator implements Validator {

	private LtsCommonService ltsCommonService;
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	public boolean supports(Class clazz) {
		return clazz.equals(LtsOrderAmendmentFormDTO.class);
	}

	
	private boolean isContainCreditCardPattern(String remark) {
		
		if (StringUtils.isEmpty(remark)) {
			return false;
		}
		
		final String expression = "^.*(?:\\d[ -]*?){13,16}.*$";
		String content = StringUtils.replace(remark, "\n", "");
        content = StringUtils.replace(content, "\r", "");
        content = StringUtils.replace(content, "\\", "");
        content = StringUtils.replace(content, "/", "");
        content = StringUtils.replace(content, ",", "");
        content = StringUtils.replace(content, ":", "");
        content = StringUtils.replace(content, ";", "");
        content = StringUtils.replace(content, ".", "");
        return content.matches(expression);
	}
	
	
	public void validate(Object obj, Errors errors) {
		LtsOrderAmendmentFormDTO orderAmendDTO = (LtsOrderAmendmentFormDTO)obj;
		
		LocalDate installDate = LocalDate.parse(orderAmendDTO.getCoreSrvAppntDtl().getAppntDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
		
		//APPOINTMENT
		if(StringUtils.equals(orderAmendDTO.getSubmitInd(), "APPOINTMENT_CONFIRM")){
			SimpleDateFormat df = new SimpleDateFormat();
			df.applyPattern("dd/MM/yyyy");
			int preWiringTime = 0, installTime = 0;
			
			if(orderAmendDTO.getBbShortageInd().equals("false")){
				if(!orderAmendDTO.isAcqOrderCompleted()){
					if (orderAmendDTO.getPreWiringAppntDtl() != null) {
						if(StringUtils.isNotBlank(orderAmendDTO.getCoreSrvAppntDtl().getAppntDate())){
							try{
								LocalDate preWiringDate = LocalDate.parse(orderAmendDTO.getPreWiringAppntDtl().getAppntDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
								if(installDate == null || preWiringDate == null || preWiringDate.isAfter(installDate)){
									errors.rejectValue("preWiringAppntDtl.appntDate", "lts.invalid.date");
								}
							}catch(IllegalArgumentException iae){
								errors.rejectValue("preWiringAppntDtl.appntDate", "lts.invalid.date");
							}
						}
						if (StringUtils.isBlank(orderAmendDTO.getPreWiringAppntDtl().getAppntTimeSlot())) {
							errors.rejectValue("preWiringAppntDtl.appntTimeSlotAndType", "lts.prewiringtime.required");
						}else{
							preWiringTime = Integer.parseInt(orderAmendDTO.getPreWiringAppntDtl().getAppntTimeSlot().split("-")[0].split(":")[0]);
						}
					}
					//check investigation case exist --BY MAX.R.MENG LTS					
					if(orderAmendDTO.isInvestigationInd()){
						errors.rejectValue("amendAppointmentInd", "lts.updateSRD.Investigation.not.allow");
					}
					
					if (StringUtils.isBlank(orderAmendDTO.getCoreSrvAppntDtl().getAppntDate())) {
						errors.rejectValue("coreSrvAppntDtl.appntDate", "lts.installdate.required");
					}else if(!LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(orderAmendDTO.getOrderType())){
						try{
							if (StringUtils.equals("CORE",orderAmendDTO.getServiceType())){
								LocalDate earliestSRD = LocalDate.parse(orderAmendDTO.getEarliestSRD().getDateString(), DateTimeFormat.forPattern("dd/MM/yyyy"));
								if(installDate.isBefore(earliestSRD)){
									errors.rejectValue("coreSrvAppntDtl.appntDate", "lts.invalid.date");
								}
								
								if(!(orderAmendDTO.getPipbInfo() != null && orderAmendDTO.isPipbAmendAfterCutOver())){
									if(orderAmendDTO.getCoreSrvAppntDtl().isCutOverInd()){
										LocalDate cutOverDate = LocalDate.parse(orderAmendDTO.getCoreSrvAppntDtl().getCutOverDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
										if(cutOverDate.isBefore(earliestSRD)){
											errors.rejectValue("coreSrvAppntDtl.cutOverDate", "lts.acq.appnt.cutOver.invalid");
										}
									}
								}
								
							}
						}catch(IllegalArgumentException iae){
							errors.rejectValue("coreSrvAppntDtl.appntDate", "lts.invalid.date");
						}

					}
					if (StringUtils.equals("CORE",orderAmendDTO.getServiceType())){
						if (StringUtils.isBlank(orderAmendDTO.getCoreSrvAppntDtl().getAppntTimeSlot())) {
							errors.rejectValue("coreSrvAppntDtl.appntTimeSlotAndType", "lts.installtime.required");
						}else if(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE.equals(orderAmendDTO.getCoreSrvAppntDtl().getAppntTimeSlotType())
								|| LtsConstant.APPOINTMENT_TIMESLOT_TYPE_RESTRICT.equals(orderAmendDTO.getCoreSrvAppntDtl().getAppntTimeSlotType())){
							errors.rejectValue("coreSrvAppntDtl.appntTimeSlotAndType", "lts.invalid.slot");
						}else{
							installTime = Integer.parseInt(orderAmendDTO.getCoreSrvAppntDtl().getAppntTimeSlot().split("-")[0].split(":")[0]);
							if(orderAmendDTO.getPreWiringAppntDtl() != null 
									&& StringUtils.equals(orderAmendDTO.getCoreSrvAppntDtl().getAppntDate(), orderAmendDTO.getPreWiringAppntDtl().getAppntDate())
									&& installTime < preWiringTime){
								errors.rejectValue("preWiringAppntDtl.appntTimeSlotAndType", "lts.late.prewiringtime");
							}
						}
					}
				}
				
				if(orderAmendDTO.getSecDelAppntDtl() != null){
					LocalDate secDelInstallDate = LocalDate.parse(orderAmendDTO.getSecDelAppntDtl().getAppntDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
					if(StringUtils.isBlank(orderAmendDTO.getSecDelAppntDtl().getAppntDate())){
						errors.rejectValue("secDelAppntDtl.appntDate", "lts.invalid.date");
					}else if(installDate.isAfter(secDelInstallDate)){
							errors.rejectValue("secDelAppntDtl.appntDate", "lts.invalid.date");
					}
					if(StringUtils.isBlank(orderAmendDTO.getSecDelAppntDtl().getAppntTimeSlot())){
						errors.rejectValue("secDelAppntDtl.appntTimeSlotAndType", "lts.installtime.required");
					}
				}
				
				if(orderAmendDTO.getPortLaterAppntDtl() != null){
					if(StringUtils.isBlank(orderAmendDTO.getPortLaterAppntDtl().getAppntDate())){
						errors.rejectValue("portLaterAppntDtl.appntDate", "lts.invalid.date");
					}else{
						LocalDate portingDate = LocalDate.parse(orderAmendDTO.getPortLaterAppntDtl().getAppntDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
						if(installDate.isAfter(portingDate)){
							errors.rejectValue("portLaterAppntDtl.appntDate", "lts.invalid.date");
						}else if(Days.daysBetween(installDate, portingDate).getDays() < orderAmendDTO.getPipbPortLaterleadtime()){
							errors.rejectValue("portLaterAppntDtl.appntDate", "lts.invalid.date");
						}else if(StringUtils.isNotBlank(orderAmendDTO.getPortLaterAppntDtl().getCutOverDate())){
							LocalDate portingCutOverDate = LocalDate.parse(orderAmendDTO.getPortLaterAppntDtl().getCutOverDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
							if(Days.daysBetween(installDate, portingCutOverDate).getDays() < orderAmendDTO.getPipbPortLaterleadtime()){
								errors.rejectValue("portLaterAppntDtl.cutOverDate", "lts.invalid.date");
							}
						}
						if(StringUtils.isNotBlank(orderAmendDTO.getPcdActivationDate())){
							DateTimeFormatter pcdDateformat = DateTimeFormat.forPattern("dd/MM/yyyy");
							LocalDate pcdDate = LocalDate.parse(orderAmendDTO.getPcdActivationDate(), pcdDateformat);
							if(pcdDate.isAfter(portingDate)){
								errors.rejectValue("portLaterAppntDtl.appntDate", "lts.acq.appointment.portDateMustSameOrLaterThanPCDActivationDate");
							}
						}
					}
					if(StringUtils.isBlank(orderAmendDTO.getPortLaterAppntDtl().getAppntTimeSlot())){
						errors.rejectValue("portLaterAppntDtl.appntTimeSlotAndType", "lts.installtime.required");
					}
				}
				
				if(StringUtils.isBlank(orderAmendDTO.getDelayReasonCode())){
					errors.rejectValue("delayReasonCode", "lts.reason.required");
				}
			}
			if(errors.getErrorCount() == 0){
				orderAmendDTO.setConfirmedInd(LtsConstant.TRUE_IND);
				orderAmendDTO.setAmendedInd(LtsConstant.TRUE_IND);
			}else{
//				orderAmendDTO.setConfirmedInd(LtsConstant.FALSE_IND);
//				orderAmendDTO.setAmendedInd(LtsConstant.FALSE_IND);
			}
		}else if(orderAmendDTO.getSubmitInd().compareTo("SUBMIT") == 0){
			if(StringUtils.isEmpty(orderAmendDTO.getName())){
				errors.rejectValue("name", "lts.applicantName.required");
			}
			if(StringUtils.isEmpty(orderAmendDTO.getRelationship())){
				errors.rejectValue("relationship", "lts.invalid.relationship");
			}
			if(StringUtils.isEmpty(orderAmendDTO.getContactNum())){
				errors.rejectValue("contactNum", "lts.contactPhone.required");
			}else if(!Utility.validatePhoneNum(orderAmendDTO.getContactNum())){
				errors.rejectValue("contactNum", "lts.invalid.contactPhone");
			}
			if(StringUtils.isNotEmpty(orderAmendDTO.getNoticeSmsNum())){
				if (!Utility.validatePhoneNum(orderAmendDTO.getNoticeSmsNum())
						|| !ltsCommonService.isMobileNumPrefix(orderAmendDTO.getNoticeSmsNum()))  {
					errors.rejectValue("noticeSmsNum", "lts.distributeSms.invalid");
				}
			}
			if(StringUtils.isNotEmpty(orderAmendDTO.getNoticeEmailAddr())){
				if (!LtsCommonValidator.isValidEmail(orderAmendDTO.getNoticeEmailAddr())) {
					errors.rejectValue("noticeEmailAddr", "lts.distributeEmail.invalid");
				}	
			}
			if(orderAmendDTO.isCancelOrderInd()){
				if(StringUtils.isEmpty(orderAmendDTO.getCancelType())){
					errors.rejectValue("cancelType", "lts.reason.required");
				}
				if(StringUtils.isEmpty(orderAmendDTO.getCancelReason())){
					errors.rejectValue("cancelReason", "lts.reason.required");
				}
				if(StringUtils.isEmpty(orderAmendDTO.getCancelRemark())){
					errors.rejectValue("cancelRemark", "lts.cancelremark.required");
				}
				else if (isContainCreditCardPattern(orderAmendDTO.getCancelRemark())) {
		        	errors.rejectValue("cancelRemark", "lts.cancelremark.invalid.ccpattern");
		        }
				if(LtsCommonValidator.containsSpecialChar(orderAmendDTO.getCancelRemark())){
					errors.rejectValue("cancelRemark", "lts.remarks.specialchar");
				}
				
			}else{
				boolean selected = false;
				if((orderAmendDTO.isAmendAppointmentInd()) ){
					selected = true;
					if(!StringUtils.equals(orderAmendDTO.getConfirmedInd(),LtsConstant.TRUE_IND)){
						errors.rejectValue("confirmedInd", "lts.confirmation.required");
						
					}
				}
				
				if(!orderAmendDTO.isAcqOrderCompleted()){
					// Check if sysdate = SRD-1 & after 13:00, force user to change SRD
					DateTime sysdate = DateTime.now();
					boolean errorFlag = false;
					int diff = Days.daysBetween(sysdate.toLocalDate() ,installDate).getDays();
					if (diff <= 0){
						errorFlag = true;
					}else if (diff < 2){
						if (sysdate.getHourOfDay() >= 13 || (sysdate.getDayOfMonth() == installDate.getDayOfMonth())){
							errorFlag = true;
						}
					}
					if(errorFlag){
						errors.rejectValue("coreSrvAppntDtl.appntDate", "lts.srd.change.required");
						orderAmendDTO.setAmendAppointmentInd(true);
					}
					
				}
				
				if(orderAmendDTO.isAmendCreditCardInd()){
					selected = true;
					boolean thirdParty = false;
					if(orderAmendDTO.getThirdPartyCard() == null){
						errors.rejectValue("thirdPartyCard", "lts.thirdPartyApplication.required");
					}else if(orderAmendDTO.getThirdPartyCard()){
						thirdParty = true;
					}
					if (StringUtils.isBlank(orderAmendDTO.getCardHolderName())) {
						errors.rejectValue("cardHolderName", "lts.holdername.required");
					}
					if(!thirdParty){
						if (StringUtils.isBlank(orderAmendDTO.getCardHolderDocType())) {
							errors.rejectValue("cardHolderDocType", "lts.invalid.doctype");
						}
						if (StringUtils.isBlank(orderAmendDTO.getCardHolderDocNum())) {
							errors.rejectValue("cardHolderDocNum", "lts.iddocnum.required");
						}
					}
					if (StringUtils.isNotBlank(orderAmendDTO.getCardHolderDocNum())) {
						if (StringUtils.isNotBlank(orderAmendDTO.getCardHolderDocType())) {
							if(orderAmendDTO.getCardHolderDocType().compareTo("HKID") == 0 
									&& !Utility.validateHKID(orderAmendDTO.getCardHolderDocNum())){
								errors.rejectValue("cardHolderDocNum", "lts.verification.required");
							}else if(orderAmendDTO.getCardHolderDocType().compareTo("HKBR") == 0){
								if(!StringUtils.isNumeric(orderAmendDTO.getCardHolderDocNum()) 
										|| !Utility.validateHKBRcheckDigit(orderAmendDTO.getCardHolderDocNum())){
									errors.rejectValue("cardHolderDocNum", "lts.verification.required");
								}
							}
						}
					}
					
					if (StringUtils.isBlank(orderAmendDTO.getCardNum())) {
						errors.rejectValue("cardNum", "lts.cardnum.required");
					}
					if (StringUtils.isBlank(orderAmendDTO.getCardType())) {
						errors.rejectValue("cardType", "lts.invalid.cardtype");
					}
					
					/*Check if today + 3months > exp date*/
					LocalDate lToday = LocalDate.now();
					LocalDate expireDate = new LocalDate(orderAmendDTO.getExpireYear(), orderAmendDTO.getExpireMonth(), 1);
					if(!lToday.plusMonths(3).isBefore(expireDate)){
						errors.rejectValue("expireYear", "lts.verification.required");
					}
				} 

				if(orderAmendDTO.isAmendAcqPipbInd()){
					selected = true;
					if(!orderAmendDTO.isPipbAmendCustNameInd()
							&& !orderAmendDTO.isPipbAmendDiffCustInd()
							&& !orderAmendDTO.isPipbAmendFlatFloorInd()
							&& !orderAmendDTO.isPipbAmendDiffAddrInd()){
						errors.rejectValue("amendAcqPipbInd", "lts.amend.pipb.required");
					}
					if(orderAmendDTO.isPipbAmendCustNameInd()){
						if(StringUtils.isEmpty(orderAmendDTO.getPipbFirstName())){
							errors.rejectValue("pipbFirstName", "lts.acq.givenName.required");
						}else if(!StringUtils.isAlphaSpace(orderAmendDTO.getPipbFirstName())){
							errors.rejectValue("pipbFirstName", "lts.acq.name.invalidChar");
						}
						if(StringUtils.isEmpty(orderAmendDTO.getPipbLastName())){
							errors.rejectValue("pipbLastName", "lts.acq.familyName.required");
						}else if(!StringUtils.isAlphaSpace(orderAmendDTO.getPipbLastName())){
							errors.rejectValue("pipbLastName", "lts.acq.name.invalidChar");
						}
					}
					if(orderAmendDTO.isPipbAmendDiffCustInd()){
						if(StringUtils.isEmpty(orderAmendDTO.getPipbDiffCustFirstName())){
							errors.rejectValue("pipbDiffCustFirstName", "lts.acq.givenName.required");
						}
						if(StringUtils.isEmpty(orderAmendDTO.getPipbDiffCustLastName())){
							errors.rejectValue("pipbDiffCustLastName", "lts.acq.familyName.required");
						}
					}
					if(orderAmendDTO.isPipbAmendFlatFloorInd()){
						if(StringUtils.isEmpty(orderAmendDTO.getPipbFloor())){
							errors.rejectValue("pipbFloor", "lts.floor.required");
						}
						
						if(orderAmendDTO.getPipbTwoNBWSrd() != null
								&& orderAmendDTO.getCoreSrvAppntDtl() != null
								&& StringUtils.isNotBlank(orderAmendDTO.getCoreSrvAppntDtl().getAppntDate())){
							DateTime coreInstDate = DateTime.parse(orderAmendDTO.getCoreSrvAppntDtl().getAppntDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
							if(coreInstDate.isBefore(orderAmendDTO.getPipbTwoNBWSrd().getSRDate())){
								errors.rejectValue("coreSrvAppntDtl.appntDate", "lts.srd.change.required");
								orderAmendDTO.setAmendAppointmentInd(true);
							}
						}
					}
					if(orderAmendDTO.isPipbAmendDiffAddrInd()){
						if(StringUtils.isEmpty(orderAmendDTO.getPipbDiffAddr())
								|| !LtsCommonValidator.isValidBaFormat(orderAmendDTO.getPipbDiffAddr())){
							errors.rejectValue("pipbDiffAddr", "lts.invalid.freeFormatAddress");
						}
					}
				}

				if(orderAmendDTO.isAmendAcqPipbInd() || orderAmendDTO.isAmendDocInd()){
					if(!orderAmendDTO.isAcqOrderCompleted()){
						DateTime earliestSRD = orderAmendDTO.getEarliestSRD().getSRDate();
						DateTime installationDate = DateTime.parse(orderAmendDTO.getCoreSrvAppntDtl().getAppntDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
						if(installationDate.isBefore(earliestSRD)){
							errors.rejectValue("coreSrvAppntDtl.appntDate", "lts.srd.change.required");
							orderAmendDTO.setAmendAppointmentInd(true);
						}
						if(orderAmendDTO.getCoreSrvAppntDtl().isCutOverInd()){
							DateTime cutOverDate = DateTime.parse(orderAmendDTO.getCoreSrvAppntDtl().getCutOverDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
							if(cutOverDate.isBefore(earliestSRD)){
								errors.rejectValue("coreSrvAppntDtl.cutOverDate", "lts.acq.appnt.cutOver.invalid");
								orderAmendDTO.setAmendAppointmentInd(true);
							}
						}
					}
				}
				
				if(orderAmendDTO.isAmendDocInd()){
					selected = true;
				}
				
				if (orderAmendDTO.isCategory1Ind()){
					selected = true;
					if (StringUtils.isEmpty(orderAmendDTO.getCategory1())){
						errors.rejectValue("category1", "lts.category.required");
					}
					if(StringUtils.isEmpty(orderAmendDTO.getContent1())){
						errors.rejectValue("content1", "lts.content.required");
					}else {
						if (isContainCreditCardPattern(orderAmendDTO.getContent1())) {
				        	errors.rejectValue("content1", "lts.content.invalid");
				        }
						if(LtsCommonValidator.containsSpecialChar(orderAmendDTO.getContent1())){
							errors.rejectValue("content1", "lts.remarks.specialchar");
						}
					}
				}
				if (orderAmendDTO.isCategory2Ind()){
					selected = true;
					if (StringUtils.isEmpty(orderAmendDTO.getCategory2())){
						errors.rejectValue("category2", "lts.category.required");
					}
					if(StringUtils.isEmpty(orderAmendDTO.getContent2())){
						errors.rejectValue("content2", "lts.content.required");
					}else {
						if (isContainCreditCardPattern(orderAmendDTO.getContent2())) {
				        	errors.rejectValue("content2", "lts.content.invalid");
				        }
						if(LtsCommonValidator.containsSpecialChar(orderAmendDTO.getContent2())){
							errors.rejectValue("content2", "lts.remarks.specialchar");
						}
					}
				}
				if (orderAmendDTO.isCategory3Ind()){
					selected = true;
					if (StringUtils.isEmpty(orderAmendDTO.getCategory3())){
						errors.rejectValue("category3", "lts.category.required");
					}
					if(StringUtils.isEmpty(orderAmendDTO.getContent3())){
						errors.rejectValue("content3", "lts.content.required");
					}else {
				        if (isContainCreditCardPattern(orderAmendDTO.getContent3())) {
				        	errors.rejectValue("content3", "lts.content.invalid");
				        }
						if(LtsCommonValidator.containsSpecialChar(orderAmendDTO.getContent3())){
							errors.rejectValue("content3", "lts.remarks.specialchar");
						}
					}
				}
				if(selected == false){
					errors.rejectValue("categorySelected", "lts.amendtype.required");
				}
			}
			
			
		}
		
	}
}
