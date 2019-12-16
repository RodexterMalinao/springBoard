package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.AmendOrderRecDTO;

public interface AmendRetrieveService {
	
	public abstract AmendOrderRecDTO[] retrieveWQAmendment(String pSbId, String pUser); 

}