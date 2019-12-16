package com.bomwebportal.lts.validator.acq;

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

public class LtsAcqPremiumSelectionValidator implements Validator{

//	private static String ITEM_SET_TYPE_ATTB_CD_MEMBERSHIP = "MEMBERSHIP";
//	private static String ITEM_SET_TYPE_ATTB_CD_NEW_PCD_BUNDLING = "NEW_PCD";
//	private static String ITEM_SET_TYPE_ATTB_CD_INPUT = "CODE";
	
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
	
	public void validate(Object command, Errors errors) {
		LtsPremiumSelectionFormDTO form = (LtsPremiumSelectionFormDTO)command;
		List<ItemSetDetailDTO> premiumSetList = form.getPremiumSetList();
		
		if (premiumSetList == null || premiumSetList.isEmpty()) {
			return;
		}
		
		if (LtsConstant.ACTION_LTS_GIFT_CODE_SEARCH.equals(form.getAction()) || LtsConstant.ACTION_LTS_PCD_SBID_SEARCH.equals(form.getAction()) ){
			return;
		}
		
		ItemDetailDTO[] itemDetails = null;
		ItemSetAttbDTO[] itemSetAttbs = null;
		
		int selectedPcdItemCount = 0;
		for (int i=0; premiumSetList != null && i < premiumSetList.size(); i++  ) {
			int selectedItemCount = 0;
			
			if(premiumSetList.get(i).getItemSetType().equalsIgnoreCase("PREM-PCD"))
			{				
				itemDetails = premiumSetList.get(i).getItemDetails();
				if (!ArrayUtils.isEmpty(itemDetails)) {
					for (ItemDetailDTO itemDetail : itemDetails) {
						if (itemDetail.isSelected()) {
							selectedItemCount++;
						}
					}
				}
			}
			
			selectedPcdItemCount += selectedItemCount;
		}
		
		for (int i=0; premiumSetList != null && i < premiumSetList.size(); i++  ) {
			itemDetails = premiumSetList.get(i).getItemDetails();
			
			int selectedItemCount = 0;
			if (!ArrayUtils.isEmpty(itemDetails)) {
				for (int j=0; itemDetails != null && j < itemDetails.length; j ++) {
					if (itemDetails[j].isSelected()) {
						selectedItemCount ++;
						ValidationResultDTO result = ltsOfferService.validateItemAttb(Lists.newArrayList(itemDetails[j]));
						if (ValidationResultDTO.Status.INVAILD == result.getStatus()) {
							errors.rejectValue("premiumSetList[" +i+ "].itemDetails[" + j +"].itemId", "DUMMY", result.getMessageList().toString());
						}
					}
				}
				
			}
			if (selectedItemCount > ((ItemSetDetailDTO)premiumSetList.get(i)).getMaxQty()) {
				errors.rejectValue("premiumSetList[" +i+ "].itemSetId", "lts.itemSetMaxQty.invalid");
			}
			if (selectedItemCount < ((ItemSetDetailDTO)premiumSetList.get(i)).getMinQty()) {
				errors.rejectValue("premiumSetList[" +i+ "].itemSetId", "lts.itemSetMinQty.invalid");
			}
			
			if(premiumSetList.get(i).getItemSetType().equalsIgnoreCase("PREM-PCD"))
			{
				if (selectedPcdItemCount > ((ItemSetDetailDTO)premiumSetList.get(i)).getMaxQty()) {
					errors.rejectValue("premiumSetList[" +i+ "].itemSetId", "lts.itemSetMaxQty.invalid");
				}
			}
			
			if (selectedItemCount == 0) {
				continue;
			}

			// Check Membership Number 
			itemSetAttbs = premiumSetList.get(i).getItemSetAttbs();
			for (int j=0; !ArrayUtils.isEmpty(itemSetAttbs) && j < itemSetAttbs.length; j++) {
				if(LtsConstant.ITEM_SET_ATTB_CD_MEMBERSHIP.equals(itemSetAttbs[j].getAttbCode())){
					if (StringUtils.isEmpty(itemSetAttbs[j].getAttbValue())) {
						errors.rejectValue("premiumSetList[" +i+ "].itemSetAttbs[" +j+ "].attbCode", "lts.membershipNum.requird");
					}	
					else if (
							StringUtils.contains(itemSetAttbs[j].getAttbValue(), LtsConstant.CC_MASK) 
							&& (StringUtils.isBlank(itemSetAttbs[j].getAttbValueCcPrefix()) 
									|| StringUtils.isBlank(itemSetAttbs[j].getAttbValueCcSuffix())
									|| itemSetAttbs[j].getAttbValue().length() != 16)) {
						errors.rejectValue("premiumSetList[" +i+ "].itemSetAttbs[" +j+ "].attbCode", "lts.creditCard.invalid");
					}
				}
				else if(LtsConstant.ITEM_SET_ATTB_CD_NEW_PCD_BUNDLING.equals(itemSetAttbs[j].getAttbCode())){
					if (StringUtils.isEmpty(itemSetAttbs[j].getAttbValue())) {
						errors.rejectValue("premiumSetList[" +i+ "].itemSetAttbs[" +j+ "].attbCode", "lts.newPcdBundling.requird");
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

}
