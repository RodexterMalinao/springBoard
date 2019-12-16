package com.bomltsportal.validator;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomltsportal.dto.form.BasketDetailFormDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;

public class BasketDetailValidator implements Validator {

	private static final String ITEM_SET_NAME_CONT = "contItemSetList";
	private static final String ITEM_SET_NAME_PREMIUM = "premiumItemSetList";
	
	
	@Override
	public boolean supports(Class clazz) {
		return BasketDetailFormDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object command, Errors errors) {
		BasketDetailFormDTO form = (BasketDetailFormDTO) command;
		
		validateItemSet(form.getContItemSetList(), errors, ITEM_SET_NAME_CONT);
		validateItemSet(form.getPremiumItemSetList(), errors, ITEM_SET_NAME_PREMIUM);
		validateNowTvItem(form.getContItemSetList(), form.getSelectedNowTvGroupId(), form.getSelectedNowTvChannelId(), errors);
	}
	
	public void validateNowTvItem(List<ItemSetDetailDTO> itemSetList, String groupId, String channelId, Errors errors){
		if(itemSetList != null && itemSetList.size() > 0){
			for (int i=0; itemSetList != null && i < itemSetList.size(); i++  ){
				ItemSetDetailDTO itemSet = itemSetList.get(i);
				for(ItemDetailDTO item :itemSet.getItemDetails()){
					if(BomLtsConstant.ITEM_TYPE_NOWTV_SPEC.equals(item.getItemType())
							|| BomLtsConstant.ITEM_TYPE_NOWTV_PAY.equals(item.getItemType())){
						if(item.isSelected() && (StringUtils.isBlank(groupId) || StringUtils.isBlank(channelId))){
							errors.rejectValue(ITEM_SET_NAME_CONT + "[" +i+ "].itemSetId", "nowTvChannel.empty");
						}
						break;
					}
				}
			}
		}
		
	}
	
	public void validateItemSet(List<ItemSetDetailDTO> itemSetList, Errors errors, String itemSetName) {
		
		if (itemSetList == null || itemSetList.isEmpty()) {
			return;
		}
		
		for (int i=0; itemSetList != null && i < itemSetList.size(); i++  )  {
			
			if (ArrayUtils.isEmpty(itemSetList.get(i).getItemDetails())){
				continue;
			}
			
			int selectedItemCount = 0;
			for (ItemDetailDTO itemDetail : itemSetList.get(i).getItemDetails()) {
				if (itemDetail.isSelected()) {
					selectedItemCount += 1;
				}
			}
			
			if (selectedItemCount > ((ItemSetDetailDTO)itemSetList.get(i)).getMaxQty()) {
				errors.rejectValue(itemSetName + "[" +i+ "].itemSetId", "itemSetMaxQty.invalid");
			}
			if (selectedItemCount < ((ItemSetDetailDTO)itemSetList.get(i)).getMinQty()) {
				errors.rejectValue(itemSetName + "[" +i+ "].itemSetId", "itemSetMinQty.invalid");
			}
			
		}
		
	}
	

}
