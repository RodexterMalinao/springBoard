package com.bomwebportal.mob.ccs.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;
import com.bomwebportal.mob.ccs.service.StockUpdateService;

public class StockUpdateValidator implements Validator{
	
	private StockUpdateService stockUpdateService;
	
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
		
		StockUpdateDTO newDto = (StockUpdateDTO)obj;
		
		List<StockUpdateDTO> oldDto = stockUpdateService.getStockUpdateDTObyImei(newDto.getImei());
		
		if (oldDto != null && oldDto.size() > 0) {
			String oldStatus = oldDto.get(0).getStatus();
			String newStatus = newDto.getStatus();
			
			// status
			// 02 FREE --> FREE, RESERVE, DOA, TRANSFER, RETURN
			// Changed in RDM  02 FREE --> FREE, RESERVE
			
			// 18 RESERVE --> FREE, RESERVE, TRANSFER, DOA, RETURN
			// Changed in RDM 18 RESERVE --> FREE, RESERVE
			
			// * 19 ASSIGN --> (ONLY REMARKS CAN BE UPDATED)
			// * 20 SOLD --> (ONLY REMARKS CAN BE UPDATED)
			
			// 21 TRANSFER --> FREE, RESERVE, TRANSFER
			// Changed in RDM 21 TRANSFER --> FREE
			
			// 22 RETURN --> RETURN
			// Changed in RDm 22 RETURN --> RETURN , Free 
			
			// 23 DOA --> RETURN, DOA
			// Changed in RDM 23 DOA --> FAULTY, DOA
			
			// * 25 CANCELLING --> (ONLY REMARKS CAN BE UPDATED)
			
			// Changed in RDM 26 DOA --> RETURN, FREE
			if ("02".equalsIgnoreCase(oldStatus)) {
				if (!("02".equalsIgnoreCase(newStatus) ||
						"18".equalsIgnoreCase(newStatus)||
						"21".equalsIgnoreCase(newStatus)||
						"22".equalsIgnoreCase(newStatus))) {
					errors.rejectValue("status", "updateFREEStatus.invalid");
				}
			} else if ("18".equalsIgnoreCase(oldStatus)) {
				if (!("02".equalsIgnoreCase(newStatus) ||
						"18".equalsIgnoreCase(newStatus) ||
						"21".equalsIgnoreCase(newStatus)||
						"22".equalsIgnoreCase(newStatus)
						)) {
					errors.rejectValue("status", "updateRESERVEStatus.invalid");
				}
			} else if ("19".equalsIgnoreCase(oldStatus)) {
				if (!("19".equalsIgnoreCase(newStatus))) {
					errors.rejectValue("status", "updateASSIGNStatus.invalid");
				}
			} else if ("20".equalsIgnoreCase(oldStatus)) {
				if (!("20".equalsIgnoreCase(newStatus))) {
					errors.rejectValue("status", "updateSOLDStatus.invalid");
				}
			}  else if ("21".equalsIgnoreCase(oldStatus)) {
				if (!("02".equalsIgnoreCase(newStatus) ||
						"21".equalsIgnoreCase(newStatus))) {
					errors.rejectValue("status", "updateTRANSFERStatus.invalid");
				}
			} else if ("22".equalsIgnoreCase(oldStatus)) {
				if (!("02".equalsIgnoreCase(newStatus)||
						"22".equalsIgnoreCase(newStatus))) {
					errors.rejectValue("status", "updateRETURNStatus.invalid");
				}
			} else if ("23".equalsIgnoreCase(oldStatus)) {
				if (!("23".equalsIgnoreCase(newStatus) ||
						"26".equalsIgnoreCase(newStatus))) {
					errors.rejectValue("status", "updateDOAStatus.invalid");
				}
			} else if ("25".equalsIgnoreCase(oldStatus)) {
				if (!("25".equalsIgnoreCase(newStatus))) {
					errors.rejectValue("status", "updateCANCELLINGStatus.invalid");
				}
			} else if ("26".equalsIgnoreCase(oldStatus)) {
				if (!("02".equalsIgnoreCase(newStatus) ||
					  "22".equalsIgnoreCase(newStatus) ||
					  "26".equalsIgnoreCase(newStatus))) {
					errors.rejectValue("status", "updateFAULTYStatus.invalid");
				}
			}
			
			if (!("".equalsIgnoreCase(newDto.getItemCode().trim()) 
					|| newDto.getItemCode() == null)) {
				if (!StringUtils.isAlphanumeric(newDto.getItemCode())) {
					errors.rejectValue("itemCode", "itemCode.pattern");
					return;
				} else if (newDto.getItemCode().trim().length() != 7) {
					errors.rejectValue("itemCode", "itemCodeLength.invalid");
					return;
				}
			}

			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				    "stockPool", null, "Stock Pool is required");
			if (newDto.getType() == null || "NONE".equalsIgnoreCase(newDto.getType())) {
				errors.rejectValue("type", "stockType.required");
				return;
			}
			
			if (newDto.getLocation() == null || "NONE".equalsIgnoreCase(newDto.getLocation())) {
				errors.rejectValue("location", "stockLocation.required");
				return;
			}

			if (newDto.getStatus() == null || "NONE".equalsIgnoreCase(newDto.getStatus())) {
				errors.rejectValue("status", "stockStatus.required");
				return;
			}
		}
	}
}
