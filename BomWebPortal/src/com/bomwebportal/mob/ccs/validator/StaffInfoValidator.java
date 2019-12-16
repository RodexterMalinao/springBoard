package com.bomwebportal.mob.ccs.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;
import com.bomwebportal.mob.ccs.service.StaffInfoService;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;

public class StaffInfoValidator implements Validator{
	
	private StaffInfoService staffInfoService;
	
	public StaffInfoService getStaffInfoService() {
		return staffInfoService;
	}

	public void setStaffInfoService(StaffInfoService staffInfoService) {
		this.staffInfoService = staffInfoService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(StaffInfoUI.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void validate(Object obj, Errors errors) {
		
		StaffInfoUI dto = (StaffInfoUI)obj;
		
		logger.debug("Start Staff Info validator");
		
		if (!"CONTINUE".equalsIgnoreCase(dto.getActionType()) || BomWebPortalCcsConstant.RECALL_ORDER.equalsIgnoreCase(dto.getWorkStatus())) {
			logger.debug("action type not continue or is recall");
			return;
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesId", "salesId.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesName",  "dummy", "Please press 'Refresh'");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPhone", "salesContactPhone.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "position", "position.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "callDateStr", "callDateStr.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "callTimeStr", "callTimeStr.required");
		
		if (dto != null) {
			
			String salesID = dto.getSalesId();
			String refSalesId = dto.getRefSalesId();
			String salesName = dto.getSalesName();
			String refSalesName = dto.getRefSalesName();
			String position = dto.getPosition();
			String contactPhone = dto.getContactPhone();
			
			if ((StringUtils.isNotBlank(salesID) && salesID.trim().length() > 8) || (StringUtils.isNotBlank(refSalesId) && refSalesId.trim().length() > 8)) {
				errors.rejectValue("errormsg", "salesIdLength.invalid");
			}
			if ((StringUtils.isNotBlank(salesName) && salesName.trim().length() > 80) || (StringUtils.isNotBlank(refSalesName) && refSalesName.trim().length() > 80)) {
				errors.rejectValue("errormsg", "salesNameLength.invalid");
			}
			if (StringUtils.isNotBlank(position) && position.trim().length() > 20) {
				errors.rejectValue("errormsg", "positionLength.invalid");
			}
			if (StringUtils.isNotBlank(refSalesId) && !dto.isManualInputBool()) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "refSalesName",  "dummy", "Please press 'Refresh'");
			}
			
			if (StringUtils.isNotBlank(contactPhone)) {
				// 1. only numeric
				if (!StringUtils.isNumeric(contactPhone)) {
					errors.rejectValue("contactPhone", "salesContactPhoneNumeric.invalid");
				}
				// 2. 8 digits
				if (contactPhone.length() != 8) {
					errors.rejectValue("contactPhone", "salesContactPhoneLength.invalid");
				}
				// 3. Start with 2,3,5,6,8,9
				if (!(StringUtils.startsWith(contactPhone, "2")
						|| StringUtils.startsWith(contactPhone, "3")
						|| StringUtils.startsWith(contactPhone, "5")
						|| StringUtils.startsWith(contactPhone, "6")
						|| StringUtils.startsWith(contactPhone, "8")
						|| StringUtils.startsWith(contactPhone, "9"))) {
					errors.rejectValue("contactPhone", "salesContactPhonePrefix.invalid");
				}
			}
			
			if (StringUtils.isBlank(dto.getAppDate())) {
				dto.setAppDate(null);
			}
			
			String staffChannelCd = staffInfoService.getChannelCd(dto.getSalesId(), dto.getAppDate());
			String refStaffChannelCd = staffInfoService.getChannelCd(dto.getRefSalesId(), dto.getAppDate());
			
			if (!BomWebPortalCcsConstant.RECALL_ORDER.equalsIgnoreCase(dto.getWorkStatus())){
				if (!dto.isManualInputBool()  ){
					String staffNameInDB = staffInfoService.getStaffName(salesID, dto.getAppDate());
					
					if (StringUtils.isBlank(staffChannelCd) 
							|| (StringUtils.isNotBlank(dto.getRefSalesId()) 
									&& StringUtils.isBlank(refStaffChannelCd))) {
						errors.rejectValue("salesId", "dummy", "Invalid (Ref.) Sales ID");
						return;
					} /*else if (!(staffChannelCd.equalsIgnoreCase(dto.getLoginChannelCd()))
									|| (StringUtils.isNotBlank(dto.getRefSalesId()) 
											&& !(refStaffChannelCd.equalsIgnoreCase(dto.getLoginChannelCd())))) {
						errors.rejectValue("salesId", "dummy", "(Ref.) Sales ID is belong to different Channel, order couldn't be created");
						return;
					}*/
					
					if (StringUtils.isNotBlank(staffNameInDB) && StringUtils.isNotBlank(salesName)) {
						if (!salesName.equals(staffNameInDB)) {
							errors.rejectValue("salesId", "dummy", "Please press 'Refresh'");
							return;
						}
					}
					
					String refStaffNameInDB = staffInfoService.getStaffName(refSalesId, dto.getAppDate());
					if (StringUtils.isNotBlank(refStaffNameInDB) && StringUtils.isNotBlank(refSalesName)) {
						if (!refSalesName.equals(refStaffNameInDB)) {
							errors.rejectValue("refSalesId", "dummy", "Please press 'Refresh'");
							return;
						}
					}
				}else{
					if (StringUtils.isNotBlank(dto.getRefSalesId())){
						
						if (StringUtils.isBlank(dto.getRefSalesName())){
							errors.rejectValue("refSalesName", "dummy", "Please input Ref. Sales Name.");
						}
						if (StringUtils.isBlank(dto.getRefSalesCentre())){
							errors.rejectValue("refSalesCentre", "dummy", "Please input Ref. Sales Centre.");
						}
						if (StringUtils.isBlank(dto.getRefSalesTeam())){
							errors.rejectValue("refSalesTeam", "dummy", "Please input Ref. Sales Team.");
						}
					}else{
						
						if (StringUtils.isNotBlank(dto.getRefSalesName()) || 
								StringUtils.isNotBlank(dto.getRefSalesCentre())||
									StringUtils.isNotBlank(dto.getRefSalesTeam())
								){
							errors.rejectValue("refSalesId", "dummy", "Please input Ref. Sales Code.");
						}
						
					}
				}
			}
		}

	}

}
