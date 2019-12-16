package com.bomwebportal.lts.validator.acq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.acq.LtsAcqPersonalInfoFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.validator.LtsCommonValidator;
import com.bomwebportal.util.Utility;

public class LtsAcqPersonalInfoValidator implements Validator {
	private LtsCommonService ltsCommonService;
		
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	public boolean supports(Class clazz) {
		return LtsAcqPersonalInfoFormDTO.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object command, Errors errors) {
		LtsAcqPersonalInfoFormDTO personalInfo = (LtsAcqPersonalInfoFormDTO) command;
		
		if(personalInfo.getThirdPartyInd() == null)
		{
			errors.rejectValue("thirdPartyInd", "lts.acq.thirdPartyInd.empty");
		}

		if((personalInfo.isMustVerify()||(personalInfo.isChannelDs() && Boolean.FALSE.equals(personalInfo.getThirdPartyInd()))) 
				&& Boolean.valueOf(personalInfo.getVerified()).equals(false))			
		{
			errors.rejectValue("verified", "lts.acq.verified.empty");	
		}
		
		/*if(personalInfo.isCreateNewCust() && ltsCommonService.isNowDrgDownTime()){
			errors.rejectValue("docNum", "lts.acq.notAllow.createNewCust");
		}*/
		
		if(StringUtils.isBlank(personalInfo.getDocumentType())){
			errors.rejectValue("documentType", "lts.invalid.doctype");
		}else if(StringUtils.isNotBlank(personalInfo.getEligibleDocType())
					&& !StringUtils.contains(personalInfo.getEligibleDocType(), personalInfo.getDocumentType())){
			errors.rejectValue("documentType", "lts.acq.personalInfo.eligibleDocTypeNotMatchMsg", 
					new Object[] {StringUtils.replace(personalInfo.getEligibleDocType(), "PASS", "PASSPORT")}, null);
		}
		
		if (LtsConstant.DOC_TYPE_HKBR.equals(personalInfo.getDocumentType()))
		{
			if(personalInfo.isChannelDs()){
				errors.rejectValue("documentType", "lts.invalid.doctype");
			}
			if(personalInfo.getCompanyName().length()>40){
				errors.rejectValue("companyName", "lts.acq.nameBR.invalid");
			}
			if (StringUtils.isBlank(personalInfo.getCompanyName())) {
				errors.rejectValue("companyName", "lts.acq.companyName.required");
			}
		}
		else{  
			if(! StringUtils.isBlank( personalInfo.getTitle())){			  
				if(personalInfo.getTitle().length()+ personalInfo.getFamilyName().length()+personalInfo.getGivenName().length() >40){
					errors.rejectValue("familyName", "lts.acq.name.invalid");
				}
			}
			else { 
				errors.rejectValue("title", "lts.title.required");
			}			
			
			if (StringUtils.isBlank(personalInfo.getFamilyName())) {
				errors.rejectValue("familyName", "lts.acq.familyName.required");
			}
			
			if (StringUtils.isBlank(personalInfo.getGivenName())) {
				errors.rejectValue("givenName", "lts.acq.givenName.required");
			}
			
			if (StringUtils.isBlank(personalInfo.getDocNum())) {
				errors.rejectValue("docNum", "lts.acq.docNum.required");
			}	
			
			if(personalInfo.isChannelDs()){
				if(Boolean.FALSE.equals(personalInfo.getThirdPartyInd())){
					if(StringUtils.isBlank(personalInfo.getDateOfBirth())){
						errors.rejectValue("dateOfBirth", "lts.acq.dob.required");	
					}
				}
				if(Boolean.TRUE.equals(personalInfo.getThirdPartyInd())){
					if(StringUtils.isNotBlank(personalInfo.getWipMsg())){
						errors.rejectValue("wipMsg", "lts.acq.thirdPartyWip");
					}
				}
			}
			
//			String errorValue = validDateOfBirth(personalInfo.getDateOfBirth());
//			if(StringUtils.isNotBlank(errorValue)){
//				errors.rejectValue("dateOfBirth", errorValue);	
//			}			
		}
		
		if(StringUtils.isNotBlank(personalInfo.getContactEmail())){
			if (!LtsCommonValidator.isValidEmail(personalInfo.getContactEmail())) {
				errors.rejectValue("contactEmail", "lts.distributeEmail.invalid");
			}	
			else if(!isValidEmailAddress(personalInfo.getContactEmail())){
				errors.rejectValue("contactEmail", "lts.distributeEmail.invalid");
			}
		}
		
		if(StringUtils.isNotBlank(personalInfo.getMobileNo())){
			if (!ltsCommonService.isMobileNumPrefix(personalInfo.getMobileNo()))  {
				errors.rejectValue("mobileNo", "lts.invalid.contactPhone");
			}
		}
		
		if(StringUtils.isNotBlank(personalInfo.getFixedLineNo())){
			if (!ltsCommonService.isFixLineNumPrefix(personalInfo.getFixedLineNo()))  {
				errors.rejectValue("fixedLineNo", "lts.invalid.fixlinenum");
			}
		}
		
		 // check if valid idDocNum
		if (LtsConstant.DOC_TYPE_HKID.equals(personalInfo.getDocumentType())){
			if (!Utility.validateHKID(personalInfo.getDocNum())){
				errors.rejectValue("docNum", "lts.invalid.hkid");	
			}else if (!Utility.validateHKIDcheckDigit(personalInfo.getDocNum())){
				errors.rejectValue("docNum", "lts.invalid.hkidCheckDigit");	
			}
		}else if (LtsConstant.DOC_TYPE_PASSPORT.equals(personalInfo.getDocumentType())){
			if(personalInfo.getDocNum().length() < 6){
				errors.rejectValue("docNum", "lts.invalid.length");
			}
//			if(!StringUtils.isAlphanumeric(personalInfo.getDocNum())){
//				errors.rejectValue("docNum", "lts.invalid.char");
//			}
			if(!Pattern.matches(LtsConstant.PASSPORT_REGEX, personalInfo.getDocNum())){
				errors.rejectValue("docNum", "lts.invalid.char");
			}
		}else if(LtsConstant.DOC_TYPE_HKBR.equals(personalInfo.getDocumentType())
				&& personalInfo.isCreateNewCust()){
			if (!Utility.validateHKBR(personalInfo.getDocNum())){
				errors.rejectValue("docNum", "lts.invalid.hkbr");	
			}else if (!Utility.validateHKBRcheckDigit(personalInfo.getDocNum())){
				errors.rejectValue("docNum", "lts.invalid.hkbrCheckDigit");	
			}
		}
		
		if (Boolean.TRUE.equals(personalInfo.getThirdPartyInd()) || LtsConstant.DOC_TYPE_HKBR.equals(personalInfo.getDocumentType())) {
			if (StringUtils.isBlank(personalInfo.getThirdPartyTitle())) {
				errors.rejectValue("thirdPartyTitle", "lts.title.required");
			}
			if (StringUtils.isBlank(personalInfo.getThirdPartyGivenName())) {
				errors.rejectValue("thirdPartyGivenName", "lts.acq.givenName.required");
			}
			if (StringUtils.isBlank(personalInfo.getThirdPartyFamilyName())) {
				errors.rejectValue("thirdPartyFamilyName", "lts.acq.familyName.required");
			}
			if(personalInfo.isMustVerify()){
				if (StringUtils.isBlank(personalInfo.getThirdPartyDoctype())) {
					errors.rejectValue("thirdPartyDoctype", "lts.invalid.doctype");
				}
				if (StringUtils.isBlank(personalInfo.getThirdPartyDocId())) {
					errors.rejectValue("thirdPartyDocId", "lts.acq.docNum.required");
				}
				/*if(Boolean.valueOf(personalInfo.isThirdPartyAppIdVerify()).equals(false)) {
					errors.rejectValue("thirdPartyAppIdVerify", "lts.acq.verified.empty");
				}*/
			}
			if(personalInfo.isChannelDs() && Boolean.TRUE.equals(personalInfo.getThirdPartyInd())){
				if (StringUtils.isBlank(personalInfo.getThirdPartyDateOfBirth())) {
					errors.rejectValue("thirdPartyDateOfBirth", "lts.acq.dob.required");
				}
				String rdPtyDoberrorValue = validDateOfBirth(personalInfo.getThirdPartyDateOfBirth());
				if(StringUtils.isNotBlank(rdPtyDoberrorValue)){
					errors.rejectValue("thirdPartyDateOfBirth", rdPtyDoberrorValue);	
				}
				if(Boolean.valueOf(personalInfo.isThirdPartyAppIdVerify()).equals(false)) {
					errors.rejectValue("thirdPartyAppIdVerify", "lts.acq.verified.empty");
				}
			}
			if (StringUtils.isBlank(personalInfo.getThirdPartyRelationship())) {
				errors.rejectValue("thirdPartyRelationship", "lts.invalid.relationship");
			}
			if (StringUtils.isBlank(personalInfo.getThirdPartyContactNum())) {
				errors.rejectValue("thirdPartyContactNum", "lts.contactPhone.required");
			}else{
				if (!ltsCommonService.isFixLineNumPrefix(personalInfo.getThirdPartyContactNum())
						&& !ltsCommonService.isMobileNumPrefix(personalInfo.getThirdPartyContactNum()))  {
					errors.rejectValue("thirdPartyContactNum", "lts.invalid.contactPhone");
				}
			}
		}
		
		if(StringUtils.isNotBlank(personalInfo.getThirdPartyDoctype()) && StringUtils.isNotBlank(personalInfo.getThirdPartyDocId())){
			if (LtsConstant.DOC_TYPE_HKID.equals(personalInfo.getThirdPartyDoctype())){
				if (!Utility.validateHKID(personalInfo.getThirdPartyDocId())){
					errors.rejectValue("thirdPartyDocId", "lts.invalid.hkid");	
				}else if (!Utility.validateHKIDcheckDigit(personalInfo.getThirdPartyDocId())){
					errors.rejectValue("thirdPartyDocId", "lts.invalid.hkidCheckDigit");	
				}
			}else if (LtsConstant.DOC_TYPE_PASSPORT.equals(personalInfo.getThirdPartyDoctype())){
				if(personalInfo.getThirdPartyDocId().length() < 6){
					errors.rejectValue("thirdPartyDocId", "lts.invalid.length");
				}
				if(!StringUtils.isAlphanumeric(personalInfo.getThirdPartyDocId())){
					errors.rejectValue("thirdPartyDocId", "lts.invalid.char");
				}
			}else if(LtsConstant.DOC_TYPE_HKBR.equals(personalInfo.getThirdPartyDoctype())){
				if (!Utility.validateHKBR(personalInfo.getThirdPartyDocId())){
					errors.rejectValue("thirdPartyDocId", "lts.invalid.hkbr");	
				}else if (!Utility.validateHKBRcheckDigit(personalInfo.getThirdPartyDocId())){
					errors.rejectValue("thirdPartyDocId", "lts.invalid.hkbrCheckDigit");	
				}
			}
			
			if (personalInfo.getThirdPartyDoctype().equals(personalInfo.getDocumentType())
					&& personalInfo.getThirdPartyDocId().toUpperCase().equals(personalInfo.getDocNum().toUpperCase()))  {
				errors.rejectValue("thirdPartyDocId", "lts.duplicate.thirdpartyinfo");
			}					
		}
		
		if(personalInfo.isDisplayPICS() && StringUtils.isBlank(personalInfo.getOptInOutInd())){
			errors.rejectValue("optInOutInd", "lts.optInOutInd.required");
		}
		
		
	}
	
	private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}	

	private String validDateOfBirth(String pDob){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			Date dob = sdf.parse(pDob);
			Date now = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dob); 
			c.add(Calendar.YEAR, 18);
			Date date = c.getTime();
			
			if(now.after(date)){
				return "";
			}
			else{
				return "lts.dateOfBirth.invalid";
			}
			
		} catch (ParseException e) {
			return "lts.invalid.date";
		}
	}	
}
