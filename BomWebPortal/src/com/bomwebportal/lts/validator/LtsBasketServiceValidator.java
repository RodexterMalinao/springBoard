package com.bomwebportal.lts.validator;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.form.LtsBasketServiceFormDTO;

public class LtsBasketServiceValidator implements Validator {
	
	public boolean supports(Class clazz) {
		return LtsBasketServiceFormDTO.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object command, Errors errors) {
		LtsBasketServiceFormDTO form = (LtsBasketServiceFormDTO) command;
		
		List<ItemSetDetailDTO> contItemSetList = form.getContItemSetList();
		List<ItemSetDetailDTO> deviceRedemSetList = form.getDeviceRedemSetList();
		
		validateItemSetList(contItemSetList, "contItemSetList", errors);
		validateItemSetList(deviceRedemSetList, "deviceRedemSetList", errors);
		
	}
	
	private void validateItemSetList(List<ItemSetDetailDTO> itemSetList, String fieldName, Errors errors){
		ItemDetailDTO[] itemDetails = null;
		for(int i=0; itemSetList != null && !itemSetList.isEmpty() && i < itemSetList.size(); i++) {
			
			itemDetails = ((ItemSetDetailDTO)itemSetList.get(i)).getItemDetails();
			int selectedItemCount = 0;
			if (!ArrayUtils.isEmpty(itemDetails)) {
				for (ItemDetailDTO itemDetail : itemDetails) {
					if (itemDetail.isSelected()) {
						selectedItemCount++;
					}
				}
			}
			if (selectedItemCount > ((ItemSetDetailDTO)itemSetList.get(i)).getMaxQty()) {
				errors.rejectValue(fieldName + "[" +i+ "].itemSetId", "lts.itemSetMaxQty.invalid");
			}
			if (selectedItemCount < ((ItemSetDetailDTO)itemSetList.get(i)).getMinQty()) {
				errors.rejectValue(fieldName + "[" +i+ "].itemSetId", "lts.itemSetMinQty.invalid");
			}
		}
	}
		
		


}
