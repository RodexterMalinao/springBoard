//created on Oct 8
//Authenticate the validity of staff number and ccc 


package com.bomwebportal.mob.ccs.validator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.CccLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobSponsorshipDTO;
import com.bomwebportal.mob.ccs.service.BCccLookupService;
import org.apache.commons.lang.StringUtils;

public class MobStaffSponsorshipInfoValidator implements Validator{

	private BCccLookupService bCccLookupService;
	
	//setter and getter for bcccLookupService
	public BCccLookupService getbCccLookupService() {
		return bCccLookupService;
	}

	public void setbCccLookupService(BCccLookupService bCccLookupService) {
		this.bCccLookupService = bCccLookupService;
	}



	
	//Alternate Approach  using StringUtils.isAlphanumeric("abcde")
	//import org.apache.commons.lang.StringUtils;
	//method:  isAlphanumeric(String str) and isEmpty(String str)
	//Reference: URL:http://commons.apache.org/proper/commons-lang/javadocs/api-2.6/index.html?org/apache/commons/lang/StringUtils.html

	public boolean supports(Class clazz) {
		
		return clazz.equals(MobSponsorshipDTO.class);
	}

	public void validate(Object obj, Errors errors) {
	    MobSponsorshipDTO sponsorship=(MobSponsorshipDTO)obj;
	    // to validate the staffId
	    if(StringUtils.isEmpty(sponsorship.getStaffId())){
	    	errors.rejectValue("staffId","staffId.required");
	    } else {
	    	if (sponsorship.getStaffId().length() > 8 || !StringUtils.isAlphanumeric(sponsorship.getStaffId())) {
	    		errors.rejectValue("staffId", "staffId.invalid");
	    	}
	    }
	    
	    String ccc = sponsorship.getCcc();
	    if (StringUtils.isEmpty(ccc) || ccc.length() > 4 || !StringUtils.isAlphanumeric(ccc)) {
	    	errors.rejectValue("ccc","ccc.invalid" );
	    } else {
		    CccLkupDTO record=bCccLookupService.getCccLkup(sponsorship.getCcc());
			if(record==null){
				errors.rejectValue("ccc","ccc.invalid");
			}
	    }

	}
    
	
}
