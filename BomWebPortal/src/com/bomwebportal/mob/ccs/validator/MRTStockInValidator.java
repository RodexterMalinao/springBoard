package com.bomwebportal.mob.ccs.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTStockUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtStatusService.MsisdnStatus;
import com.bomwebportal.mob.ccs.service.MrtInventoryService;
import com.bomwebportal.util.Utility;

public class MRTStockInValidator implements Validator {

    private MrtInventoryService mrtInventoryService;
    private CodeLkupService codeLkupService;
    private MobCcsMrtService mobCcsMrtService;
 
	/**
	 * @return the mobCcsMrtService
	 */
	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	/**
	 * @param mobCcsMrtService the mobCcsMrtService to set
	 */
	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}

	public MrtInventoryService getMrtInventoryService() {
		return mrtInventoryService;
	}

	public void setMrtInventoryService(MrtInventoryService mrtInventoryService) {
		this.mrtInventoryService = mrtInventoryService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public boolean supports(Class clazz) {
		return MRTStockUI.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {
		MRTStockUI form = (MRTStockUI) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numType", "numType.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msisdn", "mrtAdminMsisdn.required");
		
		if (StringUtils.isNotBlank(form.getMsisdn())) {
			if (form.getMsisdn().startsWith("0")) {
				errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.leadingzero");
			}
			if (!StringUtils.isNumeric(form.getMsisdn())) {
				errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.numeric");
			}
			if ("PCCW3G".equals(form.getServiceType())) {
				if (form.getMsisdn().length() != 8) {
					errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.pccw3g");
				} else if (!Utility.validateHKMobilePrefix(form.getMsisdn())) {
					errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.pccw3gPrefix");
				}
			} else if ("UNICOM1C2N".equals(form.getServiceType()) || "CCU1C2N".equals(form.getServiceType())) {
				if (form.getMsisdn().length() != 11) {
					errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.unicom1c2n");
				} else if (!Utility.validateChinaMobilePrefix(form.getMsisdn())) {
					errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.unicom1c2nPrefix");
				}
			} else if (form.getMsisdn().length() != 8 && form.getMsisdn().length() != 11) {
				errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msisdnlvl", "mrtAdminMsisdnlvl.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceType", "serviceType.required");
		if ("UNICOM1C2N".equals(form.getServiceType()) || "CCU1C2N".equals(form.getServiceType())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cityCode", "cityCode.required");
		}
		
		if ("UNICOM1C2N".equals(form.getServiceType()) && !"H".equalsIgnoreCase(form.getNumType())) {
			errors.rejectValue("serviceType", "", "Invalid Service Type for selected Number Type");
		}
		
		if ("CCU1C2N".equals(form.getServiceType()) && !"C".equalsIgnoreCase(form.getNumType())) {
			errors.rejectValue("serviceType", "", "Invalid Service Type for selected Number Type");
		}
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msisdnStatus", "msisdnStatus.required");
		if (form.getMsisdnStatus() == null) {
			errors.rejectValue("msisdnStatus", "msisdnStatus.required");
		}
		// channelCode
		if (StringUtils.isBlank(form.getChannelCode())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "channelCode", "channelCode.required");
		} else {
			if ("PCCW3G".equals(form.getServiceType())) {
				
				List<String> pccw3gMsisdnlvlList = mobCcsMrtService.getGoldenNumLvL(true);
				
				if (pccw3gMsisdnlvlList != null && !pccw3gMsisdnlvlList.isEmpty()) {
					if (pccw3gMsisdnlvlList.contains(form.getMsisdnlvl())) {
						List<CodeLkupDTO> ccsCodes = codeLkupService.getCodeLkupDTOALL("CCS_CODE");
						boolean ccsCodeFound = false;
					    if (ccsCodes != null) {
					    	for (CodeLkupDTO ccsCode : ccsCodes) {
					    		if (form.getChannelCode().equals(ccsCode.getCodeId())) {
						    		ccsCodeFound = true;
						    		break;
					    		}
					    	}
					    }
					    if (!ccsCodeFound) {
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "channelCode", "channelCode.required");
					    }
					}
				}
				 
			} 
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "channelCode", "channelCode.required");
		}
		
		if (!errors.hasErrors()) {
			List<MrtInventoryDTO> records = this.mrtInventoryService.getMrtInventoryDTOALL(form.getMsisdn());
			if (records != null) {
				for (MrtInventoryDTO r : records) {
					MsisdnStatus msisdnStatus = MsisdnStatus.valueOf(r.getMsisdnStatus());
					switch (msisdnStatus) {
					case FREE:
					case FROZEN:
					case RESERVE:
					case ASSIGN:
						errors.rejectValue("msisdn", "dummy", "Msisdn in status: " + msisdnStatus.getDesc());
						break;
					}
				}
			}
		}
	}
}
