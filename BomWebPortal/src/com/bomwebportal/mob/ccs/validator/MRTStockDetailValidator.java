package com.bomwebportal.mob.ccs.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.MrtStatusDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTStockDetailUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtStatusService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtStatusService.MsisdnStatus;
import com.bomwebportal.mob.ccs.service.MrtInventoryService;

public class MRTStockDetailValidator implements Validator {
    protected final Log logger = LogFactory.getLog(getClass());

    private MrtInventoryService mrtInventoryService;
    private MobCcsMrtStatusService mobCcsMrtStatusService;
    private CodeLkupService codeLkupService;

	public MrtInventoryService getMrtInventoryService() {
		return mrtInventoryService;
	}

	public void setMrtInventoryService(MrtInventoryService mrtInventoryService) {
		this.mrtInventoryService = mrtInventoryService;
	}

	public MobCcsMrtStatusService getMobCcsMrtStatusService() {
		return mobCcsMrtStatusService;
	}

	public void setMobCcsMrtStatusService(
			MobCcsMrtStatusService mobCcsMrtStatusService) {
		this.mobCcsMrtStatusService = mobCcsMrtStatusService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public boolean supports(Class clazz) {
		return MRTStockDetailUI.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {
		MRTStockDetailUI form = (MRTStockDetailUI) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rowId", "rowId.required");
		MrtInventoryDTO record = this.mrtInventoryService.getMrtInventoryDTO(form.getRowId());
		if (record == null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rowId", "rowId.required");
			return;
		}
		
		if (form.isOverride()) {
			if (logger.isInfoEnabled()) {
				logger.info("override msisdn: " + record.getMsisdn());
			}
			return;
		}
		
		// check if sales reserved #
		List<MrtStatusDTO> msisdnRecords = this.mobCcsMrtStatusService.getMrtStatusDTOByMsisdnAndStatus(record.getMsisdn(), MsisdnStatus.RESERVE);
		if (!this.isEmpty(msisdnRecords)) {
			//errors.rejectValue("msisdnStatus", null, "MRT is reserved by " + reservedStaffs);
			if (logger.isInfoEnabled()) {
				logger.info("msisdn in reserve, msisdn: " + record.getMsisdn());
			}
			errors.rejectValue("override", null, "Requires override existing MRT reserved status");
			return;
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msisdnlvl", "mrtAdminMsisdnlvl.required");
		if (form.getMsisdnStatus() == null) {
			errors.rejectValue("msisdnStatus", null, "msisdnStatus.required");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "channelCode", "channelCode.required");
		
		if (errors.hasErrors()) {
			return;
		}

		MsisdnStatus fromStatus = MsisdnStatus.valueOf(record.getMsisdnStatus());
		MsisdnStatus toStatus = MsisdnStatus.valueOf(form.getMsisdnStatus());
		if (logger.isInfoEnabled()) {
			logger.info(fromStatus + " to " + toStatus);
		}
		if (!this.validMsisdnStatusChange(fromStatus, toStatus)) {
			errors.rejectValue("msisdnStatus", null, "Cannot change status from " + fromStatus + " to " + toStatus);
		}
	}
		
	private boolean validMsisdnStatusChange(MsisdnStatus fromStatus, MsisdnStatus toStatus) {
		if (fromStatus.equals(toStatus)) {
			return true;
		}
		boolean valid = false;
		switch (fromStatus) {
		case FREE:
			switch (toStatus) {
			case RESERVE:
			case FROZEN:
			case SUSPEND:
				valid = true;
				break;
			}
			break;
		case FROZEN:
			switch (toStatus) {
			case FREE:
			case RESERVE:
			case SUSPEND:
				valid = true;
				break;
			}
			break;
		case RESERVE:
			switch (toStatus) {
			case FREE:
			case FROZEN:
			case SUSPEND:
				valid = true;
				break;
			}
			break;
		case SUSPEND:
			break;
		case ASSIGN:
			break;
		case SOLD:
			break;
		}
		return valid;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
