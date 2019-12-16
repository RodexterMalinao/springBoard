package com.bomwebportal.mob.ccs.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.StockDTO;

public class StockValidator implements Validator{
	
	public boolean supports(Class clazz) {
		return clazz.equals(StockDTO.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void validate(Object obj, Errors errors) {
		
		StockDTO dto = (StockDTO)obj;
		
		if (!("SEARCH1".equalsIgnoreCase(dto.getActionType()) 
				|| "SEARCH2".equalsIgnoreCase(dto.getActionType()))) {
			return;
		}
		
		if ((dto.getType() == null || "NONE".equalsIgnoreCase(dto.getType()))
				&& (dto.getModel() != null && !"".equalsIgnoreCase(dto.getModel().trim()))) {
			errors.rejectValue("type", "stockType.required");
			return;
		}

		if ((dto.getType() == null || "NONE".equalsIgnoreCase(dto.getType()))
				&& (dto.getModel() == null || "".equalsIgnoreCase(dto.getModel().trim()))
				&& (dto.getSearchItemCode() == null || "".equalsIgnoreCase(dto.getSearchItemCode().trim()))
				&& (dto.getItemSerial() == null || "".equalsIgnoreCase(dto.getItemSerial().trim()))
				&& (dto.getOrderId() == null || "".equalsIgnoreCase(dto.getOrderId().trim()))) {
			errors.rejectValue("type", "stockSearchKey.required");
			return;
		}
		
//		if (!StringUtils.isAlphanumeric(dto.getModel())) {
//			errors.rejectValue("model", "stockSearchModel.pattern");
//			return;
//		}
		
//		if (!StringUtils.isAllUpperCase(dto.getModel())) {
//			errors.rejectValue("model", "stockSearchModelUpCase.pattern");
//			return;
//		}
		
		if (!("".equalsIgnoreCase(dto.getSearchItemCode().trim()) 
				|| dto.getSearchItemCode() == null)) {
			if (!StringUtils.isAlphanumeric(dto.getSearchItemCode())) {
				errors.rejectValue("searchItemCode", "itemCode.pattern");
				return;
			} else if (dto.getSearchItemCode().trim().length() != 7) {
				errors.rejectValue("searchItemCode", "itemCodeLength.invalid");
				return;
			}
//			else if (!StringUtils.isAllUpperCase(dto.getSearchItemCode().trim())) {
//				errors.rejectValue("searchItemCode", "stockSearchItemCdUpCase.pattern");
//				return;
//			}
		}
		
		if ("SEARCH2".equalsIgnoreCase(dto.getActionType())) {
			if ((dto.getSelectedNum() == null 
					|| "".equalsIgnoreCase(dto.getSelectedNum().trim()))
					&& (dto.getSelectedNumList() == null 
					|| dto.getSelectedNumList().size() == 0)) {
				errors.rejectValue("selectedNum", "searchStockModel.required");
				return;
			}
			if (dto.getStatus() == null 
					|| "NONE".equalsIgnoreCase(dto.getStatus().trim())) {
				errors.rejectValue("status", "searchStockStatus.required");
				return;
			}
			
		}

	}

}
