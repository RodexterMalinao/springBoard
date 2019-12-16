package com.bomwebportal.lts.validator;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.form.LtsNowTvServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsNowTvServiceFormDTO.AdditionalTvType;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.util.Utility;

public class LtsNowTvServiceValidator implements Validator {

	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LtsOfferService ltsOfferService;
	
	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public boolean supports(Class clazz) {
		return LtsNowTvServiceFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		
		LtsNowTvServiceFormDTO form = (LtsNowTvServiceFormDTO) command;
		
		if (form.getAdditionalTvType() == null) {
			errors.rejectValue("additionalTvType", "lts.additionalTvType.required");
			return;
		}
		
		switch (form.getAdditionalTvType()) {
		case SD_PAY_CHANNEL:
			List<ItemDetailDTO> nowTvPayItemList = form.getNowTvPayItemList();
			
			if (!ltsOfferService.isItemSelected(nowTvPayItemList, LtsConstant.ITEM_TYPE_NOWTV_PAY)) {
				errors.rejectValue("additionalTvType", "lts.additionalTvType.required");
				return;
			}
			
			if (!validateTvChannelCredit(nowTvPayItemList, form.getPayChannelGroupList())) {
				errors.rejectValue("additionalTvType", "lts.additionalTvType.invalid");
				return;
			}
			
			if (isAdultChannelSelected(form.getPayChannelGroupList()) && !form.isConfirmAvAdultChannel()) {
				errors.rejectValue("confirmAvAdultChannel", "lts.confirmAvAdultChannel.required");
				return;
			}
			
			break;
		case SD_SPECIAL_CHANNEL:
			
			List<ItemDetailDTO> nowTvSpecItemList = form.getNowTvSpecItemList();
			
			if (!ltsOfferService.isItemSelected(nowTvSpecItemList, LtsConstant.ITEM_TYPE_NOWTV_SPEC)) {
				errors.rejectValue("additionalTvType", "lts.additionalTvType.required");
				return;
			}
			if (!validateTvChannelCredit(nowTvSpecItemList, form.getSpecChannelGroupList())) {
				errors.rejectValue("additionalTvType", "lts.additionalTvType.invalid");
				return;
			}
			
			if (isAdultChannelSelected(form.getSpecChannelGroupList()) && !form.isConfirmAvAdultChannel()) {
				errors.rejectValue("confirmAvAdultChannel", "lts.confirmAvAdultChannel.required");
				return;
			}
			
			break;
		case MIRROR:
			
			List<ItemDetailDTO> nowTvMirrorItemList = form.getNowTvMirrorItemList();
			
			if (!ltsOfferService.isItemSelected(nowTvMirrorItemList, LtsConstant.ITEM_TYPE_NOWTV_MIRR)) {
				errors.rejectValue("additionalTvType", "lts.additionalTvType.required");
				return;
			}	
			if (StringUtils.isEmpty(form.getMirrorFsa()) && (form.getMirrorTvFsaList() != null && !form.getMirrorTvFsaList().isEmpty())){
				errors.rejectValue("mirrorFsa", "lts.mirrorFsa.required");
				return;
			}
			break;
		case ADDITIONAL_CHANNELS:
			
			List<ItemDetailDTO> nowTvSportItemList = form.getNowTvSportItemList();
			
			if (!ltsOfferService.isItemSelected(nowTvSportItemList, LtsConstant.ITEM_TYPE_NOWTV_SPT)) {
				errors.rejectValue("additionalTvType", "lts.additionalTvType.required");
				return;
			}
			
			break;			
		default:
			break;
		}
		
		if (AdditionalTvType.NO_ADDITIONAL_TV != form.getAdditionalTvType()) {
			if (form.isConfirmAvAdultChannel() && form.isShowDateOfBirth()) {
				if (StringUtils.isEmpty(form.getDateOfBirth()) ) {
					errors.rejectValue("dateOfBirth", "lts.dateOfBirth.required");
					return;
				}
			}
			
			if (StringUtils.isNotBlank(form.getDateOfBirth()) && !isAgeOver18(form.getDateOfBirth())) {
				errors.rejectValue("dateOfBirth", "lts.dateOfBirth.invalid");
				return;
			}
		}
		
		if (form.getNowTvEmailItemList() != null && !form.getNowTvEmailItemList().isEmpty()) {
			if(ltsOfferService.isItemSelected(form.getNowTvEmailItemList(), LtsConstant.ITEM_TYPE_TV_EMAIL)) {
				if(StringUtils.isEmpty(form.getNowTvEmail())) {
					errors.rejectValue("nowTvEmail", "lts.nowTvEmail.required");
					return;
				}
				if (!Utility.validateEmail(form.getNowTvEmail())) {
					errors.rejectValue("nowTvEmail", "lts.nowTvEmail.invalid");
					return;
				}
			}
		}
	}
	
	
	private boolean isAgeOver18(String dateOfBirth) {
		
		try {
			Date birthDate = DateUtils.parseDate(dateOfBirth, new String[]{"dd/MM/yyyy"});
			Date today = new Date();
			
		    int age = today.getYear() - birthDate.getYear();
		    int month = today.getMonth() - birthDate.getMonth();
		    if (month < 0 || (month == 0 && today.getDate() < birthDate.getDate())) {
		        age--;
		    }  
			return (age >= 18);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e);
		}
		
	}
	
	
	private boolean isAdultChannelSelected(List<ChannelGroupDetailDTO> channelGroupDetailList) {
		if (channelGroupDetailList == null || channelGroupDetailList.isEmpty()) {
			return false;
		}
		
		for (ChannelGroupDetailDTO channelGroupDetail : channelGroupDetailList) {
			ChannelDetailDTO[] channelDetails = channelGroupDetail.getChannelDetails();
			
			if (!ArrayUtils.isEmpty(channelDetails)) {
				for (ChannelDetailDTO channelDetail : channelDetails) {
					if (channelDetail.isSelected() && StringUtils.equals(channelDetail.getIsAdultChannel(), "Y")) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	private boolean validateTvChannelCredit(List<ItemDetailDTO> itemDetailList,
			List<ChannelGroupDetailDTO> channelGroupDetailList) {

		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return false;
		}

		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (itemDetail.isSelected()) {
				if (StringUtils.isNotEmpty(itemDetail.getCredit())) {
					int selectedTvChannelCredit = getSelectedTvChannelCredit(channelGroupDetailList);
					if (Integer.parseInt(itemDetail.getCredit()) != selectedTvChannelCredit) {
						return false;
					}
				}
			}
		}

		return true;
	}
	
	private int getSelectedTvChannelCredit(List<ChannelGroupDetailDTO> channelGroupDetailList) {
		int totalCredit = 0;
		
		if (channelGroupDetailList == null || channelGroupDetailList.isEmpty()) {
			return totalCredit;
		}
		
		for (ChannelGroupDetailDTO channelGroupDetail : channelGroupDetailList) {
			ChannelDetailDTO[] channelDetails = channelGroupDetail.getChannelDetails();
			
			if (ArrayUtils.isEmpty(channelDetails)) {
				continue;
			}
			
			for (ChannelDetailDTO channelDetail : channelDetails) {
				if (channelDetail.isSelected()) {
					totalCredit += Integer.parseInt(channelDetail.getCredit()); 
				}
			}
		}
		
		return totalCredit;
	}
	
	
	
}
