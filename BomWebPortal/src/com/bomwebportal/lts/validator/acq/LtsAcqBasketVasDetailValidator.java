package com.bomwebportal.lts.validator.acq;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketVasDetailFormDTO;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsAcqBasketVasDetailValidator implements Validator {
	
	public boolean supports(Class clazz) {
		return LtsAcqBasketVasDetailFormDTO.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object command, Errors errors) {
		LtsAcqBasketVasDetailFormDTO form = (LtsAcqBasketVasDetailFormDTO) command;

		List<ItemDetailDTO> itemList = form.getIddVasItemList();
	    List<ItemSetDetailDTO> smartWrtySetList = form.getSmartWrtySetList();
	    
		if(itemList != null){
			for(int i=0; i<itemList.size(); i++){
				if(itemList.get(i).isSelected()){
					ItemAttbDTO[] attributes = itemList.get(i).getItemAttbs();
					if(attributes != null){
						for(int j=0; j<attributes.length; j++){
							if(LtsConstant.LOOKUP_IDD_PASSWORD_ATTRIBUTE_DESC.equals(attributes[i].getAttbDesc())){
								if(StringUtils.isNotBlank(attributes[j].getAttbValue()) && !StringUtils.isNumeric(attributes[j].getAttbValue())){
									errors.rejectValue("iddVasItemList[" + i + "].itemAttbs[" + j + "].attbValue", "lts.idd.password.numeric");
								}
								if(StringUtils.isNotBlank(attributes[j].getAttbValue()) && attributes[j].getAttbValue().length() != 4){
									errors.rejectValue("iddVasItemList[" + i + "].itemAttbs[" + j + "].attbValue", "lts.idd.password.invalidLength");
								}
							}
						}
					}
				}
			}
		}
		
		for (int i=0; smartWrtySetList != null && i < smartWrtySetList.size(); i++  ) {
			ItemDetailDTO[] itemDetails = null;
			itemDetails = smartWrtySetList.get(i).getItemDetails();
			
			int selectedItemCount = 0;
			if (!ArrayUtils.isEmpty(itemDetails)) {
				for (ItemDetailDTO itemDetail : itemDetails) {
					if (itemDetail.isSelected()) {
						selectedItemCount++;
					}
				}
			}
			if (selectedItemCount > ((ItemSetDetailDTO)smartWrtySetList.get(i)).getMaxQty()) {
				errors.rejectValue("smartWrtySetList[" +i+ "].itemSetId", "lts.itemSetMaxQty.invalid");
			}
			if (selectedItemCount < ((ItemSetDetailDTO)smartWrtySetList.get(i)).getMinQty()) {
				errors.rejectValue("smartWrtySetList[" +i+ "].itemSetId", "lts.itemSetMinQty.invalid");
			}
			
		}
	}
		
		


}
