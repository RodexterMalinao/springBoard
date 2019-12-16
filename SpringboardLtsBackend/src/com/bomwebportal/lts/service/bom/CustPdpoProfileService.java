package com.bomwebportal.lts.service.bom;

import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.profile.CustPdpoProfileDTO;

public interface CustPdpoProfileService {
	
	CustPdpoProfileDTO[] getCustDataPrivacyInfo(String custNum, String systemId);
	
	String updateCustDataPrivayInfo(CustOptOutInfoLtsDTO custOptOutInfo, BillingAddressLtsDTO billAddress, String userId);
	
	String updateCustDataPrivayInfoWithLob(CustOptOutInfoLtsDTO custOptOutInfo, BillingAddressLtsDTO billAddress, String userId, String lob);
}
