package com.bomwebportal.lts.validator;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetAttbDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.form.LtsPremiumSelectionFormDTO;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.util.LtsConstant;
import com.google.common.collect.Lists;

public class LtsPremiumSelectionValidator implements Validator{

	private LtsOfferService ltsOfferService;
	
	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	
	public boolean supports(Class clazz) {
		return LtsPremiumSelectionFormDTO.class.isAssignableFrom(clazz);
	}

	
	private void validateItemSetList(Object command, Errors errors, List<ItemSetDetailDTO> itemSetList, String variableName) {
		
		for (int i=0; itemSetList != null && i < itemSetList.size(); i++  ) {
			ItemDetailDTO[] itemDetails = itemSetList.get(i).getItemDetails();
			
			int selectedItemCount = 0;
			if (!ArrayUtils.isEmpty(itemDetails)) {
				for (int j=0; itemDetails != null && j < itemDetails.length; j ++) {
					if (itemDetails[j].isSelected()) {
						selectedItemCount ++;
						ValidationResultDTO result = ltsOfferService.validateItemAttb(Lists.newArrayList(itemDetails[j]));
						if (ValidationResultDTO.Status.INVAILD == result.getStatus()) {
							errors.rejectValue(variableName+"[" +i+ "].itemDetails[" + j +"].itemId", "DUMMY", result.getMessageList().toString());
						}
					}
				}
				
			}
			if (selectedItemCount > ((ItemSetDetailDTO)itemSetList.get(i)).getMaxQty()) {
				errors.rejectValue(variableName+"[" +i+ "].itemSetId", "lts.itemSetMaxQty.invalid");
			}
			if (selectedItemCount < ((ItemSetDetailDTO)itemSetList.get(i)).getMinQty()) {
				errors.rejectValue(variableName+"[" +i+ "].itemSetId", "lts.itemSetMinQty.invalid");
			}
			
			if (selectedItemCount == 0) {
				continue;
			}

			// Check Membership Number 
			ItemSetAttbDTO[] itemSetAttbs = itemSetList.get(i).getItemSetAttbs();
			for (int j=0; !ArrayUtils.isEmpty(itemSetAttbs) && j < itemSetAttbs.length; j++) {
				if(LtsConstant.ITEM_SET_ATTB_CD_MEMBERSHIP.equals(itemSetAttbs[j].getAttbCode())){
					if (StringUtils.isEmpty(itemSetAttbs[j].getAttbValue())) {
						errors.rejectValue(variableName+"[" +i+ "].itemSetAttbs[" +j+ "].attbCode", "lts.membershipNum.requird");
					}	
					else if (
							StringUtils.contains(itemSetAttbs[j].getAttbValue(), LtsConstant.CC_MASK) 
							&& (StringUtils.isBlank(itemSetAttbs[j].getAttbValueCcPrefix()) 
									|| StringUtils.isBlank(itemSetAttbs[j].getAttbValueCcSuffix())
									|| itemSetAttbs[j].getAttbValue().length() != 16)) {
						errors.rejectValue(variableName+"[" +i+ "].itemSetAttbs[" +j+ "].attbCode", "lts.creditCard.invalid");
					}
				}
				else if(LtsConstant.ITEM_SET_ATTB_CD_CODE.equals(itemSetAttbs[j].getAttbCode())){
					if (StringUtils.isEmpty(itemSetAttbs[j].getAttbValue())
							|| !StringUtils.isAlphanumeric(itemSetAttbs[j].getAttbValue())
							|| StringUtils.length(itemSetAttbs[j].getAttbValue()) != 9) {
						errors.rejectValue("premiumSetList[" +i+ "].itemSetAttbs[" +j+ "].attbCode", "lts.code.invalid");
					}	
				}
			}
		}
	}
	
	
	public void validate(Object command, Errors errors) {
		LtsPremiumSelectionFormDTO form = (LtsPremiumSelectionFormDTO)command;
		
		if(LtsConstant.ACTION_LTS_GIFT_CODE_SEARCH.equals(form.getAction())){
			return;
		}
		
		validateItemSetList(command, errors, form.getPremiumSetList(), "premiumSetList");
		validateItemSetList(command, errors, form.getGiftSetList(), "giftSetList");
		
	}

}
