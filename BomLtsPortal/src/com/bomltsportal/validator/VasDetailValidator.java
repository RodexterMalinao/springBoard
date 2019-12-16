package com.bomltsportal.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomltsportal.dto.form.VasDetailFormDTO;
import com.bomltsportal.service.ItemDetailService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;

public class VasDetailValidator implements Validator {
	
	ItemDetailService itemDetailService;
	
	@Override
	public boolean supports(Class clazz) {
		return VasDetailFormDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object command, Errors errors) {
		VasDetailFormDTO form = (VasDetailFormDTO) command;
		
//		List<ItemDetailDTO> vasItemList = new  ArrayList<ItemDetailDTO>();
//		if(form.getMoovItems() != null){
//			vasItemList.addAll(form.getMoovItems());
//		}
//		if(form.getNowTvPayItems() != null){
//			vasItemList.addAll(form.getNowTvPayItems());
//		}
//		if(form.getNowTvSpecItems() != null){
//			vasItemList.addAll(form.getNowTvSpecItems());
//		}
//		if(form.getOtherItems() != null){
//			vasItemList.addAll(form.getOtherItems());
//		}
//		
//		ValidationResultDTO result = itemDetailService.validateExclusiveItem(vasItemList, vasItemList, BomLtsConstant.LOCALE_ENG);
//		if(!result.getStatus().equals(Status.VALID)){
//			errors.rejectValue("errorMsg", "vas.error", StringUtils.join(result.getMessageList().toArray(), "\n"));
//		}
		
		if(form.isSelectedMoov()){
			boolean moovItemSeleted = false;
			for(ItemDetailDTO moovItem: form.getMoovItems()){
				if(moovItem.isSelected()){
					moovItemSeleted = true;
					break;
				}
			}
			if(!moovItemSeleted){
				errors.rejectValue("errorMsg", "moov.empty");
			}
		}
		
		
		if(form.isSelectedNowTv()){
			if((StringUtils.isEmpty(form.getSelectedNowTvPayChannelId()) || StringUtils.isEmpty(form.getSelectedNowTvPayGroupId()))
					&& (StringUtils.isEmpty(form.getSelectedNowTvSpecChannelId()) || StringUtils.isEmpty(form.getSelectedNowTvSpecGroupId()))
					){
				errors.rejectValue("errorMsg", "nowTvChannel.empty");
			}
		}
	}
	
	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}

	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}

}
