package com.bomwebportal.mob.ds.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.StockInDTO;
import com.bomwebportal.mob.ccs.service.StockInService;
import com.bomwebportal.mob.ds.dto.MobDsMrtManagementDTO;
import com.bomwebportal.mob.ds.service.MobDsMrtManagementService;

public class MobDsMrtManagementValidator implements Validator {

	private MobDsMrtManagementService mobDsMrtManagementService;
	private StockInService stockInService;
	
	public MobDsMrtManagementService getMobDsMrtManagementService() {
		return mobDsMrtManagementService;
	}
	public void setMobDsMrtManagementService(
			MobDsMrtManagementService mobDsMrtManagementService) {
		this.mobDsMrtManagementService = mobDsMrtManagementService;
	}
	public void setStockInService(StockInService stockInService) {
		this.stockInService = stockInService;
	}
	public StockInService getStockInService() {
		return stockInService;
	}
	
	public boolean supports(Class arg0) {
		return MobDsMrtManagementDTO.class.equals(arg0);
	}

	public void validate(Object arg0, Errors errors) {
		MobDsMrtManagementDTO form = (MobDsMrtManagementDTO) arg0;

		if ("SEARCH".equalsIgnoreCase(form.getActionType())) {
			
			if ((form.getSearchMrt() == null || "".equalsIgnoreCase(form.getSearchMrt())) &&
					(form.getSearchStaffId() == null || "".equalsIgnoreCase(form.getSearchStaffId()))) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "searchStoreCode", "stockStoreCode.required");
			}
			
		} else if ("ASSIGN".equalsIgnoreCase(form.getActionType())) {
			if (CollectionUtils.isEmpty(form.getSelectMsisdn())) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "selectMsisdn", "msisdn.required");
			} else {
				if (!mobDsMrtManagementService.allowUpdateMrtStatus(form.getSelectMsisdn())) {
					errors.rejectValue("selectMsisdn", "msisdn.notFree");
				}
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "storeCode", "stockStoreCode.required");
			
			if (form.getTeamCode() == null || "".equals(form.getTeamCode())) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teamCode", "stockTeamCode.required");
			} else if (!(form.getSelectMsisdn() == null || form.getSelectMsisdn().isEmpty()) 
					&& !mobDsMrtManagementService.isValidMrtStore(form.getSelectMsisdn(), form.getTeamCode())) {
				errors.rejectValue("teamCode", "channel.invalid");
			}
			
			if (form.getStaffId() != null && !"".equalsIgnoreCase(form.getStaffId())) {
				List<StockInDTO> checkStaffDto
					= stockInService.checkValidStaff(form.getStaffId(), form.getStoreCode(), form.getTeamCode());
			
				if (checkStaffDto == null || checkStaffDto.size() == 0) {
					errors.rejectValue("staffId", "validStaffId.invalid");
				}
			}
		}	
	
	}
}
