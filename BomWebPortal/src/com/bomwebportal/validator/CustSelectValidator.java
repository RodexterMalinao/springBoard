package com.bomwebportal.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.CustomerInformationDTO;

public class CustSelectValidator implements Validator {
	
	protected final Log logger = LogFactory.getLog(getClass());

	public boolean supports(Class clazz) {
		return clazz.equals(CustomerInformationDTO.class);
	}

	public void validate(Object obj, Errors errors) {
		CustomerInformationDTO dto = (CustomerInformationDTO) obj;
		
		// No empty input
		if (dto.getCustSearchResultList() != null && dto.getCustSearchResultList().size() > 0) {
			if (dto.getSelectedCust() == null) {
				errors.rejectValue("customerInformationError", "selectedCust.required");
				return;
			}
			
			boolean tempHasServInUseInfo = false;
			
			for (int i=0; i<dto.getCustSearchResultList().size(); i++) {
				if (dto.getCustSearchResultList().get(i).getServiceLineDTO().length > 0) {
					tempHasServInUseInfo = true;
				}
			}
			
			// No empty input
			if (tempHasServInUseInfo && dto.getSelectedNum() == null) {
				errors.rejectValue("customerInformationError", "selectedNum.required");
				return;
			}
		}

	}

}
