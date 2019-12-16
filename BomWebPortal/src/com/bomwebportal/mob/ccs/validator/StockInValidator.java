package com.bomwebportal.mob.ccs.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ccs.dto.StockInDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.StockInService;
import com.bomwebportal.util.Utility;

public class StockInValidator implements Validator{
	
	private StockInService stockInService;
	private CodeLkupService codeLkupService;
	
	public void setStockInService(StockInService stockInService) {
		this.stockInService = stockInService;
	}
	public StockInService getStockInService() {
		return stockInService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(StockInDTO.class);
	}
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	protected final Log logger = LogFactory.getLog(getClass());
	
	public void validate(Object obj, Errors errors) {
		
		StockInDTO dto = (StockInDTO)obj;
		
		boolean isDuplicate = false;
		int lengthOfImei = 0;
		
		List<StockInDTO> checkDto 
			= stockInService.checkDuplicateStockIn(dto.getItemCode(), dto.getImei());
		
		if (checkDto != null && checkDto.size() > 0) {
			isDuplicate = true;
		}
		
		logger.debug("*****Stock In*****");
		logger.debug("Item code: " + dto.getItemCode());
		logger.debug("Imei: " + dto.getImei());
		logger.debug("Quantity: " + dto.getQuantity());
		logger.debug("Location: " + dto.getLocation());
		logger.debug("Status: " + dto.getStatus());
		logger.debug("Type: " + dto.getType());

		if ("INSERT".equalsIgnoreCase(dto.getActionType())) {
			
			if (!("".equalsIgnoreCase(dto.getItemCode().trim()) 
					|| dto.getItemCode() == null)) {
				if (!StringUtils.isAlphanumeric(dto.getItemCode())) {
					errors.rejectValue("itemCode", "itemCode.pattern");
					return;
				} else if (dto.getItemCode().trim().length() != 7) {
					errors.rejectValue("itemCode", "itemCodeLength.invalid");
					return;
				}
				
				List<StockCatalogDTO> tempList = stockInService.getTypeNModel(dto.getItemCode().trim());
				if (CollectionUtils.isNotEmpty(tempList)) {
					dto.setType(tempList.get(0).getScItemType());
					dto.setModel(tempList.get(0).getScItemDesc());
					dto.setItemCode(tempList.get(0).getScItemCode());
					dto.setMipSimType(tempList.get(0).getMipSimType());
				} else {
					errors.rejectValue("itemCode", "dummy", "Cannot found Item Code in Stock Catalog");
					return;
				}
			}
			
			// retrieve the length of item serial according to insert type
			if (dto.getType() != null) {
				lengthOfImei = stockInService.getItemSerialLengthByTypeId(dto.getType());
			}
			
			// *** Item Serial checking (length + format)
			// 01 HANDSET/ 05 ANS = 15 (Numeric)
			// 02 SIM = 19 (Numeric)
			// 04 TABLET/ 03 GIFT-PC = <= 20 (Alpha Numeric)
			
			if (dto.getImei() != null) {
				if (!("".equalsIgnoreCase(dto.getImei().trim()) 
						|| dto.getImei() == null)) {
					
					// HANDSET/ ANS/ SIM
					if (dto.getType() != null
							&& ("01".equals(dto.getType())
								|| "02".equals(dto.getType())
								|| "05".equals(dto.getType()))) {
						
						if (!StringUtils.isNumeric(dto.getImei())) {
							errors.rejectValue("imei", "dummy", "Please input \"Item Serial\" in numeric format.");
							return;
						
						// Check the length of item serial (imei)
						} else if (dto.getImei().trim().length() != lengthOfImei) {
							errors.rejectValue("imei", "dummy", 
									"The length of \"Serial Number\" should be in "+lengthOfImei+"-digit.");
							return;
						}
					}
					
					if (dto.getType() != null && "02".equals(dto.getType())) {
						if (!dto.getImei().startsWith(dto.getItemCode())) {
							String simType = Utility.checkSimType(dto.getImei());
							if (StringUtils.isBlank(simType) || (!"X".equalsIgnoreCase(dto.getMipSimType()) && !StringUtils.equals(dto.getMipSimType(), simType))) {
								errors.rejectValue("imei", "dummy", 
										"Input SIM ICCID Prefix does not match with selected MIP SIM Type (" + codeLkupService.getCodeDesc("MIP_SIM_TYPE", dto.getMipSimType()) + ")");
							}
						}
					}
					
					// TABLET/ GIFT-PC
					if (dto.getType() != null
							&& ("03".equals(dto.getType())
								|| "04".equals(dto.getType()))) {
						
						if (!StringUtils.isAlphanumeric(dto.getImei())) {
							errors.rejectValue("imei", "dummy", "Please input \"Item Serial\" in alphanumeric format.");
							return;
						
						// Check the length of item serial (imei)
						} else if (!(dto.getImei().trim().length() <= lengthOfImei)) {
							errors.rejectValue("imei", "dummy", 
									"The length of \"Serial Number\" should be within "+lengthOfImei+"-digit.");
							return;
						}
					}
				}
			}
			
			if (dto.getQuantity() != null) {
				if (!("".equalsIgnoreCase(dto.getQuantity().trim()) 
						|| dto.getQuantity() == null)) {
					if (!StringUtils.isNumeric(dto.getQuantity())) {
						errors.rejectValue("quantity", "quantity.pattern");
						return;
					} else if (!(Integer.parseInt(dto.getQuantity().trim()) > 0
								&& Integer.parseInt(dto.getQuantity().trim()) <= 100)) {
						errors.rejectValue("quantity", "quantityRange.invalid");
						return;
					}
				}
			}
			
			if (isDuplicate) {
				errors.rejectValue("itemCode", "duplicateStockIn.invalid");
				return;
			}

			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				    "stockPool", null, "Stock Pool is required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				    "itemCode", "stockItemCode.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				    "batchRef", "stockBatchRef.required");
			if (!("06".equals(dto.getType()))) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				    "imei", "stockSerialNumber.required");
			} else {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"quantity", "stockQuantity.required");
			}
			if (dto.getLocation() == null || "NONE".equalsIgnoreCase(dto.getLocation())) {
				errors.rejectValue("location", "stockLocation.required");
			}
			if (dto.getStatus() == null || "NONE".equalsIgnoreCase(dto.getStatus())) {
				errors.rejectValue("status", "stockStatus.required");
			}
		}

	}

}
