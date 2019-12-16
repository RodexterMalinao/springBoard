package com.bomwebportal.lts.validator.acq;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketServiceFormDTO;

public class LtsAcqBasketServiceValidator implements Validator {
	
	public boolean supports(Class clazz) {
		return LtsAcqBasketServiceFormDTO.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object command, Errors errors) {
		LtsAcqBasketServiceFormDTO form = (LtsAcqBasketServiceFormDTO) command;
		
		List<ItemSetDetailDTO> contItemSetList = form.getContItemSetList();
		ItemDetailDTO[] itemDetails = null;
		for(int i=0; contItemSetList != null && !contItemSetList.isEmpty() && i < contItemSetList.size(); i++) {
				
			itemDetails = ((ItemSetDetailDTO)contItemSetList.get(i)).getItemDetails();
			int selectedItemCount = 0;
			if (!ArrayUtils.isEmpty(itemDetails)) {
				for (ItemDetailDTO itemDetail : itemDetails) {
					if (itemDetail.isSelected()) {
						selectedItemCount++;
					}
				}
			}
			if (selectedItemCount > ((ItemSetDetailDTO)contItemSetList.get(i)).getMaxQty()) {
				errors.rejectValue("contItemSetList[" +i+ "].itemSetId", "lts.itemSetMaxQty.invalid");
			}
			if (selectedItemCount < ((ItemSetDetailDTO)contItemSetList.get(i)).getMinQty()) {
				errors.rejectValue("contItemSetList[" +i+ "].itemSetId", "lts.itemSetMinQty.invalid");
			}
		}
	}
		
		


}
