package com.bomwebportal.lts.validator.acq;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO.BillMediaDtl;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.validator.LtsCommonValidator;

public class LtsAcqBillMediaBillLangValidator implements Validator {
	
	private LtsCommonService ltsCommonService;
	private LtsSalesInfoService ltsSalesInfoService;
	
	public boolean supports(Class clazz) {
		return LtsAcqBillMediaBillLangFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsAcqBillMediaBillLangFormDTO form = (LtsAcqBillMediaBillLangFormDTO) command;

		for (int i = 0; i < form.getBillMediaDtlList().size(); i++) {
			BillMediaDtl billMediaInfo = form.getBillMediaDtlList().get(i);
			
			if (form.isShowIguard() && i==0)
			{
				if(billMediaInfo.getCarecashRegisterOption()!=null)
				{
					if (billMediaInfo.getCarecashRegisterOption().equalsIgnoreCase("R"))
					{
						if (StringUtils.isBlank(billMediaInfo.getCarecashEmail())) {
						    errors.rejectValue("billMediaDtlList[" + i + "].carecashEmail", "lts.distributeEmail.required");
						}
						if(StringUtils.isNotBlank(billMediaInfo.getCarecashEmail())){
							if (!LtsCommonValidator.isValidEmail(billMediaInfo.getCarecashEmail())) {
								errors.rejectValue("billMediaDtlList[" + i + "].carecashEmail", "lts.distributeEmail.invalid");
							}	
							else if(!isValidEmailAddress(billMediaInfo.getCarecashEmail())){
								errors.rejectValue("billMediaDtlList[" + i + "].carecashEmail", "lts.distributeEmail.invalid");
							}
						}
						
						if (StringUtils.isBlank(billMediaInfo.getCarecashContactNo())) {
							errors.rejectValue("billMediaDtlList[" + i + "].carecashContactNo", "lts.contactPhone.required");
						}
						if (StringUtils.isNotBlank(billMediaInfo.getCarecashContactNo())) {
							if (!ltsCommonService.isFixLineNumPrefix(billMediaInfo.getCarecashContactNo())
									&& !ltsCommonService.isMobileNumPrefix(billMediaInfo.getCarecashContactNo()))  {
							    errors.rejectValue("billMediaDtlList[" + i + "].carecashContactNo", "lts.invalid.contactPhone");
						    }
						}
					}
				}				
			}
			
			if (billMediaInfo.getCsPortalItem()!=null && 
					billMediaInfo.getCsPortalItem().isSelected() && !billMediaInfo.isCsPortalExist()
					&& billMediaInfo.isDocValid()) {
				
			    if (StringUtils.equals(LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER, 
			    		billMediaInfo.getCsAcctInd())
							|| StringUtils.equals(LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY, 
									billMediaInfo.getCsAcctInd())) {		
			    	
					if (StringUtils.isBlank(billMediaInfo.getCspEmail())) {
					    errors.rejectValue("billMediaDtlList[" + i + "].cspEmail", "lts.acq.cspEmail.required");
					}
					if(StringUtils.isNotBlank(billMediaInfo.getCspEmail())){
						if (!LtsCommonValidator.isValidEmail(billMediaInfo.getCspEmail())) {
							errors.rejectValue("billMediaDtlList[" + i + "].cspEmail", "lts.distributeEmail.invalid");
						}	
						else if(!isValidEmailAddress(billMediaInfo.getCspEmail())){
							errors.rejectValue("billMediaDtlList[" + i + "].cspEmail", "lts.distributeEmail.invalid");
						}
						else if(StringUtils.equals(billMediaInfo.getDummyEmailInd(), "N") && StringUtils.contains(billMediaInfo.getCspEmail(),"@theclub.com.hk")){
							errors.rejectValue("billMediaDtlList[" + i + "].cspEmail", "lts.distributeEmail.invalid");
						}
					}				
					if (StringUtils.isBlank(billMediaInfo.getCspMobile())) {
						errors.rejectValue("billMediaDtlList[" + i + "].cspMobile", "lts.acq.cspMobile.required");
					}
					if (!StringUtils.isBlank(billMediaInfo.getCspMobile())) {
					    if (StringUtils.equals(billMediaInfo.getDummyMobileInd(), "N") && !ltsCommonService.isMobileNumPrefix(billMediaInfo.getCspMobile()))  {
						    errors.rejectValue("billMediaDtlList[" + i + "].cspMobile", "lts.invalid.contactPhone");
					    }
					}
					if(StringUtils.equals(billMediaInfo.getOptInOutInd(), LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_ALL) && !StringUtils.equals(LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY, 
							billMediaInfo.getCsAcctInd())){
						if (StringUtils.isBlank(billMediaInfo.getOptOutReason())){
							errors.rejectValue("billMediaDtlList[" + i + "].optOutReason", "lts.optoutReason.required");
						}
					}
					if (StringUtils.equals(billMediaInfo.getOptOutReason(), LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_REASON_CD_OTHERS) && !StringUtils.equals(LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY, 
							billMediaInfo.getCsAcctInd())){
						if (StringUtils.isBlank(billMediaInfo.getOptOutRemarks())){
							errors.rejectValue("billMediaDtlList[" + i + "].optOutRemarks", "lts.remarks.required");
						}
					}
					
					if (StringUtils.isNotBlank(billMediaInfo.getOptOutRemarks()) && !StringUtils.equals(LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY, 
							billMediaInfo.getCsAcctInd())){
						if(billMediaInfo.getOptOutRemarks().length() > 30){
							errors.rejectValue("billMediaDtlList[" + i + "].optOutRemarks", "lts.remarks.toolong.30");
						}
					}
				}
			    
			    if (StringUtils.equals(LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY, 
									billMediaInfo.getCsAcctInd())) {		
			    	
					if (StringUtils.isBlank(billMediaInfo.getClubEmail())) {
					    errors.rejectValue("billMediaDtlList[" + i + "].clubEmail", "lts.acq.cspEmail.required");
					}
					if(StringUtils.isNotBlank(billMediaInfo.getClubEmail())){
						if (!LtsCommonValidator.isValidEmail(billMediaInfo.getClubEmail())) {
							errors.rejectValue("billMediaDtlList[" + i + "].clubEmail", "lts.distributeEmail.invalid");
						}	
						else if(!isValidEmailAddress(billMediaInfo.getClubEmail())){
							errors.rejectValue("billMediaDtlList[" + i + "].clubEmail", "lts.distributeEmail.invalid");
						}
						else if(StringUtils.equals(billMediaInfo.getDummyEmailInd(), "N") && StringUtils.contains(billMediaInfo.getClubEmail(),"@theclub.com.hk")){
							errors.rejectValue("billMediaDtlList[" + i + "].clubEmail", "lts.distributeEmail.invalid");
						}
					}				
					if (StringUtils.isBlank(billMediaInfo.getClubMobile())) {
						errors.rejectValue("billMediaDtlList[" + i + "].clubMobile", "lts.acq.cspMobile.required");
					}
					if (StringUtils.equals(billMediaInfo.getDummyMobileInd(), "N") && !StringUtils.isBlank(billMediaInfo.getClubMobile())) {
					    if (!ltsCommonService.isMobileNumPrefix(billMediaInfo.getClubMobile()))  {
						    errors.rejectValue("billMediaDtlList[" + i + "].clubMobile", "lts.invalid.contactPhone");
					    }
					}
					if(StringUtils.equals(billMediaInfo.getOptInOutInd(), LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_ALL)){
						if (StringUtils.isBlank(billMediaInfo.getOptOutReason())){
							errors.rejectValue("billMediaDtlList[" + i + "].optOutReason", "lts.optoutReason.required");
						}
					}
					if (StringUtils.equals(billMediaInfo.getOptOutReason(), LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_REASON_CD_OTHERS)){
						if (StringUtils.isBlank(billMediaInfo.getOptOutRemarks())){
							errors.rejectValue("billMediaDtlList[" + i + "].optOutRemarks", "lts.remarks.required");
						}
					}
					
					if (StringUtils.isNotBlank(billMediaInfo.getOptOutRemarks())){
						if(billMediaInfo.getOptOutRemarks().length() > 30){
							errors.rejectValue("billMediaDtlList[" + i + "].optOutRemarks", "lts.remarks.toolong.30");
						}
					}
					
				}
			    
			}			
			
			if (StringUtils.isBlank(billMediaInfo.getSelectedBillItem())) {
				errors.rejectValue("billMediaDtlList[" + i + "].emailBillItem", "lts.acq.billMedia.notselected");
			}
			
			if (StringUtils.isBlank(billMediaInfo.getBillLang())) {
				errors.rejectValue("billMediaDtlList[" + i + "].billLang", "lts.acq.billLang.notselected");
			}
			
			if (billMediaInfo.getEmailBillItem() != null
					&& StringUtils.equals(billMediaInfo.getEmailBillItem().getItemId(), billMediaInfo.getSelectedBillItem())) {
				if (StringUtils.isBlank(billMediaInfo.getBillMediaEmail())) {
					errors.rejectValue("billMediaDtlList[" + i + "].billMediaEmail", "lts.resendAf.resendEmail.required");
				}
				
				if(StringUtils.isNotBlank(billMediaInfo.getBillMediaEmail())){
					if (!LtsCommonValidator.isValidEmail(billMediaInfo.getBillMediaEmail())) {
						errors.rejectValue("billMediaDtlList[" + i + "].billMediaEmail", "lts.distributeEmail.invalid");
					}	
					else if(!isValidEmailAddress(billMediaInfo.getBillMediaEmail())){
						errors.rejectValue("billMediaDtlList[" + i + "].billMediaEmail", "lts.distributeEmail.invalid");
					}
				}

				if (StringUtils.equals(billMediaInfo.getRedemMedia(), "S")) {
					if (!StringUtils.equals(billMediaInfo.getRedemMediaEmail(),
							billMediaInfo.getBillMediaEmail())) {
						errors.rejectValue("billMediaDtlList[" + i + "].redemMediaEmail", "lts.acq.redemEmail.invalid");
					}
				}
			}

			if (StringUtils.equals(billMediaInfo.getRedemMedia(), "S")) {
				if (StringUtils.isBlank(billMediaInfo.getRedemMediaEmail())) {
					errors.rejectValue("billMediaDtlList[" + i + "].redemMediaEmail", "lts.resendAf.resendEmail.required");
				}else if(StringUtils.isNotBlank(billMediaInfo.getRedemMediaEmail())){
					if (!LtsCommonValidator.isValidEmail(billMediaInfo.getRedemMediaEmail())) {
						errors.rejectValue("billMediaDtlList[" + i + "].redemMediaEmail", "lts.distributeEmail.invalid");
					}	
					else if(!isValidEmailAddress(billMediaInfo.getRedemMediaEmail())){
						errors.rejectValue("billMediaDtlList[" + i + "].redemMediaEmail", "lts.distributeEmail.invalid");
					}
				}
			}

			if (StringUtils.equals(billMediaInfo.getRedemMedia(), "M")) {
				ltsCommonService.validateSmsNum(billMediaInfo.getRedemSms(), errors, "billMediaDtlList["+i+"].redemSms");
			}
			
			if(form.isRedemMediaRequired()){
				if (StringUtils.isBlank(billMediaInfo.getRedemMedia())){
					errors.rejectValue("billMediaDtlList[" + i + "].redemMedia", "lts.acq.redemMedia.required");
				}
						
			}

			/* if paper bill ind exist (either waive/charge), waive reason will be blank and disabled, skip validation */
			if(StringUtils.isBlank(billMediaInfo.getCustExistingPaperBillInd())){
				if(StringUtils.equals(billMediaInfo.getSelectedBillItem(), billMediaInfo.getPaperBillItem().getItemId())){
					if(billMediaInfo.getSelectedBillCharging() == null || billMediaInfo.getSelectedBillCharging().isEmpty()){
						errors.rejectValue("billMediaDtlList[" + i + "].selectedBillCharging", "lts.acq.billCharging.required");
					}
				}
				/*need to check waive reason even customer chose email bill*/
				if(StringUtils.equals("W", billMediaInfo.getSelectedBillCharging()) 
						&& StringUtils.isBlank(billMediaInfo.getSelectedWaiveReason())) {
					errors.rejectValue("billMediaDtlList[" + i + "].selectedWaiveReason", "lts.acq.waiveReason.required");
				}else{
					if(LtsConstant.PAPER_BILL_WAIVE_REASON_OTHER.equals(billMediaInfo.getSelectedWaiveReason())){
						if(StringUtils.isNotBlank(billMediaInfo.getPaperBillWaiveOtherStaffId())){
							String category = ltsSalesInfoService.getStaffCategory(billMediaInfo.getPaperBillWaiveOtherStaffId());
							if(category == null){
								errors.rejectValue("billMediaDtlList[" + i + "].paperBillWaiveOtherStaffId", "lts.invalid.staffId");
							}else if(!Arrays.asList(LtsConstant.SALES_ROLES_ALL_MANAGERS).contains(category)){
								errors.rejectValue("billMediaDtlList[" + i + "].paperBillWaiveOtherStaffId", "lts.acq.staff.manager.required");
							}
						}else{
							errors.rejectValue("billMediaDtlList[" + i + "].paperBillWaiveOtherStaffId", "lts.staffId.required");
						}
					}
				}
			}
			
		}

	}
	
	private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}	
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}
}
