package com.bomwebportal.lts.validator;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.util.Utility;

public class LtsModemArrangementValidator implements Validator{

	public boolean supports(Class clazz) {
		return LtsModemArrangementFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		
		LtsModemArrangementFormDTO form = (LtsModemArrangementFormDTO) command;
		switch (form.getFormAction()) {
		case SEARCH_PCD_ORDER:
			if (StringUtils.isBlank(form.getInputPcdSbOrderId())) {
				errors.rejectValue("inputPcdSbOrderId", "lts.sbOrderId.required");
			}
			break;
		case SEARCH_OTHER_FSA:
			if (StringUtils.isBlank(form.getInputOtherFsa()) && StringUtils.isBlank(form.getInputPcdLoginId())) {
				errors.rejectValue("inputOtherFsa", "lts.fsaOrPcdLogin.required");
			}
			else if (StringUtils.isNotBlank(form.getInputOtherFsa()) && !StringUtils.isNumeric(form.getInputOtherFsa())) {
				errors.rejectValue("inputOtherFsa", "lts.fsa.invalid");
			}
			else if (StringUtils.isBlank( form.getInputDocNum())) {
				errors.rejectValue("inputDocNum", "lts.docNum.required");
			}
			else if (StringUtils.isNotBlank(form.getInputDocNum()) && form.getInputDocNum().trim().length() > 30) {
				errors.rejectValue("inputDocNum", "lts.docNum.invalid");
			}
			else if ("HKID".equals(form.getInputDocType()) && !Utility.validateHKID(form.getInputDocNum())) {
				errors.rejectValue("inputDocNum", "lts.docNum.invalid");	
			}
			else if ("HKID".equals(form.getInputDocType()) && !Utility.validateHKIDcheckDigit(form.getInputDocNum())){
				errors.rejectValue("inputDocNum", "lts.docNum.invalid");	
			}					

			break;
		case SUBMIT:
			handleSubmitValidation(form, errors);
		default:
			break;
		}
		
	}

	private void handleSubmitValidation(LtsModemArrangementFormDTO form, Errors errors) {
		switch (form.getModemType()) {
		case SHARE_EX_FSA:

			FsaDetailDTO selectedExistingFsa = getSelectedFsaDetail(form.getExistingFsaList());
			if (selectedExistingFsa == null) {
				errors.rejectValue("existingFsaList[0].selected", "lts.selectFsa.required");
			}
			else if (selectedExistingFsa.isDifferentIa() && !form.isExistingFsaConfirmSameIa() && !form.isExistingFsaER()
					&& selectedExistingFsa.getExistingService() != FsaServiceType.WG) {
				if (selectedExistingFsa.isAllowConfirmSameIa()) {
					errors.rejectValue("existingFsaConfirmSameIa", "lts.confirmSameIa.required");
				}
				else {
					errors.rejectValue("existingFsaConfirmSameIa", "lts.confirmSameIa.invalid");
				}
			}
			
			if(form.isLostModem() && LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER.equals(form.getRentalRouterInd())){
				errors.rejectValue("rentalRouterInd", "lts.modemArrangement.lostModemBrmErrorMsg");
			}
			
			if(selectedExistingFsa != null
					&& (Arrays.asList(LtsConstant.ROUTER_RENTAL_ROUTER_GROUP_CODES).contains(selectedExistingFsa.getRouterGrp())
							|| selectedExistingFsa.isMeshRouterGrp())){
				if(StringUtils.isBlank(form.getRentalRouterInd())){
					errors.rejectValue("rentalRouterInd", "lts.modemArrangement.rentalRouter.error");
				}
			}
			
			break;
		case SHARE_OTHER_FSA:
			
			FsaDetailDTO selectedOtherFsa = getSelectedFsaDetail(form.getOtherFsaList());
			
			if (selectedOtherFsa == null) {
				errors.rejectValue("modemType", "lts.selectFsa.required");
			}
			else if (!form.isOtherFsaConfirmSameCust()) {
				errors.rejectValue("otherFsaConfirmSameCust", "lts.confirmSameCust.required");
			}
			else if (selectedOtherFsa.isDifferentIa() && !form.isOtherFsaConfirmSameIa() && !form.isOtherFsaER()
					&&  selectedOtherFsa.getExistingService() != FsaServiceType.WG) {
				if (selectedOtherFsa.isAllowConfirmSameIa()) {
					errors.rejectValue("otherFsaConfirmSameIa", "lts.confirmSameIa.required");	
				}
				else {
					errors.rejectValue("otherFsaConfirmSameIa", "lts.confirmSameIa.invalid");
				}
			}

			break;
		case SHARE_TV:
		case SHARE_BUNDLE:
			if (StringUtils.isBlank(form.getNowTvServiceType())) {
				errors.rejectValue("nowTvServiceType", "lts.nowTvServiceType.required");
			}
			break;
		default:
			break;
		}
	}
	
	private FsaDetailDTO getSelectedFsaDetail(List<FsaDetailDTO> fsaDetailList) {
		if (fsaDetailList == null || fsaDetailList.isEmpty()) {
			return null;
		}
		
		for (FsaDetailDTO fsaDetail : fsaDetailList) {
			if (fsaDetail.isSelected()) {
				return fsaDetail;
			}
		}
		return null;
	}
	
}
