package com.bomwebportal.mob.ds.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.StockInDTO;
import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;
import com.bomwebportal.mob.ccs.service.StockInService;
import com.bomwebportal.mob.ccs.service.StockUpdateService;

public class MobDsStockUpdateValidator implements Validator{
	
	private StockInService stockInService;
	private StockUpdateService stockUpdateService;
	
	public void setStockInService(StockInService stockInService) {
		this.stockInService = stockInService;
	}
	public StockInService getStockInService() {
		return stockInService;
	}
	public void setStockUpdateService(StockUpdateService stockUpdateService) {
		this.stockUpdateService = stockUpdateService;
	}
	public StockUpdateService getStockUpdateService() {
		return stockUpdateService;
	}
	
	public boolean supports(Class clazz) {
		return clazz.equals(StockUpdateDTO.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void validate(Object obj, Errors errors) {
		
		StockUpdateDTO dto = (StockUpdateDTO)obj;
		
		List<StockInDTO> checkDto 
			= stockInService.checkDuplicateStockIn(dto.getItemCode(), dto.getImei());
		
		StockInDTO oDto = checkDto.get(0);
		
		boolean validStaff = true;
		if (dto.getStaffId() != null && !"".equalsIgnoreCase(dto.getStaffId())) {
			List<StockInDTO> checkStaffDto
				= stockInService.checkValidStaff(dto.getStaffId(), dto.getStoreCode(), dto.getTeamCode());
		
			if (checkStaffDto == null || checkStaffDto.size() == 0) {
				validStaff = false;
			}
		}
		
		logger.debug("*****Ds Stock Update Validator*****");
		logger.debug("Item code: " + dto.getItemCode());
		logger.debug("Imei: " + dto.getImei());
		logger.debug("Status: " + dto.getStatus());
		logger.debug("Type: " + dto.getType());

		if ("UPDATE".equalsIgnoreCase(dto.getActionType())) {
			
			if (!"27".equalsIgnoreCase(dto.getStatus()) &&
				!"28".equalsIgnoreCase(dto.getStatus()) &&
				!"29".equalsIgnoreCase(dto.getStatus())) {
				errors.rejectValue("status", "updateStatus.invalid");
				return;
			}
			
			if (!validStaff) {
				errors.rejectValue("staffId", "validStaffId.invalid");
			}

			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
			    "stockPool", null, "Stock Pool is required");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
			    "itemCode", "stockItemCode.required");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"imei", "stockSerialNumber.required");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
			    "storeCode", "stockStoreCode.required");
			
			if (!"07".equals(dto.getType())) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				    "teamCode", "stockTeamCode.required");
			}
			
			if ("28".equalsIgnoreCase(dto.getStatus())) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"eventCode", "stockEventCode.required");
			}
			
			if ("29".equalsIgnoreCase(dto.getStatus()) &&
					!("27".equalsIgnoreCase(oDto.getStatus()) 
					|| "28".equalsIgnoreCase(oDto.getStatus()))) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"status", "updateRWHStatus.invalid");
			}
			
			if (dto.getStatus() == null || "NONE".equalsIgnoreCase(dto.getStatus())) {
				errors.rejectValue("status", "stockStatus.required");
			}
		}
	}
}
