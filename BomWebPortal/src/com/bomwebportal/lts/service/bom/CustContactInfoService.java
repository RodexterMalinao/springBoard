package com.bomwebportal.lts.service.bom;

import com.bomwebportal.lts.dto.order.ContactLtsDTO;

public interface CustContactInfoService {
	
	public void updateCustContactInfo(String orderId, ContactLtsDTO contactLtsDTO, String userId, String salesChannel);
}
