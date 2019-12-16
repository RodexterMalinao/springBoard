package com.bomwebportal.lts.validator.acq;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO.DuplexAction;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Action;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Method;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Selection;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.util.Utility;

public class LtsAcqNumSelectionAndPipbValidator implements Validator {
	
	private LtsCommonService ltsCommonService;
	
	public boolean supports(Class clazz) {
		return LtsAcqNumSelectionAndPipbFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {

		LtsAcqNumSelectionAndPipbFormDTO form = (LtsAcqNumSelectionAndPipbFormDTO) command;
		Selection selection = form.getNumSelection();
		Method method = form.getSearchMethod();
		Action action = form.getFormAction();
		
		if (selection == null){
			errors.rejectValue("selectionErrMsg", "lts.acq.selection.required");	
		}
		
		if (selection==Selection.USE_NEW_DN || selection==Selection.USE_NEW_DN_AND_PIPB_DN) {
			switch (action) {
				case SEARCH_BY_FIRST_8_DIGITS:
					if (StringUtils.isBlank(form.getFirst5To8Digits())||form.getFirst5To8Digits().length()<5) {
						errors.rejectValue("searchErrMsg", "lts.acq.first8digits.required");	
					}
				break;			
				case SEARCH_BY_LAST_3_DIGITS:
					if (StringUtils.isBlank(form.getLast1To3Digits())) {
						errors.rejectValue("searchErrMsg", "lts.acq.last3digits.required");	
					}
				break;
				case LOCK_NUMBER:
					if (form.getNumSelectionStringList()!=null && form.getNumSelectionStringList().size()>0 
							&& form.getNumSelectionList().size()==0) {					
						errors.rejectValue("lockNumErrMsg", "lts.acq.lockNum.invalid");
					} else if ((form.getNumSelectionStringList()!=null && form.getNumSelectionStringList().size()>3) 
							|| (form.getNumSelectionStringList()!=null && form.getReservedDnList()!=null
									&& (form.getNumSelectionStringList().size()+form.getReservedDnList().size())>3)) {
						errors.rejectValue("lockNumErrMsg", "lts.acq.lockNum.error");	
					}
				break;
				case SUBMIT:
					if ((method==Method.DN_POOL || method==Method.DN_POOL_THEN_PIPB) 
							&& (form.getReservedDnList()==null || form.getReservedDnList().size()==0)) {
						errors.rejectValue("lockNumErrMsg", "lts.acq.lockNum.required");	
					}
					if ((method==Method.RESERVED_DN || method==Method.RESERVED_DN_THEN_PIPB) 
							&& !ltsCommonService.isFixLineNumPrefix(form.getReservedSrvNum())) {
						errors.rejectValue("reservedSrvNum", "lts.invalid.fixlinenum");	
					}					
				break;
	        }		
		} 
		
		if (selection==Selection.USE_NEW_DN_AND_PIPB_DN || selection==Selection.USE_PIPB_DN) {			
			switch (action) {
				case SUBMIT:					
					if(StringUtils.isNotBlank(form.getPipbInfo().getPipbSrvNum())){
						if (!ltsCommonService.isFixLineNumPrefix(form.getPipbInfo().getPipbSrvNum()))  {
							errors.rejectValue("pipbInfo.pipbSrvNum", "lts.invalid.fixlinenum");
						}
					}
					if (StringUtils.isBlank(form.getPipbInfo().getPortingFrom())) {
						errors.rejectValue("pipbInfo.portingFrom", "lts.acq.portingFrom.required");
					}			
					if (form.getPipbInfo().isPortFromDiffCust()) {			
						if (StringUtils.isBlank(form.getPipbInfo().getDocType())) {
							errors.rejectValue("pipbInfo.docType", "lts.invalid.doctype");
					    } else {
							if (LtsConstant.DOC_TYPE_HKBR.equals(form.getPipbInfo().getDocType())) {					
								if (StringUtils.isBlank(form.getPipbInfo().getCompanyName())) {
									errors.rejectValue("pipbInfo.companyName", "lts.acq.companyName.required");
								}
							} else {
								if (StringUtils.isBlank(form.getPipbInfo().getTitle())) {
									errors.rejectValue("pipbInfo.title", "lts.title.required");
								}
								if (StringUtils.isBlank(form.getPipbInfo().getFamilyName())) {
									errors.rejectValue("pipbInfo.familyName", "lts.acq.familyName.required");
								}					
								if (StringUtils.isBlank(form.getPipbInfo().getGivenName())) {
									errors.rejectValue("pipbInfo.givenName", "lts.acq.givenName.required");
								}												
							}					
							if (StringUtils.isBlank(form.getPipbInfo().getDocNum())) {
								errors.rejectValue("pipbInfo.docNum", "lts.acq.docNum.required");
							} else {	
								if (LtsConstant.DOC_TYPE_HKID.equals(form.getPipbInfo().getDocType())){
									if (!Utility.validateHKID(form.getPipbInfo().getDocNum())){
										errors.rejectValue("pipbInfo.docNum", "lts.invalid.hkid");	
									} else if (!Utility.validateHKIDcheckDigit(form.getPipbInfo().getDocNum())){
										errors.rejectValue("pipbInfo.docNum", "lts.invalid.hkidCheckDigit");	
									}
								}else if (LtsConstant.DOC_TYPE_PASSPORT.equals(form.getPipbInfo().getDocType())){
									if (form.getPipbInfo().getDocNum().length() < 6){
										errors.rejectValue("pipbInfo.docNum", "lts.invalid.length");
									}
									if(!StringUtils.isAlphanumeric(form.getPipbInfo().getDocNum())){
										errors.rejectValue("pipbInfo.docNum", "lts.invalid.char");
									}
								}else if(LtsConstant.DOC_TYPE_HKBR.equals(form.getPipbInfo().getDocType())){
									if (!Utility.validateHKBR(form.getPipbInfo().getDocNum())){
										errors.rejectValue("pipbInfo.docNum", "lts.invalid.hkbr");	
									}else if (!Utility.validateHKBRcheckDigit(form.getPipbInfo().getDocNum())){
										errors.rejectValue("pipbInfo.docNum", "lts.invalid.hkbrCheckDigit");	
									}
								}
							}
						}				
					}	
					
					if (form.getPipbInfo().isReuseExSocket()) {
						if (StringUtils.isBlank(form.getPipbInfo().getReuseSocketType())) {
							errors.rejectValue("pipbInfo.reuseSocketType", "lts.acq.reuseSocketType.required");
						}	
					}					
					
					if (StringUtils.isBlank(form.getPipbInfo().getPortFromDiffAddrStr())) {
						errors.rejectValue("pipbInfo.address.errMsg", "lts.portFromDiffAddrStrInd.required");
					}
					
					if(!StringUtils.isBlank(form.getPipbInfo().getPortFromDiffAddrStr())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getFlat())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getFloor())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getBlock())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getLotNum())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getBuildingName())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getStreetNum())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getStreetName())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getStreetCatgDesc())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getSectionDesc())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getDistrictDesc())
							&& StringUtils.isBlank(form.getPipbInfo().getAddress().getAreaDesc())){						
						errors.rejectValue("pipbInfo.address.errMsg", "lts.address.required");
					}
					
					if (StringUtils.isBlank(form.getPipbInfo().getDuplexIndStr())) {
						errors.rejectValue("pipbInfo.duplexIndStr", "lts.acq.duplexInd.required");
					}
					
					if (form.getPipbInfo().isDuplexInd()) {
						if (StringUtils.isBlank(form.getPipbInfo().getDuplexRadio())) {
							errors.rejectValue("pipbInfo.duplexRadio", "lts.acq.duplexAction.required");
						} else if (selection==Selection.USE_NEW_DN_AND_PIPB_DN 
								&& form.getPipbInfo().getDuplexAction()!=DuplexAction.DISCONNECT
								&& form.getPipbInfo().getDuplexAction()!=DuplexAction.RETAIN) {
							errors.rejectValue("pipbInfo.duplexRadio", "lts.acq.duplexAction.required");
						}
						if ((form.getPipbInfo().getDuplexAction()==DuplexAction.RETAIN
								|| form.getPipbInfo().getDuplexAction()==DuplexAction.PORT_IN_TOGETHER)
								&& StringUtils.isBlank(form.getPipbInfo().getDuplexDn())) {
							errors.rejectValue("pipbInfo.duplexDn", "lts.acq.duplexDn.required");
						}
						if(StringUtils.isNotBlank(form.getPipbInfo().getDuplexDn())){
							if (!ltsCommonService.isFixLineNumPrefix(form.getPipbInfo().getDuplexDn()))  {
								errors.rejectValue("pipbInfo.duplexDn", "lts.invalid.fixlinenum");
							}
						}
					}
					if (method==Method.RESERVED_DN_THEN_PIPB) {
						if (StringUtils.equals(form.getPipbInfo().getPipbSrvNum(),form.getReservedSrvNum())) {
							errors.rejectValue("pipbInfo.pipbSrvNum", "lts.acq.pipbDn.invalid");						
						} else if (form.getPipbInfo().isDuplexInd() && StringUtils.isNotBlank(form.getPipbInfo().getDuplexDn())
								&& StringUtils.equals(form.getReservedSrvNum(),form.getPipbInfo().getDuplexDn())) {
							errors.rejectValue("pipbInfo.duplexDn", "lts.acq.duplexDn.invalid");
						}
					}
					if (method==Method.DN_POOL_THEN_PIPB || method==Method.PIPB_DN) {
						if (form.getPipbInfo().isDuplexInd() && StringUtils.isNotBlank(form.getPipbInfo().getDuplexDn())
								&& StringUtils.equals(form.getPipbInfo().getPipbSrvNum(),form.getPipbInfo().getDuplexDn())) {
							errors.rejectValue("pipbInfo.pipbSrvNum", "lts.acq.pipbDn.invalid2");
						}	
					}
				break;		
			}
		}		
	}

	/**
	 * @return the ltsCommonService
	 */
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	/**
	 * @param ltsCommonService the ltsCommonService to set
	 */
	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

}
