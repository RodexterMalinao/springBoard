package com.bomwebportal.mob.ccs.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.StockModelDetailsDTO;
import com.bomwebportal.mob.ccs.service.StockModelDetailsService;

public class StockModelDetailsValidator implements Validator{
	
	private StockModelDetailsService stockModelDetailsService;
	
	public void setStockModelDetailsService(StockModelDetailsService stockModelDetailsService) {
		this.stockModelDetailsService = stockModelDetailsService;
	}
	public StockModelDetailsService getStockModelDetailsService() {
		return stockModelDetailsService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(StockModelDetailsDTO.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void validate(Object obj, Errors errors) {
		
		StockModelDetailsDTO dto = (StockModelDetailsDTO)obj;
		
//		List<StockModelDetailsDTO> checkDto 
//			= stockModelDetailsService.getStockCatalogListByItemCode(dto.getItemCode().trim());
//
//		if (checkDto != null && checkDto.size() > 0) {
//			errors.rejectValue("itemCode", "duplicateItemCode.invalid");
//			return;
//		}
		
		if ("SEARCH".equalsIgnoreCase(dto.getActionType())) {
			return;
		}
		
		if ("INSERT".equalsIgnoreCase(dto.getActionType())) {
			if (("01".equals(dto.getType()) || "04".equals(dto.getType()))
					&& ("NONE".equalsIgnoreCase(dto.getSimType()))) {
				errors.rejectValue("type", "stockSimType.required");
			}
			if (!("".equalsIgnoreCase(dto.getItemCode().trim()) 
					|| dto.getItemCode() == null)) {
				if (!StringUtils.isAlphanumeric(dto.getItemCode())) {
					errors.rejectValue("itemCode", "itemCode.pattern");
					return;
				} else if (dto.getItemCode().trim().length() != 7) {
					errors.rejectValue("itemCode", "itemCodeLength.invalid");
					return;
				}
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				    "itemCode", "stockItemCode.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				    "model", "stockModel.required");
			if (dto.getType() == null || "NONE".equalsIgnoreCase(dto.getType())) {
				errors.rejectValue("type", "stockType.required");
			}
			if (dto.getAssignMode() == null || "NONE".equalsIgnoreCase(dto.getAssignMode())) {
				errors.rejectValue("assignMode", "stockAssignMode.required");
			}
		}

	}

}
