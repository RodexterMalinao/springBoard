package com.bomwebportal.lts.validator;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.form.LtsBasketVasDetailFormDTO;

public class LtsBasketVasDetailValidator implements Validator{

	public boolean supports(Class clazz) {
		return LtsBasketVasDetailFormDTO.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object command, Errors errors) {
		
		LtsBasketVasDetailFormDTO form = (LtsBasketVasDetailFormDTO)command;
		List<ItemSetDetailDTO> teamVasSetList = form.getTeamVasSetList();
		List<ItemSetDetailDTO> smartWrtySetList = form.getSmartWrtySetList();
		List<ItemSetDetailDTO> bundleVasSetList = form.getBundleVasSetList();
		List<ItemSetDetailDTO> contItemSetList = form.getContItemSetList();
		
		if ((teamVasSetList == null || teamVasSetList.isEmpty())
				&& (smartWrtySetList == null || smartWrtySetList.isEmpty())
				&& (bundleVasSetList == null || bundleVasSetList.isEmpty())) {
			return;
		}
		
		for (int i=0; teamVasSetList != null && i < teamVasSetList.size(); i++  ) {
			ItemDetailDTO[] itemDetails = null;
			itemDetails = teamVasSetList.get(i).getItemDetails();
			
			int selectedItemCount = 0;
			if (!ArrayUtils.isEmpty(itemDetails)) {
				for (ItemDetailDTO itemDetail : itemDetails) {
					if (itemDetail.isSelected()) {
						selectedItemCount++;
					}
				}
			}
			if (selectedItemCount > ((ItemSetDetailDTO)teamVasSetList.get(i)).getMaxQty()) {
				errors.rejectValue("teamVasSetList[" +i+ "].itemSetId", "lts.itemSetMaxQty.invalid");
			}
			if (selectedItemCount < ((ItemSetDetailDTO)teamVasSetList.get(i)).getMinQty()) {
				errors.rejectValue("teamVasSetList[" +i+ "].itemSetId", "lts.itemSetMinQty.invalid");
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

		for (int i=0; bundleVasSetList != null && i < bundleVasSetList.size(); i++  ) {
			ItemDetailDTO[] itemDetails = null;
			itemDetails = bundleVasSetList.get(i).getItemDetails();
			
			int selectedItemCount = 0;
			if (!ArrayUtils.isEmpty(itemDetails)) {
				for (ItemDetailDTO itemDetail : itemDetails) {
					if (itemDetail.isSelected()) {
						selectedItemCount++;
					}
				}
			}
			if (selectedItemCount > ((ItemSetDetailDTO)bundleVasSetList.get(i)).getMaxQty()) {
				errors.rejectValue("bundleVasSetList[" +i+ "].itemSetId", "lts.itemSetMaxQty.invalid");
			}
			if (selectedItemCount < ((ItemSetDetailDTO)bundleVasSetList.get(i)).getMinQty()) {
				errors.rejectValue("bundleVasSetList[" +i+ "].itemSetId", "lts.itemSetMinQty.invalid");
			}
			
		}
		
		for(int i=0; contItemSetList != null && !contItemSetList.isEmpty() && i < contItemSetList.size(); i++) {
			ItemDetailDTO[] itemDetails = null;
				
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
